package nl.gertontenham.magnolia.templating.rendering.pages;

import info.magnolia.jcr.util.ContentMap;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.link.LinkUtil;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.template.RenderableDefinition;
import nl.gertontenham.magnolia.templating.beans.SiteConfig;
import nl.gertontenham.magnolia.templating.functions.FoundationTemplatingFunctions;
import nl.gertontenham.magnolia.templating.rendering.AbstractRenderableDefinition;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Page renderable definition which can be used as the base renderable definition class for pages
 */
public class BasePageRenderableDefinition<RD extends RenderableDefinition> extends AbstractRenderableDefinition<RD> {

    private static final Logger log = LoggerFactory.getLogger(BasePageRenderableDefinition.class);
    @Inject
    public BasePageRenderableDefinition(Node content, RD definition, RenderingModel<?> parent, FoundationTemplatingFunctions templatingFunctions) {
        super(content, definition, parent, templatingFunctions);
    }

    /**
     * Get full breadcrumb path for content
     *
     * @param content
     * @return List of breadcrumbs for content
     * @throws javax.jcr.RepositoryException
     */
    public List<ContentMap> getBreadcrumbs(ContentMap content) throws RepositoryException {
        return templatingFunctions.asContentMapList(getBreadcrumbs(content.getJCRNode()));
    }

    /**
     * Get full breadcrumb path for node
     *
     * @param node
     * @return List of breadcrumbs for node
     * @throws RepositoryException
     */
    public List<Node> getBreadcrumbs(Node node) throws RepositoryException {
        List<Node> breadcrumbs = getAncestors(node);
        breadcrumbs.add(node);
        return breadcrumbs;
    }


    /**
     * Get ancestors for content
     *
     * @param content
     * @return List of ancestors for content
     * @throws RepositoryException
     */
    public List<ContentMap> getAncestors(ContentMap content) throws RepositoryException {
        return templatingFunctions.asContentMapList(getAncestors(content.getJCRNode()));
    }

    /**
     * Get ancestors for node
     *
     * @param node
     * @return List of ancestors for node
     * @throws RepositoryException
     */
    public List<Node> getAncestors(Node node) throws RepositoryException {
        List<Node> allAncestors = templatingFunctions.ancestors(node, NodeTypes.Page.NAME);
        Node siteRoot = getTreeRoot(node);
        List<Node> ancestors = new ArrayList<Node>(0);
        for (Node current : allAncestors) {
            if (current.getDepth() >= siteRoot.getDepth()) {
                ancestors.add(current);
            }
        }
        return ancestors;
    }

    /**
     * Get page meta keywords property value
     *
     * @return String
     * @throws RepositoryException
     */
    public String getMetaKeywords() throws RepositoryException {
        String keywords = PropertyUtil.getString(content, "keywords");
        String alternativeKeywords = PropertyUtil.getString(content, "title", content.getName());
        return StringUtils.defaultIfEmpty(keywords, alternativeKeywords);
    }

    /**
     * Get page meta description property value
     *
     * @return String
     */
    public String getMetaDescription() {
        return PropertyUtil.getString(content, "description");
    }

    /**
     * Get page title property value
     *
     * @return String
     * @throws RepositoryException
     */
    public String getTitle() throws RepositoryException {
        String windowTitle = StringUtils.defaultIfEmpty(PropertyUtil.getString(content, "windowTitle"), PropertyUtil.getString(content, "title"));
        String siteTitle = getSiteTitle();

        if (StringUtils.isNotBlank(siteTitle)) {
            return windowTitle + " | " + siteTitle;
        }
        return windowTitle;
    }

    /**
     * Get Site manager value for Google Analytics
     *
     * @return Google Analytics account code
     */
    public String getGoogleAnalytics() throws RepositoryException {
        return StringUtils.defaultIfEmpty(getSiteConfig().getGaAccount(),"");
    }

    /**
     * Get Site configuration data
     *
     * @return site configuration data
     */
    public SiteConfig getSiteConfig() throws RepositoryException {
        return templatingFunctions.getSiteConfig(content);
    }

    /**
     * Get property value for site title
     *
     * @return String
     * @throws RepositoryException
     */
    public String getSiteTitle() throws RepositoryException {
        return StringUtils.defaultIfEmpty(getSiteConfig().getSiteTitle(),"");
    }

    /**
     * Creates absolute link including context path to the provided node and performing all URI2Repository mappings and applying locales.
     *
     * @return absolute link for current page
     */
    public String getAbsolutePageLink() throws RepositoryException {
        return  templatingFunctions.getAbsolutePageLink(content);
    }

    /**
     * Get localised relative page link
     * @return page link
     * @throws RepositoryException
     */
    public String getLocalisedPageLink(Locale locale) throws RepositoryException {
        return templatingFunctions.getLocalisedPageLink(content, locale);
    }

}
