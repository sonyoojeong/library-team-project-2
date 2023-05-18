package com.library.dto;

import com.library.constant.BookRentalStatus;
import com.library.entity.Book;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class BookDto {
    private Long bookId;

    private String isbn;        //ISBN

    @NotBlank(message = "도서명은 필수 입력 값입니다.")
    private String bookName;    //도서명


    private String author;  //저자

    private String translator;  //옮긴이

    private String bookPublisher;           //출판사


    @Column(nullable = false)
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishingDate;   //발행일

    @NotNull(message = "재고은(는) 필수 입력 값입니다.")
    private Integer stock; // 재고

    private String description;

    private BookRentalStatus bookRentalStatus;  //예약 여부

    private Integer price;

    private Integer page;

    private Integer weight;

    private Integer hor;

    private Integer ver;

    private Integer width;

    private String callNumber;


    // 상품 1개에 최대 3개까지의 이미지가 들어갈 수 있습니다.
    private List<BookImageDto> bookImageDtoList = new ArrayList<>();


    // 이미지들의 id를 저장할 컬렉션(이미지 수정시 필요함)
    private List<Long> bookImageIds = new ArrayList<>();




    private static ModelMapper modelMapper = new ModelMapper();

    public Book createBook(){
        return modelMapper.map(this,Book.class);
    }

    public static BookDto of(Book book){
        return modelMapper.map(book, BookDto.class);
    }

}
