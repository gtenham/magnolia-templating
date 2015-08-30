<ul class="social">
	[#list cmsfn.children(content.socialIcons) as socialIcon ]
	    <li class="social-icon"><a href="${socialIcon.link!"#"}" id="${socialIcon.platform!}"></a></li>
	[/#list]
</ul>