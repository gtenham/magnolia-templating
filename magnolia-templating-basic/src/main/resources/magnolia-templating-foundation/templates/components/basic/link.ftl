[#assign linkText = model.title!]
[#assign linkHref = model.link!]

[#if model.isDownload()]
    [#assign asset = model.asset!]
    [#assign extension = mtffn.fileExtension(asset.getFileName())]
    [#assign linkText =  "${linkText} <em>(" + extension?upper_case + ", " + mtffn.readableFileSize(asset.fileSize) + ")</em>"]
[/#if]

[#if content.newWindow!false && !linkHref?starts_with("#")]
    [#assign linkTarget = " target=\"_blank\""]
[/#if]

[#if linkHref?exists || cmsfn.editMode]
<a href="${linkHref}"${linkTarget!} title="${model.title!}" rel="${model.linkType}">
    [#if content.icon?has_content]<i class="${content.icon!}"></i>[/#if] ${linkText}
</a>
[/#if]