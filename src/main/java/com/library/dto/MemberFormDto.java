package com.library.dto;

import com.library.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberFormDto {
    private Long memberId;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min = 8,max = 16,message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요.")
    private String password;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;  //생일

    @NotEmpty(message = "핸드폰 번호는 필수 입력 값입니다.")
    private  String phone;

    @NotEmpty(message = "주소는 필수 입력 값입니다.")
    private String address;


public static MemberFormDto toMemberFormDto(Member member){
    MemberFormDto memberFormDto = new MemberFormDto();
    memberFormDto.setMemberId(member.getMemberId());
    memberFormDto.setName(member.getName());
    memberFormDto.setEmail(member.getEmail());
    memberFormDto.setPassword(member.getPassword());
    memberFormDto.setBirth(member.getBirth());
    memberFormDto.setPhone(memberFormDto.getPhone());
    memberFormDto.setAddress(memberFormDto.getAddress());

    return memberFormDto;
}




}
