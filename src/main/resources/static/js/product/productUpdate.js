// 모든 카테고리 출력
pcategoryList();
function pcategoryList(){
    $.ajax({
        url : "/pcategoryList",
        type : "get",
        success: re => {
            let phtml = '<option selected="selected">선택</option>';
            re.forEach( (p) =>{
                phtml += '<option value="'+p.pcno+'">'+ p.pcname +'</option>';
            })
            document.querySelector('.pcategoryList').innerHTML = phtml;
        }
    })
}

let pcno = 0;
document.querySelector('.pcategoryList').addEventListener( "change", e => {
    pcno = e.currentTarget.value	// 선택된 카테고리 번호 가져오기
    getproduct( pcno );	// 선택된 카테고리 번호를 제품출력으로 넘기기
});

// 카테고리에 맞는 제품 출력
function getproduct( pcno ){
    $.ajax({
        url : "/getProductList",
        type : "get",
        success: re => {
            console.log(re)
            let uhtml = '';
            re.forEach( (p) =>{
                if( p.pcno == pcno ) {
                    uhtml += '<option class="ppname" value="'+p.pno+'">'+p.pname+'</option>';
                }
            })
            document.querySelector('.productNameList').innerHTML = uhtml;
        }
    })
}

let pno = 0;
document.querySelector('.productNameList').addEventListener( "change", e => {
    pno = e.currentTarget.value	// 선택된 카테고리 번호 가져오기
    productView(pno);	// 선택된 카테고리 번호를 제품출력으로 넘기기
});

// 기존 정보 출력
function productView(pno){
    $.ajax({
        url : "/productView",
        type : "get",
        data : {"pno" : pno },
        success: re => {
            let productList = re.productDtoList[0]
            let psizeList = re.psizeDtoList[0]
            let pstockList = re.pstockDtoList[0]
            console.log(productList.pimgname)
            console.log(productList.pname)
            document.querySelector('.pname').value = productList.pname;
            document.querySelector('.pprice').value = productList.pprice;
            document.querySelector('.pdiscount').value = productList.pdiscount;
            document.querySelector('.pactive').value = productList.pactive;
            document.querySelector('.psize').value = psizeList.psize;
            document.querySelector('.pcolor').value = pstockList.pcolor;
            document.querySelector('.pstock').value = pstockList.pstock;
            document.querySelector('.imgView').src = "/pImg/"+productList.pimgname;
        }
    })
}

// 대표이미지 등록 시 미리보기 구현
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


// 제품 수정
function productUpdate(){
    let setform = document.querySelector('.setform');
    let formdata = new FormData( setform );
    formdata.set("pcno", pcno ); // 제품 카테고리 값 넣기

    $.ajax({
        url : "/productUpdate",
        type : "put",
        data : formdata,
        contentType: false,
        processData: false,
        success: re => {
            if( re == true ){
                alert('제품 수정이 완료되었습니다');
            }else {
                alert('제품 수정이 실패하였습니다.');
            }
        }
    })
}