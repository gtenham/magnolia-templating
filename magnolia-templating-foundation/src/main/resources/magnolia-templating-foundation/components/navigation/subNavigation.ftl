[#assign parentPage = mtffn.i18nWrap(cmsfn.contentById(content.parentPage))!/]
[#assign parentTitle= content.subtitle!parentPage.navigationTitle! /]

[#if (parentPage?has_content)]
	<dl class="sub-nav">
	    [#if (parentTitle?has_content)]
	    	<dt>${parentTitle}</dt>
	    [/#if] 
	    [#if (cmsfn.children(parentPage, "mgnl:page")?size > 0 ) ]
		    [#list cmsfn.children(parentPage, "mgnl:page") as childPage]
		    	[#if ((!childPage.hideInNav!false)) ]
		       		[#assign isSelected = (childPage.@path == state.handle)!false /]
		       		<dd [#if (isSelected )]class="active"[/#if] >
		       			<a href="${cmsfn.link(childPage)}">${childPage.navigationTitle!childPage.title!"NA"}</a>
		           	</dd>
		        [/#if] 
		    [/#list]
	    [/#if] 
    </dl>
[/#if]