$( document ).ready(function() {

    $("#saveServices").on('click', function () {

        var newServices = [];

        $(".services").each(function () {
            if($(this).is(":checked")) {
                var temp = {};
                temp["name"] = $(this).attr('name');
                temp["price"] = $(this).closest("div").find("input[name='price']").val();
                console
                    .log(temp["price"]);
                if(temp["price"] === "0,0") {
                    console.log("fill in the price!")
                    return false;
                }
                newServices.push(temp);
            }
        });

        var data = {};
        data["services"] = newServices;
        data["userId"] = $("#saveServices").parents(".row").attr("data-id");
        console.log(data);

        $.ajax({
            url: '/api/service/services',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function () {
                console.log("changed services of user!");
                //document.location.reload(true);
            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                //showDangerToast("Error creating blacklist");
            }
        });
    });


    $("#subscribeBtn").on('click', function () {

        var id = $("#subscribeBtn").parents(".row").attr("data-id");
        var action = $(this).text() === "Подписаться" ? "subscribe" : "unsubscribe";
        $.ajax({
            url: '/api/account/' + action + '/' + id,
            type: 'POST',
            success: function () {
                console.log("subscribed!");
                document.location.reload(true);
            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                //showDangerToast("Error creating blacklist");
            }
        });
    });


});