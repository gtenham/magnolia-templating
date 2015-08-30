[#import "/magnolia-templating-foundation/pages/layouts.ftl" as layouts /]

[@layouts.foundation]

    <!-- Start: Header -->
	<header id="dcmnt-header" role="banner">
		[@cms.area name="header" contextAttributes={"pageModel":model} /]
	</header>
	<!-- End: Header -->
	
	<!-- Start:Main-->
	<main id="dcmnt-content" role="main">
        [@cms.area name="main" contextAttributes={"pageModel":model} /]
	</main>
	<!-- End:Main-->
	
	<!-- Start:Footer-->
	<footer id="dcmnt-footer" role="contentinfo">
		[@cms.area name="footer" contextAttributes={"pageModel":model} /]
	</footer>
	<!-- End:Footer-->
	
[/@layouts.foundation]