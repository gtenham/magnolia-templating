[#if (content.link?has_content)]
	<a href="${content.link}" title="${content.linkTitle!}" [#if (content.linkTarget?has_content)]target="${content.linkTarget}"[/#if] rel="${content.linkType}">
		${content.linkTitle!content.link}
	</a>
[/#if]