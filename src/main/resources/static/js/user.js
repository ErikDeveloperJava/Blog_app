$(document).ready(function () {

    $(document).on("click",".delete-user",function (event) {
        event.preventDefault();
        var id = $(this).attr("id");
        deleteUser(id);
    })
});
function deleteUser(id) {
    $.ajax({
        type: "POST",
        url: "/admin/user/delete/" + id,
        data: {id: id},
        success: function (data) {
            if(data){
                loadUsers(parseInt($("#active-page").text()))
            }
        },
        error :function () {
            window.location = "/error";
        }
    })
}

function loadUsers(page) {
    $.ajax({
        type: "GET",
        url: "/admin",
        data: {page: page,token: "token"},
        success: function (data) {
            $("#user-blog").empty();
            $("#user-blog").html(data);
        },
        error: function () {
            window.location = "/error";
        }
    })
}