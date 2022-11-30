package com.shop.tostring.service.board;

import com.shop.tostring.domain.dto.board.BcategoryDto;
import com.shop.tostring.domain.dto.board.BoardDto;
import com.shop.tostring.domain.entity.board.BcategoryEntity;
import com.shop.tostring.domain.entity.board.BcategoryRepository;
import com.shop.tostring.domain.entity.board.BoardEntity;
import com.shop.tostring.domain.entity.board.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BcategoryRepository bcategoryRepository;


    // 1. 카테고리 등록
    public boolean setBcategory(BcategoryDto bcategoryDto){
        BcategoryEntity entity = bcategoryRepository.save( bcategoryDto.toBcEntity() );
        System.out.println("카테고리등록 ");
        System.out.println(entity);
        if ( entity.getBcno() !=0 ){
            return true;
        }else {
            return false;
        }
    }

    // 2. 카테고리 출력
    public List<BcategoryDto> bcategorylist(){
        List<BcategoryEntity> entityList = bcategoryRepository.findAll();
        List<BcategoryDto> dtoList = new ArrayList<>();

        entityList.forEach( b -> dtoList.add(b.tobcBcDto()));
        return dtoList;
    }

    // 3. 게시글 작성
    public boolean setWrite(BoardDto boardDto){

        // 카테고리 정보를 가져와서 null 인지 확인
        Optional<BcategoryEntity> optional = bcategoryRepository.findById( boardDto.getBcno() );
        if ( !optional.isPresent() ){ // 엔티티가 없으면 false
            return false;
        }

        BcategoryEntity bcategoryEntity = optional.get();

        // 저장하기
        BoardEntity boardEntity = boardRepository.save( boardDto.toBoardEntity() );

        // 게시글번호를 가져왔을때 0이 아니면
        if( boardEntity.getBno() != 0 ){
            // 카테고리 <-> 게시물 연관 관계 대입
            boardEntity.setBcategoryEntity( bcategoryEntity );
            bcategoryEntity.getBoardEntityList().add( boardEntity );
            return true;
        }
        return false;
    }



}
