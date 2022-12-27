
// 1. 회원가입
function setSignup(){
    // 입력 받은 정보 객체
    let info = {
        mid : document.querySelector('.mid').value,
        mpw : document.querySelector('.mpw').value,
        mpwcheck : document.querySelector('.mpwcheck').value,
        mname : document.querySelector('.mname').value,
        mphone : document.querySelector('.mphone').value,
        memail : document.querySelector('.memail').value,
        madress : document.querySelector('.madress').value
    }

        $.ajax({
            url: "/member/setSignup",
            type: "post",
            data: JSON.stringify(info),
            contentType: "application/json",
            success: re => {
                if (re != 0) {
                    alert("회원가입을 축하드립니다.");
                } else {
                    alert("회원가입을 다시 시도해 주세요");
                }
            }
        })

    // // 입력하지 않은 값이 있다면 안내 문구 출력
    // if ( info.mid == '' ) { // alert -> document 로 변경
    //     document.querySelector('.check1').innerHTML = '아이디를 입력해 주세요'
    // }else if ( info.mpw == '' ) {
    //     document.querySelector('.check2').innerHTML = '비밀번호를 입력해 주세요'
    // }else if ( info.mpwcheck == '' ) {
    //     document.querySelector('.check3').innerHTML = '비밀번호를 입력해 주세요'
    // }else if ( info.mname == '' ) {
    //     document.querySelector('.check4').innerHTML = '이름을 입력해주세요'
    // }else if ( info.mphone == '' ) {
    //     document.querySelector('.check5').innerHTML = '연락처를 입력해주세요'
    // }else if ( info.memail == '' ) {
    //     document.querySelector('.check6').innerHTML = '이메일을 입력해주세요'
    // }else if ( !info.mpw.includes(info.mpwcheck) ) {			// info.user_pw에 info.user_pw가 포함되어있는지 확인 [ 문자열 비교 시 ]
    //     if ( info.mpw.length == info.mpwcheck.length ) {		// info.user_pw와 info.user_pw의 문자열 길이가 맞는지 확인
    //         document.querySelector('.check3').innerHTML = ''
    //     }else{
    //         document.querySelector('.check3').innerHTML = '비밀번호가 일치하지 않습니다.'
    //     }
    // }else if(  document.querySelector('.check1').innerHTML == ''
    //         && document.querySelector('.check2').innerHTML == ''
    //         && document.querySelector('.check3').innerHTML == ''
    //         && document.querySelector('.check4').innerHTML == ''
    //         && document.querySelector('.check5').innerHTML == ''
    //         && document.querySelector('.check6').innerHTML == ''
    //         && document.querySelector('.check8').checked == true
    //         && document.querySelector('.check9').checked == true ){
    //     alert("2")
    //     $.ajax({
    //         url : "/member/setSignup",
    //         type : "post" ,
    //         data : JSON.stringify( info ) ,
    //         contentType : "application/json",
    //         success : re => {
    //             if( re != 0 ){
    //                 alert("회원가입을 축하드립니다.");
    //             }else {
    //                 alert("회원가입을 다시 시도해 주세요");
    //             }
    //         }
    //     })
    // }
}

// 유효성 검사

// 1. 아이디
function idCheck(){
    let id = document.querySelector('.mid').value;
    let idCheckCf = new RegExp(/^[a-zA-Z가-힣]{2,20}$/);
    if( idCheckCf.test(id) ){ // 입력한 아이디가 유효성검사를 통과할 경우
        $.ajax({
            url : "/member/idCheck",
            type : "get",
            data : { "mid":id },
            success : re => {
                if( re == true ){
                    document.querySelector('.check1').innerHTML = '등록된 아이디입니다.';
                }else{
                    document.querySelector('.check1').innerHTML = '';
                }
            }
        })
    }
}

