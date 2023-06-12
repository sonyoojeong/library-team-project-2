package com.library.controller;

import com.library.entity.HopeBookAppForm;
import com.library.service.HopeBookAppFormService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/library")
public class HopeBookAppFormController {

    private final HopeBookAppFormService hopeBookAppFormService;

    // 희망도서신청화면 열기
    @GetMapping(value = "/hopeBookApp")
    public String openHopeBookApp(Model model) {
        System.out.println("신청 들어왔나요??????????????");
        model.addAttribute("hopeBookAppForm", new HopeBookAppForm());
        // 페이지 이동할 주소 "/
        return "/bookApp/hopeBookApp";
    }

    @PostMapping(value = "/insert")
    public String doPostInsert(@RequestParam Map<String, String> parameters, HopeBookAppForm hopeBookAppForm, Model model, HttpServletRequest request) {

      System.out.println("hopeBookAppForm : " + hopeBookAppForm);
      System.out.println("★★★★★★★★★★★★★★★★★★★");
      System.out.println("희망도서폼 투스트링" + hopeBookAppForm.toString());
      System.out.println("신청일 ▶▶▶ " + hopeBookAppForm.getCurrentDate());
      System.out.println("휴대폰 ▶▶▶ " + hopeBookAppForm.getPhone());
      System.out.println("이메일 ▶▶▶ " + hopeBookAppForm.getEmail());
      System.out.println("희망도서명 ▶▶▶ " + hopeBookAppForm.getBookName());
      System.out.println("저자 ▶▶▶ " + hopeBookAppForm.getAuthor());
      System.out.println("출판사 ▶▶▶ " + hopeBookAppForm.getBookPublisher());
      System.out.println("출판연도 ▶▶▶ " + hopeBookAppForm.getPublishingDate());
      System.out.println("isbn ▶▶▶ " + hopeBookAppForm.getIsbn());
      System.out.println("가격 ▶▶▶ " + hopeBookAppForm.getPrice());
      System.out.println("신청사유 ▶▶▶ " + hopeBookAppForm.getAplyReason());
      System.out.println("링크 ▶▶▶ " + hopeBookAppForm.getLink());
      System.out.println("이미지 ▶▶▶ " + hopeBookAppForm.getImage());

      System.out.println("★★★★★★★★★★★★★★★★★★★");

      int cnt = -999;
      cnt = hopeBookAppFormService.Insert(hopeBookAppForm);
      System.out.println("cnt : " + cnt);
       // 인서트 결과가 1이면
       if(cnt==1){

            // 신청 목록화면으로 이동
            return "/bookApp/hopeBookAppSuccess";
       }else {
           // 실패하면 신청화면으로 이동
           return "/bookApp/hopeBookApp";
       }
   }
    // isbn 카운트
    @GetMapping(value = "/selectIsbnCnt")
    public void selectIsbnCnt(@RequestParam Map<String, String> parameters, HopeBookAppForm hopeBookAppForm, Model model
            , HttpServletResponse resp) throws IOException, JSONException {
        String isbn = parameters.get("isbn");
        System.out.println("isbn:::"+ parameters);
        resp.setContentType("text/html;charset=utf-8");

        int selectIsbnCnt = 0;
        selectIsbnCnt = hopeBookAppFormService.getIsbnCount(isbn);

        JSONArray jsonArr = new JSONArray();
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("selectIsbnCnt",selectIsbnCnt);
        jsonArr.put(jsonObj);

        resp.getWriter().print(jsonArr);
    }
}
