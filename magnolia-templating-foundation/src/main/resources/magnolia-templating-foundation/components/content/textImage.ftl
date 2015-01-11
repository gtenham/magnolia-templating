[#include "/magnolia-templating-foundation/components/macros/image.ftl"/]

[#assign tagName = model.tagFilterName! /]
[#if tagName?has_content]
<a name="${tagName}"></a>
[/#if]

<section [#if tagName?has_content]data-tagfilter="${tagName}"[/#if]>
[#if (content.boxLayout!false)]<div class="row">[/#if]
	[#assign imageLocation=content.imageLocation!"above" /]
    [#if content.subtitle?has_content]
    <header class="section-title">
    	<h2>${cmsfn.decode(content).subtitle}</h2>
    </header>
    [/#if]
    [#if content.image?? && !imageLocation?contains("below")]
	    <figure class="${imageLocation}">
	    	[@DAMImage imageUuid=content.image! altTitle=content.imageDescription! imageClass="" resizeTo="default" /]
	    	<figcaption>${content.imageCaption!} <span>${content.imageCopyright!}</span></figcaption>
	    </figure>
    [/#if]
    [#if content.text?has_content]
        ${cmsfn.decode(content).text}
    [/#if]
    [#if content.image?? && imageLocation?contains("below")]
	    <figure class="${imageLocation}">
	    	[@DAMImage imageUuid=content.image! altTitle=content.imageDescription! imageClass="" resizeTo="default" /]
	    	<figcaption>${content.imageCaption!} <span>${content.imageCopyright!}</span></figcaption>
	    </figure>
    [/#if]
[#if (content.boxLayout!false)]</div>[/#if]
</section>