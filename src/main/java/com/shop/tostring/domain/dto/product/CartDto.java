package com.shop.tostring.domain.dto.product;

import com.shop.tostring.domain.entity.product.CartEntity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CartDto {

    private int cartno;	    // 장바구니 번호
    private int amount;     // 옵션 수량

    private int pstno;		// 제품 재고 번호 fk
    private int mno;		// 회원 번호 fk


    // entity 변환
    public CartEntity toCartEntity(){
        return CartEntity.builder()
                .cartno( this.cartno )
                .amount( this.amount )
                .build();
    }


}
