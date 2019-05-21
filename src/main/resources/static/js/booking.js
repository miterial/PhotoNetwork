$( document ).ready(function() {

    $(".booking").on('click', function () {

        var userId = $("#account").attr("data-id");
        var serviceId = $(this).parents("tr").data("id");

        $("#serviceNameModal").attr("data-id-user", userId);
        $("#servicePriceModal").attr("data-id-service", serviceId);

        $("#serviceNameModal").text($(this).parents("tr").find(".serviceName").text());
        $("#servicePriceModal").text($(this).parents("tr").find(".servicePrice").text());

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
                //showSuccessToast("New blacklist created!");
            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log("not booked")
                //showDangerToast("Error creating blacklist"); todo отследить ошибку 400 и 409
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
                //showSuccessToast("New blacklist created!");
            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log("not booked")
                //showDangerToast("Error creating blacklist"); todo отследить ошибку 400 и 409
            }
        });
    });
});