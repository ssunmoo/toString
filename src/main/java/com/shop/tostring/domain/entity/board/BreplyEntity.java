package com.shop.tostring.domain.entity.board;

import com.shop.tostring.domain.dto.board.BreplyDto;
import com.shop.tostring.domain.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table( name = "breply")
@Builder
@Entity
public class BreplyEntity extends BaseEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int brno;		// 댓글 번호
    private String breply;	// 댓글내용


    // ---- 연관관계 ----
    // 리플 <-> 게시판
//    @ManyToOne
//    @ToString.Exclude   // 투스트링 금지
//    @JoinColumn(name = "bno") // bno을 fk로 받아오기
//    private BoardEntity boardEntity;


    // Dto로 변환
    public BreplyDto toBreplyDto(){
        return BreplyDto.builder()
                .brno(this.brno)
                .breply(this.breply)
                .build();
    }

}
