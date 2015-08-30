[#assign boxLayout = ctx.boxLayout!false /]
[#assign categorySource = ctx.category! /]
[#assign useBoxLayout = ctx.useBoxLayout!true /]

[#if (boxLayout && useBoxLayout)]<div class="row">[/#if]
[#if content.text?has_content]
${cmsfn.decode(content).text}
[/#if]
[#if (boxLayout && useBoxLayout)]</div>[/#if]