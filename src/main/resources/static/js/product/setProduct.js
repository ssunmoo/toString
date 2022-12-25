let pcno = 0; // 선택된 제품 카테고리 번호를 저장

// 1. 카테고리 추가
function setPcategory(){
    let data = {
        pcname : document.querySelector('.pcname').value,
    }

    $.ajax({
        url : "/admin/setPcategory",
        type : "post",
        data : JSON.stringify(data),
        contentType: "application/json",
        success: re => {
            alert("카테고리 등록 성공");
            location.href="/getProduct";
        }
    })
}

// 2. 카테고리 출력
pcategoryList();
function pcategoryList(){
    $.ajax({
        url : "/admin/pcategoryList",
        type : "get",
        success: re => {
            let html = '<option selected="selected">선택 ▾</option>';
            re.forEach( (p) =>{
                html += '<option value="'+p.pcno+'">'+ p.pcname +'</option>';
            })
            document.querySelector('.pcategoryList').innerHTML = html;

        }
    })
}

// 3. 선택한 카테고리의 value 값 가져와서 저장
document.querySelector('.pcategoryList').addEventListener( "change", e =>{
    pcno = e.currentTarget.value // 선택된 value 값을 pcno에 저장
})


// 4. 제품 등록
function setProduct(){
    let setform = document.querySelector('.setform');
    let formdata = new FormData( setform );
    formdata.set("pcno", pcno ); // 제품 카테고리 값 넣기

    $.ajax({
        url : "/admin/setProduct",
        type : "post",
        data : formdata,
        contentType: false,
        processData: false,
        success: re => {
            if( re == true ){
                alert('제품 등록이 완료되었습니다');
            }else {
                alert('제품 등록이 실패하였습니다.');
            }
        }
    })
}

// 5. 대표이미지 등록 시 미리보기 구현
let pimg = document.querySelector('.pimg')
pimg.addEventListener('change', (e) =>{
    let file = new FileReader(); // 파일 클래스 이용한 객체 생성
    // 첨부파일 경로 읽기
    file.readAsDataURL( e.target.files[0] ); // 첨부 파일로 등록된 이미지

    // 이미지 태그 src에 대입
    file.onload = (e) =>{
        document.querySelector('.imgView').src = e.target.result;
    }
})
