function questionsControl(button){
    //dates
    var fromDate=document.getElementById("froming").value;
    var toDate=document.getElementById("toing").value;

    if(fromDate==="" || toDate===""){
        alert("Dates are Null!");
    }else{
        if(button===1){
            TransactionCustomerMerchant(fromDate,toDate);
        }else if(button===2){
            TransactionAllEmployees(fromDate,toDate);
        }else{
            TransactionSpecificEmployees(fromDate,toDate);
        }
    }
}

function TransactionCustomerMerchant(from,to){
    console.log(from+"  "+to);
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        var message=xhr.responseText;
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#signIn").load("Transactions.html", function () {

                //Show all the Transactions
                var jsonData = JSON.parse(message);
                var customer=[];
                var product=[];
                var merchant=[];
                var date=[];
                var amount=[];
                var type=[];



                /*make html more beautiful*/
                document.getElementById("transactions").innerText="Transaction "+0+":\n\n";
                var div=document.getElementById("transactions").innerText;
                for (var i = 0; i < jsonData.length; i++) {
                    customer[i]=jsonData[i].customer_name;
                    product[i]=jsonData[i].product_name;
                    merchant[i]=jsonData[i].merchant_name;
                    date[i]=jsonData[i].transaction_date;
                    amount[i]=jsonData[i].transaction_amount;
                    type[i]=jsonData[i].transaction_type;
                    document.getElementById("transactions").innerText=document.getElementById("transactions").innerText+"Customer: "+customer[i]+"\n";
                    document.getElementById("transactions").innerText=document.getElementById("transactions").innerText+"Product: "+product[i]+"\n";
                    document.getElementById("transactions").innerText=document.getElementById("transactions").innerText+"Merchant: "+merchant[i]+"\n";
                    document.getElementById("transactions").innerText=document.getElementById("transactions").innerText+"Date: "+date[i]+"\n";
                    document.getElementById("transactions").innerText=document.getElementById("transactions").innerText+"Amount: "+amount[i]+"\n";
                    document.getElementById("transactions").innerText=document.getElementById("transactions").innerText+"Type: "+type[i]+"\n\n";
                    document.getElementById("transactions").innerText=document.getElementById("transactions").innerText+"------------------\n\n\n";
                    if(i+1<jsonData.length){
                        document.getElementById("transactions").innerText=document.getElementById("transactions").innerText+"Transaction "+(i+1)+":\n\n";
                    }
                }
                document.getElementById("transactions").style.fontSize="25px";
                document.getElementById("transactions").style.textAlign="center";
            });
        } else if (xhr.status !== 200) {
            alert(message);
        }
    };
    //name
    var name= document.getElementById("name_prof").innerText.substr(0, document.getElementById("name_prof").innerText.indexOf('('));
    name=name.substring(1,name.length-1);

    //property
    var property=document.getElementById("name_prof").innerText.substring(document.getElementById("name_prof").innerText.indexOf("(")+1,document.getElementById("name_prof").innerText.lastIndexOf(")"));
    property=property.toLowerCase();


    var data="&naming="+name+"&property="+property+"&from="+from+"&to="+to;
    console.log(data);
    xhr.open('POST', 'Questions?' + data);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}

function TransactionAllEmployees(from,to){
    //name
    var name= document.getElementById("name_prof").innerText.substr(0, document.getElementById("name_prof").innerText.indexOf('('));
    name=name.substring(1,name.length-1);

    //property
    var property=document.getElementById("name_prof").innerText.substring(document.getElementById("name_prof").innerText.indexOf("(")+1,document.getElementById("name_prof").innerText.lastIndexOf(")"));
    property=property.toLowerCase();

    if(property==="company") {
        console.log(from + "  " + to);
        var xhr = new XMLHttpRequest();
        xhr.onload = function () {
            var message = xhr.responseText;
            if (xhr.readyState === 4 && xhr.status === 200) {
                console.log("Message    "+message)
                $("#signIn").load("Transactions.html", function () {

                    //Show all the Transactions
                    var jsonData = JSON.parse(message);
                    var customer=[];
                    var product=[];
                    var merchant=[];
                    var date=[];
                    var amount=[];
                    var type=[];



                    /*make html more beautiful*/
                    document.getElementById("transactions").innerText="Company \""+name+"\" ALL employees transactions\n\n\n";

                    document.getElementById("transactions").innerText=document.getElementById("transactions").innerText+"Transaction "+0+":\n\n";
                    var div=document.getElementById("transactions").innerText;
                    for (var i = 0; i < jsonData.length; i++) {
                        customer[i]=jsonData[i].customer_name;
                        product[i]=jsonData[i].product_name;
                        merchant[i]=jsonData[i].merchant_name;
                        date[i]=jsonData[i].transaction_date;
                        amount[i]=jsonData[i].transaction_amount;
                        type[i]=jsonData[i].transaction_type;
                        document.getElementById("transactions").innerText=document.getElementById("transactions").innerText+"Customer: "+customer[i]+"\n";
                        document.getElementById("transactions").innerText=document.getElementById("transactions").innerText+"Product: "+product[i]+"\n";
                        document.getElementById("transactions").innerText=document.getElementById("transactions").innerText+"Merchant: "+merchant[i]+"\n";
                        document.getElementById("transactions").innerText=document.getElementById("transactions").innerText+"Date: "+date[i]+"\n";
                        document.getElementById("transactions").innerText=document.getElementById("transactions").innerText+"Amount: "+amount[i]+"\n";
                        document.getElementById("transactions").innerText=document.getElementById("transactions").innerText+"Type: "+type[i]+"\n\n";
                        document.getElementById("transactions").innerText=document.getElementById("transactions").innerText+"------------------\n\n\n";
                        if(i+1<jsonData.length){
                            document.getElementById("transactions").innerText=document.getElementById("transactions").innerText+"Transaction "+(i+1)+":\n\n";
                        }
                    }
                    document.getElementById("transactions").style.fontSize="25px";
                    document.getElementById("transactions").style.textAlign="center";
                });
            } else if (xhr.status !== 200) {
                alert(message);
            }
        };
        var data = "&naming=" + name + "&from=" + from + "&to=" + to;
        console.log(data);
        xhr.open('GET', 'Questions?' + data);
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xhr.send();
    }else{
        alert("Cannot access!");
    }
}

