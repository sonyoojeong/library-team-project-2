package com.library.controller;

import com.library.dto.MainBookDto;
import com.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@org.springframework.stereotype.Controller
@RequiredArgsConstructor
public class Controller {
 
private final BookService bookService;
 
 @RequestMapping(value = "/")
    public String main(Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);

        Page<MainBookDto> bookMainDto = bookService.getMainBookPage(pageable);

        model.addAttribute("bookMainDto", bookMainDto);
        return "/common/main";
    }

    @RequestMapping(value = "/search")
    public String search(){
        return "/search";
    }

    @RequestMapping(value = "/map")
    public String map(){
        return "/map";
    }

}
