[#import "/magnolia-templating-foundation/templates/macros/utils.ftl" as utils /]
[#assign boxLayout = ctx.boxLayout!false /]
[#assign categorySource = ctx.category! /]
[#assign useBoxLayout = ctx.useBoxLayout!true /]
[#assign cssClass = "" /]

[@utils.emptyComponentEditMode propertyValue=content.title message="This component is missing a required value for title."]
    [#if (content.tagLine?has_content)]
        [#if (boxLayout && useBoxLayout)]
            [#assign cssClass = cssClass + " row" /]
        [/#if]
        <header [#if cssClass?has_content]class="${cssClass!?trim}"[/#if]>
            <${content.titleType!"h2"}>
                ${cmsfn.decode(content).title!}
            </${content.titleType!"h2"}>
            <p class="subheader">${cmsfn.decode(content).tagLine}</p>
        </header>
    [#else]
        [#if (boxLayout!false)]<div class="row">[/#if]
        <${content.titleType!"h2"} [#if cssClass?has_content]class="${cssClass!?trim}"[/#if]>
            ${cmsfn.decode(content).title!}
        </${content.titleType!"h2"}>
        [#if (boxLayout!false)]</div>[/#if]
    [/#if]
[/@utils.emptyComponentEditMode]