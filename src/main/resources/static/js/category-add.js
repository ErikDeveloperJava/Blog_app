$(document).ready(function () {

    $("#name").on("input",function () {
        var value = $(this).val();
        if(value == null || value.length < 2 || value.length > 255){
            $("#name").css("border","1px solid red");
            $("#nameError").text("in field name wrong data");
        }else {
            $("#name").css("border","1px solid #ccc");
            $("#nameError").text("");
        }
    });
    $("#cat-form").on("submit",function (event) {
        var value = $("#name").val();
        if(value == null || value.length < 2 || value.length > 255){
            event.preventDefault();
            $("#name").css("border","1px solid red");
            $("#nameError").text("in field name wrong data");
        }
    })
});