package com.shop.tostring.domain.dto.board;

import com.shop.tostring.domain.entity.board.BreplyEntity;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BreplyDto {

    private int brno;		// 댓글 번호
    private String breply;	// 댓글내용
    private int bno;	    // 게시글 번호

    // 엔티티로 변환
    public BreplyEntity toBreplyEntity(){
        return BreplyEntity.builder()
                .brno(this.brno)
                .breply(this.breply)
                .build();
    }
}
