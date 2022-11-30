// 1. 카테고리 추가
function setWrite(){
    let data = {
        bcname : document.querySelector('.bcname').value,
    }

    $.ajax({
        url : "/board/setBcategory",
        type : "post",
        data : JSON.stringify(data),
        contentType: "application/json",
        success: re => {
            alert("카테고리 등록 성공")
        }
    })
}