function findUsersStatus(operator) {
    var data;
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                var pText;
                var jsonData = JSON.parse(xhr.responseText);
               
                if (operator === "state1") {
                    pText = document.querySelector('.s1-modal p');
                } else {
                    pText = document.querySelector('.s2-modal p');
                }
                
                for (let i = 0; i < jsonData.length; i++) {
                    pText.innerText = pText.innerText + 
                        "[User: " + jsonData[i].name + "]" +  
                        "[Amount: " + jsonData[i].amount_due + "$]" +
                        "\n";
                }
            } else {
                alert("Error " + xhr.status);
            }
        }
    };
    data = "operation=" + operator;
    xhr.open('POST', 'UsersStatus?' + data);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}

//Using an anonymous function to show state1 popup
document.getElementById("get-s1-res-btn").onclick = function () {
    findUsersStatus("state1");
    document.getElementById("s1_modal_container").classList.add('show');
};

//Using an anonymous function to hide state1 popup
document.getElementById("s1-close-res-btn").onclick = function () { 
    document.getElementById("s1_modal_container").classList.remove('show');
    document.querySelector('.s1-modal p').innerText = "";
};

//Using an anonymous function to show state2 popup
document.getElementById("get-s2-res-btn").onclick = function () {
    findUsersStatus("state2");
    document.getElementById("s2_modal_container").classList.add('show');
};

//Using an anonymous function to hide state2 popup
document.getElementById("s2-close-res-btn").onclick = function () { 
    document.getElementById("s2_modal_container").classList.remove('show');
    document.querySelector('.s2-modal p').innerText = "";
};
