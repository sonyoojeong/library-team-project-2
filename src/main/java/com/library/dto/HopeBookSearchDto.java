package com.library.dto;

import lombok.Getter;
import lombok.Setter;

// 희망도서목록에 대한 검색(조회) 조건을 가지고 있는 클래스 (필드 검색)
@Getter @Setter
public class HopeBookSearchDto {
    private String srchKind;
    private String srchData;

    // 추가
    private String currentDate;
    private String phone;
    private String email;
    private String bookName;
}
