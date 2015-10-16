$( document ).ready(function() {
    var displaying = 1;
    $(".gallery-selector:not(:nth-child(" + displaying + ")").hide();
    $("img.thumb").mouseenter(function() {
        $(".gallery-selector:nth-child(" + displaying + ")").hide();
        displaying = parseInt($(this).attr("data-order"));
        $(".gallery-selector:nth-child(" + displaying + ")").show();
    });
});
