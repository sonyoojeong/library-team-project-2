package com.library.service;

import com.library.dto.MemberFormDto;
import com.library.dto.MyPageDto;
import com.library.entity.Member;
import com.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);
        if(member == null){
            throw new UsernameNotFoundException(email) ;
        }
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

    public Member saveMember(Member member){
        validateDuplicateMember(member) ;
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember != null && !findMember.getEmail().equals(member.getEmail())) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }


    public List<MemberFormDto> findAll() {
        List<Member> memberList = memberRepository.findAll();
        List<MemberFormDto> memberFormDtoList = new ArrayList<>();

        for(Member member:memberList){
            memberFormDtoList.add(MemberFormDto.toMemberFormDto(member));
/*         MemberFormDto memberFormDto = MemberFormDto.toMemberFormDto(member);
            memberFormDtoList.add(memberFormDto);*/
        }
        return memberFormDtoList;
    }


    public MemberFormDto findById(Long memberId) {
        Optional<Member> optionalMember =memberRepository.findById(memberId);
        if(optionalMember.isPresent()){
/*            Member member =optionalMember.get();
            MemberFormDto memberFormDto = MemberFormDto.toMemberFormDto(member);
            return memberFormDto;*/
            return MemberFormDto.toMemberFormDto(optionalMember.get());
        }else{
            return null;
        }
    }

    public void deleteById(Long memberId) {
        memberRepository.deleteById(memberId);
    }



    public List<MyPageDto> getMyPage(String loggedInMemberEmail){
        return memberRepository.getMyPage(loggedInMemberEmail);
    }


    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}

