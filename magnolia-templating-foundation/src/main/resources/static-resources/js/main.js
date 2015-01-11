require.config({
	packages: [ 'mtf-core',
                { name: "mgnl-resources",
                  location: mtf.contextPath + "/resources/",
                  main: "foundation-amd"
                }
              ],
    paths: {
        /* jQuery */
        'jquery': ['//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.1/jquery.min', 'vendor/jquery.min'],

        /* Plugins */
        'text': 'plugins/text.min',
        'css': 'plugins/css.min',
        'font': 'plugins/font.min',
        'i18n': 'plugins/i18n.min',
        'image': 'plugins/image.min',
        'goog': 'plugins/goog.min',
        'async': 'plugins/async.min',
        'json': 'plugins/json.min',
        'propertyParser' : 'plugins/propertyParser.min',

        /* Foundation */
        'foundation.core': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.4.7/js/foundation/foundation.min','vendor/foundation/foundation.min'],
        'foundation.abide': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.4.7/js/foundation/foundation.abide.min','vendor/foundation/foundation.abide.min'],
        'foundation.accordion': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.4.7/js/foundation/foundation.accordion.min.','vendor/foundation/foundation.accordion'],
        'foundation.alert': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.4.7/js/foundation/foundation.alert.min','vendor/foundation/foundation.alert.min'],
        'foundation.clearing': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.4.7/js/foundation/foundation.clearing.min','vendor/foundation/foundation.clearing.min'],
        'foundation.dropdown': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.4.7/js/foundation/foundation.dropdown.min','vendor/foundation/foundation.dropdown.min'],
        'foundation.equalizer': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.4.7/js/foundation/foundation.equalizer.min','vendor/foundation/foundation.equalizer.min'],
        'foundation.interchange': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.4.7/js/foundation/foundation.interchange.min','vendor/foundation/foundation.interchange.min'],
        'foundation.joyride': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.4.7/js/foundation/foundation.joyride.min','vendor/foundation/foundation.joyride.min'],
        'foundation.magellan': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.4.7/js/foundation/foundation.magellan.min','vendor/foundation/foundation.magellan.min'],
        'foundation.offcanvas': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.4.7/js/foundation/foundation.offcanvas.min','vendor/foundation/foundation.offcanvas.min'],
        'foundation.orbit': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.4.7/js/foundation/foundation.orbit.min','vendor/foundation/foundation.orbit.min'],
        'foundation.reveal': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.4.7/js/foundation/foundation.reveal.min','vendor/foundation/foundation.reveal.min'],
        'foundation.slider': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.4.7/js/foundation/foundation.slider.min','vendor/foundation/foundation.slider.min'],
        'foundation.tab': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.4.7/js/foundation/foundation.tab.min','vendor/foundation/foundation.tab.min'],
        'foundation.tooltip': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.4.7/js/foundation/foundation.tooltip.min','vendor/foundation/foundation.tooltip.min'],
        'foundation.topbar': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.4.7/js/foundation/foundation.topbar.min','vendor/foundation/foundation.topbar.min'],

        /* Vendor Scripts */
        'jquery.cookie': ['//cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.min','vendor/jquery.cookie.min'],
        'fastclick': ['//cdnjs.cloudflare.com/ajax/libs/fastclick/1.0.3/fastclick.min', 'vendor/fastclick.min'],
        'modernizr': ['//cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min','vendor/modernizr.min'],
        'placeholder': ['//cdnjs.cloudflare.com/ajax/libs/jquery-placeholder/2.0.8/jquery.placeholder.min','vendor/jquery.placeholder.min']
    },
    shim: {
        /* Foundation */
        'foundation.core': {
            deps: ['jquery', 'modernizr'],
            exports: 'Foundation'
        },
        'foundation.abide': {
            deps: ['foundation.core']
        },
        'foundation.accordion': {
            deps: ['foundation.core']
        },
        'foundation.alert': {
            deps: ['foundation.core']
        },
        'foundation.clearing': {
            deps: ['foundation.core']
        },
        'foundation.dropdown': {
            deps: ['foundation.core']
        },
        'foundation.equalizer': {
            deps: ['foundation.core']
        },
        'foundation.interchange': {
            deps: ['foundation.core']
        },
        'foundation.joyride': {
            deps: ['foundation.core', 'jquery.cookie']
        },
        'foundation.magellan': {
            deps: ['foundation.core']
        },
        'foundation.offcanvas': {
            deps: ['foundation.core']
        },
        'foundation.orbit': {
            deps: ['foundation.core']
        },
        'foundation.reveal': {
            deps: ['foundation.core']
        },
        'foundation.slider': {
            deps: ['foundation.core']
        },
        'foundation.tab': {
            deps: ['foundation.core']
        },
        'foundation.tooltip': {
            deps: ['foundation.core']
        },
        'foundation.topbar': {
            deps: ['foundation.core']
        },

        /* Vendor Scripts */
        'fastclick': {
            exports: 'FastClick'
        },
        'modernizr': {
            exports: 'Modernizr'
        },
        'placeholder': {
            exports: 'Placeholder'
        }
    },
	urlArgs: "v=" +  mtf.version
});

require(['mtf-core','mgnl-resources'], function() {
	// Main libs - Libraries and modules that will be needed on all pages of the site
});

