function returnToMerchant(){

    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        var message=xhr.responseText;
        if (xhr.readyState === 4 && xhr.status === 200) {
            option_page(message);
        }else if (xhr.status !== 200) {
            alert(message);
        }
    };


    var name= document.getElementById("name_prof").innerText.substr(0, document.getElementById("name_prof").innerText.indexOf('('));
    name=name.substring(1,name.length-1);

    var property=document.getElementById("name_prof").innerText.substring(document.getElementById("name_prof").innerText.indexOf("(")+1,document.getElementById("name_prof").innerText.lastIndexOf(")"));
    property=property.toLowerCase();

    var data = $('#return').serialize();
    data=data+"&name="+name+"&property="+property;
    //console.log(data);
    xhr.open('POST', 'Return?' + data);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}
