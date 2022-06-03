$(document).ready(function () {
    $("#mailSender").on("click", mailSender);
    $('th,td').addClass("hyeon");
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

            var mailType = $("input[name='mailType']:checked").val();
            var mailResultType = document.createElement('td');
            mailResultType.innerText = mailType;
            $('tbody').children()[0].append(mailResultType);
            $('td').addClass("hyeon");
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
        receiveTypes: receiveTypes,
        eachType : $("input[id='eachType']").prop('checked')
    };

    var jsonData = JSON.stringify(data);
    console.log(jsonData);

    return jsonData;
}

function retrieveMail(requestId) {
    console.log(requestId);
    $.ajax({
        url: "/retrieveEmail",
        type: "post",
        contentType: 'application/json',
        data: requestId,
        dataType: "text",
        success: function (retrieveMailResult) {
            console.log(retrieveMailResult);
            alert(retrieveMailResult);
        }
    })
}

function eachMailFalseCheck() {
    // var eachCheck = document.getElementById('eachType').disabled = false;
    document.getElementById('eachType').disabled = false;
    // console.log(eachCheck);
    // eachCheck.disabled = false;
}
function eachMailtrueCheck() {
    $("input:checkbox[id='eachType']").prop("checked", false);
    // document.getElementById('eachType').removeAttribute('checked');
    document.getElementById('eachType').disabled = true;
}
