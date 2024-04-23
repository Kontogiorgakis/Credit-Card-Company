function checkAndPurchase(){

    //check values and go

    var creditLimit=document.getElementById("limit").innerText.substring(document.getElementById("limit").innerText.indexOf(":")+2,document.getElementById("limit").innerText.lastIndexOf("$"));
    var cost=document.getElementById("costing").value;
    var balance=document.getElementById("balance").innerText.substring(document.getElementById("balance").innerText.indexOf(":")+2,document.getElementById("balance").innerText.lastIndexOf("$"));
    var type=document.getElementById("trans_typing").value;


    console.log(creditLimit+"   "+cost);

    if(type==="charge" && balance<=0) {
        option_page("You can't buy with charge. Balance is 0!");
    }else if(type==="credit" && creditLimit<=0){
        option_page("You can't buy with credit. Limit is 0!");
    }else{
        purchaseFromMerchant(balance,creditLimit);
    }

}


function purchaseFromMerchant(balance,creditLimit){

    /*init values*/
    var cost=document.getElementById("costing").value;
    var name= document.getElementById("name_prof").innerText.substr(0, document.getElementById("name_prof").innerText.indexOf('('));
    name=name.substring(1,name.length-1);
    var property=document.getElementById("name_prof").innerText.substring(document.getElementById("name_prof").innerText.indexOf("(")+1,document.getElementById("name_prof").innerText.lastIndexOf(")"));
    var type=document.getElementById("trans_typing").value;
    property=property.toLowerCase();

    if(type==="charge" && balance-cost<0){
        //alert("Balance Insufficient!")
        option_page("Balance Insufficient!");
    }else if(type==="credit" && creditLimit-cost<0){
        option_page("Credit Limit Insufficient!");
    }else{
        var xhr = new XMLHttpRequest();
        xhr.onload = function () {
            console.log(xhr.status);
            var response=xhr.responseText;
            if (xhr.readyState === 4 && xhr.status === 200) {
                console.log(response);
                option_page(response);
            } else if (xhr.status !== 200) {
                alert(response);
            }
        };

        var data = $('#purchase').serialize();
        data = data +"&name="+name+"&property="+property;
        console.log(data);
        xhr.open('POST', 'Purchase?' + data);
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xhr.send();
    }
}
