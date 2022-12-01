package com.shop.tostring.domain.entity.board;

import com.shop.tostring.domain.dto.board.BfileDto;
import com.shop.tostring.domain.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table( name = "bfile")
@Builder
@Entity
public class BfileEntity extends BaseEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int bfno;	            // 별점 번호
    private String bffile;			// 파일첨부 [ 게시물 1개당 첨부파일 1개 ]


    // ---- 연관관계 ----
    // 리플 <-> 게시판
    @ManyToOne
    @ToString.Exclude   // 투스트링 금지
    @JoinColumn(name = "bno") // bno을 fk로 받아오기
    private BoardEntity boardEntity;


    // Dto로 변환
    public BfileDto toBfileDto(){
        return BfileDto.builder()
                .bfno(this.bfno)
                .bffile(this.bffile)
                .build();
    }
}
