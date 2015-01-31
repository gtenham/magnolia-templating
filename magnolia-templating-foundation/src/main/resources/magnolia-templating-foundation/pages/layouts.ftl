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

    [#-- Setting global javascrip vars --]
    <script>
        mtf = window.mtf = window.mtf || {};
        // Setting global vars
        mtf.contextPath = "${contextPath}";
        [#if (model.googleAnalytics?has_content)]
        mtf.googleAccount = "${model.googleAnalytics}";
        [/#if]
        mtf.version = "1.0.0-SNAPSHOT";
    </script>
    [#-- AMD loading javascript --]
    <script src="//cdnjs.cloudflare.com/ajax/libs/require.js/2.1.15/require.min.js" data-main="${contextPath}/static/js/main"></script>

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]> 
          <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>          
  	<![endif]-->
  </head>
  <body>
        
    [#-- Content of different page types will be rendered here --]
    [#nested/]
        
    [#if (model.googleAnalytics?has_content)]
        <script>
            (function(b,o,i,l,e,r){b.GoogleAnalyticsObject=l;b[l]||(b[l]=
                    function(){(b[l].q=b[l].q||[]).push(arguments)});b[l].l=+new Date;
                e=o.createElement(i);r=o.getElementsByTagName(i)[0];
                e.src='//www.google-analytics.com/analytics.js';
                r.parentNode.insertBefore(e,r)}(window,document,'script','ga'));
            ga('create',mtf.googleAccount,'auto');ga('send','pageview');
        </script>

    [/#if]
  </body>
</html>


[/#macro]