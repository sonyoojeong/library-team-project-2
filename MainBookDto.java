package com.library.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter @Setter
public class MainBookDto {

    private Long id;

    private String bookName;

    private String author;

    private String imageUrl;

    private String publisher;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishingDate;

    private String description;

    private String categoryId;


    @QueryProjection
    public MainBookDto(Long id, String bookName, String author, String imageUrl, String publisher, LocalDate publishingDate, String description, String categoryId) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.imageUrl = imageUrl;
        this.publisher = publisher;
        this.publishingDate = publishingDate;
        this.description = description;
        this.categoryId = categoryId;
    }
}
