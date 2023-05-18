package com.library.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class RentDto {
    @NotNull(message = "도서 아이디는 필수 입력값입니다.")
    private Long bookId;

    @Min(value = 1, message = "최소 대여값은 1권입니다.")
    @Max(value = 3, message ="대여는 3권까지 가능합니다." )
    private int count;
}
