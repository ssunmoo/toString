
// 글 작성자가 유저인지 관리자인지 확인해서 출력 or 카테고리 번호로
// 후기 게시판[유저], 시연영상 게시판[관리자]

let bcno = 2; // 1번 후기게시판

let pageInfo = {
    bcno : bcno,    // 카테고리번호
    page : 1,       // 현재페이지 번호
    key : '',       // 검색할 필드명 [ 제목, 작성자 등 ]
    keyWord : ''    // 검색할 데이터
}

// 후기 게시판 출력
boardList( bcno, 1 );
function boardList( bcno, page ){
    pageInfo.page = page; // 페이지 숫자 대입
    $.ajax({
        url : "/board/reviewList",
        type : "get",
        data : pageInfo,
        success: re => {
            console.log(re);
            let list = re.boardDtoList;
            console.log(list.bcno) // bcno 왜 0이야..?
            let html = '<tr>'
                    + '<td>번호</td><td>제목</td><td>작성자</td><td>조회수</td>'
                    + '</tr>';
            list.forEach( (r)=>{
                html += '<tr>'
                    + '<td>'+r.bno+'</td><td onclick="getView('+r.bno+')">'+r.btitle+'</td><td>예정</td><td>'+r.bview+'</td>'
                    + '</tr>';
            });
            document.querySelector('.reviewlist').innerHTML = html;

            // 페이지 출력 부분
            let pageHtml = '';
            
            // 이전 페이지
            if( page <= 1 ) {
                pageHtml += '<button type="button" onclick="boardList('+(bcno+','+page)+')">이전</button>';
            }else {
                pageHtml += '<button type="button" onclick="boardList('+(bcno+','+(page-1))+')">이전</button>';
            }

            // 페이지 번호 출력
            for( let page = re.startBtn; page <= re.endBtn; page++ ){
                pageHtml += '<button type="button" onclick="boardList('+(bcno+','+page)+')">'+page+'</button>';
            }

            // 다음 페이지
            if( page >= re.totalPage ){
                pageHtml += '<button type="button" onclick="boardList('+(bcno+','+page)+')">다음</button>';
            }else {
                pageHtml += '<button type="button" onclick="boardList('+(bcno+','+(page+1))+')">다음</button>';
            }
            document.querySelector('.pageBox').innerHTML = pageHtml;
            
        }
    })
}

// 게시글 상세보기 페이지 이동 함수
function getView( bno ){
    sessionStorage.setItem( "bno", bno );
    location.href = "/board/tListview";
}


// 검색처리
function tSearch(){
    pageInfo.key = key = document.querySelector('.key').value;
    pageInfo.keyWord = keyWord = document.querySelector('.keyWord').value;
    console.log("pageInfo.key :: " + pageInfo.key)
    console.log("pageInfo.keyword :: " + pageInfo.keyWord)
    boardList( bcno, 1 );
}

