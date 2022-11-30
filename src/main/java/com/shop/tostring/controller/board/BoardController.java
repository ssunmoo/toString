package com.shop.tostring.controller.board;

import com.shop.tostring.domain.dto.board.BcategoryDto;
import com.shop.tostring.domain.dto.board.BoardDto;
import com.shop.tostring.service.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {

    // 서비스 객체 호출
    @Autowired
    private BoardService boardService;

    // ------------------------ [ 페이지 요청 ] ------------------------

    // 카테고리 추가
    @GetMapping("/category")
    public Resource getBcategory(){
        return new ClassPathResource("templates/board/setBcategory.html");
    }

    // 게시글 작성
    @GetMapping("/write")
    public Resource getWrite(){
        return new ClassPathResource("templates/board/write.html");
    }






    // ------------------------ [ 요청 & 응답 ] ------------------------

    // 카테고리 생성
    @PostMapping("/setBcategory")
    public boolean setBcategory(@RequestBody BcategoryDto bcategoryDto ){
        return boardService.setBcategory( bcategoryDto );
    }

    //카테고리 출력
    @GetMapping("/bcategorylist")
    public List<BcategoryDto> bcategorylist(){
        return boardService.bcategorylist();
    }

    // 게시글 작성
    @PostMapping("/setWrite")
    public boolean setWrite(BoardDto boardDto){
        System.out.println("------");
        System.out.println(boardDto);
        return boardService.setWrite(boardDto);
    }





}
