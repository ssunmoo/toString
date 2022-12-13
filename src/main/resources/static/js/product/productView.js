let pno = sessionStorage.getItem( "pno" );

productList = []; // 선택된 제품의 옵션 목록 배열

// 제품 상세정보 출력
productView()
function productView(){
    $.ajax({
        url : "/productView",
        type : "get",
        data : { "pno" : pno },
        success: re => {
            console.log(re)
            document.querySelector('.pimg').src ="/pImg/"+re.pimgname;
            document.querySelector('.pname').innerHTML = re.pname;
            
            // 판매가 출력
            let phtml = '';
            if( re.pdiscount == 0 ){ // 할인이 없을 경우
                phtml += '<div>' + re.pprice.toLocaleString('ko-KR') + ' 원</div>';
            }else {
                phtml += '<div>' + re.pprice.toLocaleString('ko-KR') + ' 원</div>'
                    + '<div>' + (re.pprice - ( re.pprice * re.pdiscount ) ).toLocaleString('ko-KR') + ' 원</div>'
                    + '<div>' + Math.round(re.pdiscount*100) + ' %</div>';
            }
            document.querySelector('.ppriceBox').innerHTML = phtml;
        }
    })
}


// 장바구니 클릭 시
document.querySelector('.cartBtn').addEventListener( 'click', (e) =>{
  // 선택한 제품이 없을 경우
  if( productList.length == 0 ){
      alert('최소 1개 이상의 옵션을 선택해 주세요.');
      return;
  }

    let setform = document.querySelector('.setform');
    // console.log(setform)
    let formdata = new FormData( setform );
    formdata.set("pno", pno ); // 제품 번호 추가

    $.ajax({
        url : "/setCartList",
        type : "post",
        data : formdata,
        contentType: false,
        processData: false,
        success: re => {
            console.log(re)
            productList = [];
            if( confirm('장바구니 페이지로 이동하시겠습니까?') ) {
                location.href = "/getCartList";
            }else{
                alert('장바구니 담기 실패');
            }
        }
    })
})
