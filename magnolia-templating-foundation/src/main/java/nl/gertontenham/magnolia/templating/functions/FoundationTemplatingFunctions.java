package nl.gertontenham.magnolia.templating.functions;

import com.google.common.net.MediaType;
import info.magnolia.cms.core.AggregationState;
import info.magnolia.cms.core.SystemProperty;
import info.magnolia.cms.i18n.I18nContentSupport;
import info.magnolia.jcr.util.ContentMap;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.jcr.wrapper.I18nNodeWrapper;
import info.magnolia.link.LinkUtil;
import info.magnolia.objectfactory.Components;
import info.magnolia.templating.functions.TemplatingFunctions;
import nl.gertontenham.magnolia.templating.FoundationTemplatingModule;
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

    private Provider<FoundationTemplatingModule> moduleProvider;

    private final I18nContentSupport i18n = Components.getComponent(I18nContentSupport.class);
    private final Random randomizer = new Random();


    //TODO: aggregationStateProvider is needed in constructor of TemplatingFunctions, but might be changed in future by Magnolia
    @Inject
    public FoundationTemplatingFunctions(Provider<AggregationState> aggregationStateProvider, Provider<FoundationTemplatingModule> moduleProvider) {
        super(aggregationStateProvider);
        this.moduleProvider = moduleProvider;
    }

    /*public boolean isDefaultSiteSSLEnabled() {
        return moduleProvider.get().getDefaultSite().isSslEnabled();
    }

    public String getDefaultSiteEmail() {
        return moduleProvider.get().getDefaultSite().getEmail();
    }

    public String getDefaultSiteGaAccount() {
        return moduleProvider.get().getDefaultSite().getGaAccount();
    }

    public String getDefaultSiteTheme() {
        return moduleProvider.get().getDefaultSite().getTheme();
    }*/

    /**
     * Returns the site's root {@link ContentMap} of the passed @param content {@link ContentMap}
     *
     * @param content
     * @return ContentMap of site root
     * @throws RepositoryException
     */
    public ContentMap getSiteRoot(ContentMap content) throws RepositoryException {
        return asContentMap(getSiteRoot(content.getJCRNode()));
    }

    /**
     * Returns the site's root {@link Node} of the passed @param content {@link Node}
     *
     * @param node
     * @return Node of site root
     * @throws RepositoryException
     */
    public Node getSiteRoot(Node node) throws RepositoryException {
        Node siteNode = root(page(node), NodeTypes.Page.NAME);
        return (siteNode == null) ? page(node) : siteNode;
    }

    public ContentMap i18nWrap(ContentMap content) {
        return asContentMap(this.i18nWrap(content.getJCRNode()));
    }

    public Node i18nWrap(Node content) {
        return (content == null) ? null : encode(new I18nNodeWrapper(content));
    }

    /**
     * Get all active content locales from config:/server/i18n/content/locales without the default locale
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
     * Get all active content locales from config:/server/i18n/content/locales
     * @return Collection of locales starting with default Locale
     */
    public Collection<Locale> getLocales() {
        Collection<Locale> orderList = new ArrayList<Locale>(0);
        orderList.add(getDefaultLocale());

        orderList.addAll(getLocalesWithoutDefault());

        return orderList;
    }

    /**
     * Get current locale with fallback to config:/server/i18n/content@fallbackLocale
     * @return Locale
     */
    public Locale getDefaultLocale() {
        return (i18n.getLocale() != null) ? i18n.getLocale() : i18n.getFallbackLocale();
    }

    /**
     * Get fallback locale from config:/server/i18n/content@fallbackLocale
     *
     * @return Locale
     */
    public Locale getFallbackLocale() {
        return i18n.getFallbackLocale();
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

    public boolean isDevMode() {
        return StringUtils.equalsIgnoreCase(moduleProvider.get().getMode(), Mode.DEV.toString());
    }

    public boolean isBetaMode() {
        return StringUtils.equalsIgnoreCase(moduleProvider.get().getMode(), Mode.BETA.toString());
    }

    /**
     * Get random number falling into the provided range
     * @param range
     * @return random int
     */
    public int getRandomNumber(int range) {
        return randomizer.nextInt(range);
    }
}