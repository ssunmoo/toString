package com.shop.tostring.domain.entity.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository< MemberEntity, Integer > {
    MemberEntity findBymid( String mid );   // 로그인 아이디
    // MemberEntity findBymemail(String memail ); // 이메일
    
}
