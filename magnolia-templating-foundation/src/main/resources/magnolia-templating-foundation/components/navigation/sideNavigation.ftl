[#assign parentPage = mtffn.i18nWrap(cmsfn.contentById(content.parentPage))!/]
[#assign parentTitle= content.subtitle!parentPage.navigationTitle! /]

<nav class="widget">
	[#if (parentPage?has_content)]
		<ul class="side-nav">
		    [#if (parentTitle?has_content)]
		    	<li class="heading"><a href="${cmsfn.link(parentPage)}" title="${parentPage.title!parentTitle}">${parentTitle}</a></li>
		    [/#if] 
		    [#if (cmsfn.children(parentPage, "mgnl:page")?size > 0 ) ]
			    [#list cmsfn.children(parentPage, "mgnl:page") as childPage]
			    	[#if ((!childPage.hideInNav!false)) ]
			       		[#assign isSelected = (childPage.@path == state.handle)!false /]
			       		<li [#if (isSelected )]class="active"[/#if] >
			       			<a href="${cmsfn.link(childPage)}">${childPage.navigationTitle!childPage.title!"NA"}</a>
			           	</li>
			        [/#if] 
			    [/#list]
		    [/#if] 
	    </dl>
	[/#if]
</nav>