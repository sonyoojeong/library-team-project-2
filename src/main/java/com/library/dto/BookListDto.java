package com.library.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookListDto {
    private Long id;
    private String bookName;
    private String author;
    private String imageUrl;
    private String bookPublisher;
    private String description;



    @QueryProjection

    public BookListDto(Long id, String bookName, String author,String imageUrl, String bookPublisher,String description) {
        this.id=id;
        this.bookName = bookName;
        this.author = author;
        this.imageUrl = imageUrl;
        this.bookPublisher = bookPublisher;
        this.description = description;
    }


}
