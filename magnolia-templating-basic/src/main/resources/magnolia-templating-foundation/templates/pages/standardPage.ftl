[#import "/magnolia-templating-foundation/templates/decorators/layouts.ftl" as layouts /]

[@layouts.foundation]

<!-- Start: Header -->
[@cms.area name="morenav" contextAttributes={"pageModel":model} /]
<header id="page-header" role="banner">
   [@cms.area name="header" contextAttributes={"pageModel":model} /]
</header>
[#include "/magnolia-templating-foundation/templates/decorators/sitenav.ftl" /]
<!-- End: Header -->
<!-- Start:Main-->
<main id="page-content" role="main">
    [@cms.area name="main" contextAttributes={"pageModel":model} /]
</main>
<!-- End:Main-->

<!-- Start:Footer-->
<footer id="page-footer" role="contentinfo">
    [@cms.area name="footer" contextAttributes={"pageModel":model} /]
</footer>
<!-- End:Footer-->

[/@layouts.foundation]