[#assign tagName = model.tagFilterName! /]
[#if tagName?has_content]
<a name="${tagName}"></a>
[/#if]
<section [#if (content.sectionName?has_content)]class="${content.sectionName}"[/#if] [#if tagName?has_content]data-tagfilter="${tagName}"[/#if]>
	[#if (content.useHorizontalBlock!false)]<div class="row">[/#if]
		[@cms.area name="pageSectionArea"/]
	[#if (content.useHorizontalBlock!false)]</div>[/#if]
</section>