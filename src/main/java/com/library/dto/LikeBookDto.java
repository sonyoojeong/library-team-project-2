package com.library.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class LikeBookDto {

    @NotNull
    private Long BookId;

  @Min(value = 1,message = "수량은 최소 1개 입니다.")
    private int count;
}
