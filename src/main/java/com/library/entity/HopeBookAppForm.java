package com.library.entity;

import com.library.dto.HopeBookFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Getter @Setter @ToString
public class HopeBookAppForm {
    private int no;                 // 단순게시글순번
    private int hopeBookAppSn;      // 희망도서신청번호
    private String memberId;		// 멤버아이디

    private String currentDate ;    // 현재 날짜


    // @NotBlank(message = "신청일을(를) 반드시 입력해 주세요.")
    private String aplyDt;			// 신청일

    // @NotBlank(message = "이메일을(를) 반드시 입력해 주세요.")
    private String email;           // 이메일

    // @NotBlank(message = "휴대폰번호을(를) 반드시 입력해 주세요.") 0이 나와서 String으로 바꿔줌
    private String phone;              // 휴대폰

    // @NotBlank(message = "희망도서명을(를) 반드시 입력해 주세요.")
    private String bookName;		// 희망도서명

    private String bookPublisher;   // 출판사
    private String author;		    // 저자
    private String price;              // 가격
    private String publishingDate;  // 발행일

    // @NotBlank(message = "ISBN을(를) 반드시 입력해 주세요.")
    private String isbn;            // isbn

    private String aplyStts;	    // 신청상태(1:신청, 2:입고대기 3:반려 4:입고완료)
    private String aplySttsNm;       // 신청상태명
    private String aplyReason;       // 신청사유
    private String reviewReason;     // 검토사유
    private String link;             // img링크
    private String image;             // img
    private String rvwOpnn;             // 검토의견

    // currentDate - getter
    public String getCurrentDate(){
      return  currentDate;
    };
    //  currentDate - setter
    public void setCurrentDate(String currentDate){
      this.currentDate = currentDate;
    };


    private static ModelMapper modelMapper = new ModelMapper();
    public static HopeBookFormDto of(HopeBookAppForm HopeBookAppForm){
        return modelMapper.map(HopeBookAppForm, HopeBookFormDto.class) ;
    }
}
