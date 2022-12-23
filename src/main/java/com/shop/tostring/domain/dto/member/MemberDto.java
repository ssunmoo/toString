package com.shop.tostring.domain.dto.member;

import com.shop.tostring.domain.entity.member.MemberEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MemberDto implements UserDetails, OAuth2User {

    private int mno;            // 회원 번호
    private String mid;         // 회원 아이디
    private String mpw;         // 회원 비밀번호
    private String mname;       // 회원 이름
    private String mphone;      // 회원 연락처
    private String memail;      // 회원 이메일
    private String madress;     // 주소
    private Set<GrantedAuthority> authorities; // 인증 권한 [토큰]
    private Map<String, Object> attributes; // oauth2 인증결과

    // 엔티티로 변환
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

    // 토큰을 넣을 setter
    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    // UserDetails Override
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 컬렉션의 상속을 받는 GrantedAuthority
        return this.authorities;
    }


    @Override
    public String getPassword() {
        return this.mpw;
    }

    @Override
    public String getUsername() {
        return this.mid;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    // OAuth2User
    @Override
    public String getName() {
        return this.memail;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }



}
