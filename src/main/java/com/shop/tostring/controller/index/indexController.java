package com.shop.tostring.controller.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {

    // 인덱스 페이지 오픈
    @GetMapping("/")
    public String getIndex(){
        return "index";
    }

    // 레이아웃 페이지 오픈
    @GetMapping("/layout")
    public String getLayout(){
        return "layouts/layout1";
    }

}
