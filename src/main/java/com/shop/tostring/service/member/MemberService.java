package com.shop.tostring.service.member;

import com.shop.tostring.constant.Role;
import com.shop.tostring.domain.dto.member.MemberDto;
import com.shop.tostring.domain.dto.member.OauthDto;
import com.shop.tostring.domain.entity.member.MemberEntity;
import com.shop.tostring.domain.entity.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

@Transactional
@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService, OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    // 회원 리포지토리
    @Autowired
    private final MemberRepository memberRepository;

    // 세션
    @Autowired
    private HttpServletRequest request;

    // 메일 전송
    @Autowired
    private JavaMailSender javaMailSender;


    // 로그인 인증 메서드 [ 시큐리티 사용 ]
    @Override
    public UserDetails loadUserByUsername(String mid) throws UsernameNotFoundException {
        // 입력받은 아이디가 있는지 확인
        MemberEntity memberEntity = memberRepository
                .findBymid( mid );
                //.orElseThrow( () -> new UsernameNotFoundException("사용자가 존재하지않습니다.")); // 검색 결과 확인

        // 검증된 토큰 생성 [ 일반 유저 ]
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add( new SimpleGrantedAuthority( memberEntity.getRole() )); // 회원정보를 빼온뒤 토큰에 저장

        // 토큰 전달
        MemberDto memberDto = memberEntity.toMemberDto();
        memberDto.setAuthorities( authorities );

        return memberDto;
    }

    // 로그인 여부 판단 [ 시큐리티 사용 ]
    public String getloginMno(){
        // 인증된 토큰 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        System.out.println("토큰내용확인 " + principal);

        if( principal.equals("anonymousUser")){ // 로그인 전 기본 값
            return null;
        }else {
            MemberDto memberDto = (MemberDto) principal;
            return memberDto.getMname();
        }
    }
    

    // 1. 회원가입
    @Transactional
    public int setSignup( MemberDto memberDto ){

        // 패스워드 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setMpw( passwordEncoder.encode( memberDto.getMpw()));

        MemberEntity memberEntity = memberRepository.save( memberDto.toMemberEntity()); // 받아온 값 저장

        // 회원등급 삽입
        memberEntity.setRole("ROLE_USER");
        return memberEntity.getMno();
    }

    // 2. 아이디 확인
    @Transactional
    public boolean idCheck( String mid ){
        // 엔티티에서 값 모두 빼오기
        List< MemberEntity > entityList = memberRepository.findAll();
        // 입력한 아이디와 같은 아이디가 있는지 확인
        for( MemberEntity entity : entityList ){
            if( entity.getMid().equals(mid) ) {
                return true;
            }
        } return false;
    }

    // 3. 이메일 확인
    @Transactional
    public boolean emailCheck( String memail ){
        List< MemberEntity > entityList = memberRepository.findAll();
        // 입력한 이메일과 같은 이메일이 있는지 확인
        for( MemberEntity entity : entityList ){
            if ( entity.getMemail().equals( memail )){
                return true;
            }
        } return false;
    }

