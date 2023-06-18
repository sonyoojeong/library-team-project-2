package com.library.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.dto.BookVO;
import com.library.dto.HopeBookSearchDto;
import com.library.dto.NaverResultVO;
import com.library.entity.HopeBookAppForm;
import com.library.service.HopeBookAppListService;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/library")
public class HopeBookAppApiController {

    private final HopeBookAppListService hopeBookAppListService;
    // 최초 희망도서API화면 오픈
    @GetMapping(value = {"/bookApi","/bookApi/{page}"})
    public String openHopeBookAppApiPage(HttpServletRequest request, @PathVariable("page") Optional<Integer> page, HopeBookSearchDto searchDto, Model model
            , HttpServletResponse response) throws IOException, JSONException{
        System.out.println("api컨트롤러로 왔나????????????");

        // 현재 페이지 수
        int presentPageNumber = page.isPresent() ? page.get() : 0;
        // 처음에는 startPageNumber = 0 이 된다.

        presentPageNumber = presentPageNumber + 10;

        System.out.println("신청일 :::::::: " + request.getParameter("currentDate"));
        System.out.println("휴대폰 :::::::: " + request.getParameter("phone"));
        System.out.println("이메일 :::::::: " + request.getParameter("email"));
        System.out.println("희망도서명 :::::::: " + request.getParameter("bookName"));

        String currentDate = request.getParameter("currentDate");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String bookName = request.getParameter("bookName");
        if(bookName.equals("") || bookName == null){
            bookName = " ";
        }
        System.out.println("1?");
        // API 호출하기(네이버북스 - 개발API사이트)
        String clientId = "5dGKguWPL4brHDSjeVNZ";
        String clientSecret = "SJYk_d1OSa";
        //String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text;    // JSON 결과
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/book.json")
                .queryParam("query", bookName)
                .queryParam("display", 10)
                .queryParam("start", presentPageNumber)
                .queryParam("sort", "date")
                .encode()
                .build()
                .toUri();
        System.out.println("2?");
        // Spring 요청 제공 클래스
        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .build();
        // Spring 제공 restTemplate
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.exchange(req, String.class);
        System.out.println("3?");
        // JSON 파싱 (Json 문자열을 객체로 만듦, 문서화)
        ObjectMapper om = new ObjectMapper();
        NaverResultVO resultVO = null;
        try {
            resultVO = om.readValue(resp.getBody(), NaverResultVO.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("4?");
        List<BookVO> books =resultVO.getItems();
        System.out.println("books.size():::"+books.size());

        if(books.size() > 0){
            for (int i = 0; i < books.size() ; i++) {
                // 출판년도 꺼내기
                String pubdate = books.get(i).getPubdate();
                if(pubdate != null && !pubdate.equals("")){
                    // "19910102" -> "1991-01-02"
                    pubdate = pubdate.substring(0, 4) + "-" + pubdate.substring(4, 6) + "-" + pubdate.substring(6, 8);
                    // books리스트에 변경된 출판년도 포멧값 넣어주기
                    books.get(i).setPubdate(pubdate);

                    System.out.println("출판년도 포멧 ::::::::::  " + pubdate);
                }

                // 가격 꺼내기
                String price = books.get(i).getDiscount();
                int intPrice = Integer.parseInt(price);

                DecimalFormat formatter = new DecimalFormat("###,###");
                price = formatter.format(intPrice)+"원";

                if(price.equals("0원")){
                    price = "판매불가(단종)";
                }
                books.get(i).setDiscount(price);

                System.out.println("판매금액 포멧:::::::::  " + price);
            }
        }
        int presentListLength = books.size();
        String nextPage = "Y";
        if(presentListLength < 10){
            nextPage = "N";
        }

        // books를 list.html에 출력 -> model 선언
        model.addAttribute("books", books);
        System.out.println("5?");
        model.addAttribute("currentDate", currentDate);
        model.addAttribute("phone", phone);
        model.addAttribute("email", email);
        model.addAttribute("bookName", bookName);
        model.addAttribute("nextPage", nextPage);
        System.out.println("presentPageNumber:::"+presentPageNumber);
        System.out.println("nextPage:::"+nextPage);
        model.addAttribute("presentPageNumber", presentPageNumber);
        System.out.println("6?");
        return "/bookApp/bookApi";

    }

    @GetMapping(value = "/backToBookAppForm")
    public String backToBookAppForm(HttpServletRequest request,  Model model) throws IOException{
        System.out.println("신청일 :::::::: " + request.getParameter("currentDate"));
        System.out.println("휴대폰 :::::::: " + request.getParameter("phone"));
        System.out.println("이메일 :::::::: " + request.getParameter("email"));
        System.out.println("책이름 :::::::: " + request.getParameter("title"));
        System.out.println("저자 :::::::: " + request.getParameter("author"));
        System.out.println("출판사 :::::::: " + request.getParameter("publisher"));
        System.out.println("발행일 :::::::: " + request.getParameter("pubdate"));
        System.out.println("가격 :::::::: " + request.getParameter("discount"));
        System.out.println("isbn :::::::: " + request.getParameter("isbn"));
        System.out.println("link :::::::: " + request.getParameter("link"));
        System.out.println("image :::::::: " + request.getParameter("image"));

        String currentDate = request.getParameter("currentDate");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String publisher = request.getParameter("publisher");
        String pubdate = request.getParameter("pubdate");
        String discount = request.getParameter("discount");
        String isbn = request.getParameter("isbn");
        String link = request.getParameter("link");
        String image = request.getParameter("image");

        HopeBookAppForm dto = new HopeBookAppForm();

        dto.setCurrentDate(currentDate);
        dto.setPhone(phone);
        dto.setEmail(email);
        dto.setBookName(title);
        dto.setAuthor(author);
        dto.setBookPublisher(publisher);
        dto.setPublishingDate(pubdate);
        dto.setPrice(discount);
        dto.setIsbn(isbn);
        dto.setLink(link);
        dto.setImage(image);

        model.addAttribute("hopeBookAppForm", dto);

        // 페이지 이동할 주소 "/
        return "/bookApp/hopeBookApp";
    }
}


/*
    @GetMapping(value = {"/admin/products", "/admin/products/{page}"})
    public String productMange(ProductSearchDto dto, @PathVariable("page") Optional<Integer> page, Model model){
        // 제일 첫번째 페이지 3개만 보여줘
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
        Page<Product> products = productService.getAdminProductPage(dto, pageable);

        model.addAttribute("products", products);
        model.addAttribute("searchDto", dto); // 검색 조건 유지를 위하여
        model.addAttribute("maxPage", 5); // 하단에 보여줄 최대 페이지 번호

        return "product/prList";
    }*/
