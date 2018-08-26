$(document).ready(function () {

    $(".login-input").on("input",function (event) {
        var name = $(this).attr("name");
        var value = $(this).val();
        isValidData(name,value,event);
    });

    $("#login-form").on("submit",function (event) {
        var inputTags = [$("#username"),$("#password")];
        $.each(inputTags,function (i, input) {
            var name = $(input).attr("name");
            var value = $(input).val();
            isValidData(name,value,event);
        })
    })
});

function isValidData(name, value, event) {
    switch (name){
        case "username":
            if(value == null || value.length < 2 || value.length > 255){
                event.preventDefault();
                $("#username").css("border","1px solid red")
            }else {
                $("#username").css("border","1px solid #ccc")
            };
            break;

        case "password":
            if(value == null || value.length < 4 || value.length > 255){
                event.preventDefault();
                $("#password").css("border","1px solid red")
            }else {
                $("#password").css("border","1px solid #ccc")
            };

    }
}