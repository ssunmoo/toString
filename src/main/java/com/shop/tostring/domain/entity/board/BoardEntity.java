package com.shop.tostring.domain.entity.board;

import com.shop.tostring.domain.dto.board.BoardDto;
import com.shop.tostring.domain.entity.BaseEntity;
import com.shop.tostring.domain.entity.member.MemberEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@DynamicInsert // 컬럼 디폴트 값 사용시 선언
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table( name = "board")
@Builder
public class BoardEntity extends BaseEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int bno;		    // 게시글 번호
    private String btitle;	    // 제목
    private String bcontent;	// 내용

    @ColumnDefault("0")
    private int bview;			// 조회수 : 기본 값 0
    private int bstar;          // 별점
    private String bfile;       // 첨부파일

    // 게시판 카테고리 번호 fk 받기
    @ManyToOne
    @ToString.Exclude
    @JoinColumn( name = "bcno" )
    private BcategoryEntity bcategoryEntity;

    // 회원번호 fk 받기
    @ManyToOne
    @JoinColumn( name = "mno" )
    @ToString.Exclude
    private MemberEntity memberEntity;



//    // 게시판 <-> 리플
//    @Builder.Default
//    @OneToMany( mappedBy = "boardEntity")
//    private List<BreplyEntity> breplyEntities = new ArrayList<>();


    // Dto로 변환
    public BoardDto toBoardDto(){

        return BoardDto.builder()
                .bno(this.bno)
                .btitle(this.btitle)
                .bcontent(this.bcontent)
                .bview(this.bview)
                .bstar(this.bstar)
                .bview(this.bview)
                .bfilename( this.bfile ) // 파일명만 함께 보내기
                .build();
    }

}
