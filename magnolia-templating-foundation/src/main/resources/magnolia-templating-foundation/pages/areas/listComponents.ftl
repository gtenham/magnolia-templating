[#list components as component ]
    [@cms.component content=component contextAttributes={"index":component_index+1} /]
[/#list]
[#if ( ! components?has_content && cmsfn.isEditMode() )]
    <span class="cms-msg">This section is empty. Expand it by adding components.</span>
[/#if]