$( document ).ready(function() {

    var prevServiceCount = $('.services:checked').length;

    $("#saveServices").on('click', function () {

        var newServices = [];

        $(".services").each(function () {
            if($(this).is(":checked")) {
                var temp = {};
                temp["name"] = $(this).attr('name');
                temp["price"] = $(this).closest("div").find("input[name='price']").val();
                if(temp["price"] === "0.0") {
                    showDangerToast("Услуги без цены не будут добавлены");
                    return;
                }
                if(temp["price"] <= "0.0") {
                    showDangerToast("Цена должна быть > 0");
                    return;
                }
                newServices.push(temp);
            }
        });

        if(prevServiceCount <= newServices.length && newServices.length === 0){
            showDangerToast("Внесите корректные изменения в список сервисов");
            return;
        }

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
                showSuccessToast("Сервисы пользователя обновлены!")
            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                showDangerToast("Не удалось обновить услуги");
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
                showDangerToast("Не удалось подписаться");
            }
        });
    });

    showDangerToast = function(text) {
        'use strict';
        $.toast({
            heading: 'Error',
            text: text,
            showHideTransition: 'slide',
            icon: 'error',
            loaderBg: '#f2a654',
            position: 'top-right'
        })
    };
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