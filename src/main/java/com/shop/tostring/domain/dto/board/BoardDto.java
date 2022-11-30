package com.shop.tostring.domain.dto.board;

import com.shop.tostring.domain.entity.board.BoardEntity;
import lombok.*;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class BoardDto {

    private int bno;		    // 게시글 번호
    private String btitle;	    // 제목
    private String bcontent;	// 내용	[ 썸머노트 이용하여 사진/영상 대용량 추가 ]
    private String bdate;		// 작성일 : 기본 값 현재 시스템 날짜
    private int bview;			// 조회수 : 기본 값 0
    private int bcno;			// 게시판 카테고리 번호
    private int mno;			// 작성자


    // 엔티티로 변환
    public BoardEntity toBoardEntity(){
        return BoardEntity.builder()
                .bno(this.bno)
                .btitle(this.btitle)
                .bcontent(this.bcontent)
                .bdate(this.bdate)
                .bview(this.bview)
                .mno(this.mno)
                .build();
    }




}
