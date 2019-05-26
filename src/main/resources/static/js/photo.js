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