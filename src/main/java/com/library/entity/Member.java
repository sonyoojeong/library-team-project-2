package com.library.entity;


import com.library.constant.Role;
import com.library.dto.MemberFormDto;
import com.library.dto.MemberUpdateDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter @Setter
@Entity
@Table(name = "members")
public class Member {
    @Id // 기본키
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long memberId;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)  //유니크한 스타일 (동일한 아이디 접근불가)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;  //생일



    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    private int rentCount;  //대여권수

    @Enumerated(EnumType.STRING)
    private Role role;    // 관리자,사용자
    
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rent> rents = new ArrayList<>();


    public static Member createMember(MemberFormDto memberFormDto,
                                      PasswordEncoder passwordEncoder) {
        Member member = new Member();

        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setBirth(memberFormDto.getBirth());
        member.setPhone(memberFormDto.getPhone());
        member.setAddress(memberFormDto.getAddress());

        String password = passwordEncoder.encode(memberFormDto.getPassword()) ;
        member.setPassword(password);
        member.setRole(Role.USER);

        return member;

    }

    public static Member updateMember(@Valid MemberUpdateDto memberFormDto,
                                      PasswordEncoder passwordEncoder,
                                      Member existingMember) {
        existingMember.setName(memberFormDto.getName());
        existingMember.setEmail(memberFormDto.getEmail());
        existingMember.setBirth(memberFormDto.getBirth());
        existingMember.setPhone(memberFormDto.getPhone());
        existingMember.setAddress(memberFormDto.getAddress());

        // 비밀번호 업데이트
        if (memberFormDto.getPassword() != null) {
            String password = passwordEncoder.encode(memberFormDto.getPassword());
            existingMember.setPassword(password);
        }

        // 역할 업데이트 (필요한 경우)
        // existingMember.setRole(Role.USER);

        return existingMember;
    }
}
