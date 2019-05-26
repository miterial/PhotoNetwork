$( document ).ready(function() {

    $(".booking").on('click', function () {

        var userId = $("#account").attr("data-id");
        var serviceId = $(this).parents("tr").data("id");

        $("#serviceNameModal").attr("data-id-user", userId);
        $("#servicePriceModal").attr("data-id-service", serviceId);

        $("#serviceNameModal").text($(this).parents("tr").find(".serviceName").text());
        $("#servicePriceModal").text($(this).parents("tr").find(".servicePrice").text());

    });

    $(".delete").on('click', function () {

        var bookingId = $(this).parents("tr").attr("data-id");

        $.ajax({
            url: '/api/booking/remove/' + bookingId,
            type: 'POST',
            success: function () {
                console.log("changed status!");
                document.location.reload(true);
            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log("not changed status");
                showDangerToast("Не удалось отменить услугу!");
            }
        });

    });

    $(".confirm").on('click', function () {

        var bookingId = $(this).parents("tr").attr("data-id");
        var statusId = $(this).parents("tr").find(".serviceStatus").attr("data-id");

        var data = {};
        data["bookingId"] = bookingId;
        data["prevStatusId"] = statusId;
        console.log(data);

        $.ajax({
            url: '/api/booking/status',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function () {
                console.log("changes status!");
                document.location.reload(true);
            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log("not changed status");
                showDangerToast("Не удалось обновить статус услуги");
            }
        });

    });


    function fillPaymentModal(context) {

        var userId = $("#account").attr("data-id");
        var serviceId = $(context).parents("tr").data("id");

        $("#serviceNameModal").attr("data-id-user", userId);
        $("#servicePriceModal").attr("data-id-service", serviceId);

        $("#serviceNameModal").text($(context).parents("tr").find(".serviceName").text());
        $("#servicePriceModal").text($(context).parents("tr").find(".servicePrice").text());
    }

    $("#bookingBtn").on('click', function () {

        var masterId = $("#serviceNameModal").attr("data-id-user");
        var serviceId = $("#servicePriceModal").attr("data-id-service");

        var date = $(".bookingdate").val();

        if (date === "") {
            console.log("empty date, not booked");
            return;
        }

        var data = {};
        data["masterId"] = masterId;
        data["serviceId"] = serviceId;
        data["datetime"] = date;
        console.log(data);

        $.ajax({
            url: '/api/booking',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function () {
                console.log("booked!");
                $(".modal").modal("toggle");
                showSuccessToast("Услуга забронирована!");
            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log("not booked");
                showDangerToast("Не удалось забронировать услугу! Возможно, такое время уже занято");
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