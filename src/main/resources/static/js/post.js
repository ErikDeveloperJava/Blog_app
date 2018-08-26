$(document).ready(function () {

    $(document).on("click",".delete-post",function (event) {
        event.preventDefault();
        var id = $(this).attr("id");
        $.ajax({
            type: "POST",
            url: "/admin/post/delete/" + id,
            success: function (data) {
                if(data){
                    loadPosts($("#active-page").text())
                }
            },
            error: function () {
                window.location = "/error";
            }
        })
    })
});

function loadPosts(page) {
    $.ajax({
        type: "GET",
        url: "/admin/posts",
        data: {page: page,token: "token"},
        success: function (data) {
            $("#post-blog").empty();
            $("#post-blog").html(data)
        },
        error: function () {
            window.location = "/error";
        }
    })
}