var string;
var property;
function init(){
    document.getElementById("user").value="civilian";
}


function signin(){
    var xhr = new XMLHttpRequest();
    property=document.getElementById("user").value;
    xhr.onload = function () {
        console.log(xhr.status);
        if (xhr.readyState === 4 && xhr.status === 200) {
            var json = xhr.responseText;
            console.log(json);
            //string=json
            string=JSON.stringify(json);
            console.log(string);
            if(property==="civilian"){
                redirectCivilian(string,"Options.html");
            }else if(property==="merchant"){
                redirectMerchant(string,"Options.html");
            }else if(property==="company"){
                redirectCompany(string,"Options.html");
            }else{
                redirectEmployee(string,"Options.html");
            }
        } else if (xhr.status !== 200) {
            alert("#ERROR: "+property+" doesn't exist!");
        }
    };
    var name="&naming="+document.getElementById("naming").value;
    var propertyl="&property="+document.getElementById("user").value;
    var data=name+propertyl;
    console.log(data);
    xhr.open('POST', 'SignIN?' + data);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}

/* Page redirections*/
function stringOfRedirection(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var json = xhr.responseText;
            string=JSON.stringify(json);
            console.log("lastring   "+string);
        } else if (xhr.status !== 200) {
            alert("#ERROR!!!!");
        }
    };
    //name
    var splitter1= document.getElementById("name_prof").innerText.substr(0, document.getElementById("name_prof").innerText.indexOf('('));
    splitter1=splitter1.substring(1,splitter1.length-1);

    //property
    var splitter2=document.getElementById("name_prof").innerText.substring(document.getElementById("name_prof").innerText.indexOf("(")+1,document.getElementById("name_prof").innerText.lastIndexOf(")"));
    splitter2=splitter2.toLowerCase();
    var name="&naming="+splitter1;
    var property="&property="+splitter2;
    var data=name+property;

    console.log(data);
    xhr.open('POST', 'SignIN?' + data);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}


/*for purchase*/
function option_page(message){
    stringOfRedirection();
    alert(message);
    if(property==="civilian"){
        redirectCivilian(string,"Options.html");
    }else if(property==="merchant"){
        redirectMerchant(string,"Options.html");
    }else if(property==="company"){
        redirectCompany(string,"Options.html");
    }else{
        redirectEmployee(string,"Options.html");
    }
}


function delete_page(){
    stringOfRedirection();
    if(property==="civilian"){
        redirectCivilian(string,"delete.html");
    }else if(property==="merchant"){
        redirectMerchant(string,"delete.html");
    }else if(property==="company"){
        redirectCompany(string,"delete.html");
    }else{
        redirectEmployee(string,"delete.html");
    }
}
function purchase_page(){
    stringOfRedirection();
    if(property==="civilian"){
        redirectCivilian(string,"purchase.html");
    }else if(property==="merchant"){
        alert("You can't buy from another merchant!");
    }else if(property==="company"){
        alert("Companies can't make transactions");
    }else{
        redirectEmployee(string,"purchase.html");
    }
}

function transactions_page(){
    stringOfRedirection();
    if(property==="civilian"){
        redirectCivilian(string,"Transactions.html");
    }else if(property==="merchant"){
        redirectMerchant(string,"Transactions.html");
    }else if(property==="company"){
        redirectCompany(string,"Transactions.html");
    }else{
        redirectEmployee(string,"Transactions.html");
    }
}


function return_page(){
    stringOfRedirection();
    if(property==="civilian"){
        redirectCivilian(string,"return.html");
    }else if(property==="merchant"){
        alert("You can't return products to merchants!");
    }else if(property==="company"){
        alert("Companies can't return products");
    }else{
        redirectEmployee(string,"return.html");
    }
}
function payment_page(){
    stringOfRedirection();
    if(property==="civilian"){
        redirectCivilian(string,"payment.html");
    }else if(property==="merchant"){
        redirectMerchant(string,"payment.html");
    }else if(property==="company"){
        redirectCompany(string,"payment.html");
    }else{
        redirectEmployee(string,"payment.html");
    }
}
function questions_page(){
    stringOfRedirection();
    if(property==="civilian"){
        redirectCivilian(string,"questions.html");
    }else if(property==="merchant"){
        redirectMerchant(string,"questions.html");
    }else if(property==="company"){
        redirectCompany(string,"questions.html");
    }else{
        redirectEmployee(string,"questions.html");
    }
}

