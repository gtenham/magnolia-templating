[#assign tagName = model.tagFilterName! /]
[#if tagName?has_content]
<a name="${tagName}"></a>
[/#if]
[#if content.editHTML?has_content]
<section [#if tagName?has_content]data-tagfilter="${tagName}"[/#if]>
[#if (content.boxLayout!false)]<div class="row">[/#if]
    ${cmsfn.decode(content).editHTML}
[#if (content.boxLayout!false)]</div>[/#if]
</section>
[/#if]