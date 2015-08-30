package nl.gertontenham.magnolia.templating.rendering.components;

import info.magnolia.dam.api.Asset;
import info.magnolia.dam.templating.functions.DamTemplatingFunctions;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.template.RenderableDefinition;
import info.magnolia.repository.RepositoryConstants;
import info.magnolia.util.EscapeUtil;
import nl.gertontenham.magnolia.templating.functions.FoundationTemplatingFunctions;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

/**
 * Created by gtenham on 2015-08-28.
 */
public class LinkModel<RD extends RenderableDefinition> extends BaseComponentRenderableDefinition<RD> {

    private static final Logger log = LoggerFactory.getLogger(LinkModel.class);

    public static final String INTERNAL = "internal";
    public static final String EXTERNAL = "external";
    public static final String DOWNLOAD = "download";

    public static final String PROPERTY_NAME_LINK_TYPE = "linkType";
    public static final String PROPERTY_NAME_TITLE = "title";

    public static final String PROPERTY_NAME_INTERNAL = PROPERTY_NAME_LINK_TYPE + INTERNAL;
    public static final String PROPERTY_NAME_EXTERNAL = PROPERTY_NAME_LINK_TYPE + EXTERNAL;
    public static final String PROPERTY_NAME_DOWNLOAD = PROPERTY_NAME_LINK_TYPE + DOWNLOAD;

    private final DamTemplatingFunctions damTemplatingFunctions;

    @Inject
    public LinkModel(Node content, RD definition, RenderingModel<?> parent, FoundationTemplatingFunctions templatingFunctions, DamTemplatingFunctions damTemplatingFunctions) {
        super(content, definition, parent, templatingFunctions);
        this.damTemplatingFunctions = damTemplatingFunctions;
    }

    /**
     * Returns the type of the link, {@link #INTERNAL}, {@link #EXTERNAL} or {@link #DOWNLOAD}.
     */
    public String getLinkType() {
        return PropertyUtil.getString(content, PROPERTY_NAME_LINK_TYPE, "");
    }

    /**
     * Checks whether the link is an {@link #INTERNAL} link.
     */
    public boolean isInternal() {
        return INTERNAL.equals(getLinkType());
    }

    /**
     * Checks whether the link is an {@link #EXTERNAL} link.
     */
    public boolean isExternal() {
        return EXTERNAL.equals(getLinkType());
    }

    /**
     * Checks whether the link is a {@link #DOWNLOAD} link.
     */
    public boolean isDownload() {
        return DOWNLOAD.equals(getLinkType());
    }

    /**
     * Get the title for the link based on the link type.
     *
     * @see #getLinkType()
     */
    public String getTitle() {
        if (isInternal()) {
            return getInternalTitle();
        } else if (isDownload()) {
            return getDownloadTitle();
        } else if (isExternal()) {
            return getExternalTitle();
        }

        return "";
    }

    /**
     * Get the download title by trying to determine the title of the {@link info.magnolia.dam.api.Asset}.
     */
    protected String getDownloadTitle() {
        String title = PropertyUtil.getString(content, PROPERTY_NAME_TITLE);

        if (StringUtils.isBlank(title)) {
            final Asset asset = getAsset();
            if (asset != null) {
                title = asset.getTitle();
                if (StringUtils.isBlank(title)) {
                    title = asset.getFileName();
                }
            }
        }

        return title;
    }

    /**
     * Get the title for an internal link by returning the first of the following items that are available
     * <ul>
     * <li>The title field of the component</li>
     * <li>The title of the referenced page</li>
     * <li>The node name of the referenced page</li>
     * </ul>.
     */
    protected String getInternalTitle() {
        final String title = PropertyUtil.getString(content, PROPERTY_NAME_TITLE);
        if (StringUtils.isNotBlank(title)) {
            return title;
        }

        try {
            final Node linkedNode = templatingFunctions.contentByReference(content, PROPERTY_NAME_INTERNAL, RepositoryConstants.WEBSITE);
            final String pageTitle = PropertyUtil.getString(linkedNode, PROPERTY_NAME_TITLE, "");
            if (StringUtils.isNotBlank(pageTitle)) {
                return pageTitle;
            }
            return linkedNode.getName();
        } catch (RepositoryException e) {
            log.warn("An error occurred when trying to get referenced content for node [{}] and property [{}]", content, PROPERTY_NAME_INTERNAL, e);
        }

        return "";
    }

    /**
     * Get the title for an external link.
     *
     * @see info.magnolia.templating.functions.TemplatingFunctions#externalLinkTitle(Node, String, String)
     */
    protected String getExternalTitle() {
        final String title = templatingFunctions.externalLinkTitle(content, PROPERTY_NAME_EXTERNAL, PROPERTY_NAME_TITLE);
        return EscapeUtil.escapeXss(title);
    }

    /**
     * Get the url for the link based on the linkType.
     */
    public String getLink() {
        if (isInternal()) {
            return getInternalLink();
        } else if (isDownload()) {
            return getDownloadLink();
        } else if (isExternal()) {
            return getExternalLink();
        }

        return "";
    }

    /**
     * Get the internal link by using the stored identifier to resolve the referenced {@link Node} and linking to it.
     * Limited to workspace {@link RepositoryConstants#WEBSITE} only.
     *
     * @see info.magnolia.templating.functions.TemplatingFunctions#link(String, String)
     */
    protected String getInternalLink() {
        return templatingFunctions.link(RepositoryConstants.WEBSITE, PropertyUtil.getString(content, PROPERTY_NAME_INTERNAL));
    }

    /**
     * Get the external link. Anchors starting with <code>#</code> are returned as is.
     *
     * @see info.magnolia.templating.functions.TemplatingFunctions#externalLink(Node, String)
     */
    protected String getExternalLink() {
        final String linkRaw = PropertyUtil.getString(content, PROPERTY_NAME_EXTERNAL);
        if (StringUtils.startsWith(linkRaw, "#")) {
            return EscapeUtil.escapeXss(linkRaw);
        }

        final String link = templatingFunctions.externalLink(content, PROPERTY_NAME_EXTERNAL);
        return EscapeUtil.escapeXss(link);
    }

    /**
     * Get the link to a downloadable {@link Asset}.
     *
     * @see #getAsset()
     */
    protected String getDownloadLink() {
        final Asset asset = getAsset();
        if (asset != null) {
            return asset.getLink();
        }
        return null;
    }

    /**
     * Retrieve the Asset referenced by the identifier stored into the 'link'
     * properties.
     */
    public Asset getAsset() {
        final String assetIdentifier = PropertyUtil.getString(content, PROPERTY_NAME_DOWNLOAD);
        if (StringUtils.isNotBlank(assetIdentifier)) {
            return damTemplatingFunctions.getAsset(assetIdentifier);
        }
        return null;
    }

}
