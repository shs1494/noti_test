$("#normalMail").on("click", normalMailSender);
$("#authMail").on("click", authMailSender);
$("#mailSender").on("click", mailSender);
// document.getElementById("hello").onclick = a;

function normalMailSender() {
    $.ajax({
        url: "/email",
        type: "get",
        data: {
            type: "normal"
        },
        dataType: "text",
        success: function (data) {
             $("#normalMailResult").text(data);
        }
    });
}

function authMailSender() {
    $.ajax({
        url: "/email",
        type: "get",
        data: {
            type: "auth"
        },
        dataType: "text",
        success: function (data) {
            $("#authMailResult").text(data);
        }
    });
}

function mailSender() {
    $.ajax({
        url: "/totalEmail",
        type: "post",
        contentType: 'application/json',
        data: checkBoxValue(),
        dataType: "text",
        success: function (result) {
            $("#mailResult").text(result);
        }
    });
}

function checkBoxValue() {

    var data = {
        'mailType' : document.getElementById("mailType").value,
        'receiveType': document.getElementById("receiveType").value
    };

    var jsonData = JSON.stringify(data);
    console.log(jsonData);

    return jsonData;
}
