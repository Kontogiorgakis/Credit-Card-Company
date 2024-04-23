function init(){
    document.getElementById("user").value="civilian";
    document.getElementById("Employee_name").style.display="none";
    document.getElementById("ceo").style.display="none";
    document.getElementById("change_names").innerHTML='Full Name';
}


function selectUser(){
    var user=document.getElementById("user").value;

    if(user==="company"){
        document.getElementById("ceo").style.display="block";
        document.getElementById("Employee_name").style.display="none";
        document.getElementById("change_names").innerHTML='Full Name';
    }else if(user==="employee"){
        document.getElementById("Employee_name").style.display="block";
        document.getElementById("ceo").style.display="none";
        document.getElementById("change_names").innerHTML='Company Name';
    }else{
        document.getElementById("Employee_name").style.display="none";
        document.getElementById("ceo").style.display="none";
        document.getElementById("change_names").innerHTML='Full Name';
    }

}



/* lagou function*/
function signUp(){
    var property=document.getElementById("user").value;
    console.log(property);
    if(property==="civilian"){
        servletCommunication("civilian");
    }else if(property==="merchant"){
        servletCommunication("merchant");
    }else if(property==="company") {
        servletCommunication("company");
    }else{
        servletCommunication("employee");
    }
}

function servletCommunication(property){
    var xhr = new XMLHttpRequest();
    console.log("to mialo mou exei aprei fora");
    xhr.onload = function () {
        console.log(xhr.status);
        if (xhr.readyState === 4 && xhr.status === 200) {
            alert("#SUCCESS: "+property+" is now registered");
            location.href='signIn.html';
        } else if (xhr.status !== 200) {
            if(xhr.status===500 || xhr.status===600){
                var message=xhr.responseText;
                alert(message);
            }else{
                alert("#ERROR: "+property+" already exists!");
            }
        }
    };

    var data = $('#account').serialize();
    console.log(data);
    data=data+"&property="+property;
    console.log(data);
    xhr.open('POST', 'Servlet?' + data);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}

