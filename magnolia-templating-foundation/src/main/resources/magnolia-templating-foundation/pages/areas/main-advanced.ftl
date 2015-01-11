[#include "/magnolia-templating-foundation/components/macros/siteNavigation.ftl" /]

[#assign pageModel = ctx.pageModel! /]
[#assign currentPage = mtffn.page(content)! /]
<article>
    [#if ((!currentPage.hideBreadcrumbs!false))]
        [@renderBreadcrumbs pageModel=pageModel currentPage=currentPage! /]
    [/#if]
	[#if ((!currentPage.hidePageTitle!false) && currentPage.title?has_content)]
	<header>
		<h1>${mtffn.decode(currentPage).title!}</h1>
	</header>
	[/#if]
	
	[#list components as component ]
    	[@cms.component content=component contextAttributes={"index":component_index+1} /]
	[/#list]
</article>