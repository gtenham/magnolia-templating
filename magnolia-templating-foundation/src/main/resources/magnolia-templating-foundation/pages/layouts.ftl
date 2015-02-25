[#include "/magnolia-templating-foundation/components/macros/seo.ftl" /]

[#macro foundation]

<!doctype html>
<!--[if IE 8]> <html lang="${cmsfn.language()}" class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="${cmsfn.language()}" class="no-js"> <!--<![endif]-->
  <head>
  	[#-- Show Magnolia edit bar --]
	[@cms.init /]

    <meta charset="utf-8" />
    <link rel="dns-prefetch" href="//cdnjs.cloudflare.com">

    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    
    <meta name="keywords" content="${model.getMetaKeywords()!}" />
	<meta name="description" content="${model.getMetaDescription()!}" />
    <meta name="robots" content="${content.robots!'index, follow'}" />
	
	[@renderSeoPageLinks pageModel=model /]
	
    <title>${model.getTitle()!}</title>
    [#-- Get css resources --]
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/foundation/5.5.0/css/normalize.min.css">
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/foundation/5.5.0/css/foundation.min.css">
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.min.css">

    [@renderOpenGraph pageModel=model /]

    [#-- Setting global javascrip vars --]
    <script>
        mtf = window.mtf = window.mtf || {};
        // Setting global vars
        mtf.contextPath = "${contextPath}";
        mtf.version = "${model.moduleVersion}";
        mtf.DNT = navigator.doNotTrack == "yes" || navigator.doNotTrack == "1" || navigator.msDoNotTrack == "1";
    </script>
    [#-- AMD loading javascript --]
    <script src="//cdnjs.cloudflare.com/ajax/libs/require.js/2.1.15/require.min.js" data-main="${contextPath}/static/js/main"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="//cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.js"></script>
    <![endif]-->
  </head>
  <body [#if (model.googleAnalytics?has_content)]data-google-analytics="${model.googleAnalytics}"[/#if]>
        
    [#-- Content of different page types will be rendered here --]
    [#nested/]

  </body>
</html>


[/#macro]