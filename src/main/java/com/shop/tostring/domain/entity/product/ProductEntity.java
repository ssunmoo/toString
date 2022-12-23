package com.shop.tostring.domain.entity.product;

import com.shop.tostring.domain.dto.product.PViewVo;
import com.shop.tostring.domain.dto.product.ProductDto;
import com.shop.tostring.domain.dto.product.PsizeDto;
import com.shop.tostring.domain.dto.product.PstockDto;
import com.shop.tostring.domain.entity.BaseEntity;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.*;

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
                .pstock( this.psizeEntityList.get(0).getPstockEntityList().get(0).getPstock() )
                .build();
    }

    // 사이즈, 컬러를 dto로 변환
    public PViewVo toPViewVo(){

        Map<String, Set<String>> map = new HashMap<>();
        for(int i = 0 ; i < psizeEntityList.size(); i++){
            PsizeEntity psizeEntity = psizeEntityList.get(i);
            String size = psizeEntity.getPsize();

            // 컬러 뽑아서 저장
            Set<String> set = new HashSet<>();
            for(int j = 0 ; j < psizeEntity.getPstockEntityList().size(); j++){
                PstockEntity pstockEntity = psizeEntity.getPstockEntityList().get(j);
                set.add(pstockEntity.getPcolor());
            }
            map.put(size, set);
        }
        return PViewVo.builder()
                .sizecolor(map)
                .build();


//        // 사이즈, 컬러 뽑아내기
//        List<String> psizeDtoList = new ArrayList<>();
//        Set<String> pcolorDtoList = new HashSet<>();
//
//        // 사이즈와 컬러를 하나의 객체로 묶기
//        Map<String, Set<String>> sizecolor = new HashMap<>();
//
//        psizeEntityList.forEach( (s) ->{
//            psizeDtoList.add( s.getPsize() ); // 제품 사이즈 리스트에 넣기
//            sizecolor.put("sizecolor", pcolorDtoList<String> ); // sizecolor에 사이즈 넣기
//
//            s.getPstockEntityList().forEach( (c) -> {
//                pcolorDtoList.add( c.getPcolor() ); // 제품 컬러 리스트에 넣기
//                sizecolor.put(pcolorDtoList.toString(), pcolorDtoList ); // sizecolor에에 컬러 객체 넣기
//            });
//        });

//        return PViewVo.builder()
//                .sizecolor( sizecolor )
//                .build();
    }



}
