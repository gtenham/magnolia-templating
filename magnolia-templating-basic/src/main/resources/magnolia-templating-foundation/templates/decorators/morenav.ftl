[#assign pageModel= ctx.pageModel! /]
[#assign siteConfig = pageModel.siteConfig! /]

<div id="more-nav">
<h4>Morenav implementation</h4>
</div>
[#--<div id="top-bar" class="row">--]

        [#--<div class="large-12 columns">--]
            [#--<!-- left text -->--]
            [#--<div class="left">--]
                [#--<div>${siteConfig.siteSlogan!''}</div>--]
            [#--</div>--]
            [#--<!-- top bar right -->--]
            [#--<nav class="right" role="navigation" aria-label="top bar navigation">--]
                [#--<ul class="inline-list">--]
                    [#--<li class="active"><a href="#">Some link</a></li>--]
                    [#--<li class="divider"></li>--]
                    [#--[#if mtffn.localisationEnabled!false]--]
                    [#--<li>--]
                        [#--<a data-dropdown="language-selector" data-options="is_hover:true; hover_timeout:5000">${mtffn.defaultLocaleCode?upper_case}</a>--]
                        [#--<ul id="language-selector" class="tiny f-dropdown" data-dropdown-content aria-hidden="true" tabindex="-1">--]
                        [#--[#list mtffn.localesWithoutDefault as locale]--]
                            [#--[#assign language = locale.language /]--]
                            [#--[#assign localisedPageLink = pageModel.getLocalisedPageLink(locale) /]--]
                            [#--[#if ctx.request.queryString?has_content]--]
                                [#--[#assign localisedPageLink = localisedPageLink + "?" + ctx.request.queryString /]--]
                            [#--[/#if]--]
                            [#--<li><a href="${localisedPageLink}">${language?upper_case}</a></li>--]
                        [#--[/#list]--]
                        [#--</ul>--]
                    [#--</li>--]
                    [#--[/#if]--]
                [#--</ul>--]

            [#--</nav>--]

        [#--</div><!-- .large-12 columns -->--]

[#--</div>--]