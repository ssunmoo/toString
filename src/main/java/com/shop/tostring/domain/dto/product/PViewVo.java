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
