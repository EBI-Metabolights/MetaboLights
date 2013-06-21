/*  
    http://www.dailycoding.com/ 
*/
(function ($) {
    $.fn.imageLens = function (options) {

        var defaults = {
            lensSize: 100,
            borderSize: 4,
            borderColor: "#888"
        };
        var options = $.extend(defaults, options);
        var lensStyle = "background-position: 0px 0px;width: " + String(options.lensSize) + "px;height: " + String(options.lensSize)
            + "px;float: left;display: none;border-radius: " + String(options.lensSize / 2 + options.borderSize)
            + "px;border: " + String(options.borderSize) + "px solid " + options.borderColor 
            + ";background-repeat: no-repeat;position: absolute;cursor: none";

        return this.each(function () {

            // obj:
            var img = $(this);

            //var offset = $(this).offset();

            // Creating lens
            var target = $("<div style='" + lensStyle + "' class='" + options.lensCss + "'>&nbsp;</div>").appendTo($(this).parent());
            //var targetSize = target.size();

            // Calculating actual size of image
            var imageSrc = options.imageSrc ? options.imageSrc : $(this).attr("src");
            var imageTag = "<img style='display:none' src='" + imageSrc + "' />";

            var naturalWidth = 0;
            var naturalHeight= 0;

            $(imageTag).load(function () {
                naturalWidth = this.naturalWidth;
                naturalHeight = this.naturalHeight;
            }).appendTo($(this).parent());

            target.css({ backgroundImage: "url('" + imageSrc + "')" });

            target.mousemove(setPosition);
            $(this).mousemove(setPosition);
//            $(target).click(setPosition);
//            $(this).click (setPosition);


            function setPosition(e) {

                var x = e.pageX;
                var y = e.pageY;

                var offset = img.offset();
                var leftPos = parseInt(x - offset.left);
                var topPos = parseInt(y - offset.top);

                var widthRatio = naturalWidth / img.width();
                var heightRatio = naturalHeight / img.height();

                if (leftPos < 0 || topPos < 0 || leftPos > img.width() || topPos > img.height()) {
                    target.hide();
                }
                else {
                    target.show();

                    leftPos = String(((x - offset.left) * widthRatio - target.width() / 2) * (-1));
                    topPos = String(((y - offset.top) * heightRatio - target.height() / 2) * (-1));
                    target.css({ backgroundPosition: leftPos + 'px ' + topPos + 'px' });

                    leftPos = String((x - offset.left) - target.width() / 2);
                    topPos = String((y - offset.top) - target.height() / 2);
                    target.css({ left: leftPos + 'px', top: topPos + 'px' });
                }
            }
        });
    };
})(jQuery);