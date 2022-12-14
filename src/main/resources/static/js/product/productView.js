let pno = sessionStorage.getItem( "pno" );

productList = []; // 선택된 제품의 옵션 목록 배열
let stock = null; // 재고 목록
let color = null; // 선택한 색상

// 제품 상세정보 출력
productView()
function productView(){
    $.ajax({
        url : "/productView",
        type : "get",
        data : { "pno" : pno },
        success: re => {
            console.log(re)
            let pproduct = re.productDtoList[0]
            let psize = re.psizeDtoList[0]
            let pstock = null;
            console.log("---pproduct---")
            console.log(pproduct)
            console.log("---psize---")
            console.log(psize)


            document.querySelector('.pimg').src ="/pImg/"+pproduct.pimgname;
            document.querySelector('.pname').innerHTML = pproduct.pname;
            
            // 판매가 출력
            let phtml = '';
            if( re.pdiscount == 0 ){ // 할인이 없을 경우
                phtml += '<div>' + pproduct.pprice.toLocaleString('ko-KR') + ' 원</div>';
            }else {
                phtml += '<div>' + pproduct.pprice.toLocaleString('ko-KR') + ' 원</div>'
                    + '<div>' + (pproduct.pprice - ( pproduct.pprice * pproduct.pdiscount ) ).toLocaleString('ko-KR') + ' 원</div>'
                    + '<div>' + Math.round(pproduct.pdiscount*100) + ' %</div>';
            }
            document.querySelector('.ppriceBox').innerHTML = phtml;

            // 사이즈 목록 중복 제거
            let sizeList = []; // 중복 값 담을 배열
            psize.forEach( s => {
                sizeList.push( s.psize );   // 중복제거가 필요한 내용을 리스트에 담기
            })
            let sizeSet = new Set( sizeList ) // 사이즈 리스트 -> Set 목록 변경 [ 중복제거 ] 무슨 말..?

            // 사이즈 종류
            let shtml = '<span> [ '
                    sizeSet.forEach( s => {
                        shtml += " " + s + " ";
                    })
            shtml += ' ] </span> '

            document.querySelector('.sizeBox').innerHTML = shtml;


            // 색상, 재고

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
