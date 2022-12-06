package com.shop.tostring.controller.member;

import com.shop.tostring.domain.dto.member.MemberDto;

import com.shop.tostring.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/member")
@RestController
public class MemberController {

    // 서비스 객체 호출
    @Autowired
    public MemberService memberService;


    // ------------------------ [ 페이지 요청 ] ------------------------

    // 1. 회원가입
    @GetMapping("/signup")
    public Resource getSignup(){
        return new ClassPathResource("templates/member/signup.html");
    }

    // 2. 로그인
    @GetMapping("/login")
    public Resource getLogin(){
        return new ClassPathResource("templates/member/login.html");
    }

    // 2. 로그인 error
    @GetMapping("/login/error")
    public Resource getLoginError(Model model ){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해 주세요");
        return new ClassPathResource("templates/member/login.html");
    }

    // 3. 아이디 찾기
    @GetMapping("getId")
    public Resource getId(){
        return new ClassPathResource("templates/member/findid.html");
    }

    // 4. 비밀번호 찾기
    @GetMapping("/getpassword")
    public Resource getPw(){
        return new ClassPathResource("templates/member/findpw.html");
    }

    @GetMapping("/layout")
    public Resource getlayout(){
        return new ClassPathResource("templates/layouts/layout1.html");
    }

    // ------------------------ [ 요청 & 응답 ] ------------------------

    // 1. 회원가입
    @PostMapping("/setSignup")
    public int setSignup(@RequestBody MemberDto memberDto ){
        System.out.println( "컨트롤러ㅓ " + memberDto );
        return memberService.setSignup( memberDto );
    }

    // 2. 아이디 확인
    @GetMapping("/idCheck")
    public boolean idCheck( @RequestParam("mid") String mid ){
        System.out.println( "컨트롤 mid "+mid );
        return memberService.idCheck( mid );
    }

    // 3. 이메일 확인
    @GetMapping("/emailCheck")
    public boolean emailCheck( @RequestParam("emailCheck") String memail ){
        return memberService.emailCheck( memail );
    }

    // 4. 로그인
    @PostMapping("/loginMember")
    public int loginMember( @RequestBody MemberDto memberDto ){
        int result = memberService.loginMember( memberDto );
        return result;
    }

    // 5. 아이디 찾기
    @PostMapping("/findId")
    public String findId( @RequestBody MemberDto memberDto ){
        System.out.println("--아이디찾기con--");
        System.out.println(memberDto);
        String result = memberService.findId( memberDto );
        return result;
    }

    // 6. 비밀번호 찾기
    @PostMapping("/findPw")
    public String findPw( @RequestBody MemberDto memberDto ){
        System.out.println("--비번찾기con--");
        System.out.println(memberDto);
        String result = memberService.findPw( memberDto );
        return result;
    }

    // 7. 이메일 인증코드 발송
    @GetMapping("/getAuth")
    public String getAuth( @RequestParam("toemail") String toemail ) {
        return memberService.getAuth( toemail );
    }
















}
