$( document ).ready(function() {

    $("#likeBtn").on('click', function () {
        var action = $(this).text() === "like" ? "like" : "dislike";
        var id = $(this).parents(".row").find("img").data("id");
        $.ajax({
            url: '/api/photo/' + action + "/" + id,
            type: 'POST',
            success: function () {
                var text = $(this).text() === "like" ? "dislike" : "like";
                $("#likeBtn").val(text);
                console.log("liked/disliked photo!");
                document.location.reload(true);
            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                //showDangerToast("Error creating blacklist");
            }
        });
    });

    $("#modifyBtn").on('click', function () {
        var id = $(this).parents(".row").find("img").data("id");

        $.ajax({
            url: '/upload',
            type: 'GET',
            success: function (resp) {
                $("html").html(resp);
            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                //showDangerToast("Error creating blacklist");
            }
        });
    });

    /** формирование ссылки для скачивания */
    var link = $("#photoFile").attr("src");
    $("#downloadBtn").attr("href",link);
    var name = $("#photoFile").attr("data-id");
    var ext = $("#photoFile").attr("src").split(';')[0].split('/')[1];
    $("#downloadBtn").attr("download",name+"."+ext);

});