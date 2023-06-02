package com.library.dto;

import com.library.constant.RentStatus;
import com.library.entity.Rent;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class RentHistDto {
    private Long rentId ; // 대여 아이디
    private String rentDate ; // 대여 일자
    private RentStatus rentStatus ; // 대여 상태 정보

    // 대여 책 리스트
    private List<RentBookDto> rentBookDtoList = new ArrayList<>() ;

    public void addBookDto(RentBookDto rentBookDto){rentBookDtoList.add(rentBookDto);}

    public RentHistDto(Rent rent){
        this.rentId = rent.getRentId() ;

        String pattern = "yyyy-MM-dd HH:mm";
        this.rentDate = rent.getRentDate().format(DateTimeFormatter.ofPattern(pattern));

        this.rentStatus = rent.getRentStatus() ;

    }



}
