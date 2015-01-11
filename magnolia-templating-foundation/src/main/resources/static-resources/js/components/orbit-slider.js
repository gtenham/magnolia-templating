require(['main'], function() {
	require(['foundation.orbit'], function () {
		$(".orbit-slider").foundation({});
		console.log("foundation.orbit loaded");
	});
});