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
    private float pdiscount;				// 제품 할인율
    private int pactive; 				// 제품 상품 상태 : 0 준비중 등
    private String pimg;				// 제품 썸네일


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
                .pimgname( this.pimg ) // 파일명 전달
                .pcno( this.pcategoryEntity.getPcno() ) // @ToString.Exclude으로 막아놔서 dto에 함께 출력하려면 이렇게 호출해야함
                .psize( this.psizeEntityList.get(0).getPsize() )
                .pcolor( this.psizeEntityList.get(0).getPstockEntityList().get(0).getPcolor() )
                .pstock( this.psizeEntityList.get(0).getPstockEntityList().get(0).getPstock() )
                .build();
    }


}
