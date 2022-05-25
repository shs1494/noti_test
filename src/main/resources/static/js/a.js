$(document).ready(function () {
    $("#mailSender").on("click", mailSender);
    // $("span[name='resultId']").on("click", test);
});

function mailSender() {
    $.ajax({
        url: "/totalEmail",
        type: "post",
        contentType: 'application/json',
        data: checkBoxValue(),
        dataType: "html",
        success: function (result) {
            var mailResult = document.createElement('tr');
            mailResult.innerHTML = result;
            $("#includedContent").prepend(mailResult);
        }
    });
}

function checkBoxValue() {
    var receiveTypes = [];
    $("input[name='receiveType']:checked").each(function() {
    receiveTypes.push($(this).attr('value'));
    });

    var data = {
        mailType : $("input[name='mailType']:checked").val(),
        receiveTypes: receiveTypes
    };

    var jsonData = JSON.stringify(data);
    console.log(jsonData);

    return jsonData;
}
