function paymentCivilian(){
    var name = document.getElementById("name_prof").innerText.substr(0, document.getElementById("name_prof").innerText.indexOf('('));
    name = name.substring(1,name.length-1);
    var amount = document.getElementById("costing").value;
    var owed = document.getElementById("owed").innerHTML.substr(13);
    owed = owed.substring(0, owed.length - 1);
    var balance = document.getElementById("balance").innerHTML.substring(document.getElementById("balance").innerText.indexOf(":")+2,document.getElementById("balance").innerText.lastIndexOf("$"));//substr(19);
    var profit = document.getElementById("balance").innerHTML.substr(14); // For Merchant
    profit = profit.substring(0, profit.length - 1);
    var property = document.getElementById("name_prof").innerText.substring(document.getElementById("name_prof").innerText.indexOf("(")+1,document.getElementById("name_prof").innerText.lastIndexOf(")"));
    property = property.toLowerCase();

    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var message="Success! Payment completed!";
            option_page(message);
        } else if (xhr.status !== 200) {
            alert(xhr.responseText);
        }
    };

    if(balance === ""){
        balance = profit;
    }
    var data = "&name=" + name + "&costing=" + amount + "&property=" + property + "&owed=" + owed + "&balance=" + balance;
    console.log(data);
    xhr.open('POST', 'Payment?' + data);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}