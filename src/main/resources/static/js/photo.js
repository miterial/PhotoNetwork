$( document ).ready(function() {

    $("#likeBtn").on('click', function () {
        var action = $(this).text() === "like" ? "like" : "dislike";
        var id = $(this).parents(".row").find("img").data("id");
        $.ajax({
            url: '/api/photo/' + action + "/" + id,
            type: 'POST',
            success: function () {
                console.log("liked/disliked photo!");
                document.location.reload(true);
            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                showDangerToast("Не удалось лайкнуть/дизлайкнуть фото");
            }
        });
    });

    $("#deletePhotoBtn").on('click', function () {

        var id = $(this).parents(".row").find("img").data("id");
        var userId = $(this).parents(".row").find("img").data("user-id");

        $.ajax({
            url: '/api/photo/' + id,
            type: 'DELETE',
            success: function (res) {
                console.log("liked/disliked photo!");
                window.location.replace("/user/" + userId);
            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                showDangerToast("Не удалось удалить фото: " + showSuccessToast.responseText);
            }
        });
    });

    /** формирование ссылки для скачивания */
    var link = $("#photoFile").attr("src");
    $("#downloadBtn").attr("href",link);
    var name = $("#photoFile").attr("data-id");
    var ext = $("#photoFile").attr("src").split(';')[0].split('/')[1];
    $("#downloadBtn").attr("download",name+"."+ext);

    showSuccessToast = function(text) {
        'use strict';
        $.toast({
            heading: 'Success',
            text: text,
            showHideTransition: 'slide',
            icon: 'success',
            loaderBg: '#f96868',
            position: 'top-right'
        })
    };

});