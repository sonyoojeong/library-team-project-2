package com.library.controller;

import com.library.dto.RentDto;
import com.library.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RentController {
    private final RentService rentService;

    @PostMapping(value = "/rent")
    public @ResponseBody ResponseEntity rent(@RequestBody @Valid RentDto rentDto, BindingResult error, Principal principal){
        if(error.hasErrors()){
            StringBuilder sb = new StringBuilder() ;
            List<FieldError> fieldErrors = error.getFieldErrors();
            for(FieldError ferr : fieldErrors){
                sb.append(ferr.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST) ;
        }
        String email = principal.getName();
        Long rentId = 0L ;

        try{
            rentId = rentService.rent(rentDto, email) ;
        }catch (Exception err){
            err.printStackTrace();
            return new ResponseEntity<String>(err.toString(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(rentId, HttpStatus.OK) ;
    }
}
