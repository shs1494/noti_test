$(document).ready(function () {
    $("#mailSender").on("click", mailSender);
    $('th,td').addClass("hyeon");
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
            if ($("input[id='eachType']").prop('checked')) {
                mailResultType.innerText = languageChangeByMailType(mailType)+"(개별)";
            } else {
                mailResultType.innerText = languageChangeByMailType(mailType);
            }
            $('tbody').children()[0].append(mailResultType);
            $('td').addClass("hyeon");
        }
    });
}

function languageChangeByMailType(mailType) {
    if (mailType === "mail") {
        return "일반"
    } else if (mailType === "auth-mail") {
        return "인증"
    } else {
        return "광고"
    }
}

function checkBoxValue() {
    var receiveTypes = [];
    var mailAddressList = [];

    $("input[name='receiveType']:checked").each(function() {
        receiveTypes.push($(this).attr('value'));
    });

    $("input[name='emailAddress']").each(function() {
        mailAddressList.push($(this).val())
    });

    var data = {
        mailType : $("input[name='mailType']:checked").val(),
        receiveTypes: receiveTypes,
        eachType : $("input[id='eachType']").prop('checked'),
        mailAddressList : mailAddressList
    };

    var jsonData = JSON.stringify(data);

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

function checkEachTypeByValue(value) {
    if (value == 'auth-mail') {
        $("input:checkbox[id='eachType']").prop("checked", false);
        document.getElementById('eachType').disabled = true;
    } else {
        document.getElementById('eachType').disabled = false;
    }
}

function ccMailAddressCheck() {
    if ($("input:checkbox[id='ccType']").prop("checked")) {
        document.getElementById('ccMailAddr').disabled = false;
    } else {
        $("input[id='ccMailAddr']").val(null);
        document.getElementById('ccMailAddr').disabled = true;
    }
}

function bccMailAddressCheck() {
    if ($("input:checkbox[id='bccType']").prop("checked")) {
        document.getElementById('bccMailAddr').disabled = false;
    } else {
        $("input[id='bccMailAddr']").val(null);
        document.getElementById('bccMailAddr').disabled = true;
    }
}

var re = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;

function verify(id) {
    var email = document.getElementById(id).value;
    if (email == '' || !re.test(email)) {
        alert("올바른 이메일 주소를 입력하세요")
        document.getElementById('mailSender').disabled = true;
    } else {
        document.getElementById('mailSender').disabled = false;
    }
}

