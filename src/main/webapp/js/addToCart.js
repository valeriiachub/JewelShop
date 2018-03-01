$(document).ready(function () {
    $('.addToCart').click(function () {
        $.ajax({
            url: 'addToCart',
            type: 'POST',
            data: {
                productInfo: $(this).prev().val()
            },
            success: function (message) {
               $(".cart").text(message);
            }
        });
    });
});