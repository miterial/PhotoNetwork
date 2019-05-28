$( document ).ready(function() {

    var requestType,
        url;

    var updateData = {};


    $(".addCategory").on('click', function () {
        requestType = 'POST';
        var data = {};
        data["name"] = $(this).parents("tr").find(".new-category").val();
        url = "/api/admin/category";
        sendRequest(data);
    });

    $(".addService").on('click', function () {
        requestType = 'POST';
        var data = {};
        data["name"] = $(this).parents("tr").find(".new-service").val();
        url = "/api/admin/service";
        sendRequest(data);
    });

    $(".updateCategory").on('click', function () {
        $("#adminModal #oldNameModal").text($(this).parents("tr").find(".existing-category").text());
        $("#adminModal #oldNameModal").attr("data-id", $(this).parents("tr").attr("data-id"));

        requestType = 'PUT';
        url = "/api/admin/category";
    });

    $(".updateService").on('click', function () {
        $("#adminModal #oldNameModal").text($(this).parents("tr").find(".existing-service").text());
        $("#adminModal #oldNameModal").attr("data-id", $(this).parents("tr").attr("data-id"));

        requestType = 'PUT';
        url = "/api/admin/service";
    });

    $(".deleteCategory").on('click', function () {
        requestType = 'DELETE';
        var data = {};
        data["name"] = $(this).parents("tr").find(".existing-category").text();
        url = "/api/admin/category";
        sendRequest(data);
    });

    $(".deleteService").on('click', function () {
        requestType = 'DELETE';
        var data = {};
        data["name"] = $(this).parents("tr").find(".existing-service").text();
        url = "/api/admin/service";
        sendRequest(data);
    });

    $("#updateBtn").on('click', function () {
        updateData["name"] = document.getElementById('newName').value;
        updateData["id"] = $("#oldNameModal").attr("data-id");
        sendRequest(updateData);
        updateData = {};
    });

    function sendRequest(data) {

        if(data["name"] === '' && requestType !== 'DELETE') {
            showDangerToast("Название не может быть пустым");
            return;
        }

        $.ajax({
            url: url,
            type: requestType,
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (res) {
                showSuccessToast(res);
                $("#adminModal").modal("hide");
            }, error: function (XMLHttpRequest) {
                showDangerToast(XMLHttpRequest.responseText);
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