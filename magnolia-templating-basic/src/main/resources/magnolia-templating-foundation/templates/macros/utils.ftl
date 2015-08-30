[#macro contentEditMode message]
    [#if ( cmsfn.editMode )]
        <div style="border: dashed #6a9000  1px">
            <div style="background: #a5bc66"></div>
            <div style="background:#94120f;color:white;padding:10px;">
                <p>${message!}</p>
            </div>
        </div>
        [#nested/]
    [#else]
        [#nested/]
    [/#if]
[/#macro]

[#macro emptyComponentEditMode propertyValue message]
    [#if ( !propertyValue?has_content && cmsfn.editMode )]
        <div style="border: dashed #6a9000  1px">
            <div style="background: #a5bc66" cms:add="bar"></div>
            <div style="background:#94120f;color:white;padding:10px;">
                <p>${message!}</p>
            </div>
        </div>
    [#else]
        [#nested/]
    [/#if]
[/#macro]

[#macro listArea message]
    [#if ( !components?has_content && cmsfn.editMode )]
    <div style="border: dashed #6a9000  1px">
        <div style="background: #a5bc66" cms:add="bar"></div>
        <div style="background:#94120f;color:white;padding:10px;">
            <p>${message!}</p>
        </div>
    </div>
    [#else]
        [#nested/]
    [/#if]
[/#macro]

[#macro singleArea message]
    [#if ( !component?has_content && cmsfn.editMode )]
    <div style="border: dashed #6a9000  1px">
        <div style="background: #a5bc66" cms:add="bar"></div>
        <div style="background:#94120f;color:white;padding:10px;">
            <p>${message!}</p>
        </div>
    </div>
    [#else]
        [#nested/]
    [/#if]
[/#macro]