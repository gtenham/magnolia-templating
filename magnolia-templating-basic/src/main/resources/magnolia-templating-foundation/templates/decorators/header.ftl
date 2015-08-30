[#include "/magnolia-templating-foundation/templates/macros/siteNavigation.ftl" /]
[#include "/magnolia-templating-foundation/templates/macros/image.ftl"/]
[#assign model= ctx.pageModel! /]
[#assign siteConfig = model.siteConfig! /]
[#assign treeRoot = model.getTreeRoot(content)! /]
[#assign isSelected = (treeRoot.@path == state.handle)!false /]
[#assign searchTerm = ctx.parameters.s!?html /]
[#assign currentPageNode = cmsfn.page(content)! /]
[#assign iconBarCounter = "one-up" /]

<div class="icon-bar one-up hide-for-large-up left-position">
    <a href="#" class="item" data-toggle-target=".off-canvas-left"><i class="fa fa-bars fa-2x"></i></a>
</div>

<div id="logo">
    <a href="${cmsfn.link(treeRoot)}" title="${siteConfig.siteTitle!''}">
    [@DAMImage imageUuid=siteConfig.siteLogoImg! altTitle=siteConfig.siteAlternativeLogoText!'' imageClass="logo-img" /]
    </a>
</div>

[#if (siteConfig.siteSearchResultsPage?has_content) ]
    [#assign iconBarCounter = "two-up" /]
[/#if]

<div class="icon-bar ${iconBarCounter!"one-up"} right-position">
[#if (siteConfig.siteSearchResultsPage?has_content) ]
    [#if (siteConfig.siteSearchResultsPage?contains(currentPageNode.@id) && searchTerm?has_content)]
        [#assign isVisible = "-minus" /]
    [/#if]
    <a href="#" class="item" data-toggle-target="#site-search"><i class="fa fa-search${isVisible!} fa-2x"></i></a>
[/#if]
    [#--<a href="#" class="item" data-toggle-target="#mini-cart"><i class="fa fa-opencart fa-2x"></i></a>--]
    <a href="#" class="item" data-toggle-target="#more-nav"><i class="fa fa-ellipsis-v fa-2x"></i></a>
</div>