[#assign upsize]
    [#switch components?size + 1]
        [#case 1]
        one-up
            [#break]
        [#case 2]
        two-up
            [#break]
        [#case 3]
        three-up
            [#break]
        [#case 4]
        four-up
            [#break]
        [#case 5]
        five-up
            [#break]
        [#case 6]
        six-up
            [#break]
        [#case 7]
        seven-up
            [#break]
        [#case 8]
        eight-up
            [#break]
        [#default]
        one-up
    [/#switch]
[/#assign]
<div class="icon-bar ${upsize} label-right" role="navigation">
    <a class="item" role="button" tabindex="0" aria-label="filter all" aria-labelledby="#itemlabel1">
        <i class="fa fa-filter"></i>
        <label id='itemlabel1'>All (${model.totalCount})</label>
    </a>
[#list components as component ]
    [#assign x = component_index + 2 /]
    <a class="item" role="button" tabindex="0" aria-label="filter ${model.getComponentSubtitle(component)!""}" aria-labelledby="#component${x}">
        <i class="fa fa-filter"></i>
        <label id='component${x}'>${component.subtitle!""} (${model.getComponentTotal(component)!"oops"})</label>
    </a>
[/#list]

</div>

[#list components as component ]
    [@cms.component content=component /]
[/#list]