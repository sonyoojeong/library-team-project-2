package com.library.controller;

import org.springframework.web.bind.annotation.RequestMapping;

@Controller
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
