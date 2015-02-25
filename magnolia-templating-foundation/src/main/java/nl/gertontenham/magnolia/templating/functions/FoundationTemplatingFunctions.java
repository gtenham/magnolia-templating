package nl.gertontenham.magnolia.templating.functions;

import info.magnolia.cms.core.AggregationState;
import info.magnolia.cms.i18n.I18nContentSupport;
import info.magnolia.context.MgnlContext;
import info.magnolia.context.WebContext;
import info.magnolia.jcr.util.ContentMap;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.jcr.wrapper.I18nNodeWrapper;
import info.magnolia.link.LinkUtil;
import info.magnolia.objectfactory.Components;
import info.magnolia.repository.RepositoryConstants;
import info.magnolia.templating.functions.TemplatingFunctions;
import nl.gertontenham.magnolia.templating.FoundationTemplatingModule;
import nl.gertontenham.magnolia.templating.beans.SiteConfig;
import nl.gertontenham.magnolia.templating.managers.SiteManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Random;

/**
 * An object exposing several methods useful for foundation based templates.
 * It extends the Magnolia TemplatingFunctions and is exposed in templates as <code>mtffn</code>.
 *
 */
public class FoundationTemplatingFunctions extends TemplatingFunctions {
    private static final Logger log = LoggerFactory.getLogger(FoundationTemplatingFunctions.class);



    private enum Mode {
        DEV, BETA;
        @Override public String toString() {
            return super.toString().toLowerCase();
        }
    }

    private FoundationTemplatingModule module;
    private SiteManager siteManager;

    private final I18nContentSupport i18n = Components.getComponent(I18nContentSupport.class);
    private final WebContext webContext = MgnlContext.getWebContext();
    private final String queryString = webContext.getRequest().getQueryString();
    private final Random randomizer = new Random();


    //TODO: aggregationStateProvider is needed in constructor of TemplatingFunctions, but might be changed in future by Magnolia
    @Inject
    public FoundationTemplatingFunctions(Provider<AggregationState> aggregationStateProvider, FoundationTemplatingModule module, SiteManager siteManager) {
        super(aggregationStateProvider);
        this.module = module;
        this.siteManager = siteManager;
    }

    /**
     * Get Site configuration mapped to root node for given node.
     *
     * @param content
     * @return Site configuration, if not found an empty SiteConfig instance will be returned
     * @throws RepositoryException
     */
    public SiteConfig getSiteConfig(ContentMap content) throws RepositoryException {
        return getSiteConfig(content.getJCRNode());
    }

    /**
     * Get Site configuration mapped to root node for given node.
     *
     * @param node
     * @return Site configuration, if not found an empty SiteConfig instance will be returned
     * @throws RepositoryException
     */
    public SiteConfig getSiteConfig(Node node) throws RepositoryException {
        return siteManager.getCurrentSiteConfig(getTreeRoot(node));
    }

    /**
     * Returns the tree's root {@link ContentMap} of the passed @param content {@link ContentMap}
     *
     * @param content
     * @return ContentMap of tree root
     * @throws RepositoryException
     */
    public ContentMap getTreeRoot(ContentMap content) throws RepositoryException {
        return asContentMap(getTreeRoot(content.getJCRNode()));
    }

    /**
     * Returns the tree's root {@link Node} of the passed @param content {@link Node}
     *
     * @param node
     * @return Node of tree root
     * @throws RepositoryException
     */
    public Node getTreeRoot(Node node) throws RepositoryException {
        Node rootNode = root(page(node), NodeTypes.Page.NAME);
        return (rootNode == null) ? page(node) : rootNode;
    }

    public ContentMap i18nWrap(ContentMap content) {
        return asContentMap(this.i18nWrap(content.getJCRNode()));
    }

    public Node i18nWrap(Node content) {
        return (content == null) ? null : encode(new I18nNodeWrapper(content));
    }

