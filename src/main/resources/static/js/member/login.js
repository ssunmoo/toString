
// 1. 로그인
function setLogin() {

    let info = {
        mid: document.querySelector('.mid').value,
        mpw: document.querySelector('.mpw').value
    }
    $.ajax({
        url: "/member/loginMember",
        type: "post",
        data : JSON.stringify(info),
        contentType: "application/json",
        success: function(re){
            location.href="/"
        }
    })

}

// 2. 아이디 찾기
function findId(){
    let info = {
        mname: document.querySelector('.mname').value,
        memail: document.querySelector('.memail').value
    }

    $.ajax({
        url : "/member/findId",
        type : "get",
        data : JSON.stringify(info),
        contentType: "application/json",
        success: re => {
            console.log( "결과ㅏㅏ "+re.mid );
            document.querySelector('.findid').innerHTML = re.mid;
        }
    })
}

// 3. 비밀번호 찾기
function findPw(){
    let info = {
        mname: document.querySelector('.mname').value,
        mid: document.querySelector('.mid').value
    }

    $.ajax({
        url : "/member/findPw",
        type : "get",
        data : JSON.stringify(info),
        contentType: "application/json",
        success: re => {
            console.log( "결과ㅏㅏ "+re.mpw );
           document.querySelector('.findpw').innerHTML = re.mpw;
        }
    })
}