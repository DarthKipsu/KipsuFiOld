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
        displaying = moveToNextImage(displaying, galleryCount);
    });

    $("div.previous").click(function() {
        displaying = moveToPreviousImage(displaying, galleryCount);
    });

    adjustThumbWidth(displaying, galleryCount);
    $("img.thumb").mouseenter(function() {
        adjustThumbWidth(imgNumber(this), galleryCount);
    });
    $("img.thumb").mouseleave(function() {
        adjustThumbWidth(displaying, galleryCount);
    });

    $(document).keydown(function(e) {
        displaying = addKeyBindings(e, displaying, galleryCount);
    });
});

function imgNumber(img) {
    return parseInt($(img).attr("data-order"));
}

function adjustThumbWidth(displaying, galleryCount) {
    $("img.thumb").removeClass("displaying");
    $("img.thumb:nth-child(" + displaying + ")").addClass("displaying");
    var thumbContainWidth = $("div.thumbnails").width();
    if (thumbContainWidth / 100 < galleryCount) {
        var compressedWidth = (thumbContainWidth - 310) / (galleryCount + 3);
        if (displaying == 1 || displaying == galleryCount) {
            compressedWidth = (thumbContainWidth - 210) / (galleryCount + 1);
        } else if (displaying == 2 || displaying == galleryCount - 1) {
            compressedWidth = (thumbContainWidth - 310) / galleryCount;
        } else if (displaying == 3 || displaying == galleryCount - 2) {
            compressedWidth = (thumbContainWidth - 310) / (galleryCount + 2);
        }
        if (compressedWidth > 100 / 3) {
            compressedWidth = (thumbContainWidth - 110) / (galleryCount - 1);
            $("img.thumb").width(compressedWidth);
            $("img.thumb:nth-child(" + displaying + ")").width(100);
        } else {
            $("img.thumb").width(compressedWidth);
            $("img.thumb:nth-child(" + displaying + ")").width(100);
            $("img.thumb:nth-child(" + (displaying + 1) + ")").width(100);
            $("img.thumb:nth-child(" + (displaying + 2) + ")").width(compressedWidth * 3);
            $("img.thumb:nth-child(" + (displaying + 3) + ")").width(compressedWidth * 2);
            $("img.thumb:nth-child(" + (displaying - 1) + ")").width(100);
            $("img.thumb:nth-child(" + (displaying - 2) + ")").width(compressedWidth * 3);
            $("img.thumb:nth-child(" + (displaying - 3) + ")").width(compressedWidth * 2);
        }
    }
}

function addKeyBindings(e, displaying, galleryCount) {
    switch (e.which) {
        case 37:
        case 65:
            return moveToPreviousImage(displaying, galleryCount);
        break;

        case 39:
        case 68:
            return moveToNextImage(displaying, galleryCount);
        break;

        default: return displaying;
    }
    e.preventDefault();
}

function moveToNextImage(displaying, galleryCount) {
    $(".gallery-selector:nth-child(" + displaying + ")").hide();
    displaying++;
    if (displaying > galleryCount) displaying = 1;
    $(".gallery-selector:nth-child(" + displaying + ")").show();
    adjustThumbWidth(displaying, galleryCount);
    return displaying;
}

function moveToPreviousImage(displaying, galleryCount) {
    $(".gallery-selector:nth-child(" + displaying + ")").hide();
    displaying--;
    if (displaying === 0) displaying = galleryCount;
    $(".gallery-selector:nth-child(" + displaying + ")").show();
    adjustThumbWidth(displaying, galleryCount);
    return displaying;
}
