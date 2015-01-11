[#assign tagName = model.tagFilterName! /]
[#if tagName?has_content]
<a name="${tagName}"></a>
[/#if]
<section [#if (content.sectionName?has_content)]class="${content.sectionName}"[/#if] [#if tagName?has_content]data-tagfilter="${tagName}"[/#if]>
	[#if (content.useHorizontalBlock!false)]<div class="row">[/#if]
		<ul class="small-block-grid-${content.smallViewPortMaxItems!"1"} medium-block-grid-${content.mediumViewPortMaxItems!"2"} large-block-grid-${content.largeViewPortMaxItems!"3"}">
			[@cms.area name="widgets"/]
			[#if cmsfn.editMode]
	      		<li cms:add="bar">&nbsp;</li>
	    	[/#if]
		</ul>
	[#if (content.useHorizontalBlock!false)]</div>[/#if]
</section>