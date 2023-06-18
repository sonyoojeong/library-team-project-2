package com.library.dto;

import com.library.constant.RentStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MyPageDto {
  private Long memberId;
  private String name;
  private String bookName;
  private String isbn;
  private int count;
  private RentStatus rentStatus;
  private LocalDateTime startDate;
  private LocalDateTime expectedReturnDate;



  @QueryProjection  // Projection은 테이블의 특정 컬럼정보를 조회하는 동작을 말합니다. (특정한 것만 골라내서 뽑아내는걸 프로젝션, 전체 컬럼에서 필요한 것만 뽑아냄)
  // 해당 조회 결과를 dto에 대입해 줍니다.
  public MyPageDto(Long memberId,String name,String bookName, String isbn,int count, RentStatus rentStatus, LocalDateTime startDate,LocalDateTime expectedReturnDate) {
    this.memberId = memberId;
    this.name = name;
    this.bookName = bookName;
    this.isbn = isbn;
    this.count = count;
    this.rentStatus = rentStatus;
    this.startDate = startDate;
    this.expectedReturnDate = expectedReturnDate.plusWeeks(2);


  }

  public MyPageDto() {

  }

  public MyPageDto(String name) {
    this.name=name;
  }


}



