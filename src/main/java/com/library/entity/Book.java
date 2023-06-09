package com.library.entity;

import com.library.constant.BookRentalStatus;
import com.library.dto.BookDto;
import com.library.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="books")
@Getter @Setter
public class Book extends SaveBy {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;    //도서관 내부 도서 관리번호

    @Column(name = "isbn")
    private String isbn;        //ISBN

    @Column(nullable = false)
    private String bookName;    //도서명

    private String author;      //저자

    private String bookPublisher;           //출판사

    @Column(nullable = false)
    private Integer stock;             //책 재고

    @Lob    //CLOB(=문자열을 처리할 때 사용), BLOB(=대용량 이미지나 동영상을 처리할 때 사용)
    private String description;

    @Column(nullable = false)
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishingDate;   //발행일


    @Enumerated(EnumType.STRING)
    private BookRentalStatus bookRentalStatus;  //예약 여부

    private Integer price;

    private Integer page;

    private Integer weight;

    private Integer hor;

    private Integer ver;

    private Integer width;

    private String callNumber;

    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
    private List<BookImage> bookImages;

    public void updateBook(BookDto bookDto) {
        this.bookId = bookDto.getBookId();
        this.isbn = bookDto.getIsbn();
        this.bookName = bookDto.getBookName();
        this.author = bookDto.getAuthor();
        this.bookPublisher = bookDto.getBookPublisher();
        this.stock = bookDto.getStock();
        this.publishingDate = bookDto.getPublishingDate();
        this.bookRentalStatus = bookDto.getBookRentalStatus();
        this.price = bookDto.getPrice();
        this.page = bookDto.getPage();
        this.weight = bookDto.getWeight();
        this.hor = bookDto.getHor();
        this.ver = bookDto.getVer();
        this.width = bookDto.getWidth();
        this.callNumber = bookDto.getCallNumber();
        this.description = bookDto.getDescription();

    }

    // 책 대여시 재고 수량 감소
    public void removeStock(int vstock){
        int restStock = this.stock - vstock;

        if(restStock <0){  //재고 부족
            String message = "상품의 재고가 부족합니다. (현재 재고 수량 : " + this.stock + "개)";
            throw new OutOfStockException(message);
        }
        this.stock = restStock;
    }

    //주문 취소시 다시 재고 수량 증가
    public void addStock(int vstock){
        this.stock += vstock;

    }


}
