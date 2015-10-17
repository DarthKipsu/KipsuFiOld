$( document ).ready(function() {
    var displaying = 1;
    var galleryCount = $(".gallery-selector").length;
    $(".gallery-selector:not(:nth-child(" + displaying + ")").hide();
    $("img.thumb").click(function() {
        $(".gallery-selector:nth-child(" + displaying + ")").hide();
        displaying = imgNumber(this);
        $(".gallery-selector:nth-child(" + displaying + ")").show();
    });

    $("div.next").click(function() {
        $(".gallery-selector:nth-child(" + displaying + ")").hide();
        displaying++;
        if (displaying > galleryCount) displaying = 1;
        $(".gallery-selector:nth-child(" + displaying + ")").show();
        adjustThumbWidth(displaying, galleryCount);
    });

    $("div.previous").click(function() {
        $(".gallery-selector:nth-child(" + displaying + ")").hide();
        displaying--;
        if (displaying === 0) displaying = galleryCount;
        $(".gallery-selector:nth-child(" + displaying + ")").show();
        adjustThumbWidth(displaying, galleryCount);
    });

    adjustThumbWidth(displaying, galleryCount);
    $("img.thumb").mouseenter(function() {
        adjustThumbWidth(imgNumber(this), galleryCount);
    });
    $("img.thumb").mouseleave(function() {
        adjustThumbWidth(displaying, galleryCount);
    });
});

function imgNumber(img) {
    return parseInt($(img).attr("data-order"));
}

function adjustThumbWidth(displaying, galleryCount) {
    var thumbContainWidth = $("div.thumbnails").width();
    if (thumbContainWidth / 100 < galleryCount) {
        var compressedWidth = (thumbContainWidth - 100) / galleryCount;
        $("img.thumb").width(compressedWidth);
        $("img.thumb:nth-child(" + displaying + ")").width(100);
    }
}
