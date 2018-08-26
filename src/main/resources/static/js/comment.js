$(document).ready(function () {
    var postId = $("#postId").attr("content");
    loadCommentsCount(postId);
    loadComments(postId);

    $(document).on("input",".comment-text",function () {
        var value = $(this).val();
        if(value == null || value.length < 2){
            $(this).css("border","1px solid red")
        }else {
            $(this).css("border","1px solid #ccc")
        }
    });

    $(document).on("click",".reply-comment",function (event) {
        event.preventDefault();
        var title = $(this).attr("title");
        $(".reply-comment").attr("title"," ");
        $(".reply-comment-form").remove();
        var parentId = $(this).attr("id");
        if(title == " "){
            var commentForm = "<div class='reply-comment-form' style='position:relative;left: 53px;top:4px'>" +
                "<form class='form-horizontal contact-form comment-form' method='post'  action='post/comment/add'>" +
                "<input type='hidden' class='parent-id' value='" + parentId + "'>" +
                "<div  class='form-group'>" +
                "<div class='col-md-12'>" +
                "<textarea style='width: 200px;height:50px' class='form-control comment-text' rows='6' placeholder='Write your comment' ></textarea>" +
                "</div>" +
                "</div>" +
                "<button type='submit' class='btn send-btn'>send</button>" +
                "</form>" +
                "</div>";
            $("#" + parentId + "-child-blog").after(commentForm);
            $(this).attr("title","");
        }else {
            $(this).attr("title"," ");
        }
    });

    $(document).on("submit",".comment-form",function (event) {
        event.preventDefault();
        var commentText = $(this).children(".form-group").children().children();
        var value = commentText.val();
        if(value == null || value.length < 2){
            $(commentText).css("border","1px solid red")
        }else {
            var parentId = $(this).children(".parent-id").val();
            $.ajax({
                type: "POST",
                url: "/post/comment/add",
                data: {commentText: value,"post.id": postId,"parent.id": parentId},
                success: function (comment) {
                    if(comment.id > 0){
                        var commentDiv = "";
                        if(parentId > 0){
                            commentDiv+="<div style='position: relative;left: 25px;' class='single-comment single-comment-reply'>";
                        }else {
                            commentDiv+="<div class='single-comment'>";
                        }
                        commentDiv +=
                            "<div class='media'>" +
                            "<div class='media-left text-center'>" +
                            "<a style='cursor: pointer'><img height='40px' width='41px' class='media-object' src='/resources/users/" +   comment.user.imgUrl+ "' alt=''></a>" +
                            "</div>" +
                            "<div class='media-body'>" +
                            "<div class='media-heading'>" +
                            "<h3 class='text-uppercase'>" +
                            "<a style='cursor: pointer'>" +  comment.user.name + " " + comment.user.surname +  "</a>" +
                            "<a  title=' ' style='cursor: pointer;position: relative;left: 20px;color: #aeada6;' id='" + comment.id + "' class='reply-comment'>reply</a>" +
                            "</h3>" +
                            "</div>" +
                            "<p class='comment-date'>" + formatCommentDate(new Date(comment.sendDate)) + "</p>" +
                            "<p class='comment-p'>" + comment.commentText + "</p>" +
                            "</div>" +
                            "</div>" +
                            "<div  id='" + comment.id +"-child-blog'></div>" +
                            "</div>";
                        if(parentId > 0){
                            $("#" + parentId + "-child-blog").append(commentDiv);
                        }else {
                            $("#comment-blog").append(commentDiv)
                        }
                    }
                    $(commentText).val("")
                    $("#comments-count").text((parseInt($("#comments-count").text()) + 1) + " ")
                },
                error: function () {
                    window.location = "/error";
                }
            })
        }
    })
});

function loadCommentsCount(postId) {
    $.ajax({
        type: "GET",
        url: "/post/comments/count",
        data: {postId: postId},
        success: function (data) {
            $("#comments-count").text(data + " ");
        },
        error: function () {
            window.location = "/error";
        }
    })
}

function loadComments(postId) {
    $.ajax({
        type: "GET",
        url: "/post/comments",
        data: {postId: postId},
        success: function (data) {
            $.each(data,function (i, commentForm) {
                var parentDiv = "<div class='single-comment'>" +
                    "<div class='media'>" +
                    "<div class='media-left text-center'>" +
                    "<a style='cursor: pointer'><img height='40px' width='41px' class='media-object' src='/resources/users/" +   commentForm.comment.user.imgUrl+ "' alt=''></a>" +
                    "</div>" +
                    "<div class='media-body'>" +
                    "<div class='media-heading'>" +
                    "<h3 class='text-uppercase'>" +
                    "<a style='cursor: pointer'>" +  commentForm.comment.user.name + " " + commentForm.comment.user.surname +  "</a>" +
                    "<a title=' ' style='cursor: pointer;position: relative;left: 20px;color: #aeada6;' id='" + commentForm.comment.id + "' class='reply-comment'>reply</a>" +
                    "</h3>" +
                    "</div>" +
                    "<p class='comment-date'>" + formatCommentDate(new Date(commentForm.comment.sendDate)) + "</p>" +
                    "<p class='comment-p'>" + commentForm.comment.commentText + "</p>" +
                    "</div>" +
                    "</div>";
                parentDiv = setChildrens(commentForm,false,parentDiv)
                $("#comment-blog").append(parentDiv);
            })
        },
        error: function () {
            window.location = "/error";
        }
    })
}

function setChildrens(commentForm,token,parentDiv) {
    if(token){
        parentDiv+="<div style='position:relative;left: 25px' class='single-comment single-comment-reply'>" +
            "<div class='media'>" +
            "<div class='media-left text-center'>" +
            "<a style='cursor: pointer'><img height='40px' width='41px' class='media-object' src='/resources/users/" +   commentForm.comment.user.imgUrl+ "' alt=''></a>" +
            "</div>" +
            "<div class='media-body'>" +
            "<div class='media-heading'>" +
            "<h3 class='text-uppercase'>" +
            "<a style='cursor: pointer'>" +  commentForm.comment.user.name + " " + commentForm.comment.user.surname +  "</a>" +
            "<a title=' ' style='cursor: pointer;position: relative;left: 20px;color: #aeada6;' id='" + commentForm.comment.id + "' class='reply-comment'>reply</a>" +
            "</h3>" +
            "</div>" +
            "<p class='comment-date'>" + formatCommentDate(new Date(commentForm.comment.sendDate)) + "</p>" +
            "<p class='comment-p'>" + commentForm.comment.commentText + "</p>" +
            "</div>" +
            "</div>";
    }
    if(commentForm.childrenList.length == 0){
        parentDiv+= "<div id='" +commentForm.comment.id + "-child-blog'>"
    }else {
        parentDiv+="<div id='" +commentForm.comment.id + "-child-blog'>";
    }
    $.each(commentForm.childrenList,function (i, child) {
        parentDiv = setChildrens(child,true,parentDiv);
    })
    parentDiv+="</div>";
    parentDiv+="</div>";
    return parentDiv;

}

function formatCommentDate(date) {
    var day = date.getDate();
    if(day  < 10){
        day = "0" + day;
    }
    var month = date.getMonth() + 1;
    if(month  < 10){
        month = "0" + month;
    }
    var year = date.getFullYear();
    var hours = date.getHours();
    var minute = date.getMinutes();
    return day + "." + month + "." + year + " " + hours + ":" + minute;
}