package com.shop.tostring.domain.entity.board;

import com.shop.tostring.domain.dto.board.BcategoryDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@Entity
@Table( name = "bcategory")
public class BcategoryEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int bcno;
    private String bcname;

    // 연관관계

    // 게시판 카테고리 <-> 게시판
    @Builder.Default
    @OneToMany( mappedBy = "bcategoryEntity")
    private List<BoardEntity> boardEntityList = new ArrayList<>();


    // Dto로 변환
    public BcategoryDto tobcBcDto(){
        return BcategoryDto.builder()
                .bcno(this.bcno)
                .bcname(this.bcname)
                .build();
    }


}
