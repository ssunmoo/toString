package com.shop.tostring.domain.entity.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PsizeRepository extends JpaRepository< PsizeEntity, Integer > {
}
