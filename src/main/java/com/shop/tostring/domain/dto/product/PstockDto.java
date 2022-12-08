package com.shop.tostring.domain.dto.product;

import com.shop.tostring.domain.entity.product.PstockEntity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PstockDto {

    private int pstno;	        // 제품 재고 번호
    private String pcolor;		// 제품 이름
    private int pstock;			// 제품 재고

    private int psno;		    // 제품 사이즈 번호 fk


    // entity 변환
    public PstockEntity toPstockEntity(){
        return PstockEntity.builder()
                .pstno( this.pstno )
                .pcolor( this.pcolor )
                .pstock( this.pstock )
                .build();
    }


}
