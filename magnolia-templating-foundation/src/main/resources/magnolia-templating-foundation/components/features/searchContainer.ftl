[#include "/magnolia-templating-foundation/components/macros/siteNavigation.ftl"/]

[#assign searchTerm = ctx.parameters.s!"" /]
[#assign queryString = "s=${searchTerm}" /]
<div>
    <form class="row collapse" action="${cmsfn.link(cmsfn.page(content))}" method="get">
        <div class="small-10 columns">
            <input type="text" name="s" placeholder="Search website" value="${searchTerm}">
        </div>
        <div class="small-2 columns">
            <button type="submit" class="small button alert postfix"><i class="fa fa-search fa-lg"></i> Search</button>
        </div>
    </form>

    [@cms.area name="results"/]
</div>

