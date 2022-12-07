package com.shop.tostring.controller.board;

import com.shop.tostring.domain.dto.board.BcategoryDto;
import com.shop.tostring.domain.dto.board.BoardDto;
import com.shop.tostring.domain.dto.board.PageVo;
import com.shop.tostring.service.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    // 서비스 객체 호출
    @Autowired
    private BoardService boardService;

    // ------------------------ [ 페이지 요청 ] ------------------------

    // 카테고리 추가
    @GetMapping("/category")
    public String getBcategory(){
        return "board/setBcategory";
    }

    // 게시글 작성
    @GetMapping("/write")
    public String getWrite(){
        return "board/write";
    }

    // 후기 게시판
    @GetMapping("/rList")
    public String getRlist(){
        return "board/review";
    }

    // 후기 게시판 상세보기
    @GetMapping("/rListview")
    public String rListview(){
        return "board/rListview";
    }

    // 게시글 수정
    @GetMapping("/rListPut")
    public String rListPut(){
        return "board/rListUpdate";
    }

    // 시연영상 게시판
    @GetMapping("/tList")
    public String tList(){
        return "board/testing";
    }

    // 시연영상 게시판 상세보기
    @GetMapping("/tListview")
    public String tListview(){
        return "board/tListview";
    }

    // ------------------------ [ 요청 & 응답 ] ------------------------

    // 카테고리 생성
    @ResponseBody
    @PostMapping("/setBcategory")
    public boolean setBcategory(@RequestBody BcategoryDto bcategoryDto ){
        return boardService.setBcategory( bcategoryDto );
    }

    //카테고리 출력
    @ResponseBody
    @GetMapping("/bcategorylist")
    public List<BcategoryDto> bcategorylist(){
        return boardService.bcategorylist();
    }

    // 게시글 작성
    @ResponseBody
    @PostMapping("/setWrite")
    public boolean setWrite(BoardDto boardDto){
        return boardService.setWrite(boardDto);
    }

    // 첨부파일 다운로드
    @ResponseBody
    @GetMapping("/filedownload")
    public void filedownload( @RequestParam("filename") String filename ){
        boardService.filedownload( filename );
    }

    // 후기 게시판
    @ResponseBody
    @GetMapping("/reviewList")
    public PageVo reviewList(
            @RequestParam("bcno") int bcno, // 카테고리번호
            @RequestParam("page") int page, // 페이지 번호
            @RequestParam("key") String key, // 검색할 필드명 [ 제목, 작성자 등 ]
            @RequestParam("keyWord") String keyWord ){ // 검색할 데이터
        return boardService.reviewList( bcno, page, key, keyWord );
    }

    // 후기 게시판 상세보기
    @ResponseBody
    @GetMapping("/reviewSelect")
    public BoardDto reviewSelect( @RequestParam("bno") int bno ){
        return boardService.reviewSelect( bno );
    }

    // 후기 게시판 게시글 수정
    @ResponseBody
    @PutMapping("/rUpdate")
    public boolean rUpdate( BoardDto boardDto ){
        return boardService.rUpdate( boardDto );
    }

    // 후기 게시판 게시글 삭제
    @ResponseBody
    @DeleteMapping("/rDelete")
    public boolean rDelete( @RequestParam("bno") int bno ){
        return boardService.rDelete( bno );
    }





}
