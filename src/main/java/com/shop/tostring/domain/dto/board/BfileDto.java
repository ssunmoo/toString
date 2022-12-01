package com.shop.tostring.domain.dto.board;

import com.shop.tostring.domain.entity.board.BfileEntity;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BfileDto {

    private int bfno;	            // 별점 번호
    private String bffile;			// 파일첨부 [ 게시물 1개당 첨부파일 1개 ]
    private int bno;                // 게시글 번호 FK

    // 연관관계


    // 엔티티로 변환
    public BfileEntity toBfileEntity(){
        return BfileEntity.builder()
                .bfno(this.bfno)
                .bffile(this.bffile)
                .build();
    }


}
