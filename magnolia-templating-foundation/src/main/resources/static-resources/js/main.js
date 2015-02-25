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

        /* Optional modules */

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
        'foundation.core': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.5.0/js/foundation/foundation.min','vendor/foundation/5.5.0/foundation'],
        'foundation.abide': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.5.0/js/foundation/foundation.abide.min','vendor/foundation/5.5.0/foundation.abide'],
        'foundation.accordion': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.5.0/js/foundation/foundation.accordion.min.','vendor/foundation/5.5.0/foundation.accordion'],
        'foundation.alert': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.5.0/js/foundation/foundation.alert.min','vendor/foundation/5.5.0/foundation.alert'],
        'foundation.clearing': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.5.0/js/foundation/foundation.clearing.min','vendor/foundation/5.5.0/foundation.clearing'],
        'foundation.dropdown': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.5.0/js/foundation/foundation.dropdown.min','vendor/foundation/5.5.0/foundation.dropdown'],
        'foundation.equalizer': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.5.0/js/foundation/foundation.equalizer.min','vendor/foundation/5.5.0/foundation.equalizer'],
        'foundation.interchange': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.5.0/js/foundation/foundation.interchange.min','vendor/foundation/5.5.0/foundation.interchange'],
        'foundation.joyride': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.5.0/js/foundation/foundation.joyride.min','vendor/foundation/5.5.0/foundation.joyride'],
        'foundation.magellan': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.5.0/js/foundation/foundation.magellan.min','vendor/foundation/5.5.0/foundation.magellan'],
        'foundation.offcanvas': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.5.0/js/foundation/foundation.offcanvas.min','vendor/foundation/5.5.0/foundation.offcanvas'],
        'foundation.orbit': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.5.0/js/foundation/foundation.orbit.min','vendor/foundation/5.5.0/foundation.orbit'],
        'foundation.reveal': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.5.0/js/foundation/foundation.reveal.min','vendor/foundation/5.5.0/foundation.reveal'],
        'foundation.slider': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.5.0/js/foundation/foundation.slider.min','vendor/foundation/5.5.0/foundation.slider'],
        'foundation.tab': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.5.0/js/foundation/foundation.tab.min','vendor/foundation/5.5.0/foundation.tab'],
        'foundation.tooltip': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.5.0/js/foundation/foundation.tooltip.min','vendor/foundation/5.5.0/foundation.tooltip'],
        'foundation.topbar': ['//cdnjs.cloudflare.com/ajax/libs/foundation/5.5.0/js/foundation/foundation.topbar.min','vendor/foundation/5.5.0/foundation.topbar'],

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

require(['mtf-core','mgnl-resources','modules/analytics'], function() {
	// Main libs - Libraries and modules that will be needed on all pages of the site
});

