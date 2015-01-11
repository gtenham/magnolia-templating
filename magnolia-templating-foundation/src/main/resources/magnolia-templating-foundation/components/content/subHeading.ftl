[#assign tagName = model.tagFilterName! /]
[#if tagName?has_content]
<a name="${tagName}"></a>
[/#if]

<header [#if tagName?has_content]data-tagfilter="${tagName}"[/#if]>
[#if (content.boxLayout!false)]<div class="row">[/#if]
    <${content.titleType!"h1"}>${cmsfn.decode(content).title}</${content.titleType!"h1"}>
    [#if (content.tagLine?has_content)]
    <p>${content.tagLine}</p>
    [/#if]
[#if (content.boxLayout!false)]</div>[/#if]
</header>