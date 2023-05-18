package com.library.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;


@Getter @Setter @ToString
public class Board {
    private Integer no ; // 게시물 번호

    @NotBlank(message = "작성자을(를) 반드시 입력해 주세요.")
    private String writer ; // 작성자

    @NotBlank(message = "제목을(를) 반드시 입력해 주세요.")
    private String subject; // 제목

    @NotBlank(message = "내용을(를) 반드시 입력해 주세요.")
    private String description; // 내용

}
