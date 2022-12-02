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

    // 후기 게시판
    @GetMapping("/rList")
    public Resource rList(){
        return new ClassPathResource("templates/board/review.html");
    }

    // 후기 게시판 상세보기
    @GetMapping("/rListview")
    public Resource rListview(){
        return new ClassPathResource("templates/board/rListview.html");
    }

    // 게시글 수정
    @GetMapping("/rListPut")
    public Resource rListPut(){
        return new ClassPathResource("templates/board/rListUpdate.html");
    }

    // 시연영상 게시판
    @GetMapping("/tList")
    public Resource tList(){
        return new ClassPathResource("templates/board/testing.html");
    }

    // 시연영상 게시판 상세보기
    @GetMapping("/tListview")
    public Resource tListview(){
        return new ClassPathResource("templates/board/tListview.html");
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
        return boardService.setWrite(boardDto);
    }

    // 첨부파일 다운로드
    @GetMapping("/filedownload")
    public void filedownload( @RequestParam("filename") String filename ){
        boardService.filedownload( filename );
    }

    // 후기 게시판
    @GetMapping("/reviewList")
    public List<BoardDto> reviewList(){
        return boardService.reviewList();
    }

    // 후기 게시판 상세보기
    @GetMapping("/reviewSelect")
    public BoardDto reviewSelect( @RequestParam("bno") int bno ){
        return boardService.reviewSelect( bno );
    }

    // 후기 게시판 게시글 수정
    @PutMapping("/rUpdate")
    public boolean rUpdate( BoardDto boardDto ){
        return boardService.rUpdate( boardDto );
    }

    // 후기 게시판 게시글 삭제
    @DeleteMapping("/rDelete")
    public boolean rDelete( @RequestParam("bno") int bno ){
        return boardService.rDelete( bno );
    }

//    // 시연영상 게시판
//    public List<BoardDto> testingList(){
//        return boardService.testingList();
//    }

}
