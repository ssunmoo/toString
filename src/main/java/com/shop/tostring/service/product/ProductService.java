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
        List<ProductDto> dtoList = new ArrayList<>();

        // 엔티티 내용을 dto로 변환
        dtoList.add(entity.toProductDto());

        // 사이즈 정보 불러오기
        List<PsizeEntity> psizeEntityList = optional.get().getPsizeEntityList();
        // 사이즈 정보 저장
        List<PsizeDto> psizeDtoList = new ArrayList<>();
        for(PsizeEntity pentity : psizeEntityList){
            psizeDtoList.add(pentity.toPsizeDto());
        }

        // 색상, 재고 리스트 불러오기
        List<PstockEntity> pstockEntityList = psizeEntityList.get(0).getPstockEntityList();
        List<PstockDto> pstockDtoList = new ArrayList<>();
        for(PstockEntity pentity : pstockEntityList ){
            pstockDtoList.add(pentity.toPstockDto());
        }

        // pViewVo에 모두 담기
        pViewVo.setProductDtoList( dtoList );
        pViewVo.setPsizeDtoList( psizeDtoList );
        pViewVo.setPstockDtoList( pstockDtoList );
        return pViewVo;
    }

//    // 7. 제품 수정
//    public boolean productUpdate ( ProductDto productDto ){
//        Optional<ProductEntity> optional = productRepository.findById(productDto.getPno());
//        // 1. 프로덕트 레코드 생성
//        ProductEntity productEntity = productRepository.save( productDto.toProductEntity() );
//        // 2. 사이즈 레코드 생성
//        PsizeEntity psizeEntity = psizeRepository.save( productDto.toSizeEntity() );
//        // 3. 컬러, 재고 레코드 생성
//        PstockEntity pstockEntity = pstockRepository.save( productDto.toStockEntity() );
//
//        if( productEntity.getPno() != 0 ){ // 제품 번호가 0이 아니면
//            pimgUpload( productDto, productEntity ); // 이미지 업로드 함수 실행
//
//            // 제품 <-> 사이즈 연관관계
//            psizeEntity.setProductEntity( productEntity );
//            productEntity.getPsizeEntityList().add(psizeEntity);
//
//            // 사이즈 <-> 재고 연관관계
//            pstockEntity.setPsizeEntity( psizeEntity );
//            psizeEntity.getPstockEntityList().add(pstockEntity);
//
////            System.out.println("************");
////            System.out.println(productEntity.getPcategoryEntity());
////            System.out.println("************");
//            return true;
//        } else{
//            return false;
//        }
//    }

    // 8. 장바구니 페이지
    public ProductDto setCartList( ProductDto productDto ){


        return null;
    }




}
