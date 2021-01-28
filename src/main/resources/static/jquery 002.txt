/*!
 * jquery.fixer.js 0.0.3 - https://github.com/yckart/jquery.fixer.js
 * Fix elements as `position:sticky` do.
 *
 *
 * Copyright (c) 2013 Yannick Albert (http://yckart.com/) | @yckart
 * Licensed under the MIT license (http://www.opensource.org/licenses/mit-license.php).
 * 2013/07/02
 **/

;(function($, window) {

    var $win = $(window);
    var defaults = {
        gap: 0,
        horizontal: false,
        isFixed: $.noop,
        wrapFrom: '',
        wrapTo: ''
    };

    var supportSticky = function(elem) {
        var prefixes = ['', '-webkit-', '-moz-', '-ms-', '-o-'], prefix;
        while (prefix = prefixes.pop()) {
            elem.style.cssText = 'position:' + prefix + 'sticky';
            if (elem.style.position !== '') return true;
        }
        return false;
    };

    $.fn.fixer = function(options) {
        options = $.extend(defaults, options);
        var hori = options.horizontal,
            cssPos = hori ? 'left' : 'top',
            wrapFrom = options.wrapFrom,
            wrapTo = options.wrapTo

        return this.each(function() {
            var style = this.style,
                $this = $(this),
                $parent = $this.parent();
            
            // $(wrapFrom).children().siblings().height('100%')
            // $(wrapTo).children().siblings().height('100%')

            if (supportSticky(this)) {
                style[cssPos] = options.gap + 'px';
                return;
            }

            $win.on('scroll', function() {
                var scrollPos = $win[hori ? 'scrollLeft' : 'scrollTop'](),
                    elemSize = $this[hori ? 'outerWidth' : 'outerHeight'](),
                    parentPos = $parent.offset()[cssPos],
                    parentSize = $parent[hori ? 'outerWidth' : 'outerHeight']();
                
                if (scrollPos >= parentPos - options.gap && (parentSize + parentPos - options.gap) >= (scrollPos + elemSize)) {
                    $this.addClass('is-sticky')
                    style[cssPos] = options.gap + 'px';
                    options.isFixed();
                } else if (scrollPos < parentPos) {
                    $this.removeClass('is-sticky')
                    style[cssPos] = 0;
                } else {
                    //$this.removeClass('is-sticky')
                    //style[cssPos] = parentSize - elemSize + 'px';
                }
            }).resize();
        });
    };

}(jQuery, this));

