define(function (require) {
    var $ = require('jquery'),
        fcore = require('foundation.core'),
        $offCanvasLeft = $('.off-canvas-left'),
        $offCanvasRight = $('.off-canvas-right'),
        $siteSearch = $('#site-search'),
        $moreNav = $('#more-nav');

    function resetVisibilityExceptFor(triggerEl) {

        $('body').addClass(triggerEl+'-visible');
        if (triggerEl !== "off-canvas-left") {
            $('body').removeClass('off-canvas-left-visible');
            $offCanvasLeft.removeClass( "visible" );
        }
        if (triggerEl !== "off-canvas-right") {
            $('body').removeClass('off-canvas-right-visible');
            $offCanvasRight.removeClass( "visible" );
        }
        if (triggerEl !== "site-search") {
            $('body').removeClass('site-search-visible');
            $('a[data-toggle-target="#site-search"]').find('i').removeClass( "fa-search-minus" ).addClass( "fa-search" );
            $siteSearch.removeClass( "visible" );
        }
        if (triggerEl !== "more-nav") {
            $('body').removeClass('more-nav-visible');
            $moreNav.removeClass( "visible" );
        }
    }

    $offCanvasLeft.on('off-canvas-left:toggle', function(event) {
        if ( $(this).is( ".visible" ) ) {
            $('body').removeClass('off-canvas-left-visible');
            $(this).removeClass( "visible" );
        } else {
            resetVisibilityExceptFor("off-canvas-left");
            $(this).addClass( "visible" );
        }
    });

    $offCanvasRight.on('off-canvas-right:toggle', function(event) {
        if ( $(this).is( ".visible" ) ) {
            $('body').removeClass('off-canvas-right-visible');
            $(this).removeClass( "visible" );
        } else {
            resetVisibilityExceptFor("off-canvas-right");
            $(this).addClass( "visible" );
        }
    });

    $siteSearch.on('site-search:toggle', function(event, trigger) {
        if ( $(this).is( ".visible" ) ) {
            $('body').removeClass('site-search-visible');
            $('a[data-toggle-target="#site-search"]').find('i').removeClass( "fa-search-minus" ).addClass( "fa-search" );
            $(this).removeClass( "visible" );
        } else {
            $('a[data-toggle-target="#site-search"]').find('i').removeClass( "fa-search" ).addClass( "fa-search-minus" );
            resetVisibilityExceptFor("site-search");
            $(this).addClass( "visible" );
        }
    });

    $moreNav.on('more-nav:toggle', function(event) {
        if ( $(this).is( ".visible" ) ) {
            $('body').removeClass('more-nav-visible');
            $(this).removeClass( "visible" );
        } else {
            resetVisibilityExceptFor("more-nav");
            $(this).addClass( "visible" );
        }
    });
});