$( document ).ready(function() {

    $("#saveServices").on('click', function () {

        var services = [];

        $(".service").each(function () {
            if($(".service").prop('checked')) {
                var temp = {};
                temp["name"] = $(".service").attr('name');
                temp["price"] = $(".service").closest("div").find("input[name='price']").val();
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


});