$(document).ready(function () {

    $("#img").on("change",function (event) {
        event.preventDefault();
        var value = $(this).val();
        if(value != null && value.length >= 4 && value.length <= 255) {
            $.ajax({
                type: "POST",
                url: "/admin/gallery/image/upload",
                data: new FormData($("#gallery-form")[0]),
                contentType: false,
                processData: false,
                success: function (data) {
                    if(data){
                        loadGalleries(1);
                    }
                },
                error: function (error) {
                    console.log(error)
                }
            })
        }
    });

    $(document).on("click",".delete-gallery",function () {
        var id = $(this).attr("id");
        deleteGallery(id);
    })
});

function loadGalleries(page) {
    $.ajax({
        type: "GET",
        url: "/gallery",
        data: {token: "token",page: page},
        success: function (data) {
            $("#gallery-blog_").empty();
            $("#gallery-blog_").html(data);
        },
        error: function () {
            window.location = "/error";
        }
    })
}
function deleteGallery(id) {
    $.ajax({
        type: "POST",
        url: "/admin/gallery/delete/"+ id,
        success: function (data) {
            if(data){
                loadGalleries($("#active-page").text())
            }
        },
        error: function () {

        }
    })
}