package com.shop.tostring.domain.dto.product;

import com.shop.tostring.domain.entity.product.ProductEntity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductDto {

    private int pno;		            // 제품 번호
    private String pname;				// 제품 이름
    private int pprice;					// 제품 가격
    private int pdiscount;				// 제품 할인율
    private int pactive; 				// 제품 상품 상태 : 0 준비중 등
    private String pimg;				// 제품 썸네일 경로

    private int pcno;                   // 제품 카테고리 fk


    // entity 변환
    public ProductEntity toProductEntity(){
        return ProductEntity.builder()
                .pno( this.pno )
                .pname( this.pname )
                .pprice( this.pprice )
                .pdiscount( this.pdiscount )
                .pactive( this.pactive )
                .pimg( this.pimg )
                .build();
    }

}
