[#macro renderNavigation navigation maxLevel]
    [#assign isSelected = (navigation.@path == state.handle)!false /]
    [#assign isLowerLevel = ( navigation.@level <= maxLevel)!true /]
    [#assign isEqualLevel = ( navigation.@level == maxLevel)!true /]
	[#assign navTitle= navigation.navigationTitle!navigation.title!"[no title]" /]
	[#assign deletedOn = false /]
	
    [#if (!navigation.hideInNav!false) && (isLowerLevel) && (!deletedOn)]
	    [#if (cmsfn.children(navigation, "mgnl:page")?size > 0 && (!isEqualLevel)) && hasVisibleChildren(navigation) ]
	        <li class="has-dropdown">
	            <a href="${cmsfn.link(navigation)}" [#if (isSelected ) ]class="active"[/#if]>${navTitle}</a>
	            <ul class="dropdown">
	               [#list cmsfn.children(navigation, "mgnl:page") as childPage]
	                   [@renderNavigation navigation=childPage maxLevel=maxLevel/]
	               [/#list]
	            </ul>
	         </li>
	    [#else]
	        <li>
	            <a href="${cmsfn.link(navigation)}" [#if (isSelected ) ]class="active"[/#if]>${navTitle}</a>
	        </li>
	    [/#if]
   [/#if] 
[/#macro]

[#macro renderPagination pageModel pageLink queryString]

	[#assign numPages = pageModel.numPages! /]
	[#assign prevPage = pageModel.getPreviousPage()! /]
	[#assign nextPage = pageModel.getNextPage()! /]
	[#assign firstPageInRange = pageModel.getFirstPageInRange()! /]
	[#assign lastPageInRange = pageModel.getLastPageInRange()! /]
	[#assign currentPage = pageModel.getPageNumber()! /]

	<div class="line">
		<section class="pagination-centered"> 
			<ul class="pagination"> 
				<li class="arrow [#if (currentPage?number == 1)]unavailable[/#if]">
					<a href="${pageLink}?${queryString}&amp;p=${prevPage}">&laquo;</a>
				</li> 
                [#if (currentPage?number > 3)]
					<li>
						<a href="${pageLink}?${queryString}&amp;p=1">1</a>
					</li> 
				[/#if]
				[#if (currentPage?number > 4)]
					<li class="unavailable"><a href="">&hellip;</a></li> 
				[/#if]
				
				[#list firstPageInRange..lastPageInRange as index]
                    [#assign itemClass = "" /]
					[#if (currentPage?number == index)]
						[#assign itemClass = 'class="current"' /]
					[/#if]
					<li ${itemClass!""}>
						<a href="${pageLink}?${queryString}&amp;p=${index}">${index}</a>
					</li> 
				[/#list]
				
				[#if ((lastPageInRange + 1) < numPages)]
					<li class="unavailable"><a href="">&hellip;</a></li> 
				[/#if]
				[#if (numPages > lastPageInRange)]
					<li>
						<a href="${pageLink}?${queryString}&amp;p=${numPages}">${numPages}</a>
					</li> 
				[/#if]
				<li class="arrow [#if (currentPage?number == numPages)]unavailable[/#if]">
					<a href="${pageLink}?${queryString}&amp;p=${nextPage}">&raquo;</a>
				</li> 
			</ul> 
		</section>
	</div>
[/#macro]

[#macro renderBreadcrumbs pageModel currentPage]
	[#assign breadcrumbs = pageModel.getBreadcrumbs(currentPage)! /]
    [#if breadcrumbs??]
        [#assign listSize = breadcrumbs?size /]
        <nav>
            <ul class="breadcrumbs">
                [#list breadcrumbs as node]
                    [#assign isLast = (node_index + 1 == listSize!0)!false /]
                    [#if (isLast)]
                        <li class="current">${node.navigationTitle!node.title!""}</li>
                    [#else]
                        <li><a href="${mtffn.link(node)}">${node.navigationTitle!node.title!""}</a></li>
                    [/#if]
                [/#list]
            </ul>
        </nav>
    [/#if]
[/#macro]

[#function hasVisibleChildren navigation]
    [#local hasVisibleChildren = false /]
    [#list cmsfn.children(navigation, "mgnl:page") as childPage]
        [#if !childPage.hideInNav!false]
            [#local hasVisibleChildren = true /]
            [#break /]
        [/#if]
    [/#list]
    [#return hasVisibleChildren /]
[/#function]