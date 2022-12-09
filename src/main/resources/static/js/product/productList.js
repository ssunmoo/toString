
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
                    + '<div class="pname">'+p.pname+'</div>'
                    + '<div class="pprice">'+p.pprice+' 원</div>'
                    + '<div class="discountprice">'+ (p.pprice*(1-p.pdiscount)).toLocaleString('ko-KR') +' 원</div>'
                    + '<div class="pdiscount">'+p.pdiscount+' %</div>'
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