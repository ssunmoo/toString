package com.shop.tostring.domain.entity.product;

import com.shop.tostring.domain.dto.product.ProductDto;
import com.shop.tostring.domain.dto.product.PsizeDto;
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
@Table( name = "psize" )
public class PsizeEntity extends BaseEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int psno;	        // 제품 사이즈 번호
    private String psize;		// 제품 사이즈 이름


    // 제품번호 fk 받기
    @ManyToOne
    @JoinColumn( name = "pno" )
    @ToString.Exclude
    private ProductEntity productEntity;

    // 제품 사이즈 번호 pk 전달
    @OneToMany( mappedBy = "psizeEntity" )
    @Builder.Default
    private List<PstockEntity> pstockEntityList = new ArrayList<>();


    // dto로 변환
    public PsizeDto toPsizeDto(){
        return PsizeDto.builder()
                .psno( this.psno )
                .psize( this.psize )
                .pno( this.getProductEntity().getPno() ) // ProductEntity에서 pno를 가져오겠다
                .build();
    }



}
