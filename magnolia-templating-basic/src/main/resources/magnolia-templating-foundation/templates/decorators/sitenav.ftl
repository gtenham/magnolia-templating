[#include "/magnolia-templating-foundation/templates/macros/siteNavigation.ftl" /]
[#assign siteConfig = model.siteConfig! /]
[#assign treeRoot = model.getTreeRoot(content)! /]
[#assign isSelected = (treeRoot.@path == state.handle)!false /]
[#assign searchTerm = ctx.parameters.s!?html /]
[#assign currentPageNode = cmsfn.page(content)! /]

<nav id="main-nav" class="off-canvas-left" role="navigation">
    <ul class="side-nav">
        [#if !treeRoot.hideInNav!false ]
        <li>
            <a href="${cmsfn.link(treeRoot)}" [#if (isSelected ) ]class="active"[/#if]>${treeRoot.navigationTitle!treeRoot.title!"Home"}</a>
        </li>
        [/#if]
        [#list cmsfn.children(treeRoot, "mgnl:page") as childPage]
            [@renderNavigation navigation=childPage maxLevel=4 /]
        [/#list]
    </ul>
</nav>
[#if (siteConfig.siteSearchResultsPage?has_content) ]
[#if (siteConfig.siteSearchResultsPage?contains(currentPageNode.@id) && searchTerm?has_content)]
    [#assign isVisible = "visible" /]
[/#if]
<div id="site-search" class="search ${isVisible!}" role="search">
    <form action="${cmsfn.link("website", siteConfig.siteSearchResultsPage)}">
        <input type="search" name="s" placeholder="Search..." value="${searchTerm!}" >
    </form>
</div>
[/#if]