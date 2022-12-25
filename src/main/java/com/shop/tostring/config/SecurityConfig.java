package com.shop.tostring.config;

import com.shop.tostring.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    protected MemberService memberService;

    // 로그인 인증 관련 메소드
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService( memberService ).passwordEncoder( new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // 인증된 요청 --> 권한키면 전체확인 어려움 추후 수정 예쩡
//            .authorizeRequests()
//                .antMatchers("/admin/**").hasRole("ADMIN") // 관리자는 /admin/ 링크에 모두 접근 가능
//                .antMatchers("/member/info").hasRole("USER")
//                .antMatchers("/**").permitAll() // 인증없이도 요청 가능 [ 모든 접근 허용 ]
//                .and()

            // 로그인 페이지 보안 설정
            .formLogin()
                .loginPage("/member/login")     // 아이디&비밀번호 입력 페이지 URL
                .loginProcessingUrl("/member/loginMember") // 로그인 처리할 URL
                .defaultSuccessUrl("/")         //로그인 성공시 이동 페이지 URL
                .usernameParameter("mid")       // 로그인 시 아이디로 입력받을 변수명
                .passwordParameter("mpw")       // 로그인 시 비밀번호로 입력받을 변수명
                .failureUrl("/error")   // 로그인 실패 시 이동할 URL
                .and()

             // 페이지 권한 설정
            .csrf()
                .ignoringAntMatchers("/") // 메인
                .ignoringAntMatchers("/member/setSignup") //회원가입
                .ignoringAntMatchers("/member/loginMember") // 로그인
                .ignoringAntMatchers("/member/findId") // 아이디 찾기
                .ignoringAntMatchers("/member/findPw") // 비밀번호 찾기
                .ignoringAntMatchers("/board/setBcategory") // 게시판 카테고리 등록
                .ignoringAntMatchers("/board/setWrite") // 게시글 작성
                .ignoringAntMatchers("/board/rUpdate") // 게시글 수정
                .ignoringAntMatchers("/admin/setPcategory") // 제품 카테고리등록
                .ignoringAntMatchers("/admin/setProduct") // 제품 등록
                .ignoringAntMatchers("/admin/productUpdate") // 제품 수정
                .ignoringAntMatchers("/admin/setCartList") // 장바구니 페이지
                .and()

             // 로그아웃 설정
            .logout()
                .logoutRequestMatcher( new AntPathRequestMatcher("/member/logout") ) // 로그아웃 처리할 URL
                .logoutSuccessUrl("/")           // 로그아웃 성공 시
                .invalidateHttpSession( true )   // 세션 초기화
                .and()

            // 오류 발생 설정
            .exceptionHandling()
                .accessDeniedPage("/error") // 오류 발생 시 시큐리티 페이지 전환
                .and()
        // SNS 로그인 보안 설정
                .oauth2Login()
                .defaultSuccessUrl("/") // 로그인 성공 시 이동할 URL
                .userInfoEndpoint() // Endpoint[종착점] : 소셜 회원 정보가 들어오는 곳
                .userService( memberService ); // 해당 서비스에서 정보를 전달해서 사용
    }

    // 비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean // 인증 [로그인]관리 메소드
//    protected void configure(AuthenticationManagerBuilder auth ) throws Exception{
//        auth.userDetailsService( memberService ).passwordEncoder( new BCryptPasswordEncoder() );
//
//    }




}
