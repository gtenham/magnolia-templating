[#list components as component ]
	<li data-orbit-slide="slide-${component_index+1}">
		[@cms.component content=component contextAttributes={"index":component_index+1} /]
	</li>
[/#list]