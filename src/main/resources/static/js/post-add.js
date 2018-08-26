$(document).ready(function () {
    loadCategories();

    $(".post-input").on("input",function (event) {
        var name = $(this).attr("name");
        var value = $(this).val();
        isValidData(name,value,event)
    });

    $("#category-select").on("change",function (event) {
        var value = $(this).val();
        var name  = $(this).attr("name");
        isValidData(name,value,event)
    });

    $("#post-form").on("submit",function (event) {
        var inputTags = [$("#title"),$("#description"),$("#category-select"),$("#file")];
        $.each(inputTags,function (i, input) {
            var value = $(input).val();
            var name = $(input).attr("name");
            isValidData(name,value,event);
        })
    })
});

function loadCategories() {
    $.ajax({
        type: "GET",
        url: "/categories",
        success: function (data) {
            $.each(data,function (i, category) {
                var opt = "<option value='" + category.id +"'>" + category.name +"</option>"
                $("#category-select").append(opt);
            })
        },
        error: function () {
            window.location = "/error";
        }
    })
};

function isValidData(name,value,event) {
    switch (name) {
        case "title":
            if (value == null || value.length < 2 || value.length > 255) {
                event.preventDefault();
                $("#title").css("border", "1px solid red");
                $("#titleError").text("in field title wrong data");
            } else {
                $("#title").css("border", "1px solid #ccc")
                $("#titleError").text("");

            };
            break;

        case "description":
            if (value == null || value.length < 10) {
                event.preventDefault();
                $("#description").css("border", "1px solid red");
                $("#descriptionError").text("in field description wrong data");
            } else {
                $("#description").css("border", "1px solid #ccc")
                $("#descriptionError").text("");

            };
            break;

        case "image":
            if (value == null || value.length < 4 || value.length > 255) {
                event.preventDefault();
                $("#imageError").text("in field image wrong data");
            } else {
                $("#imageError").text("");

            };
            break;

        case "catId":
            if (value == null || value <= 0) {
                event.preventDefault();
                $("#catIdError").text("please choose a category");
            } else {
                $("#catIdError").text("");

            };
    }
}