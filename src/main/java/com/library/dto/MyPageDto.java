package com.library.dto;

import com.library.constant.RentStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class MyPageDto {
  private String name;
  private int totalCount;
  private String bookName;
  private String isbn;
  private int count;
  private RentStatus rentStatus;
  private LocalDateTime startDate;
  private LocalDateTime returnDate;


  @QueryProjection  // Projection은 테이블의 특정 컬럼정보를 조회하는 동작을 말합니다. (특정한 것만 골라내서 뽑아내는걸 프로젝션, 전체 컬럼에서 필요한 것만 뽑아냄)
  // 해당 조회 결과를 dto에 대입해 줍니다.
  public MyPageDto(String name, int totalCount,String bookName, String isbn,int count, RentStatus rentStatus, LocalDateTime startDate, LocalDateTime returnDate) {
    this.name = name;
    this.totalCount = totalCount;
    this.bookName = bookName;
    this.isbn = isbn;
    this.count = count;
    this.rentStatus = rentStatus;
    this.startDate = startDate;
    this.returnDate = returnDate.plusWeeks(2);
  }

  public MyPageDto() {

  }


  public int getTotalCount() {
    return totalCount;
  }

  public void calculateTotalCount(List<MyPageDto> myPageDtoList) {
    int total = 0;
    for (MyPageDto dto : myPageDtoList) {
      total += dto.getCount();
    }
    this.totalCount = total;
  }
}

  /*  public MyPageDto(Member member, RentBook rentBook) {

    Book book = new Book();  // Book 객체 생성
    Rent rent = new Rent();
// book 객체의 속성 초기화


    rentBook.setBook(book);  // Book 객체를 rentBook의 book 속성에 할당
    rentBook.setRent(rent);
    this.name = member.getName();
    System.out.println("이름은: " +this.name);
    this.bookName = rentBook.getBook().getBookName();
    System.out.println("책이름 : "+bookName);
    this.isbn = rentBook.getBook().getIsbn();
    this.bookRentalStatus = rentBook.getBook().getBookRentalStatus();
    this.count = rentBook.getCount();
    System.out.println("dto start 테스트 ㄴ되낭?");
    this.startDate = rentBook.getRent().getRentDate();
    System.out.println("과연 값은 ?: "+this.startDate);
    this.returnDate = startDate.plusWeeks(2);
    System.out.println("dto 테스트 ㄴ되낭?");
  }*/


