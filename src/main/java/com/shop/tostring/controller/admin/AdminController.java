package com.shop.tostring.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    // 관리자 페이지
    @GetMapping("/admin")
    public String getAdmin(){
        return "admin/admin";
    }

    



}
