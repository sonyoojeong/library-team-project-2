package com.library.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class HopeBookFormDto {
    private Long id ;



    private String aplyDt ;

    @NotNull(message = "휴대폰 번호은(는) 필수 입력 값입니다.")
    private Integer phone ;

    @NotBlank(message = "이메일은(는) 필수 입력 값입니다.")
    private String email ;

    @NotNull(message = "희망도서명은(는) 필수 입력 값입니다.")
    private String bookName ;

    private String author ; // 저자

    private String bookPublisher; // 출판사

    private String publishingDate; // 발행일

    private Integer isbn; // isbn

    private Integer price; // 가격

    private String aplyReason; // 신청사유

}
