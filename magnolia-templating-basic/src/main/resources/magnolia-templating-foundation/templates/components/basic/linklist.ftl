[#assign boxLayout = ctx.boxLayout!false /]
[#assign categorySource = ctx.category! /]
[#assign useBoxLayout = ctx.useBoxLayout!true /]

[#-- List style, default is UL --]
[#assign listStyle = "ul"]
[#if content.listStyle?has_content]
    [#if content.listStyle == "ordered"]
        [#assign listStyle = "ol"]
    [/#if]
[/#if]

[#if categorySource?has_content && categorySource?contains("section")]
    [#if (boxLayout && useBoxLayout)]<div class="row">[/#if]
        <${listStyle} class="${content.listStyle!}">
            [@cms.area name="links" /]
        </${listStyle}>
    [#if (boxLayout && useBoxLayout)]</div>[/#if]
[#else]
    <section>
        [@cms.area name="header" /]

        <${listStyle} class="${content.listStyle!}">
            [@cms.area name="links" /]
        </${listStyle}>
    </section>
[/#if]
