let pno = sessionStorage.getItem( "pno" );

productList = []; // 선택된 제품의 옵션 목록 배열
let stock = null; // 재고 목록
let color = null; // 선택한 색상

// 제품 상세정보 출력
productView()
function productView(){
    $.ajax({
        url : "/admin/productView",
        type : "get",
        data : { "pno" : pno },
        success: re => {
            let productList = re.productDtoList;
            let sizecolor = re.sizecolor;

            document.querySelector('.viewPimg').src ="/pImg/"+productList.pimgname; // 이미지 출력
            document.querySelector('.pname').innerHTML = productList.pname; // 제품명 출력
            
            // 판매가 출력
            if( re.pdiscount == 0 ){ // 할인이 없을 경우
                document.querySelector('.viewPrice').innerHTML = productList.pprice.toLocaleString('ko-KR')+"원";
            }else {
                document.querySelector('.viewPrice').innerHTML = productList.pprice.toLocaleString('ko-KR')+"원";
                document.querySelector('.viewDiscountprice').innerHTML = (productList.pprice - ( productList.pprice * productList.pdiscount ) ).toLocaleString('ko-KR');
                document.querySelector('.viewPdiscount').innerHTML = Math.round(productList.pdiscount*100)+"%OFF";

                // phtml += '<div class="viewPrice">' + productList.pprice.toLocaleString('ko-KR') + '</div>'
                //     + '<span class="won1">원</span>'
                //     + '<div class="viewDisPrice">' + (productList.pprice - ( productList.pprice * productList.pdiscount ) ).toLocaleString('ko-KR') + '<span class="won2">원</span></div>'
                //     + '<div class="viewDis">' + Math.round(productList.pdiscount*100) + '% SALE</div>'
                    // + '<div>' + psizeList.psize + '</div>'
                    // + '<div>' + pstockList.pcolor + '</div>'
                    // + '<div>' + pstockList.pstock + '</div>';
            }

            // let keys = object.keys();
            // for(let i = 0 ; i<keys.length; i++){
            //     let key = keys[i];
            //     data.key
            // }

            document.querySelector('.sizeBox').innerHTML = sizecolor.L;

            // 사이즈, 컬러 뽑아내기
            // console.log(sizecolor)
            // for ( let i = 0; i < sizecolor.length; i++ ){
            //     document.querySelector('.sizeBox').innerHTML = sizecolor.get();
            // }

            // document.querySelector('.ppriceBox').innerHTML = phtml;
            // // 사이즈 목록 중복 제거
            // let sizeList = []; // 중복 값 담을 배열
            // psizeList.forEach( s => {
            //     sizeList.push( s.psize );   // 중복제거가 필요한 내용을 리스트에 담기
            // })
            // let sizeSet = new Set( sizeList ) // 사이즈 리스트 -> Set 목록 변경 [ 중복제거 ] 무슨 말..?
            //
            // // 사이즈 종류
            // let shtml = '<span> [ '
            // sizeSet.forEach( s => {
            //     shtml += " " + s + " ";
            // })
            // shtml += ' ] </span> '
            // document.querySelector('.sizeBox').innerHTML = shtml;
            // console.log("***sizeList***");
            // console.log(sizeList);
            // console.log("==============");
            //
            // // 5. 색상 select 구성
            // let colorlist = [];
            // pstockList.pcolor.forEach( c => {
            //     colorlist.push( c.pcolor ) });
            //
            // let colorSet = new Set( colorlist );
            // // console.log( colorlist )
            //
            // let chtml = '<option> 색상 </option>';
            // colorSet.forEach( c => {
            //     chtml += '<option value="'+ c +'"> '+ c +' </option>'
            // })
            // document.querySelector('.colorBox').innerHTML += chtml;


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
        url : "/admin/setCartList",
        type : "post",
        data : formdata,
        contentType: false,
        processData: false,
        success: re => {
            console.log(re)
            productList = [];
            if( confirm('장바구니 페이지로 이동하시겠습니까?') ) {
                location.href = "/admin/getCartList";
            }else{
                alert('장바구니 담기 실패');
            }
        }
    })
})
