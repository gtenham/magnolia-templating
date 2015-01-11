<ul class="link-list">
    [#list components as component ]
    	<li>
        	[@cms.component content=component /]
        </li>
    [/#list]
    [#if cmsfn.editMode]
      <li cms:add="bar"></li>
    [/#if]
</ul>