$(document).ready(function () {
    $(".register-input").on("input",function (event) {
        var name = $(this).attr("name");
        var value = $(this).val();
        isValidData(name,value,event);
    });

    $("#register-form").on("submit",function (event) {
        var inputTags = [$("#name"),$("#surname"),$("#username"),$("#password"),
        $("#repeatPassword"),$("#file")];
        $.each(inputTags,function (i,input) {
            var name = $(input).attr("name");
            var value = $(input).val();
            isValidData(name,value,event)
        });

    })
});

function isValidData(name,value,event) {
    switch (name){
        case "name":
            if(value == null || value.length < 2 || value.length > 255){
                event.preventDefault();
                $("#name").css("border","1px solid red");
                $("#nameError").text("in field name wrong data");
            }else {
                $("#name").css("border","1px solid #ccc")
                $("#nameError").text("");
            };
            break;
        case "surname":
            if(value == null || value.length < 4 || value.length > 255){
                event.preventDefault();
                $("#surname").css("border","1px solid red");
                $("#surnameError").text("in field surname wrong data");
            }else {
                $("#surname").css("border","1px solid #ccc")
                $("#surnameError").text("");
            };
            break;
        case "username":
            if(value == null || value.length < 2 || value.length > 255){
                event.preventDefault();
                $("#username").css("border","1px solid red");
                $("#usernameError").text("in field username wrong data");
            }else {
                $("#username").css("border","1px solid #ccc");
                $("#usernameError").text("");
            };
            break;
        case "password":
            if(value == null || value.length < 4 || value.length > 255){
                event.preventDefault();
                $("#password").css("border","1px solid red");
                $("#passwordError").text("in field password wrong data");
            }else {
                if(value  != $("#repeatPassword").val()){
                    $("#repeatPassword").css("border","1px solid red");
                    $("#repeatPasswordError").text("passwords does not matches");
                    $("#password").css("border","1px solid red");
                    $("#passwordError").text("passwords does not matches");
                }else {
                    $("#repeatPassword").css("border","1px solid #ccc");
                    $("#repeatPassword").text("");
                    $("#password").css("border","1px solid #ccc");
                    $("#passwordError").text("");
                }
            };
            break;
        case "repeatPassword":
            if(value == null || value.length < 4 || value.length > 255){
                event.preventDefault();
                $("#repeatPassword").css("border","1px solid red");
                $("#repeatPasswordError").text("in field repeat Password wrong data");
            }else {
                if(value  != $("#password").val()){
                    $("#repeatPassword").css("border","1px solid red");
                    $("#repeatPasswordError").text("passwords does not matches");
                    $("#password").css("border","1px solid red");
                    $("#passwordError").text("passwords does not matches");
                }else {
                    $("#repeatPassword").css("border","1px solid #ccc");
                    $("#repeatPasswordError").text("");
                    $("#password").css("border","1px solid #ccc");
                    $("#passwordError").text("");
                }
            };
            break;
        case "image":
            if(value == null || value.length < 4 || value.length > 255){
                event.preventDefault();
                $("#imageError").text("in field image wrong data");
            }else {
                $("#imageError").text("");
            };
            break;
    }
}