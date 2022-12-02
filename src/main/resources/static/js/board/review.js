
// 글 작성자가 유저인지 관리자인지 확인해서 출력 or 카테고리 번호로
// 후기 게시판[유저], 시연영상 게시판[관리자]


// 후기 게시판 출력
boardList()
function boardList(){
    $.ajax({
        url : "/board/reviewList",
        type : "get",
        success: re => {
            let html = '<tr>'
                    + '<td>번호</td><td>제목</td><td>작성자</td><td>조회수</td>'
                    + '</tr>';
            re.forEach( (r)=>{
                html += '<tr>'
                    + '<td>'+r.bno+'</td><td onclick="getView('+r.bno+')">'+r.btitle+'</td><td>예정</td><td>'+r.bview+'</td>'
                    + '</tr>'
            });
            document.querySelector('.reviewlist').innerHTML = html;
        }
    })
}

// 페이지 이동 함수
function getView( bno ){
    sessionStorage.setItem( "bno", bno );
    location.href = "/board/rListview";
}

