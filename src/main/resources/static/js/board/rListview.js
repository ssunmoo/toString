
let bno = sessionStorage.getItem( "bno" );
alert("게시글번호 : " + bno);

// 후기 게시판 상세보기
reviewSelect()
function reviewSelect(){
    $.ajax({
        url : "/board/reviewSelect",
        type : "get",
        data : { "bno" : bno },
        success: re => {
            console.log(re)
            let html = '<tr>'
                + '<td>회원번호'+re.mno+'</td>'
                + '<td>별점'
                + '<form name="myform" id="myform" method="post">'
                + '<fieldset>'
                + '<input type="radio" name="bstar" value="5" id="rate1" class="star1">'
                + '<label for="rate1">★</label>'
                + '<input type="radio" name="bstar" value="4" id="rate2" class="star2">'
                + '<label for="rate2">★</label>'
                + '<input type="radio" name="bstar" value="3" id="rate3" class="star3">'
                + '<label for="rate3">★</label>'
                + '<input type="radio" name="bstar" value="2" id="rate4" class="star4">'
                + '<label for="rate4">★</label>'
                + '<input type="radio" name="bstar" value="1" id="rate5" class="star5">'
                + '<label for="rate5">★</label>'
                + '</fieldset>'
                + '</form>'
                + '</td>'
                + '</tr>'
                + '<tr><td>'+re.btitle+'</td></tr>'
                + '<tr><td>'+re.bcontent+'</td></tr>'
                + '<tr><td>'+re.bfile+'</td></tr>';
            document.querySelector('.rlist').innerHTML = html;
        }
    })
}

// 본인일때만 버튼 생성
function btnCreate(){

}

// 후기 게시판 게시글 삭제
function rDelete(){

}

// 수정하기 버튼 클릭 시 수정 페이지 이동
// 페이지 이동 함수
function rUpdateBtn( bno ){
    sessionStorage.setItem( "bno", bno );
    location.href = "/board/rListPut";
}