package com.shop.tostring.domain.entity.product;

import com.shop.tostring.domain.dto.product.ProductDto;
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
@Table( name = "product" )
public class ProductEntity extends BaseEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int pno;		            // 제품 번호
    private String pname;				// 제품 이름
    private int pprice;					// 제품 가격
    private int pdiscount;				// 제품 할인율
    private int pactive; 				// 제품 상품 상태 : 0 준비중 등
    private String pimg;				// 제품 썸네일 경로


    // 제품 카테고리번호 fk 받기
    @ManyToOne
    @JoinColumn( name = "pcno" )
    @ToString.Exclude
    private PcategoryEntity pcategoryEntity;

    // 제품 번호 pk 전달
    @OneToMany( mappedBy = "productEntity" )
    @Builder.Default
    private List<PsizeEntity> psizeEntityList = new ArrayList<>();



    // dto로 변환
    public ProductDto toProductDto(){
        return ProductDto.builder()
                .pno( this.pno )
                .pname( this.pname )
                .pprice( this.pprice )
                .pdiscount( this.pdiscount )
                .pactive( this.pactive )
                .pimg( this.pimg )
                .build();
    }


}
