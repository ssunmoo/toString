package com.shop.tostring.domain.entity.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository < ProductEntity, Integer > {


    @Query( value = "select * from product where pcno = :pcno", nativeQuery = true)
    List<ProductEntity> findByBcno(@Param("pcno") int pcno );

//    @Query( value = "select * from product where pno = :pno", nativeQuery = true)
//    List<ProductEntity> findByPno(@Param("pno") String pno );


}
