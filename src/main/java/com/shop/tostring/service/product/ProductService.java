package com.shop.tostring.service.product;

import com.shop.tostring.domain.dto.product.PcategoryDto;
import com.shop.tostring.domain.dto.product.ProductDto;
import com.shop.tostring.domain.entity.product.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ProductService {

    @Autowired
    private PcategoryRepository pcategoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PsizeRepository psizeRepository;

    @Autowired
    private PstockRepository pstockRepository;


    // 1. 제품 카테고리 추가
    @Transactional
    public boolean setPcategory ( PcategoryDto pcategoryDto ){
        PcategoryEntity entity = pcategoryRepository.save( pcategoryDto.toPcategoryEntity() );
        if( entity.getPcno() != 0 ){
            return true;
        }else {
            return false;
        }
    }

    // 2. 제품 카테고리 출력
    public List<PcategoryDto> pcategoryList (){
       List<PcategoryEntity> entity = pcategoryRepository.findAll();
       List<PcategoryDto> dto = new ArrayList<>();
       entity.forEach( p -> dto.add( p.toPcategoryDto() ));
       return dto;
    }


    // 3. 대표이미지 업로드 경로
    String productpath = "C:\\Users\\504\\IdeaProjects\\toString\\src\\main\\resources\\static\\pImg\\";

    @Transactional
    public boolean pimgUpload(  ProductDto productDto, ProductEntity productEntity ){
        if( productDto.getPimg() != null ){ // 첨부파일이 있을 경우

            // 중복방지를 위하여 UUID를 이용한 랜덤문자 생성
            String uuid = UUID.randomUUID().toString();
            String fileName = uuid + "_" + productDto.getPimg().getOriginalFilename();
            productEntity.setPimg(fileName);

            // 지정된 경로에 파일 생성하기
            try{
                File upLoadImg = new File( productpath + fileName );
                productDto.getPimg().transferTo( upLoadImg ); // .transferTo() 지정한 경로에 파일을 생성하기
            }catch (Exception e){
                System.out.println("대표이미지 업로드 실패 : " + e);
            }
            return true;
        }else {
            return false;
        }
    }

    // 4. 제품 등록
    @Transactional
    public boolean setProduct(ProductDto productDto){

        System.out.println("*** 제품 정보 ***");
        System.out.println(productDto.toString());

        // 카테고리 정보 호출
        Optional<PcategoryEntity> optional = pcategoryRepository.findById( productDto.getPcno() );
        if ( !optional.isPresent() ) { // null 인지 확인
            return false;
        }
        PcategoryEntity pcategoryEntity = optional.get();
        ProductEntity productEntity = productRepository.save( productDto.toProductEntity() );

        // productDto에 있는 속성을 다른 엔티티로 넣어야함
        // 1. 사이즈 넣어주기
//        Optional<ProductEntity> optional2 = productRepository.findById( productDto.getPno() );
//        if( !optional.isPresent() ){
//            return false;
//        }
        PsizeEntity psizeEntity = psizeRepository.save( productDto.toSizeEntity() );
        psizeEntity.setPsize( productDto.getPsize() );
        psizeEntity.setpno( productDto.getPno() );

        // 2. 컬러, 재고 넣어주기
        PstockEntity pstockEntity = pstockRepository.save( productDto.toStockEntity() );
        pstockEntity.setPcolor( productDto.getPcolor() );
        pstockEntity.setPstock( productDto.getPstock() );

        if( productEntity.getPno() != 0 ){
            pimgUpload( productDto, productEntity ); // 이미지 업로드 함수 실행

            productEntity.setPcategoryEntity( pcategoryEntity );
            pcategoryEntity.getProductEntityList().add( productEntity );
//            System.out.println("************");
//            System.out.println(productEntity.getPcategoryEntity());
//            System.out.println("************");
            return true;
        }
        return false;
    }

    // 5. 제품 출력
    public List<ProductDto> getProductList(){
        List<ProductEntity> entityList = productRepository.findAll();
        List<ProductDto> dtoList = new ArrayList<>();
        entityList.forEach( (p) -> dtoList.add( p.toProductDto()) );
        return dtoList;
    }

    // 6. 제품 상세페이지
    public ProductDto productView( int pno ){
        Optional< ProductEntity > optional = productRepository.findById( pno );
        if( optional.isPresent() ){
            ProductEntity productEntity = optional.get();
            return  productEntity.toProductDto();
        }else {
            return null;
        }
    }

    // 7. 장바구니 페이지
    public ProductDto setCartList( ProductDto productDto ){


        return null;
    }




}
