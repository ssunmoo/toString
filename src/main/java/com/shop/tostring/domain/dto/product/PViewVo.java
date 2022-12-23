package com.shop.tostring.domain.dto.product;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class PViewVo { // 제품 출력용 클래스

//    private int pcno;                   // 제품 카테고리
//    private int pno;                    // 제품 번호
//    private String pname;				// 제품 이름
//    private int pprice;					// 제품 가격
//    private float pdiscount;			// 제품 할인율
//    private int pactive; 				// 제품 상품 상태 : 0 준비중 등
//    private MultipartFile pimg;         // 첨부파일 객체 [ 업로드용 ]
//    private String pimgname;            // 첨부파일 [ 출력용 ]

    // 제품 정보
    ProductDto productDtoList;
    Map<String, Set<String>> sizecolor;


}

/*
{
        제품명 : 삼익
        판매가 : ㅁㅁ
        제품정보~~
            사이즈/칼라 : {
                M : [ 파랑, 노랑 ] ,
                L : [ 빨강 , 초롱 ]
            }

            }
}
*/
