package com.shop.tostring.domain.dto.board;

import com.shop.tostring.domain.entity.board.BcategoryEntity;
import com.shop.tostring.domain.entity.board.BoardEntity;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BcategoryDto {

    private int bcno;
    private String bcname;

    // 엔티티로 변환
    public BcategoryEntity toBcEntity(){
        return BcategoryEntity.builder()
                .bcno(this.bcno)
                .bcname(this.bcname)
                .build();
    }

}