//    // 4. 로그인
//    @Transactional
//    public int loginMember( MemberDto memberDto){
//        // 모든 정보 호출
//        List<MemberEntity> entityList = memberRepository.findAll();
//        // 2. 입력 바은 데이터와 일치하는 값 찾기
//        for( MemberEntity entity : entityList ){
//            if( entity.getMid().equals(memberDto.getMid())){
//                if( entity.getMpw().equals(memberDto.getMpw())){
//                    request.getSession().setAttribute("loginMno", entity.getMno());
//                    return 1;   // 1: 성공
//                } else {
//                    return 2; // 2: 패스워드 틀림
//                }
//            }
//        }
//        return 0; // 0 : 로그인 실패
//    }

    // 5. 아이디 찾기
    @Transactional
    public String findId( MemberDto memberDto ){
        // 1. 모든 레코드/엔티티 꺼내기
        List<MemberEntity> entityList = memberRepository.findAll();

        System.out.println("아이디찾기 서비스 : " + entityList.toString());

        // 2. 리스트에서 찾기
        for ( MemberEntity entity : entityList ){
            if( entity.getMname().equals( memberDto.getMname() )){  // 저장된 이름과 입력한 이름이 같으면
                System.out.println(memberDto.getMname());
                if( entity.getMemail().equals( memberDto.getMemail() )){  // 저장된 이메일과 입력한 이메일이 같으면
                    System.out.println(memberDto.getMemail());
                    System.out.println(entity.getMid());
                    return entity.getMid();
                }
            }
        }
        return null;
    }

    // 6. 비밀번호 찾기
    @Transactional
    public String findPw( MemberDto memberDto ){
        // 1. 모든 레코드/엔티티 꺼내기
        List<MemberEntity> entityList = memberRepository.findAll();
        // 2. 리스트에서 찾기
        for ( MemberEntity entity : entityList ){
            if( entity.getMname().equals( memberDto.getMname() )){  // 저장된 이름과 입력한 이름이 같으면
                if( entity.getMid().equals( memberDto.getMid() )){  // 저장된 아이디와 입력한 아이디가 같으면
                    return entity.getMpw();
                }
            }
        }
        return null;
    }


    // 7. 이메일 인증코드 발송
    public String getAuth( String toemail ){
        String auth = ""; // 인증 코드
        String html = "<html><body> <h2> .toString 회원가입 이메일 인증코드 입니다.</h2><br>";

        Random random = new Random();   // 난수 객체
        for ( int i = 0; i < 6; i++ ){  // 6번 반복
            char randomchar = (char)(random.nextInt(26)+97); // 97부터 26개 : 97~122까지 : 알파벳 소문자
            // char randomchar = (char)(random.nextInt(10)+48); // 48부터 10개 : 48~57까지 : 0~9
            auth += randomchar;
        }
        html += "<h3> 인증코드 : " + auth + "</h3><br>"
                + "<div> 해당 인증코드를 회원가입 창에 입력해 주세요 </div>";

        html += "</body></html>";

        emailsend( toemail, "인증코드입니다.", html ); // 메일 전송
        return auth; // 인증 코드를 반환
    } // getauth e

    // **. 메일 전송 서비스
    public void emailsend( String toemail, String title, String content ) {

        try{
            // 1. Mime 프로토콜 객체 생성
            MimeMessage message = javaMailSender.createMimeMessage();
            // 2. Mime 설정 객체 생성 new MimeMessageHelper( mime객체명, 첨부파일여부, 인코딩타입 )
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper( message, true, "UTF-8");
            // 3. 보내는 사람의 정보
            mimeMessageHelper.setFrom("14hhy@naver.com", "관리자");
            // 4. 받는 사람의 정보
            mimeMessageHelper.setTo( toemail );
            // 5. 메일 제목
            mimeMessageHelper.setSubject( title );
            // 6. 메일의 내용
            mimeMessageHelper.setText( content.toString(), true ); // HTML 형식
            // 7. 메일 전송
            javaMailSender.send( message );

        } catch ( Exception e ) {
            System.out.println("메일 전송 실패 :: " + e);
        }

    } // emailsend e


    // OAuth2 로그인
    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser( userRequest ); // oAuth2User.getAttributes() 임

        System.out.println("2. oAuth2User " + oAuth2User.toString());
        // 2. oauth2 클라이언트 식별 [ 카카오, 네이버, 구글 등 ]
        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId(); // 프로퍼티스에 입력한 클라이언트 아이디 가져오기
        System.out.println("3. oauth2 회사명 " + registrationId );

        // 3. 회원 정보를 담는 객체명 [ JSON 형태 ]
        String oauth2UserInfo = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
        System.out.println("4. 회원정보 객체명 " + oauth2UserInfo );
        System.out.println("5. 인증 결과 " + oAuth2User.getAttributes() );
        // 4. Dto 처리
        OauthDto oauthDto = OauthDto.of( registrationId, oauth2UserInfo, oAuth2User.getAttributes() );

        // 5. DB처리 ***
        // 5-1. 기존 회원인지 아닌지 확인을 위해 엔티티에 이메일 검색
        Optional<MemberEntity> optional =  memberRepository.findByMemail(oauthDto.getMemail()); // Optional 클래스 [ nill 예외처리 방지 ]
        MemberEntity memberEntity = null;
        if( optional.isPresent()){ // 기존 회원이면
            memberEntity = optional.get();
//            // 이메일이 존재하면서 sns 구분[ 카카오, 네이버 등 ]이 동일할 경우
//            if( optional.get().getRole().equals( registrationId )){
//                memberEntity = optional.get(); // 검색된 내용을 memberEntity에 담기
//            }else {  // 이메일이 존재하면서 sns[ 카카오, 네이버 등 ] 구분이 다를 경우 새로 저장
//                memberEntity = memberRepository.save( oauthDto.toEntity() );
//            }
        }else { // 기존 회원이 아니면 새로 저장
            memberEntity = memberRepository.save( oauthDto.toEntity() );
        }
//        memberRepository.findByMemail( oauthDto.getMemail())
//                .orElseThrow( ()-> {});

        // 5-2. 권한 부여
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add( new SimpleGrantedAuthority(memberEntity.getRole())); // 권한이름

        // 6. 반환 [ 세션 역할 부분 : DB에 있는 정보를 꺼내서 세션에 저장 ]
        MemberDto memberDto = new MemberDto();
        memberDto.setMemail( memberEntity.getMemail());
        memberDto.setAuthorities( authorities ); // 권한 이름 전달 kakaoUser 등
        memberDto.setAttributes( oauthDto.getAttributes() );
        memberDto.setMname( memberEntity.getMname());
        memberDto.setMid( memberEntity.getMname());
        return memberDto; // dto에 담았으니 dto 리턴
    }
    
    
    // 로그인 엔티티 호출
    public MemberEntity getEntity(){
        // 시큐리티로 확인
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (object == null ){
            return null;
        }
        // 로그인된 회원번호 호출
        MemberDto memberDto = (MemberDto) object;
        Optional<MemberEntity> optional = memberRepository.findByMemail( memberDto.getMemail() );
        if( !optional.isPresent() ){
            return null;
        }
        // 로그인된 회원 엔티티 반환
        return optional.get();
    }
    
    
}

