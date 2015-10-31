[#import "/magnolia-templating-foundation/templates/macros/utils.ftl" as utils /]
[#assign wrapElementName = def.parameters.wrapElement! /]
[#assign emptyMessage = def.parameters.emptyMsg!"This area is empty. Expand it by adding a component." /]
[#assign categorySource = def.parameters.category! /]

[@utils.singleArea message = emptyMessage]
    [#if (wrapElementName?has_content)]<${wrapElementName}>[/#if]
        [@cms.component content=component contextAttributes={"category":categorySource} /]
    [#if (wrapElementName?has_content)]</${wrapElementName}>[/#if]
[/@utils.singleArea]