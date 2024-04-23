function findTraderOfMonth() {
    var data, xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200)               
                document.querySelector('.trader-modal p').innerText = xhr.responseText;
            else
                document.querySelector('.trader-modal p').innerText =
                "Error " + xhr.status + "\n"
                + "Unable to find trader of the month";
        }
    };
    data = "date=" 
            + document.getElementById("year").value
            + "-"
            + document.getElementById("month").value;
    xhr.open('POST', 'TraderOfMonth?' + data);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}

//Show PopUp
document.getElementById("get-trader-btn").onclick = function () {
    findTraderOfMonth();
    document.getElementById("trader_modal_container").classList.add('show');
};

//Hide PopUp
document.getElementById("close-trader-btn").onclick = function () { 
    document.getElementById("trader_modal_container").classList.remove('show');
    document.querySelector('.trader-modal p').innerText = "";
};
