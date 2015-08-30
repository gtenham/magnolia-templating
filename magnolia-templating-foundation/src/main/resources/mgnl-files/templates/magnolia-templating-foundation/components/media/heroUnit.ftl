[#include "/magnolia-templating-foundation/components/macros/image.ftl"/]
[#assign linkPage = cmsfn.contentById(content.link)!/]
[#assign teaserTitle = cmsfn.decode(content).alternativeTitle!cmsfn.decode(linkPage).title! /]
[#assign teaserAbstract = content.alternativeAbstract!linkPage.abstract! /]

<div class="hero-unit">
	 <div class="hero-details small-12 large-7 columns">
	 	<h2>${teaserTitle}</h2>
	 	[#if content.subTitle?has_content]
	 		<h4>${content.subTitle}</h4>
	 	[/#if]
	 	<p>${teaserAbstract}</p>
	 	<a href="${cmsfn.link(linkPage)}" class="button small">${i18n["magnolia-templating-foundation.text.read.more"]}</a>
	 </div>
	 <div class="hero-image small-12 large-5 columns">
	 	[@DAMImage imageUuid=content.image! altTitle=content.imageDescription! imageClass="" resizeTo="default" /]
	 </div>
 </div>