function TransactionSpecificEmployees(from,to){
    console.log(" dayto rain");
    //name
    var name= document.getElementById("name_prof").innerText.substr(0, document.getElementById("name_prof").innerText.indexOf('('));
    name=name.substring(1,name.length-1);
    //property
    var property=document.getElementById("name_prof").innerText.substring(document.getElementById("name_prof").innerText.indexOf("(")+1,document.getElementById("name_prof").innerText.lastIndexOf(")"));
    property=property.toLowerCase();

    if(property==="company") {
        var emp1=document.getElementById("emp1").value;

        if(emp1===""){
            alert("input is Empty!");
        }else{
            var xhr = new XMLHttpRequest();
            xhr.onload = function () {
                var message = xhr.responseText;
                if (xhr.readyState === 4 && xhr.status === 200) {
                    $("#signIn").load("Transactions.html", function () {

                        //Show all the Transactions
                        var jsonData = JSON.parse(message);
                        var customer=[];
                        var product=[];
                        var merchant=[];
                        var date=[];
                        var amount=[];
                        var type=[];



                        /*make html more beautiful*/
                        document.getElementById("transactions").innerText="Employee \""+emp1+"\" transactions\n\n\n";

                        document.getElementById("transactions").innerText=document.getElementById("transactions").innerText+"Transaction "+0+":\n\n";
                        var div=document.getElementById("transactions").innerText;
                        for (var i = 0; i < jsonData.length; i++) {
                            customer[i]=jsonData[i].customer_name;
                            product[i]=jsonData[i].product_name;
                            merchant[i]=jsonData[i].merchant_name;
                            date[i]=jsonData[i].transaction_date;
                            amount[i]=jsonData[i].transaction_amount;
                            type[i]=jsonData[i].transaction_type;
                            document.getElementById("transactions").innerText=document.getElementById("transactions").innerText+"Customer: "+customer[i]+"\n";
                            document.getElementById("transactions").innerText=document.getElementById("transactions").innerText+"Product: "+product[i]+"\n";
                            document.getElementById("transactions").innerText=document.getElementById("transactions").innerText+"Merchant: "+merchant[i]+"\n";
                            document.getElementById("transactions").innerText=document.getElementById("transactions").innerText+"Date: "+date[i]+"\n";
                            document.getElementById("transactions").innerText=document.getElementById("transactions").innerText+"Amount: "+amount[i]+"\n";
                            document.getElementById("transactions").innerText=document.getElementById("transactions").innerText+"Type: "+type[i]+"\n\n";
                            document.getElementById("transactions").innerText=document.getElementById("transactions").innerText+"------------------\n\n\n";
                            if(i+1<jsonData.length){
                                document.getElementById("transactions").innerText=document.getElementById("transactions").innerText+"Transaction "+(i+1)+":\n\n";
                            }
                        }
                        document.getElementById("transactions").style.fontSize="25px";
                        document.getElementById("transactions").style.textAlign="center";
                    });
                } else if (xhr.status !== 200) {
                    alert(message);
                }
            };
            var data = "&naming=" + name + "&from=" + from + "&to=" + to +"&emp1="+emp1;
            console.log(data);
            xhr.open('POST', 'LastQuestion?' + data);
            xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xhr.send();
        }
    }else{
        alert("Cannot access!");
    }
}

function goTo(){
    $("#signIn").load("signIn.html");
}