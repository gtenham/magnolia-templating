require(['main'], function() {
	require(['jquery', 'modernizr', 'fastclick', 'modules/scrollPageSection','foundation.core'], function ($, Modernizr, FastClick, sps) {
		$(document).foundation({});
		
		sps.initPageScrollSection();

        $('a[data-toggle-target]').each(function(index) {
            $(this).on('click', function(e){
                e.preventDefault();
                var targetEl = $(this).data('toggle-target');
                $(targetEl).trigger( targetEl.substring(1) + ":toggle" );
            });
        });
		console.log("main loaded");
	});
});