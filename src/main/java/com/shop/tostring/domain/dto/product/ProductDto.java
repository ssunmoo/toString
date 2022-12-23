package com.shop.tostring.domain.dto.product;

import com.shop.tostring.domain.entity.product.ProductEntity;
import com.shop.tostring.domain.entity.product.PsizeEntity;
import com.shop.tostring.domain.entity.product.PstockEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private float pdiscount;			// 제품 할인율
    private int pactive; 				// 제품 상품 상태 : 0 준비중 등
    private MultipartFile pimg;         // 첨부파일 객체 [ 업로드용 ]
    private String pimgname;            // 첨부파일 [ 출력용 ]

    private int pcno;                   // 제품 카테고리 fk
    private String psize;               // 제품 사이즈
    private String pcolor;              // 제품 컬러
    private int pstock;			        // 제품 재고


    // entity 변환
    public ProductEntity toProductEntity(){
        return ProductEntity.builder()
                .pno( this.pno )
                .pname( this.pname )
                .pprice( this.pprice )
                .pdiscount( this.pdiscount )
                .pactive( this.pactive )
                .build();
    }

    // 사이즈 저장
    public PsizeEntity toSizeEntity(){
        return PsizeEntity.builder()
                .psize( this.psize )
                .build();
    }

    // 컬러, 재고 저장
    public PstockEntity toStockEntity(){
        return PstockEntity.builder()
                .pcolor( this.pcolor )
                .pstock( this.pstock )
                .build();
    }



}
