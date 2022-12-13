
getProductList()
function getProductList(){
    $.ajax({
        url : "/getProductList",
        type : "get",
        success: re => {
            console.log(re)
            let html = '';
            re.forEach( (p) =>{
                html += '<div class="product" onclick="productPage('+p.pno+')">'
                    + '<div class="pimg">'
                    + '<img src="/pImg/'+p.pimgname+'">'
                    + '</div>'
                    + '<div class="pname">'+p.pname+'</div>'
                    + '<div class="pprice">정상가 '+p.pprice.toLocaleString('ko-KR')+' 원</div>'
                    + '<div class="discountprice">할인가 '+ (p.pprice - ( p.pprice * p.pdiscount ) ).toLocaleString('ko-KR') +' 원</div>'
                    + '<div class="pdiscount">'+Math.round(p.pdiscount*100)+' %</div>' // 소수점 제거 후 반올림
                    + '</div>';
            })
            document.querySelector('.productList').innerHTML = html;
        }
    })
}

// 제품 상세페이지로 이동
function productPage( pno ){
    sessionStorage.setItem( "pno", pno );
    location.href = "/getProductView";
}