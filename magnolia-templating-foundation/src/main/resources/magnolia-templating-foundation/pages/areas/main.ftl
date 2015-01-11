[#include "/magnolia-templating-foundation/components/macros/siteNavigation.ftl" /]

[#assign pageLayout = "1-col" /]
[#assign pageModel = ctx.pageModel! /]
[#assign currentPage = cmsfn.page(content)! /]

[#if content.pageLayout?has_content]
    [#assign pageLayout = content.pageLayout!"1-col" /]
[/#if]

[#if pageLayout?contains("left-aside") ]
	<article class="small-12 large-8 large-push-4 columns">
        [#if ((!currentPage.hideBreadcrumbs!false))]
            [@renderBreadcrumbs pageModel=pageModel currentPage=currentPage! /]
        [/#if]
		[#if ((!currentPage.hidePageTitle!false) && currentPage.title?has_content)]
		<header>
			<h1>${mtffn.decode(currentPage).title!}</h1>
		</header>
		[/#if]

    	[@cms.area name="content" /]
    </article>
    <aside class="small-12 large-4 large-pull-8 columns">
    	[@cms.area name="aside" /]
    </aside>
[#elseif pageLayout?contains("right-aside") ]
    <article class="small-12 large-8 columns">
        [#if ((!currentPage.hideBreadcrumbs!false))]
            [@renderBreadcrumbs pageModel=pageModel currentPage=currentPage! /]
        [/#if]
    	[#if ((!currentPage.hidePageTitle!false) && currentPage.title?has_content)]
		<header>
			<h1>${mtffn.decode(currentPage).title!}</h1>
		</header>
		[/#if]
    	[@cms.area name="content" /]
    </article>
    <aside class="small-12 large-4 columns">
    	[@cms.area name="aside" /]
    </aside>
[#else]
	<article>
        [#if ((!currentPage.hideBreadcrumbs!false))]
            [@renderBreadcrumbs pageModel=pageModel currentPage=currentPage! /]
        [/#if]
		[#if ((!currentPage.hidePageTitle!false) && currentPage.title?has_content)]
		<header>
			<h1>${mtffn.decode(currentPage).title!}</h1>
		</header>
		[/#if]
    	[@cms.area name="content" /]
    </article>
[/#if]