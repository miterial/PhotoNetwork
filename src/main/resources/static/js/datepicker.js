$( document ).ready(function() {

    var today = new Date();
    var start = new Date(today.getTime() + (24 * 60 * 60 * 1000));
    start.setHours(9);
    start.setMinutes(0);

    $(".bookingdate").datepicker({
        timepicker: true,
        minDate: start,
        startDate: start,
        minHours: 9,
        maxHours: 18,
        minutesStep: 60
    });

});