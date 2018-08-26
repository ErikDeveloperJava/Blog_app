$(document).ready(function () {
    var token = $("#popularsLoad").attr("content");
    if(token == "true"){
        loadPopularPosts();
    }
    token = $("#galleriesLoad").attr("content");
    if(token == "true"){
        loadGalleries();
    }
    loadCategories();

});

function loadPopularPosts() {
    $.ajax({
        type: "GET",
        url: "/posts/popular",
        success: function (data) {
            $.each(data,function (i, post) {
                var postDiv = "<li>" +
                    "<a href='/post/" + post.id + "' class='popular-img'><img width='100px' height='180px' src='/resources/posts/" + post.imgUrl + "' alt=''>" +
                    "</a>" +
                    "<div class='p-content'>" +
                    "<h4><a href='/post/" + post.id + "' class='text-uppercase'>" + post.title + "</a></h4>" +
                    "<span class='p-date'>" + formatDate(new Date(post.createdDate)) + "</span>" +
                    "</div>" +
                    "</li>";
                $("#popular-posts-blog").append(postDiv);
            })
        },
        error: function () {
            window.location = "/error"
        }
    })
}

function loadCategories() {
    $.ajax({
        type: "GET",
        url: "/categories",
        success: function (data) {
          $.each(data,function (i, category) {
              var catDiv = "<li class=''><a href='/posts/category/" + category.id + "'>"+category.name +"</a></li>";
              $("#cat-blog").append(catDiv);
          })
        },
        error: function () {
            window.location = "/error";
        }
    })
}

function loadGalleries() {
    $.ajax({
        type: "GET",
        url: "/galleries",
        success: function (data) {
            $.each(data,function (i, gallery) {
                var galleryDiv = "<div class='single-instagram'>" +
                    "<img width='170px' height='100px' src='/resources/galleries/" + gallery.imgUrl + "' alt=''>" +
                    "</div>";
                $("#gallery-blog").append(galleryDiv);
            })
        },
        error: function () {
            window.location = "/error";
        }
    })
}

function formatDate(date) {
    var day = date.getDate();
    if(day  < 10){
        day = "0" + day;
    }
    var month = date.getMonth() + 1;
    if(month  < 10){
        month = "0" + month;
    }
    var year = date.getFullYear();
    return day + "." + month + "." + year;
}