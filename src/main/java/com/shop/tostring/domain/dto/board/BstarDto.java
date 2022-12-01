package com.shop.tostring.domain.dto.board;

import com.shop.tostring.domain.entity.board.BstarEntity;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BstarDto {

    private int bstno;	// 별점 번호
    private int bstar;	// 게시글 별점
    private int bno;	// 게시글 번호 FK

    // 엔티티로 변환
    public BstarEntity toBstarEntity(){
        return BstarEntity.builder()
                .bstno(this.bstno)
                .bstar(this.bstar)
                .build();
    }

}
