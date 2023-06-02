package com.library.controller;

import com.library.dto.LikeBookDto;
import com.library.dto.LikeDetailDto;
import com.library.dto.LikeRentDto;
import com.library.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping(value = "/like")
    public @ResponseBody ResponseEntity addLike(@RequestBody @Valid LikeBookDto likeBookDto, BindingResult error, Principal principal){

        System.out.println(likeBookDto);
        System.out.println(error);

        //likeBookDto : 관심목록에 담을 책의 정보
        if(error.hasErrors()){

            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = new ArrayList<>();

            for(FieldError err : fieldErrors){
                sb.append(err.getDefaultMessage());
            }

            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();
        Long likeBookId = 0L;
        try{
            likeBookId = likeService.addLike(likeBookDto,email);
            return new ResponseEntity<Long>(likeBookId,HttpStatus.OK);
        } catch (Exception err) {
            err.printStackTrace();
            return new ResponseEntity<String>(err.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/like")
    public String orderHist(Principal principal, Model model){
        String email = principal.getName();
        List<LikeDetailDto> likeDetailDtoList = likeService.getLikeList(email);

        model.addAttribute("likeBooks",likeDetailDtoList);

        return "like/likeList";
    }

    @DeleteMapping(value = "/likeBook/{likeBookId}")
    public @ResponseBody ResponseEntity deleteLikeBook(@PathVariable("likeBookId") Long likeBookId, Principal principal){  //관심목록에서 특정 상품 삭제
        String email = principal.getName();

        if(likeService.validateLikeBook(likeBookId, email) == false){  //권한 체크
            return new ResponseEntity<String>("삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        likeService.deleteLikeBook(likeBookId);  // 장바구니 상품 삭제하기
        return new ResponseEntity<Long>(likeBookId, HttpStatus.OK);
    }

    @PostMapping(value = "/like/rent")
    public @ResponseBody ResponseEntity likeRentBook(@RequestBody LikeRentDto likeRentDto, Principal principal){
        List<LikeRentDto> likeRentDtoList = likeRentDto.getLikeRentDtoList();

        //주문 상품 선택 여부 확인
        if (likeRentDtoList == null || likeRentDtoList.size()==0){
            return new ResponseEntity<String>("대여할 책을 선택해 주세요.",HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();  //변수를 만들어서 넣는 이유: for문장을 돌때마다 메소드 호출이 발생하면 불필요한 일이 여러번 반복하여 삭제되고 생성되고를 반복함 -> 메모리 낭비가 발생하기 때문에  email 이라는 변수를 만들어서 한번만 생성하기 위해

        for (LikeRentDto dto : likeRentDtoList){
            //주문 권한 체크하기

            boolean bool = likeService.validateLikeBook(dto.getLikeBookId(),email);
            if(bool == false){
                return new ResponseEntity<String>("대여 권한이 없습니다.",HttpStatus.FORBIDDEN);
            }
        }
        //주문 로직을 호출하고 주문 아이디를 반환 받습니다.
        Long rentId = likeService.likeRentBook(likeRentDtoList,email);

        //생성된 주문 번호와 함께 HTTP 응답 코드를 반환해 줍니다.
        return new ResponseEntity<Long>(rentId,HttpStatus.OK);
    }

}
