package com.shop.tostring.domain.entity.product;

import com.shop.tostring.domain.dto.product.PstockDto;
import com.shop.tostring.domain.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
@Table( name = "pstock" )
public class PstockEntity extends BaseEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int pstno;	        // 제품 재고 번호
    private String pcolor;		// 제품 이름
    private int pstock;			// 제품 재고


    // 제품 사이즈 번호 fk 받기
    @ManyToOne
    @JoinColumn( name = "psno" )
    @ToString.Exclude
    private PsizeEntity psizeEntity;


    // 제품 재고 번호 pk 전달
    @OneToMany( mappedBy = "pstockEntity")
    @Builder.Default
    private List<CartEntity> cartEntityList = new ArrayList<>();



    // dto로 변환
    public PstockDto toPstockDto(){
        return PstockDto.builder()
                .pstno( this.pstno )
                .pcolor( this.pcolor )
                .pstock( this.pstock )
                .build();
    }



}
