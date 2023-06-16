package com.library.controller;

import com.library.dto.MemberFormDto;
import com.library.dto.MemberUpdateDto;
import com.library.dto.MyPageDto;
import com.library.entity.Member;
import com.library.repository.MemberRepository;
import com.library.repository.RentBookRepository;
import com.library.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/members") //members 일지?
public class MemberController {
    @GetMapping("/sign_up")
    public String memberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "/member/memberInsertForm";
    }

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final RentBookRepository rentBookRepository;


    @PostMapping("/sign_up")
    public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/member/memberInsertForm";
        }
        try {
            Member member = Member.createMember(memberFormDto,passwordEncoder);
            memberService.saveMember(member);

        } catch (IllegalStateException e) {
            model.addAttribute("errMessage", e.getMessage());
            return "/member/memberInsertForm";
        }
        System.out.println("포스트 방식 요청 들어옴");
        return "redirect:/" ;
    }
    @GetMapping(value = "/login")
    public String loginMember(){return "/member/memberLoginForm" ;}

    @GetMapping(value = "login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg","이메일 또는 비밀번호를 입력해 주세요.");
        return "member/memberLoginForm" ;
    }


    @GetMapping("/update")
    public String memberUpdateForm(Model model) {
        model.addAttribute("memberUpdateDto", new MemberUpdateDto());
        return "/member/memberUpdateForm";
    }

    @PostMapping("/update")
    public String updateMember(@Valid MemberUpdateDto memberUpdateDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/member/memberUpdateForm";
        }
        try {
            Member existingMember = memberService.findMemberByEmail(memberUpdateDto.getEmail());
            if (existingMember == null) {
                throw new IllegalStateException("해당 회원을 찾을 수 없습니다.");
            }
            Member updatedMember = Member.updateMember(memberUpdateDto, passwordEncoder, existingMember);
            memberService.saveMember(updatedMember);
        } catch (IllegalStateException e) {
            model.addAttribute("errMessage", e.getMessage());
            return "/member/memberUpdateForm";
        }
        System.out.println("회원 정보 업데이트 요청 들어옴");
        return "redirect:/";
    }



    @GetMapping(value = "/mypage")
    public String mypage(Model model, Principal principal){

        // 로그인 된 user_email 받아오기
        String userEmail = principal.getName(); // 현재 로그인한 사용자의 이메일

        Member member = memberRepository.findByEmail(userEmail); // 이메일로 멤버 조회

        List<MyPageDto> myPageDtos = memberRepository.getMyPage(userEmail);

        MyPageDto myPageDto = new MyPageDto();


        // myPageDtos가 비어있는 경우에도 멤버의 이름을 가져옴
        if (myPageDtos.isEmpty()) {
            myPageDtos.add(new MyPageDto(member.getName())); // 멤버의 이름과 대여권수가 없는 MyPageDto 객체 추가
        }

        model.addAttribute("myPageDto", myPageDtos);




        return "/member/mypage";
    }



    @GetMapping("/member/")
    public String findAll(Model model){
       List<MemberFormDto> memberFormDtoList = memberService.findAll();
        //어떠한 html로 가져갈 데이터가 있다면 model 사용
       model.addAttribute("memberList", memberFormDtoList);
       return "/member/memberList";

    }

    @GetMapping("/member/{memberId}")
    public String findById(@PathVariable Long memberId, Model model){
        MemberFormDto memberFormDto = memberService.findById(memberId);
        model.addAttribute("member",memberFormDto);
        return "/member/memberDetail";
    }

    @GetMapping("/member/delete/{memberId}")
    public String deleteById(@PathVariable Long memberId, Principal principal, HttpServletRequest request) {
        Optional<Member> member = memberRepository.findById(memberId);

        if (principal.getName().equals(member.get().getEmail()) && member.get().getRole() != Role.ADMIN) {
            memberService.deleteById(memberId);
            request.getSession().invalidate(); // 세션 무효화
            return "redirect:/";

        } else {
            memberService.deleteById(memberId);

            return "redirect:/members/member/";

        }
    }
