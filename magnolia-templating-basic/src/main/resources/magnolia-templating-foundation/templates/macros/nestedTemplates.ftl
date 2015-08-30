[#-- Macro for header in Magnolia pages --]
[#macro topSection]

	[#include "/magnolia-templating-foundation/components/macros/siteNavigation.ftl" /]
	[#include "/magnolia-templating-foundation/components/macros/image.ftl"/]
	[#assign model= ctx.pageModel! /]
    [#assign siteConfig = model.siteConfig! /]
	[#assign treeRoot = model.getTreeRoot(content)! /]
	[#assign isSelected = (treeRoot.@path == state.handle)!false /]

	<div class="nav-section">
		
			<nav class="top-bar" data-topbar data-options="is_hover: false">
                [#if (siteConfig.siteLogoImg?has_content || siteConfig.siteSlogan?has_content || siteConfig.siteTitle?has_content)]
				<ul class="title-area"> 
		       		<li class="name">
		                <h1>
		                	<a href="${cmsfn.link(treeRoot)}" class="logo">
		                	[@DAMImage imageUuid=siteConfig.siteLogoImg! altTitle=siteConfig.siteAlternativeLogoText!'' imageClass="logo-img" /]
		                	</a>
                            <small>${siteConfig.siteSlogan!siteConfig.siteTitle!''}</small>
		                </h1>
		            </li>
		            <li class="toggle-topbar"><a href="#"><i class="fa fa-bars fa-2x"></i></a></li>
		        </ul>
                [/#if]
		        <section class="top-bar-section"> 
		        	<ul class="left">
		        		[#if !treeRoot.hideInNav!false ]
		          			<li [#if (isSelected ) ]class="active"[/#if]>
		          				<a href="${cmsfn.link(treeRoot)}">${treeRoot.navigationTitle!treeRoot.title!"Home"}</a>
		          			</li>
		      			[/#if]   
		      			[#list cmsfn.children(treeRoot, "mgnl:page") as childPage]
		          			[@renderNavigation navigation=childPage maxLevel=4 /]
		      			[/#list]
		        	</ul>

                    <ul class="extras right">
                        <li class="has-form search-bar">
                            <form action="#" class="row collapse">
                                <div class="small-10 columns">
                                    <input type="text" name="s" placeholder="Search">
                                </div>
                                <div class="small-2 columns">
                                    <button class="button expand"><i class="fa fa-search"></i></button>
                                </div>
                            </form>
                        </li>

                        [#if mtffn.localisationEnabled!false]
                        <li class="has-dropdown language-switcher">
                            <a>${mtffn.defaultLocaleCode?upper_case}</a>

                            <ul class="dropdown">

                                [#list mtffn.localesWithoutDefault as locale]
                                    [#assign language = locale.language /]
                                    [#assign localisedPageLink = model.getLocalisedPageLink(locale) /]
                                    [#if ctx.request.queryString?has_content]
                                        [#assign localisedPageLink = localisedPageLink + "?" + ctx.request.queryString /]
                                    [/#if]
                                    <li><a href="${localisedPageLink}">${language?upper_case}</a></li>
                                [/#list]
                            </ul>
                        </li>
                        [/#if]
                    </ul>
				</section>


			</nav>
		
	</div>
	
[/#macro]