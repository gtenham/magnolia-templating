[#import "/magnolia-templating-foundation/templates/macros/utils.ftl" as utils /]
[#assign tagName = model.tagFilterName! /]

[@utils.contentEditMode message="This is an edit mode placeholder for the html component."]
    [#if tagName?has_content]
    <a name="${tagName}"></a>
    [/#if]
    [#if content.editHTML?has_content]
    <div [#if tagName?has_content]class="${tagName!}"[/#if] ${model.tagFilterData!}>
        ${cmsfn.decode(content).editHTML}
    </div>
    [/#if]
[/@utils.contentEditMode]