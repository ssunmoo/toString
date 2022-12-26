package com.shop.tostring.service.product;

import com.shop.tostring.domain.dto.product.*;
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

    @Autowired
    private CartRepository cartRepository;

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
    String productpath = "C:\\Users\\504\\Desktop\\toString\\src\\main\\resources\\static\\pImg\\";

    @Transactional
    public boolean pimgUpload(  ProductDto productDto, ProductEntity productEntity ){
        if( !productDto.getPimg().getOriginalFilename().equals("") ){ // 첨부파일이 있을 경우

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

        // 카테고리 정보 호출
        Optional<PcategoryEntity> optional = pcategoryRepository.findById( productDto.getPcno() );
        if ( !optional.isPresent() ) { // null 인지 확인
            return false;
        }
        // 확인된 카테고리 번호를 카테고리 엔티티에 저장
        PcategoryEntity pcategoryEntity = optional.get();
        // 1. 프로덕트 레코드 생성
        ProductEntity productEntity = productRepository.save( productDto.toProductEntity() );
        // 2. 사이즈 레코드 생성
        PsizeEntity psizeEntity = psizeRepository.save( productDto.toSizeEntity() );
        // 3. 컬러, 재고 레코드 생성
        PstockEntity pstockEntity = pstockRepository.save( productDto.toStockEntity() );

        if( productEntity.getPno() != 0 ){ // 제품 번호가 0이 아니면
            pimgUpload( productDto, productEntity ); // 이미지 업로드 함수 실행

            // 제품 카테고리 <-> 제품 연관관계
            productEntity.setPcategoryEntity( pcategoryEntity );
            pcategoryEntity.getProductEntityList().add( productEntity );

            // 제품 <-> 사이즈 연관관계
            psizeEntity.setProductEntity( productEntity );
            productEntity.getPsizeEntityList().add(psizeEntity);

            // 사이즈 <-> 재고 연관관계
            pstockEntity.setPsizeEntity( psizeEntity );
            psizeEntity.getPstockEntityList().add(pstockEntity);

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
        for(ProductEntity p : entityList){
            dtoList.add( p.toProductDto());
        }
        return dtoList;
    }

    // 6. 제품 상세페이지
    public PViewVo productView(int pno ){
        // 해당 제품 번호에 관련된 내용만 빼오기
        Optional< ProductEntity > optional = productRepository.findById( pno );
        // 내용 없으면 null 반환
        if( !optional.isPresent() ){
            return null;
        }
        // 확인된 정보를 entity에 담기
        ProductEntity entity = optional.get();

        // 출력용 객체 생성
        PViewVo pViewVo = new PViewVo();

        ProductDto dtoList = new ProductDto();
        // 엔티티 내용을 dto로 변환
        dtoList = entity.toProductDto();

        // pViewVo에 모두 담기
        pViewVo.setProductDtoList( dtoList );
        pViewVo.setSizecolor( entity.toPViewVo().getSizecolor() );
        return pViewVo;
    }

    // 7. 제품 수정
    @Transactional
    public boolean productUpdate ( ProductDto productDto ) {

        // pk 번호로 제품 찾기
        Optional<ProductEntity> optional = productRepository.findById(productDto.getPno());
        if (optional.isPresent()) {
            ProductEntity productEntity = optional.get();
            System.out.println(productEntity);

//            // 2. 사이즈 레코드 생성
//            PsizeEntity psizeEntity = psizeRepository.save(productDto.toSizeEntity());
//            // 3. 컬러, 재고 레코드 생성
//            PstockEntity pstockEntity = pstockRepository.save(productDto.toStockEntity());

            // 1. 수정할 첨부파일이 있을 때 : 기존 파일 삭제 후 새로 업로드
            if( !productDto.getPimg().getOriginalFilename().equals("") ){       // 수정할 정보
                if( productEntity.getPimg() != null ){    // 기존 첨부파일 있을 떄
                    File file = new File( productpath + productEntity.getPimg() ); // 기존 첨부파일 객체화
                    if( file.exists()) { // 존재하면
                        file.delete();   // 파일 삭제
                    }
                }
                pimgUpload( productDto, productEntity );
            }
            productEntity.setPname( productDto.getPname() );
            productEntity.setPactive( productDto.getPactive() );
            productEntity.setPdiscount( productDto.getPdiscount() );
            productEntity.setPprice( productDto.getPprice() );

            return true;
        } else{
            return false;
        }
    }

    // 8. 장바구니 페이지
    @Transactional
    public PViewVo getCartList( int pno ){

        Optional<ProductEntity> optional = productRepository.findById( pno );
        if( !optional.isPresent() ){
            return null;
        }
        // 확인된 정보를 entity에 담기
        ProductEntity entity = optional.get();
        // 출력용 객체 생성
        PViewVo pViewVo = new PViewVo();
        ProductDto dtoList = new ProductDto();
        // 엔티티 내용을 dto로 변환
        dtoList = entity.toProductDto();
        // pViewVo에 모두 담기
        pViewVo.setProductDtoList( dtoList );
        return pViewVo;

    }

    // 피아노 카테고리 제품 출력
    public List<ProductDto> productPiano( int pcno ){
        System.out.println("★★★★★★");
        System.out.println(pcno);
        System.out.println("★★★★★★");
        Optional<PcategoryEntity> optional = pcategoryRepository.findById( pcno );
        if( optional.isPresent() ){
            List<ProductEntity> entityList = (List<ProductEntity>) optional.get();
            List<ProductDto> dtoList = new ArrayList<>();
            for(ProductEntity p : entityList){
                dtoList.add( p.toProductDto());
            }
            return dtoList;
        }
        return null;
    }



}
