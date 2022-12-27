package com.shop.tostring.domain.entity.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository< MemberEntity, Integer > {
    MemberEntity findBymid( String mid );   // 로그인 아이디
    Optional<MemberEntity> findByMemail(String memail); // sns

}
