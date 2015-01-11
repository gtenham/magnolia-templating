<div class="widget">
	[#if content.subtitle?has_content]
		<div class="title"><h4>${cmsfn.decode(content).subtitle}</h4></div>
	[/#if]
	[#if content.intro?has_content]
		<p>${content.intro}</p>
	[/#if]
	
	[@cms.area name="links"/]
 </div>