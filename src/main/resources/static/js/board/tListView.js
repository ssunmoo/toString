
let bno = sessionStorage.getItem( "bno" );
let path = "/home/ec2-user/toString/build/resources/main/static/upload/";
let host = "http://localhost:8083/upload/";
// 후기 게시판 상세보기
reviewSelect()
function reviewSelect(){

    $.ajax({
        url : "/board/reviewSelect",
        type : "get",
        data : { "bno" : bno },
        success: re => {
            console.log(re)
            let bfile = re.bfilename
            let html = '<div>'
                + '<div>'+ re.btitle +'</div>'
                + '</div>'
                + '<div>'
                + '<video src="' +(host)+(bfile)+ '" width="720px" height="480px" controls></video>'
                + '</div>'
            document.querySelector('.tlist').innerHTML = html;

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
            location.href="/board/tList";
        }
    })
}

// 수정하기 버튼 클릭 시 수정 페이지 이동
function rUpdateBtn( bno ){
    sessionStorage.setItem( "bno", bno );
    location.href = "/board/rListPut";
}