package com.shop.tostring.controller.product;

import com.shop.tostring.domain.dto.product.PcategoryDto;
import com.shop.tostring.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    public ProductService productService;



    // 제품 등록 페이지
    @GetMapping("/getProduct")
    public String getProduct(){
        return "product/setProduct";
    }

    


    // -----------------------------------------------------------------

    // 제품 카테고리 주가
    @ResponseBody
    @PostMapping("/setPcategory")
    public boolean setPcategory(@RequestBody PcategoryDto pcategoryDto ){
        return productService.setPcategory( pcategoryDto );
    }

    @ResponseBody
    @GetMapping("/pcategoryList")
    public List<PcategoryDto> pcategoryList(){
        return productService.pcategoryList();
    }



}
