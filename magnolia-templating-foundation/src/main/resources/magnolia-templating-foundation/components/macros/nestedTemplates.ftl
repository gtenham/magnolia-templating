[#-- Macro for header in Magnolia pages --]
[#macro topSection]

	[#include "/magnolia-templating-foundation/components/macros/siteNavigation.ftl" /]
	[#include "/magnolia-templating-foundation/components/macros/image.ftl"/]
	[#assign model= ctx.pageModel! /]
	[#assign siteRoot = model.getSiteRoot(content)! /]
	[#assign isSelected = (siteRoot.@path == state.handle)!false /]
	[#assign hasSearchpage = (siteRoot.searchResultLink?has_content) /]
	[#assign searchTerm = ctx.parameters.s!"" /]
	

		
	<div class="nav-section">
		
			<nav class="top-bar" data-topbar data-options="is_hover: false">
				<ul class="title-area"> 
		       		<li class="name">
		                <h1>
		                	<a href="${cmsfn.link(siteRoot)}" class="logo">
		                	[@DAMImage imageUuid=siteRoot.logoImg! altTitle=siteRoot.alternativeLogoText!'' imageClass="logo-img" /]
		                	${cmsfn.decode(siteRoot).siteSlogan!''}
		                	</a>
		                </h1>
		            </li>
		            <li class="toggle-topbar"><a href="#"><i class="fa fa-bars fa-2x"></i></a></li>
		        </ul> 
		        <section class="top-bar-section"> 
		        	<ul class="left">
		        		[#if !siteRoot.hideInNav!false ]
		          			<li [#if (isSelected ) ]class="active"[/#if]>
		          				<a href="${cmsfn.link(siteRoot)}">${siteRoot.navigationTitle!siteRoot.title!"Home"}</a>
		          			</li>
		      			[/#if]   
		      			[#list cmsfn.children(siteRoot, "mgnl:page") as childPage]    
		          			[@renderNavigation navigation=childPage maxLevel=4 /]
		      			[/#list]
		        	</ul>
				</section>
			</nav>
		
	</div>
	
[/#macro]