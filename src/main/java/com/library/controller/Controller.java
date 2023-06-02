package com.library.controller;

import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
public class Controller {

    @RequestMapping(value = "/")
    public String main(){
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
