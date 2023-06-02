package com.library.controller;

import com.library.dto.MemberFormDto;
import com.library.dto.MemberUpdateDto;
import com.library.entity.Member;
import com.library.repository.MemberRepository;
import com.library.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/members") //members 일지?
public class MemberController {
    @GetMapping("/join")
    public String memberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "/member/memberForm";
    }

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/join")
    public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/member/memberForm";
        }
        try {
            Member member = Member.createMember(memberFormDto,passwordEncoder);
            memberService.saveMember(member);

        } catch (IllegalStateException e) {
            model.addAttribute("errMessage", e.getMessage());
            return "/member/memberForm";
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

























}
