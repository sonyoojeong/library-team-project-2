package com.library.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "like_books")
@Getter @Setter
public class LikeBook extends SaveBy{

    @Id
    @Column(name = "like_book_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long likeBookId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "like_id")
    private Like like;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public static LikeBook createLikeBook(Like like,Book book){
        LikeBook likeBook = new LikeBook();
        likeBook.setLike(like);
        likeBook.setBook(book);


        return likeBook;
    }

}
