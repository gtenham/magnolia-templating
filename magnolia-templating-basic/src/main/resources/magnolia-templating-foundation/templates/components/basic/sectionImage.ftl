[#assign hasImage=false]
[#if content.image?exists && content.image?has_content]
    [#assign hasImage=true]
[/#if]

[#if hasImage]
    [#assign assetRendition=damfn.getRendition(content.image, "original")]

<figure class="${content.alignment!"text-left"}">
    [#assign assetRendition=damfn.getRendition(content.image, "original")]
    [@image image=assetRendition content=content imageClass="list-image" useOriginal=false /]
    <figcaption>${content.title!}</figcaption>
</figure>
[/#if]


[#-- Renders an image (asset) rendition --]
[#macro image image content imageClass="img-responsive" useOriginal=false]
    [#if image?has_content]
    [#-- Fallback text for alt text --]
        [#assign assetTitle=i18n['image.alt.unavailable']]
        [#if image.asset?? && image.asset.title?has_content]
            [#assign assetTitle=image.asset.title]
        [/#if]

    [#-- Alt text and title --]
        [#assign imageAlt=content.title!i18n['image.alt.prefix']+": "+assetTitle]

        [#assign imageLink=image.link /]
    [#-- For PNGs it might be useful to use render the original asset and therefore bypassing imaging --]
        [#if useOriginal]
            [#assign imageLink=image.asset.link /]
        [/#if]

    [#-- Render the image --]
    <img src="${imageLink}" alt="${imageAlt}" class="${imageClass}"/>
    [/#if]
[/#macro]