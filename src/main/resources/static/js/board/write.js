let bcno = 0; // 선택된 카테고리 번호를 저장할 변수

// 1. 카테고리 출력
bcategorylist()
function bcategorylist(){
    $.ajax({
        url : "/board/bcategorylist",
        type : "get",
        success: re => {
            let html = '<option selected="selected"> 선택 </option> ';
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

// 3. 게시글 작성
function setWrite(){
    let setform = document.querySelector('.setform');
    console.log(setform)
    let formdata = new FormData( setform );
    formdata.set("bcno", bcno ); // 카테고리 값 넣기
    console.log("bcno : "+bcno)
    console.log(formdata)
    console.log(formdata.bcno)
    console.log(formdata.btitle)
    $.ajax({
        url : "/board/setWrite",
        type : "post",
        data : formdata,
        contentType: false,
        processData: false,
        success: re => {
            alert(re)
            if( re == true ){
                alert('게시글 등록이 완료되었습니다');
            }else {
                alert('게시글 등록이 실패하였습니다.');
            }
        }
    })
}


