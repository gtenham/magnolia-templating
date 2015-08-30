[#assign tagName = model.tagFilterName! /]

[#if tagName?has_content]
<a name="${tagName}"></a>
[/#if]
<section [#if tagName?has_content]class="${tagName!}"[/#if] ${model.tagFilterData!}>
    [@cms.area name="section" contextAttributes={"boxLayout":content.boxLayout!false}/]
</section>