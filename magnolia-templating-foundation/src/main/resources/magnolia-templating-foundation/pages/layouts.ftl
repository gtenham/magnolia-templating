[#include "/magnolia-templating-foundation/components/macros/seo.ftl" /]

[#macro foundation]

<!doctype html>
<!--[if IE 8]> <html lang="${cmsfn.language()}" class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="${cmsfn.language()}" class="no-js"> <!--<![endif]-->
  <head>
  	[#-- Show Magnolia edit bar --]
	[#if model.isSiteRoot(content) ]
    	[@cms.init dialog="magnolia-templating-foundation:pages/siteProperties"/]
    [#else]
        [@cms.init /]
    [/#if]
    
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge;chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    
    <meta name="keywords" content="${model.getMetaKeywords()!}" />
	<meta name="description" content="${model.getMetaDescription()!}" />
    <meta name="robots" content="${content.robots!'index, follow'}" />
	
	[@renderSeoPageLinks pageModel=model /]
	
    <title>${model.getTitle()!}</title>
    [#-- Get css resources --]
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/foundation/5.4.7/css/normalize.min.css">
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/foundation/5.4.7/css/foundation.min.css">
    <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
    [#if model.siteTheme?has_content]
    <link rel="stylesheet" href="${model.siteTheme}">
    [/#if]
    [#-- AMD loading javascript --]
    <script>
        mtf = window.mtf = window.mtf || {};
        // Setting global vars
        mtf.contextPath = "${contextPath}";
        mtf.version = "1.0.0-SNAPSHOT";
    </script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/require.js/2.1.15/require.min.js" data-main="${contextPath}/static/js/main"></script>

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]> 
          <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>          
  	<![endif]-->
    
    [#if (model.googleAnalytics?has_content)]
	    <!-- Google analytics (asynchronous) part I -->
		<script type="text/javascript">
			var _gaq = _gaq || [];
			_gaq.push(['_setAccount', '${model.googleAnalytics}']);
			_gaq.push(['_trackPageview']);
		</script>
    [/#if]
  </head>
  <body>
        
    [#-- Content of different page types will be rendered here --]
    [#nested/]
        
    [#if (model.googleAnalytics?has_content)]
	    <!-- Google analytics (asynchronous) part II -->
	    <script type="text/javascript">
	        (function() {
	            var ga = document.createElement('script');
	            ga.type = 'text/javascript'; ga.async = true;
	            ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
	            var s = document.getElementsByTagName('script')[0];
	            s.parentNode.insertBefore(ga, s);
	        })();
	    </script>
    [/#if]
  </body>
</html>


[/#macro]