package com.library.dto;

import com.library.constant.BookRentalStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookSearchDto {

    private String searchDateType;  //전체기간의 날짜타입
    private BookRentalStatus bookRentalStatus;  //판매상태(전체)

    private String searchBy;  // 상품명
    private String searchQuery; //검색어를 입력해주세요
}
