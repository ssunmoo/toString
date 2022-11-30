package com.shop.tostring.domain.entity.board;

import com.shop.tostring.domain.dto.board.BoardDto;
import lombok.*;

import javax.persistence.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table( name = "board")
@Builder
public class BoardEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int bno;		    // 게시글 번호
    private String btitle;	    // 제목
    private String bcontent;	// 내용
    private String bdate;		// 작성일 : 기본 값 현재 시스템 날짜
    private int bview;			// 조회수 : 기본 값 0
    private int mno;			// 작성자


    // 연관관계

    // 게시판 <-> 게시판 카테고리
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name="bcno") // bcno을 FK로 받아오기
    private BcategoryEntity bcategoryEntity;

    // Dto로 변환
    public BoardDto toBoardDto(){
        return BoardDto.builder()
                .bno(this.bno)
                .btitle(this.btitle)
                .bcontent(this.bcontent)
                .bdate(this.bdate)
                .bview(this.bview)
                .mno(this.mno)
                .build();
    }

}
