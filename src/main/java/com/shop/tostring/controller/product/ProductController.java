package com.shop.tostring.controller.product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    // 제품 등록
    @GetMapping("/getProduct")
    public String getProduct(){
        return "product/productSet";
    }

    





}
