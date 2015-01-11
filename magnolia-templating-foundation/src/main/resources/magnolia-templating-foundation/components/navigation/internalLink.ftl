[#assign linkPage = mtffn.i18nWrap(cmsfn.contentById(content.link))!/]
[#if (linkPage?has_content)]
	<a href="${cmsfn.link(linkPage)}[#if (content.linkParams?has_content)]${content.linkParams}[/#if]" 
		title="${content.linkTitle!linkPage.title}"	[#if (content.linkTarget?has_content)]target="${content.linkTarget}"[/#if] rel="${content.linkType}">
		${content.linkTitle!linkPage.title}
	</a>
[/#if]