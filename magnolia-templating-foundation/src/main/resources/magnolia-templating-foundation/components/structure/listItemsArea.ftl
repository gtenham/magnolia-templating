[#list components as component ]
	<li>
		[@cms.component content=component contextAttributes={"index":component_index+1} /]
	</li>
[/#list]