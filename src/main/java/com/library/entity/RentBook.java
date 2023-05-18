package com.library.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "rent_books")
@Getter @Setter
public class RentBook {

    @Id
    @Column(name = "rent_book_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long rentBookId;

    private int count; //대여 권수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rent_id")
    private Rent rent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")  // Ibsn과 연결이 맞는지 ,상품으로 봐야하는게 맞는지
    private Book book;

    public  static RentBook createRentBook(Book book,int count){
        RentBook rentBook = new RentBook();
        rentBook.setBook(book);
        rentBook.setCount(count);

        book.removeStock(count);
        return rentBook ;
    }

}
