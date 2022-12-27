
let pcno = 2;
productPiano()
function productPiano(){
    $.ajax({
        url : "/admin/productPiano",
        type : "post",
        data : {"pcno" : pcno },
        success: re => {
            console.log(re)
            let html = '';
            re.forEach( (p) =>{

                html += '<span class="product" onclick="productPage('+p.pno+')">'
                    + '<div class="pimg">'
                    + '<img src="/pImg/'+p.pimgname+'">'
                    + '</div>'
                    + '<div class="pname">'+p.pname+'</div>'
                    + '<div class="ppriceBox">'
                    + '<span class="pdiscount">'+Math.round(p.pdiscount*100)+'%</span>' // 소수점 제거 후 반올림
                    + '<span class="discountprice"> '+ (p.pprice - ( p.pprice * p.pdiscount ) ).toLocaleString('ko-KR') +'원</span>'
                    + '</div>'
                    + '</span>';

            })
            document.querySelector('.pianoList').innerHTML = html;
        }
    })
}

// 제품 상세페이지로 이동
function productPage( pno ){
    sessionStorage.setItem( "pno", pno );
    location.href = "/admin/getProductView";
}