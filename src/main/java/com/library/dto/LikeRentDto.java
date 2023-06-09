package com.library.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class LikeRentDto {
    private Long likeBookId;

    private List<LikeRentDto> likeRentDtoList;

}
