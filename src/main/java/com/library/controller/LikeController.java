package com.library.controller;

import com.library.dto.LikeBookDto;
import com.library.dto.LikeDetailDto;
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

}
