
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
    // // 유효성 검사 객체
    // let checkList = {
    //     check1 : document.querySelector('.check1').innerHTML,
    //     check2 : document.querySelector('.check2').innerHTML,
    //     check3 : document.querySelector('.check3').innerHTML,
    //     check4 : document.querySelector('.check4').innerHTML,
    //     check5 : document.querySelector('.check5').innerHTML,
    //     check6 : document.querySelector('.check6').innerHTML,
    //     check7 : document.querySelector('.check7').innerHTML
    // }

    // 입력하지 않은 값이 있다면 안내 문구 출력
    if ( info.mid == '' ) { // alert -> document 로 변경
        document.querySelector('.check1').innerHTML = '아이디를 입력해 주세요'
    }else if ( info.mpw == '' ) {
        document.querySelector('.check2').innerHTML = '비밀번호를 입력해 주세요'
    }else if ( info.mpwcheck == '' ) {
        document.querySelector('.check3').innerHTML = '비밀번호를 입력해 주세요'
    }else if ( info.mname == '' ) {
        document.querySelector('.check4').innerHTML = '이름을 입력해주세요'
    }else if ( info.mphone == '' ) {
        document.querySelector('.check5').innerHTML = '연락처를 입력해주세요'
    }else if ( info.memail == '' ) {
        document.querySelector('.check6').innerHTML = '이메일을 입력해주세요'
    }else if ( !info.mpw.includes(info.mpwcheck) ) {			// info.user_pw에 info.user_pw가 포함되어있는지 확인 [ 문자열 비교 시 ]
        if ( info.mpw.length == info.mpwcheck.length ) {		// info.user_pw와 info.user_pw의 문자열 길이가 맞는지 확인
            document.querySelector('.check3').innerHTML = ''
        }else{
            document.querySelector('.check3').innerHTML = '비밀번호가 일치하지 않습니다.'
        }
    }else if(  document.querySelector('.check1').innerHTML == ''
            && document.querySelector('.check2').innerHTML == ''
            && document.querySelector('.check3').innerHTML == ''
            && document.querySelector('.check4').innerHTML == ''
            && document.querySelector('.check5').innerHTML == ''
            && document.querySelector('.check6').innerHTML == ''
            && document.querySelector('.check8').checked == true
            && document.querySelector('.check9').checked == true ){

        $.ajax({
            url : "/member/setSignup",
            type : "post" ,
            data : JSON.stringify( info ) ,
            contentType : "application/json",
            success : re => {
                if( re != 0 ){
                    alert("회원가입을 축하드립니다.");
                }else {
                    alert("회원가입을 다시 시도해 주세요");
                }
            }
        })
    }
}

// 유효성 검사

// 1. 아이디
function idCheck(){
    let mid = document.querySelector('.mid').value;
    let idCheckCf = new RegExp(/^[a-zA-Z가-힣]{2,20}$/);
    console.log("아이디체크 mid : " + mid)
    if( idCheckCf.test( mid ) ){ // 입력한 아이디가 유효성검사를 통과할 경우
        $.ajax({
            url : "/member/idCheck",
            type : "get",
            data : { "mid":mid },
            success:re => {
                console.log("아이디 아작스 결과 : " + re);
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
