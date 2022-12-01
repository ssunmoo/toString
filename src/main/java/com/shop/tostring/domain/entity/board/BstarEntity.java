package com.shop.tostring.domain.entity.board;

import com.shop.tostring.domain.dto.board.BstarDto;
import com.shop.tostring.domain.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table( name = "bstar")
@Builder
@Entity
public class BstarEntity extends BaseEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int bstno;	// 별점 번호
    private int bstar;	// 게시글 별점


    // ---- 연관관계 ----
    // 별점 <-> 게시판
    @ManyToOne
    @ToString.Exclude   // 투스트링 금지
    @JoinColumn(name = "bno") // bno을 fk로 받아오기
    private BoardEntity boardEntity;


    // Dto로 변환
    public BstarDto toBstarDto(){
        return BstarDto.builder()
                .bstno(this.bstno)
                .bstar(this.bstar)
                .build();
    }

}
