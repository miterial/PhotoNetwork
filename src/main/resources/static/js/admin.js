$( document ).ready(function() {

    var requestType,
        url;


    $(".addCategory").on('click', function () {
        requestType = 'POST';
        var data = {};
        data["name"] = $(this).parents("tr").find(".new-category").val();
        url = "/api/category";
        sendRequest();
    });

    $(".addService").on('click', function () {
        requestType = 'POST';
        data["name"] = $(this).parents("tr").find(".new-service").val();
        url = "/api/service";
        sendRequest();
    });

    $(".updateCategory").on('click', function () {
        requestType = 'PUT';
        var data = {};
        data["name"] = $(this).parents("tr").find(".existing-category").text();
        data["id"] = $(this).parents("tr").attr("data-id");
        url = "/api/category";
        sendRequest();
    });

    $(".updateService").on('click', function () {
        requestType = 'PUT';
        data["name"] = $(this).parents("tr").find(".existing-service").text();
        data["id"] = $(this).parents("tr").attr("data-id");
        url = "/api/service";
        sendRequest();
    });

    $(".deleteCategory").on('click', function () {
        requestType = 'DELETE';
        var data = {};
        data["name"] = $(this).parents("tr").find(".existing-category").text();
        url = "/api/category";
        sendRequest();
    });

    $(".deleteService").on('click', function () {
        requestType = 'DELETE';
        data["name"] = $(this).parents("tr").find(".existing-service").text();
        url = "/api/service";
        sendRequest();
    });

    function sendRequest(data) {
        $.ajax({
            url: url,
            type: requestType,
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function () {
                console.log("success!");
                document.location.reload(true);
            }, error: function (XMLHttpRequest) {
                showDangerToast(XMLHttpRequest.responseText);
            }
        });
    }

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

});