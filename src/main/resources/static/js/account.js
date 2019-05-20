$( document ).ready(function() {

    $(".datepickers-container").css('z-index',1055);

    $("#saveServices").on('click', function () {

        var services = [];

        $(".services").each(function () {
            if($(".services").prop('checked')) {
                var temp = {};
                temp["name"] = $(this).attr('name');
                temp["price"] = $(this).closest("div").find("input[name='price']").val();
                if(temp["price"] === "") {
                    console.log("fill in the price!")
                    return false;
                }
                services.push(temp);
            }
        });

        if(services.length === 0) {
            console.log("choose at least one service")
            return false;
        }

        var data = {};
        data["services"] = services;
        data["userId"] = $("#saveServices").parents(".row").attr("data-id");
        console.log(data);

        $.ajax({
            url: '/api/account/services',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function () {
                console.log("changed services of user!");
                //showSuccessToast("New blacklist created!");
            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                //showDangerToast("Error creating blacklist");
            }
        });
    });


    $("#subscribeBtn").on('click', function () {

        var id = $("#subscribeBtn").parents(".row").attr("data-id");
        $.ajax({
            url: '/api/account/subscribe/' + id,
            type: 'POST',
            success: function () {
                console.log("subscribed!");
                //showSuccessToast("New blacklist created!");
            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                //showDangerToast("Error creating blacklist");
            }
        });
    });


    $(".booking").on('click', function () {

        var userId = $("#account").attr("data-id");
        var serviceId = $(this).parents("tr").data("id");

        $("#serviceNameModal").attr("data-id-user", userId);
        $("#servicePriceModal").attr("data-id-service", serviceId);

        $("#serviceNameModal").text($(this).parents("tr").find(".serviceName").text());
        $("#servicePriceModal").text($(this).parents("tr").find(".servicePrice").text());

    });

    $("#bookingBtn").on('click', function () {

        var masterId = $("#serviceNameModal").attr("data-id-user");
        var serviceId = $("#servicePriceModal").attr("data-id-service");;
        var date = $(".datepicker-here").val();

        var data = {};
        data["masterId"] = masterId;
        data["serviceId"] = serviceId;
        data["date"] = date;

        console.log(data);

        $.ajax({
            url: '/api/account/booking',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function () {
                console.log("booked!");
                //showSuccessToast("New blacklist created!");
            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                //showDangerToast("Error creating blacklist"); todo отследить ошибку 400 и 409
            }
        });
    });
});