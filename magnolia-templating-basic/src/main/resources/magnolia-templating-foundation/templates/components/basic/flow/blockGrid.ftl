[#assign boxLayout = ctx.boxLayout!false /]
[#assign categoryBlockgrid = ctx.category! /]
[#assign cssClass = "small-block-grid-${content.smallView!'1'} medium-block-grid-${content.mediumView!'3'} large-block-grid-${content.largeView!'6'}" /]

[#if (boxLayout!false)]<div class="row">[/#if]
    <ul class="${cssClass!?trim}">
        [@cms.area name="widgets" contextAttributes={"category":categoryBlockgrid, "useBoxLayout":false}/]
        [#if cmsfn.editMode]
            <li>
                <span cms:add="bar">&nbsp;</span>
            </li>
        [/#if]
    </ul>
[#if (boxLayout!false)]</div>[/#if]