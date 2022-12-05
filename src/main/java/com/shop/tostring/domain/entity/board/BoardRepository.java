package com.shop.tostring.domain.entity.board;

import com.shop.tostring.domain.dto.board.PageVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer > {

    // 제목 검색 시
    @Query( value = "select * from board where bcno = :bcno and btitle like %:keyWord%", nativeQuery = true )
    Page<BoardEntity> findByBtitle(@Param("bcno") int bcno, String keyWord, Pageable pageable);

    // 내용 검색 시
    @Query( value = "select * from board where bcno = :bcno and bcontent like %:keyWord%", nativeQuery = true )
    Page<BoardEntity> findByBcontent(@Param("bcno") int bcno, String keyWord, Pageable pageable);

    // 검색 없을 경우
    @Query( value = "select * from board where bcno = :bcno", nativeQuery = true )
    Page<BoardEntity> findByBcno(@Param("bcno") int bcno, Pageable pageable);
    
}
