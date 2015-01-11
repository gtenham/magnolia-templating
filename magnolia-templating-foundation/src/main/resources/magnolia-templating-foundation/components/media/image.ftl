[#include "/magnolia-templating-foundation/components/macros/image.ftl"/]

[#assign tagName = model.tagFilterName! /]
[#if tagName?has_content]
<a name="${tagName}"></a>
[/#if]

<section [#if tagName?has_content]data-tagfilter="${tagName}"[/#if]>
[#if (content.boxLayout!false)]<div class="row">[/#if]
    <figure>
        [@DAMImage imageUuid=content.image! altTitle=content.imageDescription! imageClass="" resizeTo="default" /]
        [#if content.imageCaption?has_content]
        <figcaption>${content.imageCaption!} <span>${content.imageCopyright!}</span></figcaption>
        [/#if]
    </figure>
[#if (content.boxLayout!false)]</div>[/#if]
</section>