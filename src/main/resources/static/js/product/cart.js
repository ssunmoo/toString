let pno = sessionStorage.getItem( "pno" );
let cartlist = JSON.parse(sessionStorage.getItem( "cartlist" )); // 장바구니 리스트
let totalprice = 0;

// 제품 출력
cartList()
function cartList(){

    console.log( JSON.stringify(cartlist) );
    $.ajax({
        url : "/admin/setCartList",
        type : "post",
        data : JSON.stringify(cartlist),
        contentType: "application/json",
        success: re => {
            let cartProduct = re.productDtoList;
            console.log(cartProduct)
            console.log("-----")
            console.log(cartProduct.pimgname)
            let cartHtml = '';
            re.forEach( cartProduct => {
                cartHtml += '<tr class="cartProductList">'
                + '<td><input type="checkbox" class="checkBoxBtn" name="checkBoxBtn"></td>'
                + '<td><img src="/pImg/'+ cartProduct.pimgname +'" class="cartImg"></td>'
                + '<td class="cartPname">'+cartProduct.pname+'</td>'
                + '<td class="cartSize">'+cartProduct.psize+'</td>'
                + '<td class="cartOption">1개</td>'
                + '<td class="cartPrice">'+(cartProduct.pprice - ( cartProduct.pprice * cartProduct.pdiscount ) ).toLocaleString('ko-KR')+'원</td>'
                + '</tr>';
            // document.querySelector('.cartImg').src = cartProduct.pimgname;
            // document.querySelector('.cartPname').innerHTML = cartProduct.pname;
            // document.querySelector('.cartSize').innerHTML = cartProduct.psize;
            // document.querySelector('.cartPrice').innerHTML = (cartProduct.pprice - ( cartProduct.pprice * cartProduct.pdiscount ) ).toLocaleString('ko-KR')+'원';
            totalprice += (cartProduct.pprice - ( cartProduct.pprice * cartProduct.pdiscount ) );
        })
    document.querySelector('.cartProduct').innerHTML = cartHtml;
    document.querySelector('.totalprice').innerHTML = totalprice.toLocaleString('ko-KR')+'원';

        }
    })
}

let checkbtnlist = [];

// 전체 체크 이벤트
function selectAll(selectAll)  {
    checkbtnlist = document.getElementsByName('checkBoxBtn');
    checkbtnlist.forEach((checkbox) => {
        checkbox.checked = selectAll.checked;
    })
}

let checkplist = []; // 장바구니에서 선택한 제품만 저장하는 리스트

function cartBuy(){
    checkbtnlist.forEach( ( c, i ) =>{
        if( i != 0 && c.checked == true ){ 		// 체크가 되어있는 경우
            checkplist.push( cartlist[i-1] ); 	// 0번 인덱스는 모든 체크 기능이므로 빼야 함
        }
    })
    if( checkplist.length == 0 ){
        alert('1개 이상 선택해 주세요');
        return;
    }
    if( confirm('결제하시겠습니까') ){
        sessionStorage.removeItem('cartlist'); // 장바구니 세션 초기화
        location.href = "/";
    }
}
