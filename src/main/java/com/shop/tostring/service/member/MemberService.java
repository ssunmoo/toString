package com.shop.tostring.service.member;

import com.shop.tostring.domain.dto.member.MemberDto;
import com.shop.tostring.domain.entity.member.MemberEntity;
import com.shop.tostring.domain.entity.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Transactional
@RequiredArgsConstructor
@Service
public class MemberService {

    // 회원 리포지토리
    @Autowired
    private final MemberRepository memberRepository;

    // 세션
    @Autowired
    private HttpServletRequest request;

    // 메일 전송
    @Autowired
    private JavaMailSender javaMailSender;


//    // 로그인 정보 호출
//    @Autowired
//    public UserDetails loadUserByUSername( String mid ) throws UsernameNotFoundException{
//        MemberEntity memberEntity = memberRepository.findBymid( mid ); // 로그인 유저의 아이디 받아오기
//
//        if( memberEntity == null ){
//            throw new UsernameNotFoundException( mid );
//        }
//
//        return User.builder()   // 유저 객체 반환 / 유저 객체 생성을위해 생성자로 회원 아이디, 비밀번호, 룰 전달
//                .username( memberEntity.getMid() )
//                .password( memberEntity.getMpw() )
//                .roles( memberEntity.getRole().toString() )
//                .build();
//
//    }


    //    public MemberEntity saveMember( MemberEntity member ){
//        membercheck( member );
//        return memberRepository.save( member );
//    }
//
//    // 가입되어있는지 확인
//    private void membercheck ( MemberEntity member ){
//        MemberEntity findMember = null;
//        findMember = memberRepository.findById( member.getMid() ); // 아이디 확인
//        if( findMember != null ){ // 아이디가 있으면
//            findMember = memberRepository.findByEmail( member.getMemail() ); // 이메일 확인
//            if( findMember != null ){ // 이메일이 있으면
//                throw new IllegalStateException("이미 가입된 회원입니다.");
//            }
//        }
//    }

    // 1. 회원가입
    @Transactional
    public int setSignup( MemberDto memberDto ){
        MemberEntity memberEntity = memberRepository.save( memberDto.toMemberEntity()); // 받아온 값 저장
        System.out.println("서비스 memberEntity : "+ memberEntity);
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
        System.out.println("서비스 memail : " + memail );

        List< MemberEntity > entityList = memberRepository.findAll();
        System.out.println("서비스 entityList" + entityList );

        // 입력한 이메일과 같은 이메일이 있는지 확인
        for( MemberEntity entity : entityList ){
            if ( entity.getMemail().equals( memail )){
                return true;
            }
        } return false;
    }

    // 4. 로그인
    @Transactional
    public int loginMember( MemberDto memberDto){
        // 모든 정보 호출
        List<MemberEntity> entityList = memberRepository.findAll();
        // 2. 입력 바은 데이터와 일치하는 값 찾기
        for( MemberEntity entity : entityList ){
            if( entity.getMid().equals(memberDto.getMid())){
                if( entity.getMpw().equals(memberDto.getMpw())){
                    request.getSession().setAttribute("loginMno", entity.getMno());
                    return 1;   // 1: 성공
                } else {
                    return 2; // 2: 패스워드 틀림
                }
            }
        }
        return 0; // 0 : 로그인 실패
    }

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






}

