[#macro renderSeoPageLinks pageModel]
	<link rel="canonical" href="${pageModel.absolutePageLink!}" />
    [#if mtffn.localisationEnabled!false ]
        [#list mtffn.localesWithoutDefault as locale]
            [#assign language = locale.language /]
            <link rel="alternate" hreflang="${language}" href="${pageModel.getLocalisedPageLink(locale)}" />
        [/#list]
    [/#if]
[/#macro]

[#macro renderOpenGraph pageModel]
    <meta property="og:title" content="${pageModel.getTitle()!}" />
    <meta property="og:url" content="${pageModel.absolutePageLink!}" />
    <meta property="og:site_name" content="${pageModel.getSiteTitle()!}" />
    <meta property="og:locale" content="${cmsfn.language()!"en"}" />
    <meta property="og:description" content="${pageModel.getMetaDescription()!}" />
    <meta property="og:type" content="article" />
[/#macro]