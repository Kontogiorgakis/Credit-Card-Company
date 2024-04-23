function deleteUser(){
    var owed=document.getElementById("owed").innerText;
    owed=owed.substring(owed.indexOf(":")+2,owed.indexOf('$'));
    if(owed>0){
        alert("There is still amount owed! ("+owed+")");
    }else{
        var xhr = new XMLHttpRequest();
        console.log(owed);
        xhr.onload = function () {
            //console.log(xhr.status);
            if (xhr.readyState === 4 && xhr.status === 200) {
                alert("#User successfully deleted!");
                $("#signIn").load("signIn.html");
            } else if (xhr.status !== 200) {
                alert("Can't delete right now!")
            }
        };
        //name
        var splitter1= document.getElementById("name_prof").innerText.substr(0, document.getElementById("name_prof").innerText.indexOf('('));
        splitter1=splitter1.substring(1,splitter1.length-1);

        //property
        var splitter2=document.getElementById("name_prof").innerText.substring(document.getElementById("name_prof").innerText.indexOf("(")+1,document.getElementById("name_prof").innerText.lastIndexOf(")"));
        splitter2=splitter2.toLowerCase();


        var data="&naming="+splitter1+"&property="+splitter2;
        console.log(data);
        xhr.open('POST', 'Delete?' + data);
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xhr.send();
    }
}