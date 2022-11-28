//package com.shop.tostring.config;
//
//import com.shop.tostring.service.member.MemberService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@EnableWebSecurity
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    // 멤버 서비스 객체 생성
//    @Autowired
//    private MemberService memberService;
//
//    // 재정의 [ 상속받은 클래스로부터 메소드 재구현 ]
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//            // 인증된 요청
//            .authorizeRequests()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/member/info").hasRole("USER")
//                .antMatchers("/**").permitAll() // 인증없이도 요청 가능 [ 모든 접근 허용 ]
//                .and()
//
//            // 로그인 페이지 보안 설정
//            .formLogin()
//                .loginPage("/member/login")     // 아이디&비밀번호 입력 페이지 URL
//                .loginProcessingUrl("/member/logincontroller") // 로그인 처리할 URL
//                .defaultSuccessUrl("/")         //로그인 성공시 이동 페이지 URL
//                .usernameParameter("mid")       // 로그인 시 아이디로 입력받을 변수명
//                .passwordParameter("mpw")       // 로그인 시 비밀번호로 입력받을 변수명
//                .failureUrl("/members/login/error") // 로그인 에러페이지 URL
//                .and()
//
//             // 페이지 권한 설정
//            .csrf()
//                .ignoringAntMatchers("/member/setSignup")
//                .ignoringAntMatchers("/member/getPw")
//                .and()
//
//             // 로그아웃 설정
//            .logout()
//                .logoutRequestMatcher( new AntPathRequestMatcher("/member/logout") ) // 로그아웃 처리할 URL
//                .logoutSuccessUrl("/")      // 로그아웃 성공 시
//                .invalidateHttpSession( true )   // 세션 초기화
//                .and()
//
//            // 오류 발생 설정
//            .exceptionHandling()
//            .accessDeniedPage("/error"); // 오류 발생 시 시큐리티 페이지 전환
//    }
//
//    // 비밀번호 암호화
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
////    @Bean // 인증 [로그인]관리 메소드
////    protected void configure(AuthenticationManagerBuilder auth ) throws Exception{
////        auth.userDetailsService( memberService ).passwordEncoder( new BCryptPasswordEncoder() );
////
////    }
//
//
//
//
//
//
//
//
//
//}
