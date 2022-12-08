package com.shop.tostring.domain.entity.product;

import com.shop.tostring.domain.dto.product.PcategoryDto;
import com.shop.tostring.domain.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter @Setter
@Builder
@Table( name = "pcategory" )
public class PcategoryEntity extends BaseEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int pcno; 	            // 제품 카테고리 번호
    private String pcname;	        // 제품 카테고리 이름


    // 제품 카테고리번호 pk 전달
    @OneToMany( mappedBy = "pcategoryEntity" )
    @Builder.Default
    private List<ProductEntity> productEntityList = new ArrayList<>();




    // dto로 변환
    public PcategoryDto toPcategoryDto(){
        return PcategoryDto.builder()
                .pcno( this.pcno )
                .pcname( this.pcname )
                .build();
    }




}
