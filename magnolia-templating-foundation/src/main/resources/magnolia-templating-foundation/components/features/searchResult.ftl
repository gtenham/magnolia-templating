[#include "/magnolia-templating-foundation/components/macros/siteNavigation.ftl"/]

[#assign results = model.results! /]
[#assign hasResults = results?has_content /]
[#assign searchTerm = ctx.parameters.s!"" /]
[#assign maxResultsPerPage = content.maxResultsPerPage!"4" /]
[#assign queryString = "s=${searchTerm}" /]
[#assign descriptiveHeader = content.descriptiveHeader! /]

<div>
<h3>${descriptiveHeader!}:</h3>
[#if (hasResults && searchTerm?has_content)]
<br/>
	<dl>
	[#list results as item]
		[#if (item_index < maxResultsPerPage?number)]
			<dt><a href="${cmsfn.link(item)}">${item.title!item.navigationTitle!item.@name}</a></dt>
			<dd>${cmsfn.decode(item).abstract!cmsfn.decode(item).description!}</dd>
		[/#if]
	[/#list]
	</dl>
	[#-- Pagination --]
	[#if (model.count > maxResultsPerPage?number)]
		[@renderPagination pageModel=model! pageLink=cmsfn.link(cmsfn.page(content))! queryString=queryString /]
	[/#if]
[#else]
	<p>No results found.</p>
[/#if]
</div>