$( document ).ready(function() {

    var requestType,
        id,
        url;

    $(".addCategory").on('click', function () {
        requestType = 'POST';
        let name = $(this).parents("tr").find(".new-category").val();
        url = "/api/category?category_name=" + name;
        sendRequest();
    });

    $(".addService").on('click', function () {
        requestType = 'POST';
        let name = $(this).parents("tr").find(".new-service").val();
        url = "/api/service?service_name=" + name;
        sendRequest();
    });

    function sendRequest() {
        $.ajax({
            url: url,
            type: requestType,
            success: function () {
                console.log("success!");
                document.location.reload(true);
            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                showDangerToast("Не удалось добавить данные");
            }
        });
    }
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