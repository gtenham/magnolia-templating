require(['main'], function() {
	require(['jquery', 'modernizr', 'fastclick', 'modules/scrollPageSection', 'foundation.topbar'], function ($, Modernizr, FastClick, sps) {
		$(document).foundation({});
		
		sps.initPageScrollSection();
		
		console.log("main loaded");
	});
});