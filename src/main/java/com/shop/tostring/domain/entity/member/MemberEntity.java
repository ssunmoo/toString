package com.shop.tostring.domain.entity.member;

import com.shop.tostring.constant.Role;
import com.shop.tostring.domain.dto.member.MemberDto;
import com.shop.tostring.domain.entity.BaseEntity;
import com.shop.tostring.domain.entity.board.BoardEntity;
import com.shop.tostring.domain.entity.product.CartEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
@Entity
@Table( name = "member" )
@Builder
public class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int mno;            // 회원 번호

    @Column( unique = true )
    private String mid;         // 회원 아이디
    private String mpw;         // 회원 비밀번호
    private String mname;       // 회원 이름
    private String mphone;      // 회원 연락처
    @Column( unique = true )
    private String memail;      // 회원 이메일
    private String madress;     // 주소

//    // 타입을 엔티티 속성으로 지정
//    @Enumerated(EnumType.STRING)
//    private Role role;

    // 타입을 엔티티 속성으로 지정
    private String role; // 토큰 타입

    // 카트에 회원 번호 pk 전달
    @OneToMany( mappedBy = "memberEntity" )
    @Builder.Default
    private List<CartEntity> cartEntityList = new ArrayList<>();

    // 게시판에 회원 번호 pk 전달
    @OneToMany( mappedBy = "memberEntity" )
    @Builder.Default
    private List<BoardEntity> boardEntityList = new ArrayList<>();


    // Dto로 형변환
    public MemberDto toMemberDto(){
        return MemberDto.builder()
                .mno( this.mno )
                .mname( this.mname )
                .mid( this.mid )
                .mpw( this.mpw )
                .mphone( this.mphone )
                .memail( this.memail )
                .madress( this.madress )
                .build();
    }



}
