package com.shop.tostring.domain.entity.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PsizeRepository extends JpaRepository< PsizeEntity, Integer > {

//    @Query(value = "select p.pno, p.pactive, p.pdiscount, p.pimg, p.pname, p.pprice, p.pcno, s.psno, s.psize"
//            + "from product p inner join psize s where p.pno = :p.pno;", nativeQuery = true)
//    List<PsizeEntity> findBySize(@Param("psize") int psize );

}
