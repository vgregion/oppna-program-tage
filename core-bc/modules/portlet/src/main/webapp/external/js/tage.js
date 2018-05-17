$( document ).ready(function() {
    $('.hide-ad-link').on('click', function (e) {
        e.preventDefault();
        $('#justConfirmDialog').show();
        $('#maskElement').show();

        $('#justConfirmButton').off('click').on('click', function () {
            $('#justConfirmDialog').hide();
            $('#maskElement').hide();
            location.href = $('.hide-ad-link').attr('data-href');
        });
    });

    $('.hide-ad-link-with-decision').on('click', function (e) {
        e.preventDefault();
        $('#decideWhetherBooked').show();
        $('#maskElement').show();

        $('#decideWhetherBookedButtonNotBooked').off('click').on('click', function () {
            $('#decideWhetherBooked').hide();
            $('#maskElement').hide();
            location.href = $('.hide-ad-link-with-decision').attr('data-href');
        });

        $('#decideWhetherBookedButtonBooked').off('click').on('click', function () {
            $('#decideWhetherBooked').hide();
            $('#maskElement').hide();
            location.href = $('.hide-ad-link-with-decision').attr('data-href-booked');
        });
    });

    $('#maskElement').on('click', function () {
        $('#justConfirmDialog').hide();
        $('#decideWhetherBooked').hide();
        $('#maskElement').hide();
    });
});