$("#mailSender").on("click", mailSender);

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
