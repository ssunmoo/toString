package com.shop.tostring.controller.member;

import com.shop.tostring.domain.dto.member.MemberDto;

import com.shop.tostring.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/member")
@Controller
public class MemberController {

    // 서비스 객체 호출
    @Autowired
    public MemberService memberService;


    // ------------------------ [ 페이지 요청 ] ------------------------

    // 1. 회원가입
    @GetMapping("/signup")
    public String getSignup(){
        return "member/signup";
    }

    // 2. 로그인
    @GetMapping("/login")
    public String getLogin(){
        return "member/login";
    }

    // 3. 아이디 찾기
    @GetMapping("/getId")
    public String getId(){
        return "member/findid";
    }

    // 4. 비밀번호 찾기
    @GetMapping("/getpassword")
    public String getPw(){
        return "member/findpw";
    }



    // ------------------------ [ 요청 & 응답 ] ------------------------

    // 1. 회원가입
    @ResponseBody
    @PostMapping("/setSignup")
    public int setSignup(@RequestBody MemberDto memberDto ){
        return memberService.setSignup( memberDto );
    }

    // 2. 아이디 확인
    @ResponseBody
    @GetMapping("/idCheck")
    public boolean idCheck( @RequestParam("mid") String mid ){
        System.out.println( "컨트롤 mid "+mid );
        return memberService.idCheck( mid );
    }

    // 3. 이메일 확인
    @ResponseBody
    @GetMapping("/emailCheck")
    public boolean emailCheck( @RequestParam("emailCheck") String memail ){
        return memberService.emailCheck( memail );
    }

    // 4. 로그인
    @ResponseBody
    @PostMapping("/loginMember")
    public int loginMember( @RequestBody MemberDto memberDto ){
        int result = memberService.loginMember( memberDto );
        return result;
    }

    // 5. 아이디 찾기
    @ResponseBody
    @PostMapping("/findId")
    public String findId( @RequestBody MemberDto memberDto ){
        System.out.println("--아이디찾기con--");
        System.out.println(memberDto);
        String result = memberService.findId( memberDto );
        return result;
    }

    // 6. 비밀번호 찾기
    @ResponseBody
    @PostMapping("/findPw")
    public String findPw( @RequestBody MemberDto memberDto ){
        System.out.println("--비번찾기con--");
        System.out.println(memberDto);
        String result = memberService.findPw( memberDto );
        return result;
    }

    // 7. 이메일 인증코드 발송
    @ResponseBody
    @GetMapping("/getAuth")
    public String getAuth( @RequestParam("toemail") String toemail ) {
        return memberService.getAuth( toemail );
    }
















}
