package com.library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
// items에 들어갈 VO


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BookVO {
    private String title;       // ★1.책제목
    private String link;        // 도서정보url
    private String image;       // ★2.섬네일img
    private String author;      // ★3.저자 이름
    private String discount;    // ★4.판매가격(없으면null)
    private String publisher;   // ★5.출판사
    private String pubdate;     // ★6.출간일자
    private String isbn;        // ★7.ISBN
    private String description; // 도서소개글

    @Override
    public String toString() {
        return "BookVO{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", image='" + image + '\'' +
                ", author='" + author + '\'' +
                ", discount='" + discount + '\'' +
                ", publisher='" + publisher + '\'' +
                ", pubdate='" + pubdate + '\'' +
                ", isbn='" + isbn + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
