package com.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class searchController {
    @RequestMapping(value = "/search")
    public String practice(){
        return "/search";
    }
}

