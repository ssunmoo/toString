package com.shop.tostring.controller.index;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class indexController {

    // 인덱스 페이지 오픈
    @GetMapping("/")
    public String getIndex(){
        return "index";
    }


}
