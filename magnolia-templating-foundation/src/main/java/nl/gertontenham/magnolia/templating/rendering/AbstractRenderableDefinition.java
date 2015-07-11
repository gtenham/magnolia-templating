package nl.gertontenham.magnolia.templating.rendering;

import info.magnolia.context.MgnlContext;
import info.magnolia.context.WebContext;
import info.magnolia.jcr.util.ContentMap;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.RenderableDefinition;
import nl.gertontenham.magnolia.templating.functions.FoundationTemplatingFunctions;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.Collection;
import java.util.Locale;

/**
 * Created by gtenham on 2015-01-04.
 */
public class AbstractRenderableDefinition<RD extends RenderableDefinition> extends RenderingModelImpl<RD> {

    private static final Logger log = LoggerFactory.getLogger(AbstractRenderableDefinition.class);

    private static final String HTTPS_SCHEME = "https://";

    protected final FoundationTemplatingFunctions templatingFunctions;
    protected final WebContext webContext = MgnlContext.getWebContext();
    protected final String queryString = webContext.getRequest().getQueryString();

    @Inject
    public AbstractRenderableDefinition(Node content, RD definition, RenderingModel<?> parent, FoundationTemplatingFunctions templatingFunctions) {
        super(content, definition, parent);
        this.templatingFunctions = templatingFunctions;
    }

    /**
     * Check if given content node is the level 1 root node for a page
     *
     * @param content
     * @return Boolean
     * @throws javax.jcr.RepositoryException
     */
    public Boolean isSiteRoot(ContentMap content) throws RepositoryException {
        return isSiteRoot(content.getJCRNode());
    }

    /**
     * Check if given content node is the level 1 root node for a page
     *
     * @param node
     * @return Boolean
     * @throws RepositoryException
     */
    public Boolean isSiteRoot(Node node) throws RepositoryException {
        return templatingFunctions.page(node).getDepth() == 1;
    }

    /**
     * Returns the site's root {@link ContentMap} of the passed @param content {@link ContentMap}
     *
     * @param content
     * @return ContentMap of site root
     * @throws RepositoryException
     */
    public ContentMap getTreeRoot(ContentMap content) throws RepositoryException {
        return templatingFunctions.getTreeRoot(content);
    }

    /**
     * Returns the site's root {@link Node} of the passed @param content {@link Node}
     *
     * @param node
     * @return Node of site root
     * @throws RepositoryException
     */
    public Node getTreeRoot(Node node) throws RepositoryException {
        return templatingFunctions.getTreeRoot(node);
    }

    /**
     * Get all active content locales from beans:/server/i18n/content/locales without the default locale
     * @return Collection of no-default locales
     */
    public Collection<Locale> getLocalesWithoutDefault() {
        return templatingFunctions.getLocalesWithoutDefault();
    }

    /**
     * Get all active content locales from beans:/server/i18n/content/locales
     * @return Collection of locales starting with default Locale
     */
    public Collection<Locale> getLocales() {
        return templatingFunctions.getLocales();
    }

    /**
     * Get current locale with fallback to beans:/server/i18n/content@fallbackLocale
     * @return Locale
     */
    public Locale getDefaultLocale() {
        return templatingFunctions.getDefaultLocale();
    }

    /**
     * Get default locale code
     * @return language code eg. "nl", "en" ...
     */
    public String getDefaultLocaleCode() {
        return templatingFunctions.getDefaultLocaleCode();
    }

    /**
     * Get fallback locale from beans:/server/i18n/content@fallbackLocale
     *
     * @return Locale
     */
    public Locale getFallbackLocale() {
        return templatingFunctions.getFallbackLocale();
    }

    /**
     * Is https used already
     *
     * @return true when https scheme is used in request
     */
    private boolean isSecuredPage() {

        String requestUrl = webContext.getRequest().getRequestURL().toString();
        if (StringUtils.startsWithIgnoreCase(requestUrl, HTTPS_SCHEME)) {
            return true;
        }
        return false;
    }

    /**
     * Get magnolia-templating-foundation module version number
     * @return version
     */
    public String getModuleVersion() {
        return templatingFunctions.getModuleVersion();
    }
}
