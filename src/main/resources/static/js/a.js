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
        url: "/email",
        type: "get",
        data: {
            // type: document.getElementById('result')
        },
        dataType: "text",
        success: function (data) {
            $("#mailResult").text(data);
        }
    });
}

function checkBoxValue() {
    const query = 'input[name="type"]:checked';
    const selectedEls = document.querySelectorAll(query);
    let result = selectedEls.values();
}