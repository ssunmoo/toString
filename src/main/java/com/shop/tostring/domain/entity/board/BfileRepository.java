package com.shop.tostring.domain.entity.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BfileRepository extends JpaRepository<BfileEntity, Integer> {
}
