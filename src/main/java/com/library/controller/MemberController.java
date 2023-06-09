package com.library.controller;

import com.library.dto.MemberFormDto;
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


/*    @PostMapping(value = "/member/update")
    public String memberUpdate(@Valid MemberFormDto dto, BindingResult error, Model model){

        String whenError = "/member/memberUpdateForm";
        if(error.hasErrors()){
            return whenError;
        }

        try {
            memberService.updateMember(dto);
        }catch (Exception err){
            model.addAttribute("errorMessage","회원 수정 중에 오류가 발생하였습니다.");
            err.printStackTrace();
            return whenError;
        }
        return "redirect:/";


    }*/

/*    @GetMapping(value = "/update")
    public String updateForm(Model model){
        String myEmail = (String) session.getAttribute("Email");
        MemberFormDto memberFormDto = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberFormDto);

        return "member/memberUpdateForm";
    } */

   @GetMapping(value = "/update")
   public String updateForm(){

       return "member/memberUpdateForm";
   }

    @PostMapping(value = "/update/{memberId}")
    public String update(@ModelAttribute MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        memberService.updateMember(memberFormDto,passwordEncoder);
        return "redirect:/members/" + memberFormDto.getMemberId();
    }



    @GetMapping(value = "/mypage")
    public String mypage(Model model, HttpSession session){

        // 세션에서 로그인된 멤버의 이메일 정보 가져오기
        String loggedInMemberEmail = session.getAttribute("loggedInMemberEmail") != null ? session.getAttribute("loggedInMemberEmail").toString() : null;

        List<MyPageDto> myPageDtos = memberRepository.getMyPage(loggedInMemberEmail);

        MyPageDto myPageDto = new MyPageDto();
        myPageDto.calculateTotalCount(myPageDtos);

        int totalCount = myPageDto.getTotalCount();


        model.addAttribute("myPageDto", myPageDtos);

        System.out.println("멤버 : "+myPageDtos);

        System.out.println("총 개수 : " +totalCount);
        model.addAttribute("Dto", new MyPageDto());
        System.out.println("테스트 타나요?");
        return "/member/mypage";
    }

/*    @GetMapping("/mypage")
    public String myPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberEmail = authentication.getName(); // 현재 로그인한 멤버의 이메일

        Member member = memberService.getMemberByEmail(memberEmail);
        List<mmypageDto> myPageDtos = memberService.getMyPageByMemberId(member.getMemberId());

        int totalCount = 0;
        if (!myPageDtos.isEmpty()) {
            mmypageDto mmypageDto = myPageDtos.get(0);
            totalCount = mmypageDto.getTotalCount() ; // 대여 권수의 총합
        }

        model.addAttribute("myPageDto", myPageDtos);
        model.addAttribute("totalCount", totalCount);

        return "/member/mypage";
    }*/

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
    public String deleteById(@PathVariable Long memberId){
        memberService.deleteById(memberId);
        return "redirect:/members/member/";
    }

}
