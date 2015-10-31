define(function (require) {
	var $ = require('jquery');
	var sm = require('vendor/scrollMonitor');
	var $tagFilters = null;
    var currentTagFilter = null;
	var watchers = [];
	
	function initPageScrollSection() {
		$tagFilters = $( "*[data-tagfilter^='pgs-']" );
		
		for (var i = 0; i < $tagFilters.length; i++) {
			watchers.push = createWatcher( $tagFilters[i] );
		}
	};
 
	function createWatcher( element ) {
		var watcher = sm.create( element );
		var elementTagFilter = element.getAttribute( "data-tagfilter" );
		
		watcher.stateChange( function() {
			if (!watcher.isFullyInViewport) {
                return
			} else if (currentTagFilter !== elementTagFilter) {
                currentTagFilter = elementTagFilter;
                requestAnimationFrame( function( ) {
                    $(document.body).attr('data-sectiontag', elementTagFilter); }
                        , element
                );
			}
		});
	};
	
	return {
		initPageScrollSection: initPageScrollSection
	}
});