// 2. 비밀번호
function pwCheck(){
    let pwCheck = document.querySelector('.mpw').value;
    let pwCheckCf = new RegExp(/^[a-zA-Z0-9]{8,20}$/);
    if( pwCheckCf.test(pwCheck)){
        document.querySelector('.check2').innerHTML = '';
    }else {
        document.querySelector('.check2').innerHTML = '8~20글자 사이로 입력해주세요';
    }

}
// 3. 비밀번호 확인
function pwCheck2(){
    let pwCheck = document.querySelector('.mpw').value;
    let pwCheckCf = document.querySelector('.mpwcheck').value;
    if( pwCheckCf != pwCheck ){ // 두개의 비밀번호가 일치하지 않으면
        document.querySelector('.check3').innerHTML = '비밀번호를 재 입력해 주세요';
    }else {
        document.querySelector('.check3').innerHTML = '';
    }
}
// 4. 이름
function nameCheck(){
    let nameCheck = document.querySelector('.mname').value;
    let nameCheckCf = new RegExp(/^[a-zA-Z가-힣]{2,20}$/); // 영대소문자, 한글 최소 2글자, 최대 20글자
    if( nameCheckCf.test(nameCheck)){
        document.querySelector('.check4').innerHTML = '';
    }else {
        document.querySelector('.check4').innerHTML = '두글자 이상 입력해주세요';
    }
}
// 5. 연락처
function phoneCheck(){
    let phoneCheck = document.querySelector('.mphone').value;
    let phoneCheckCf = new RegExp(/^([0-9]{2,3})-([0-9]{3,4})-([0-9]{4})$/);
    if( phoneCheckCf.test(phoneCheck)){
        document.querySelector('.check5').innerHTML = '';
    }else {
        document.querySelector('.check5').innerHTML = '올바른 방식으로 입력해주세요';
    }
}
// 6. 이메일
function emailCheck(){
    let emailCheck = document.querySelector('.memail').value;
    let emailCheckCf = new RegExp(/^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-]+$/);
    if( emailCheckCf.test(emailCheck) ){
        $.ajax({
            url        : "/member/emailCheck",
            type       : "get",
            data       : { "emailCheck" : emailCheck },
            success    : re => {
                if( re == true ){
                    document.querySelector('.check6').innerHTML = '등록된 이메일입니다.';
                }else{
                    document.querySelector('.check6').innerHTML = '';
                }
            }
        })
    }else {
        document.querySelector('.check6').innerHTML = '올바른 이메일 주소를 입력해 주세요.';
    }

}


let auth = 0;        // 발급된 인증 코드 변수
let timer = 0;          // 인증 시간 변수
let timerinter = null;  // setInterval 타이머 함수

// 4. 이메일 인증코드 발급
function getAuth(){
    // 1. 입력받은 이메일
    let toemail = document.querySelector('.memail').value;

    // 2. 입력받은 이메일에게 인증코드를 전송하고 전송된 인증코드를 반환
    $.ajax({    // 아작스의 기본 전송 타입은 [문자열]
        url : "/member/getAuth",
        data : { "toemail" : toemail },
        type : "get",
        success : re => {
            auth = re; // 응답 받은 인증코드를 전역 변수에 대입
            alert("해당 이메일로 인증코드가 발송되었습니다.");
            console.log( auth );
            document.querySelector('.getauthbtn').innerHTML = "인증코드 재발급" // 버튼의 문자 변경
            timer = 120;    // 초 단위
            setTimer();     // 타이머 함수 실행
        }
    })
}

// 5. 이메일 인증 확인
function authCode(){
    // 입력받은 인증코드 가져오기
    let authinput = document.querySelector('.authinput').value;

    // 입력 받은 코드와 발급된 코드가 동일하면
    if( authinput == auth ) {
        alert("인증이 완료되었습니다.")
        clearInterval( timerinter ); // setInterval 타이머 종료
        auth = null;
        timer = 0;
        document.querySelector('.timerbox').innerHTML = "인증완료"
    }else{
        alert("인증코드가 일치하지 않습니다.")
    }
} // authcode e

// 6. 타이머 함수
function setTimer(){
    timerinter = setInterval( function() {
        let minutes, seconds;
        minutes = parseInt(timer / 60); // 분
        seconds = parseInt(timer % 60); // 초

        minutes = minutes < 10 ? "0"+minutes : minutes;
        seconds = seconds < 10 ? "0"+seconds : seconds;

        let timehtml = minutes + " : " + seconds;   // 시 : 분 구성 html
        document.querySelector('.timerbox').innerHTML = timehtml; // 대입

        // 종료 조건
        timer--;    // 1초씩 차감
        if( timer < 0 ){    // 시간이 0초가 되면
            clearInterval( timerinter ); // 타이머 종료
            alert('인증실패');
            auth = null; // 발급 인증코드 초기화
            document.querySelector('.timerbox').innerHTML = "인증완료" // 버튼의 문자 변경
        }
    }, 1000); // 1초 간격으로 함수 실행
} // settimer e
