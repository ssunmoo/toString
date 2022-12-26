let pno = sessionStorage.getItem( "pno" );
let cartlist = []; // 장바구니 리스트
let totalprice = 0;

// 장바구니 체크박스 표시
let checkbtnlist = document.querySelectorAll('.checkbtn');
checkbtnlist[0].addEventListener('click', (e) => {

    // 첫번째 체크 박스를 체크할 경우 모든 체크 박스에 체크
    if( e.currentTarget.checked == true ){
        checkbtnlist.forEach( c => {
            c.checked = true;	// 모든 체크 박스에 체크
        })
    } else{ // 아니면 체크박스에 모든 체크 해제
        checkbtnlist.forEach( c => {
            c.checked = false;	// 모든 체크 박스에 체크 해제
        })
    }
}) // click e

let checkplist = []; // 장바구니에서 선택한 제품만 저장하는 리스트

function cartAdd(){
    checkbtnlist.forEach( ( c, i ) =>{
        if( i != 0 && c.checked == true ){ 		// 체크가 되어있는 경우
            checkplist.push( cartlist[i-1] ); 	// 0번 인덱스는 모든 체크 기능이므로 빼야 함
        }
    })
    if( checkplist.length == 0 ){				// 만약에 체크된 제품이 없을 경우
        alert('1개 이상 선택해 주세요');
        return;
    }
    localStorage.removeItem('checkplist'); 		// 저장소 초기화

    if( confirm('결제하시겠습니까') ){
        location.href = "/";
    }
}

// 제품 출력
cartList()
function cartList(){
    $.ajax({
        url : "/admin/setCartList",
        type : "post",
        data : { "pno" : pno },
        success: re => {
            console.log(re)
            let cartProduct = re.productDtoList;
            document.querySelector('.cartImg').src = cartProduct.pimgname;
            document.querySelector('.cartPname').innerHTML = cartProduct.pname;
            document.querySelector('.cartSize').innerHTML = cartProduct.psize;
            document.querySelector('.cartPrice').innerHTML = (cartProduct.pprice - ( cartProduct.pprice * cartProduct.pdiscount ) ).toLocaleString('ko-KR')+'원';
            totalprice = (cartProduct.pprice - ( cartProduct.pprice * cartProduct.pdiscount ) );
            document.querySelector('.totalprice').innerHTML = totalprice.toLocaleString('ko-KR')+'원';
        }
    })
}
