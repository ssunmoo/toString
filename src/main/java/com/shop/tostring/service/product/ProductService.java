package com.shop.tostring.service.product;

import com.shop.tostring.domain.dto.product.PcategoryDto;
import com.shop.tostring.domain.entity.product.PcategoryEntity;
import com.shop.tostring.domain.entity.product.PcategoryRepository;
import com.shop.tostring.domain.entity.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
public class ProductService {

    @Autowired
    private PcategoryRepository pcategoryRepository;

    @Autowired
    private ProductRepository productRepository;


    // 제품 카테고리 추가
    @Transactional
    public boolean setPcategory ( PcategoryDto pcategoryDto ){
        PcategoryEntity entity = pcategoryRepository.save( pcategoryDto.toPcategoryEntity() );
        if( entity.getPcno() != 0 ){
            return true;
        }else {
            return false;
        }
    }

    // 제품 카테고리 출력
    public List<PcategoryDto> pcategoryList (){
       List<PcategoryEntity> entity = pcategoryRepository.findAll();
       List<PcategoryDto> dto = new ArrayList<>();
       entity.forEach( p -> dto.add( p.toPcategoryDto() ));
       return dto;
    }







}
