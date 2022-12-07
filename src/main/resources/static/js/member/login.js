
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
            if( re == true ){
                location.href="/"
            }else {
                alert('로그인 정보를 다시 입력해주세요');
            }

        }
    })
}

// 2. 아이디 찾기
function findId(){
    let info = {
        mname : document.querySelector('.mname').value,
        memail : document.querySelector('.memail').value
    }

    $.ajax({
        url : "/member/findId",
        type : "post",
        data : JSON.stringify(info),
        contentType: "application/json",
        success: re => {
            let html = '';
            if( re !== '' ){
                html += '<span>아이디  </span>'
                    + '<span>'+ re +'</span>';
                document.querySelector('.findid').innerHTML = html;
            } else {
                html += '<span> 정보를 다시 입력해 주세요 </span>';
                document.querySelector('.findid').innerHTML = html;
            }
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
        type : "post",
        data : JSON.stringify(info),
        contentType: "application/json",
        success: re => {
            let html = '';
            if( re !== '' ){
                html += '<span>비밀번호  </span>'
                    + '<span>'+ re +'</span>';
                document.querySelector('.findpw').innerHTML = html;
            } else {
                html += '<span> 정보를 다시 입력해 주세요 </span>';
                document.querySelector('.findpw').innerHTML = html;
            }
        }
    })
}


