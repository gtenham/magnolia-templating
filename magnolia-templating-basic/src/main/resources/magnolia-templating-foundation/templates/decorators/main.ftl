[#include "/magnolia-templating-foundation/templates/macros/siteNavigation.ftl" /]

[#assign pageLayout = content.pageLayout!"1-col" /]
[#assign pageModel = ctx.pageModel! /]
[#assign currentPage = cmsfn.page(content)! /]
[#assign elementName = def.parameters.element!"article" /]

[#if pageLayout?contains("left-aside") ]
    [#assign articleClass = "small-12 large-8 large-push-4 columns" /]
    [#assign asideClass = "small-12 large-4 large-pull-8 columns" /]
[#elseif pageLayout?contains("right-aside") ]
    [#assign articleClass = "small-12 large-8 columns" /]
    [#assign asideClass = "small-12 large-4 columns" /]
[/#if]
[#-- Breadcrumbs rendering when active for page --]
[#if ((!currentPage.hideBreadcrumbs!false))]
    [@renderBreadcrumbs pageModel=pageModel currentPage=currentPage! /]
[/#if]

[#if pageLayout?contains("aside") ]
	<article class="${articleClass!}">
		[#if ((!currentPage.hidePageTitle!false) && currentPage.title?has_content)]
		<header>
			<h1>${mtffn.decode(currentPage).title!}</h1>
		</header>
		[/#if]

    	[@cms.area name="content" /]
    </article>
    <aside class="${asideClass!} ${pageLayout!}">
    	[@cms.area name="aside" /]
    </aside>
[#else]
	<${elementName}>
        [#if ((!currentPage.hidePageTitle!false) && currentPage.title?has_content)]
		<header>
			<h1>${mtffn.decode(currentPage).title!}</h1>
		</header>
		[/#if]
    	[@cms.area name="content" /]
    </${elementName}>
[/#if]