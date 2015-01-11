[#macro DAMImage imageUuid altTitle imageClass resizeTo="noResize"]
    [#if imageUuid?has_content]
        [#assign asset = damfn.getAssetForId(imageUuid) /]
        [#if asset??]
            [#assign imageAlt=altTitle!asset.title!content.subtitle!]
            [#if resizeTo == "noResize"]
            <img class="${imageClass!}" src="${damfn.getAssetLinkForId(imageUuid)}" alt="${imageAlt}"/>
            [#else]
            <img data-interchange="[${damfn.getAssetLinkForId(imageUuid, 'small')}, (small)], [${damfn.getAssetLinkForId(imageUuid, 'medium')}, (medium)], [${damfn.getAssetLinkForId(imageUuid, resizeTo)}, (large)]"
                 class="${imageClass!}" alt="${imageAlt}" />
                 
            <noscript><img src="${damfn.getAssetLinkForId(imageUuid, 'small')}" class="${imageClass!}" alt="${imageAlt}" /></noscript>
            [/#if]
        [/#if]
    [/#if]
[/#macro]

[#macro DAMImageByPath path altTitle imageClass resizeTo="noResize"]
    [#if path?has_content]
        [#if resizeTo == "noResize"]
            [#assign imageLink = mtffn.getImageLinkForAsset(path) /]
            [#if imageLink?has_content]
                [#assign imageAlt=altTitle!content.subtitle!]
            <img class="${imageClass!}" src="${imageLink}" alt="${imageAlt}"/>
            [/#if]
        [#else]
            [#assign imageAlt=altTitle!content.subtitle!]
        <img data-interchange="[${mtffn.getImageLinkForAsset(path, 'small')}, (small)], [${mtffn.getImageLinkForAsset(path, 'medium')}, (medium)], [${mtffn.getImageLinkForAsset(path, 'large')}, (large)]"
             class="${imageClass!}" alt="${imageAlt}" />
             <noscript><img src="${mtffn.getImageLinkForAsset(path, 'small')}" class="${imageClass!}" alt="${imageAlt}"/></noscript>
        [/#if]
    [/#if]
[/#macro]

[#macro imageFromBinaryNode image workspace altText resizeTo="default"]
    [#if image?has_content && workspace?has_content]
    <img src="${contextPath}/.imaging/${resizeTo}/${workspace}/${image.@uuid!}/${image.fileName!"photo"}.${image.fileType!"jpg"}"
         alt="${altText!}"/>
    [/#if]
[/#macro]