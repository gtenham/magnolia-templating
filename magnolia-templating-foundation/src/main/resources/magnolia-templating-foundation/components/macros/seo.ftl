[#macro renderSeoPageLinks pageModel]
	<link rel="canonical" href="${pageModel.absolutePageLink!}" />
	[#list mtffn.localesWithoutDefault as locale]
		[#assign language = locale.language /]
		<link rel="alternate" hreflang="${language}" href="${pageModel.getLocalisedPageLink(locale)}" />
	[/#list]
[/#macro]