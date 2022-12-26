let pno = sessionStorage.getItem( "pno" );


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
            }
            document.querySelector('.sizeBox').innerHTML = productList.psize;
            document.querySelector('.colorBox').innerHTML = productList.pcolor;

        }
    })
}


// 장바구니 클릭 시
document.querySelector('.cartBtn').addEventListener( 'click', (e) =>{

    $.ajax({
        url : "/admin/setCartList",
        type : "post",
        data : { "pno" : pno },
        success: re => {
            if( confirm('장바구니 페이지로 이동하시겠습니까?') ) {
                sessionStorage.setItem( "pno", pno );
                location.href = "/admin/getCartList";
            }else{
                alert('장바구니 담기 실패');
            }
        }
    })
})