function usersStatus_page() {
    if (property === "civilian")
        redirectCivilian(string,"usersStatus.html");
    else if (property === "merchant")
        redirectMerchant(string,"usersStatus.html");
    else if (property === "company")
        redirectCompany(string,"usersStatus.html");
}

function traderOfMonth_page() {
    if (property === "civilian")
        redirectCivilian(string,"traderOfMonth.html");
    else if (property === "merchant")
        redirectMerchant(string,"traderOfMonth.html");
    else if (property === "company")
        redirectCompany(string,"traderOfMonth.html");
}

function back(){
    stringOfRedirection();
    if(property==="civilian"){
        redirectCivilian(string,"Options.html");
    }else if(property==="merchant"){
        redirectMerchant(string,"Options.html");
    }else if(property==="company"){
        redirectCompany(string,"Options.html");
    }else{
        redirectEmployee(string,"Options.html");
    }
}

function redirectCivilian(string,location){
    var instance=JSON.parse(string);
    var status = JSON.parse(instance);
    var keys = Object.keys(status);


    $("#signIn").load(location, function () {
        document.getElementById("name_prof").innerText =":"+ status[keys[1]]+ " (CIVILIAN)";
        document.getElementById("limit").innerText ="Credit limit: "+ status[keys[4]]+"$";
        document.getElementById("owed").innerText ="Amount owed: "+ status[keys[5]]+"$";
        document.getElementById("balance").innerText ="Available balance: "+ status[keys[6]]+"$";
    });
}

function redirectMerchant(string,location){
    var instance=JSON.parse(string);
    var status = JSON.parse(instance);
    var keys = Object.keys(status);
    console.log(instance);

    $("#signIn").load(location, function () {
        document.getElementById("name_prof").innerText =":"+ status[keys[1]]+ " (MERCHANT)";
        document.getElementById("limit").innerText ="Supply: "+ status[keys[3]]+"$";
        document.getElementById("owed").innerText ="Amount owed: "+ status[keys[5]]+"$";
        document.getElementById("balance").innerText ="Total profit: "+ status[keys[4]]+"$";
    });
}

function redirectCompany(string,location){
    var instance=JSON.parse(string);
    var status = JSON.parse(instance);
    var keys = Object.keys(status);
    console.log(instance);

    $("#signIn").load(location, function () {
        document.getElementById("name_prof").innerText =":"+ status[keys[3]]+ " (COMPANY)";
        document.getElementById("limit").innerText ="Credit limit: "+ status[keys[6]]+"$";
        document.getElementById("owed").innerText ="Amount owed: "+ status[keys[7]]+"$";
        document.getElementById("balance").innerText ="Available balance: "+ status[keys[8]]+"$";
    });
}

function redirectEmployee(string,location){
    var instance=JSON.parse(string);
    var status = JSON.parse(instance);
    var keys = Object.keys(status);
    console.log(instance);

    $("#signIn").load(location, function () {
        document.getElementById("name_prof").innerText =":"+ status[keys[2]]+ " (EMPLOYEE)";
        document.getElementById("limit").innerText ="Credit limit: "+ status[keys[5]]+"$";
        document.getElementById("owed").innerText ="Amount owed: "+ status[keys[6]]+"$";
        document.getElementById("balance").innerText ="Available balance: "+ status[keys[7]]+"$\n" +
            "Company: "+ status[keys[1]];
    });
}

function logout(){
    console.log("logout");
    $("#signIn").load("signIn.html");
}