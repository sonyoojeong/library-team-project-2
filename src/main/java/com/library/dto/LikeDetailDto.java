package com.library.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LikeDetailDto {
    private Long likeBookId; //관심목록 책을 위한 아이디
    private String bookName; //책이름
    private String imageUrl; // 책 이미지 경로


    public LikeDetailDto(Long likeBookId, String bookName, String imageUrl){
        this.likeBookId = likeBookId;
        this.bookName = bookName;
        this.imageUrl=imageUrl;

    }
}
