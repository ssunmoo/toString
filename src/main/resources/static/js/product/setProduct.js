let pcno = 0; // 선택된 제품 카테고리 번호를 저장

// 1. 카테고리 추가
function setPcategory(){
    let data = {
        pcname : document.querySelector('.pcname').value,
    }

    $.ajax({
        url : "/setPcategory",
        type : "post",
        data : JSON.stringify(data),
        contentType: "application/json",
        success: re => {
            alert("카테고리 등록 성공")
        }
    })
}

// 2. 카테고리 출력
pcategoryList();
function pcategoryList(){
    $.ajax({
        url : "/pcategoryList",
        type : "get",
        success: re => {
            console.log(re)
            let html = '<option selected="selected">선택</option>';
            re.forEach( (p) =>{
                html += '<option value="'+p.pcno+'">'+ p.pcname +'</option>';
            })
            document.querySelector('.pcategoryList').innerHTML = html;
        }
    })
}

// 선택한 카테고리의 value 값 가져와서 저장
document.querySelector('.pcategoryList').addEventListener( "change", e =>{
    pcno = e.currentTarget.value
    alert(pcno)
})