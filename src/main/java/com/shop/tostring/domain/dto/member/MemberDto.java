package com.shop.tostring.domain.dto.member;

import com.shop.tostring.domain.entity.member.MemberEntity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MemberDto {

    private int mno;            // 회원 번호
    private String mid;         // 회원 아이디
    private String mpw;         // 회원 비밀번호
    private String mname;       // 회원 이름
    private String mphone;      // 회원 연락처
    private String memail;      // 회원 이메일
    private String madress;     // 주소

    public MemberEntity toMemberEntity(){
        return MemberEntity.builder()
                .mno( this.mno )
                .mid( this.mid )
                .mpw( this.mpw )
                .mname( this.mname )
                .mphone( this.mphone )
                .memail( this.memail )
                .madress( this.madress )
                .build();
    }

}
