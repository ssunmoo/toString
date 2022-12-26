package com.shop.tostring.controller.product;

import com.shop.tostring.domain.dto.product.PViewVo;
import com.shop.tostring.domain.dto.product.PcategoryDto;
import com.shop.tostring.domain.dto.product.ProductDto;
import com.shop.tostring.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class ProductController {

    @Autowired
    public ProductService productService;


    // 제품 등록 페이지
    @GetMapping("/getProduct")
    public String getProduct(){
        return "product/setProduct";
    }

    // 제품 상세페이지
    @GetMapping("/getProductView")
    public String getProductView(){
        return "product/productView";
    }

    // 제품 수정페이지
    @GetMapping("/putProduct")
    public String putProduct(){
        return "product/productUpdate";
    }

    // 장바구니 페이지
    @GetMapping("/getCartList")
    public String getCartList(){
        return "product/cartList";
    }

    // 피아노 카테고리 제품 페이지
    @GetMapping("/getPiano")
    public String getPiano(){
        return "product/piano";
    }

    // 현악기 카테고리 제품 페이지
    @GetMapping("/getString")
    public String getString(){
        return "product/string";
    }


    // -----------------------------------------------------------------

    // 제품 카테고리 주가
    @ResponseBody
    @PostMapping("/setPcategory")
    public boolean setPcategory(@RequestBody PcategoryDto pcategoryDto ){
        return productService.setPcategory( pcategoryDto );
    }

    // 제품 카테고리 출력
    @ResponseBody
    @GetMapping("/pcategoryList")
    public List<PcategoryDto> pcategoryList(){
        return productService.pcategoryList();
    }

    // 제품 등록
    @ResponseBody
    @PostMapping("/setProduct")
    public boolean setProduct( ProductDto productDto ){
        return productService.setProduct( productDto );
    }

    // 제품 출력
    @ResponseBody
    @GetMapping("/getProductList")
    public List<ProductDto> getProductList(){
        return productService.getProductList();
    }

    // 제품 상세페이지
    @ResponseBody
    @GetMapping("/productView")
    public PViewVo getPView(@RequestParam("pno") int pno ){
        return productService.productView( pno );
    }

    // 제품 수정페이지
    @ResponseBody
    @PutMapping("/productUpdate")
    public boolean productUpdate( ProductDto productDto ){
        return productService.productUpdate( productDto );
    }

    // 장바구니 페이지
    @ResponseBody
    @PostMapping("/setCartList")
    public PViewVo setCartList(@RequestParam("pno") int pno){
        return productService.getCartList( pno );
    }

    // 피아노 카테고리 제품 출력
    @ResponseBody
    @PostMapping("/productPiano")
    public List<ProductDto> productPiano( @RequestParam("pcno") int pcno){
        System.out.println(pcno);
        System.out.println("////////////////////");
        return productService.productPiano(pcno);
    }




}
