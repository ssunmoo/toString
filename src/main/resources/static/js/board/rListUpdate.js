let bno = sessionStorage.getItem( "bno" );
let bcno = 0;

// 1. 카테고리 출력
bcategorylist()
function bcategorylist(){
    $.ajax({
        url : "/board/bcategorylist",
        type : "get",
        success: re => {
            let html = '<option selected="selected"> 선택 ▾</option> ';
            re.forEach( (b) => {
                html += '<option value="'+b.bcno+'">'+b.bcname+'</option> '
            })
            document.querySelector('.categorylist').innerHTML = html;
        }
    })
}

// 2. 선택한 카테고리의 value 값 가져와서 저장
document.querySelector('.categorylist').addEventListener( "change", e =>{
    bcno = e.currentTarget.value
})

// 후기 게시판 상세보기
reviewSelect()
function reviewSelect(){
    $.ajax({
        url : "/board/reviewSelect",
        type : "get",
        data : { "bno" : bno },
        success: re => {
            console.log(re)
            document.querySelector('.btitle').value = re.btitle;
            document.querySelector('.bcontent').value = re.bcontent;
            document.querySelector('.rfileImg').src = "/upload/" + re.bfilename;
            ;
        }
    })
}

// 후기 게시판 게시글 수정
function rUpdate(){
    let updateform = document.querySelector('.updateform');
    let formdata = new FormData( updateform );
    formdata.set("bno", bno ); // 게시글 번호 넣기

    $.ajax({
        url : "/board/rUpdate",
        type : "put",
        data : formdata,
        contentType: false,
        processData: false,
        success: re => {
            if( re == true ){
                alert('게시글 수정이 완료되었습니다');
                // location.href="/board/rList";
            }else {
                alert('게시글 수정이 실패하였습니다.');
            }
        }
    })
}
