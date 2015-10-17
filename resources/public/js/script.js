$( document ).ready(function() {
    var displaying = 1;
    var galleryCount = $(".gallery-selector").length;
    $(".gallery-selector:not(:nth-child(" + displaying + ")").hide();
    $("img.thumb").mouseenter(function() {
        $(".gallery-selector:nth-child(" + displaying + ")").hide();
        displaying = parseInt($(this).attr("data-order"));
        $(".gallery-selector:nth-child(" + displaying + ")").show();
    });

    $("div.next").click(function() {
        $(".gallery-selector:nth-child(" + displaying + ")").hide();
        displaying++;
        if (displaying > galleryCount) displaying = 1;
        $(".gallery-selector:nth-child(" + displaying + ")").show();
    });

    $("div.previous").click(function() {
        $(".gallery-selector:nth-child(" + displaying + ")").hide();
        displaying--;
        if (displaying === 0) displaying = galleryCount;
        $(".gallery-selector:nth-child(" + displaying + ")").show();
    });
});