    /**
     * Get all active content locales from beans:/server/i18n/content/locales without the default locale
     * @return Collection of no-default locales
     */
    public Collection<Locale> getLocalesWithoutDefault() {
        Collection<Locale> orderList = new ArrayList<Locale>(0);
        for(Locale loc : i18n.getLocales()) {
            if (!StringUtils.equalsIgnoreCase(loc.getLanguage(), getDefaultLocaleCode())) {
                orderList.add(loc);
            }
        }
        return orderList;
    }

    /**
     * Get all active content locales from beans:/server/i18n/content/locales
     * @return Collection of locales starting with default Locale
     */
    public Collection<Locale> getLocales() {
        Collection<Locale> orderList = new ArrayList<Locale>(0);
        orderList.add(getDefaultLocale());

        orderList.addAll(getLocalesWithoutDefault());

        return orderList;
    }

    /**
     * Get current locale with fallback to beans:/server/i18n/content@fallbackLocale
     * @return Locale
     */
    public Locale getDefaultLocale() {
        return (i18n.getLocale() != null) ? i18n.getLocale() : i18n.getFallbackLocale();
    }

    /**
     * Get fallback locale from beans:/server/i18n/content@fallbackLocale
     *
     * @return Locale
     */
    public Locale getFallbackLocale() {
        return i18n.getFallbackLocale();
    }

    /**
     * Is content localisation enabled
     * @return true when (/server/i18n/content@enabled has value true)
     */
    public Boolean getLocalisationEnabled() {
        return i18n.isEnabled();
    }

    /**
     * Get default locale code
     * @return language code eg. "nl", "en" ...
     */
    public String getDefaultLocaleCode() {
        return getDefaultLocale().getLanguage();
    }

    /**
     * Get localised relative page link
     * @return page link
     * @throws javax.jcr.RepositoryException
     */
    public String getLocalisedPageLink(ContentMap content, Locale locale) throws RepositoryException {
        return getLocalisedPageLink(content.getJCRNode(), locale);
    }

    /**
     * Get localised relative page link
     * @return page link
     * @throws RepositoryException
     */
    public String getLocalisedPageLink(Node node, Locale locale) throws RepositoryException {
        final Locale currentLocale = i18n.getLocale();
        final Locale pageLinkLocale = (locale == null) ? currentLocale : locale;
        // Set locale temporarily for page link rendering
        i18n.setLocale(pageLinkLocale);
        String relLink = LinkUtil.createAbsoluteLink(page(node));
        // Use basic page link (without language in url) if locale is the same as the fallback locale in Magnolia
        if (StringUtils.equalsIgnoreCase(getFallbackLocale().getLanguage(), getDefaultLocaleCode())) {
            relLink = link(node);
        }
        // reset temporarily locale back to current locale.
        i18n.setLocale(currentLocale);
        return relLink;
    }

    /**
     * Creates absolute link including context path to the provided node and performing all URI2Repository mappings and applying locales.
     *
     * @return absolute link for current page
     */
    public String getAbsolutePageLink(Node node) throws RepositoryException {
        StringBuilder pageUriSB = new StringBuilder(LinkUtil.createExternalLink(page(node))).
                append(StringUtils.isBlank(queryString) ? "": "?"+queryString);
        return pageUriSB.toString();
    }

    public boolean isDevMode() {
        return StringUtils.equalsIgnoreCase(module.getMode(), Mode.DEV.toString());
    }

    public boolean isBetaMode() {
        return StringUtils.equalsIgnoreCase(module.getMode(), Mode.BETA.toString());
    }

    /**
     * Get random number falling into the provided range
     * @param range
     * @return random int
     */
    public int getRandomNumber(int range) {
        return randomizer.nextInt(range);
    }

    /**
     * Get magnolia-templating-foundation module version number
     * @return version
     */
    public String getModuleVersion() {
        Node node = nodeByPath("/modules/magnolia-templating-foundation", RepositoryConstants.CONFIG);
        return StringUtils.lowerCase(PropertyUtil.getString(node, "version", "1.0.0"));
    }
}