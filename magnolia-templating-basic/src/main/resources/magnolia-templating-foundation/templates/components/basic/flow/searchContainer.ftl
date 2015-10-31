[#assign searchTerm = ctx.parameters.s!?html /]
[#assign contentTerm = ctx.parameters.content!"all"?html /]
[#assign queryString = "s=${searchTerm}" /]

<div>
    <form class="row collapse fullwidth" action="${cmsfn.link(cmsfn.page(content))}" method="get">
        <div class="small-11 columns">
            <input type="text" name="s" placeholder="Search website" value="${searchTerm!}">
        </div>
        <div class="small-1 columns">
            <button type="submit" class="small button postfix"><i class="fa fa-search fa-lg"></i></button>
        </div>
        <input type="hidden" name="content" value="${contentTerm}">
    </form>

[@cms.area name="results"/]
</div>