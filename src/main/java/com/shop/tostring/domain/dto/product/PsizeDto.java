package com.shop.tostring.domain.dto.product;

import com.shop.tostring.domain.entity.product.PsizeEntity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PsizeDto {

    private int psno;	        // 제품 사이즈 번호
    private String psize;		// 제품 사이즈 이름

    private int pno;			// 제품 번호 fk


    // entity 변환
    public PsizeEntity toPsizeEntity(){
        return PsizeEntity.builder()
                .psno( this.psno )
                .psize( this.psize )
                .build();
    }

}
