package com.shop.tostring.domain.entity.product;

import com.shop.tostring.domain.dto.product.CartDto;
import com.shop.tostring.domain.entity.BaseEntity;
import com.shop.tostring.domain.entity.member.MemberEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
@Table( name = "cart" )
public class CartEntity extends BaseEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int cartno;	    // 장바구니 번호
    private int amount;     // 옵션 수량


    // 제품 재고 번호 fk 받기
    @ManyToOne
    @JoinColumn( name = "pstno" )
    @ToString.Exclude
    private PstockEntity pstockEntity;

    // 회원 번호 fk 받기
    @ManyToOne
    @JoinColumn( name = "mno" )
    @ToString.Exclude
    private MemberEntity memberEntity;


    // dto로 변환
    public CartDto toCartDto(){
        return CartDto.builder()
                .cartno( this.cartno )
                .amount( this.amount )
                .build();
    }

}
