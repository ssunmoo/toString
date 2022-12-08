package com.shop.tostring.domain.dto.product;

import com.shop.tostring.domain.entity.product.PcategoryEntity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PcategoryDto {

    private int pcno; 	            // 제품 카테고리 번호
    private String pcname;	        // 제품 카테고리 이름


    // entity 변환
    public PcategoryEntity toPcategoryEntity(){
        return PcategoryEntity.builder()
                .pcno( this.pcno )
                .pcname( this.pcname )
                .build();
    }

}
