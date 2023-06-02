package com.library.controller;

import com.library.dto.RentDto;
import com.library.dto.RentHistDto;
import com.library.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

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

    @GetMapping(value = {"/rents", "/rents/{page}"})
    public String rentHist(@PathVariable("page") Optional<Integer> page, Principal principal, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 2);

        Page<RentHistDto> rentHistDtoPage = rentService.getRentList(principal.getName(), pageable) ;

        model.addAttribute("rents", rentHistDtoPage);
        model.addAttribute("page",pageable.getPageNumber());
        model.addAttribute("maxPage",5);

        return "rent/rentHist";
    }

}
