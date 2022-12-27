
let bno = sessionStorage.getItem( "bno" );

// 후기 게시판 상세보기
reviewSelect()
function reviewSelect(){

    $.ajax({
        url : "/board/reviewSelect",
        type : "get",
        data : { "bno" : bno },
        success: re => {
            console.log(re)

            let html = '<div>'
                + '<div class="rViewMnameBox">'
                + '<span class="profileImg"><img src="/pImg/violin05.jpg"></span>'
                + '<span class="rViewMname">'+ re.mname +'</span>'
                + '</div>'
                + '<form name="myform" id="myform" method="post">'
                + '<fieldset>' // 체크 설정 추가
                + '<input type="radio" name="bstar" value="5" id="rate1" class="star1" disabled>'
                + '<label for="rate1">★</label>'
                + '<input type="radio" name="bstar" value="4" id="rate2" class="star2" disabled>'
                + '<label for="rate2">★</label>'
                + '<input type="radio" name="bstar" value="3" id="rate3" class="star3" disabled>'
                + '<label for="rate3">★</label>'
                + '<input type="radio" name="bstar" value="2" id="rate4" class="star4" disabled>'
                + '<label for="rate4">★</label>'
                + '<input type="radio" name="bstar" value="1" id="rate5" class="star5" disabled>'
                + '<label for="rate5">★</label>'
                + '</fieldset>'
                + '</form>'
                + '</div>'
                + '</div>'
                + '<div class="rViewTitle">'+re.btitle+'</div>'
                + '<div class="rViewCon">'+re.bcontent+'</div>'
                + '<div><img src="/upload/'+re.bfilename+'"></div>'; // 이미지 경로 수정
            document.querySelector('.rlist').innerHTML = html;

            if( re.bstar === 5 ){
                document.querySelector('.star1').checked = true;
            }else if( re.bstar === 4 ){
                document.querySelector('.star2').checked = true;
            }else if( re.bstar === 3 ){
                document.querySelector('.star3').checked = true;
            }else if( re.bstar === 2 ){
                document.querySelector('.star4').checked = true;
            }else if( re.bstar === 1 ){
                document.querySelector('.star5').checked = true;
            }

        }
    })
}

// 본인일때만 버튼 생성
function btnCreate(){

}

// 후기 게시판 게시글 삭제
function rDelete(){
    $.ajax({
        url : "/board/rDelete",
        type : "delete",
        data: { "bno" : bno },
        success: re => {
            if(confirm('게시글을 삭제 하시겠습니까?')){
                location.href="/board/rList";
            }
        }
    })
}

// 수정하기 버튼 클릭 시 수정 페이지 이동
function rUpdateBtn( bno ){
    sessionStorage.setItem( "bno", bno );
    location.href = "/board/rListPut";
}