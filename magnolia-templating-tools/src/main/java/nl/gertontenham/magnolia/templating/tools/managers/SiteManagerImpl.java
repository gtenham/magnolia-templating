package nl.gertontenham.magnolia.templating.tools.managers;

import info.magnolia.cms.beans.config.ServerConfiguration;
import info.magnolia.cms.beans.config.URI2RepositoryMapping;
import info.magnolia.cms.core.AggregationState;
import info.magnolia.cms.i18n.I18nContentSupport;
import info.magnolia.context.MgnlContext;
import info.magnolia.context.WebContext;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.objectfactory.Components;
import info.magnolia.repository.RepositoryConstants;
import info.magnolia.templating.functions.TemplatingFunctions;
import nl.gertontenham.magnolia.templating.FoundationTemplatingModule;
import nl.gertontenham.magnolia.templating.beans.SiteConfig;
import nl.gertontenham.magnolia.templating.managers.SiteManager;
import nl.gertontenham.magnolia.templating.tools.beans.SiteProxyBasedVirtualURIMapping;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Site manager implementation
 */
public class SiteManagerImpl implements SiteManager {

    private static final Logger log = LoggerFactory.getLogger(SiteManagerImpl.class);

    private final ServerConfiguration serverConfig = Components.getComponent(ServerConfiguration.class);
    private FoundationTemplatingModule module;
    private Provider<AggregationState> aggregationStateProvider;
    private TemplatingFunctions templatingFunctions;

    @Inject
    public SiteManagerImpl(FoundationTemplatingModule module, Provider<AggregationState> aggregationStateProvider, TemplatingFunctions templatingFunctions) {
        this.module = module;
        this.aggregationStateProvider = aggregationStateProvider;
        this.templatingFunctions = templatingFunctions;
    }

    public Collection<SiteConfig> getSites() {
        Collection<SiteConfig> sites = new ArrayList<SiteConfig>(0);
        Iterator it = module.getSites().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            SiteConfig site = (SiteConfig) entry.getValue();

            sites.add(site);
        }
        return sites;
    }

    public SiteConfig getCurrentSiteConfig(Node rootNode) {
        SiteConfig siteConfig = new SiteConfig();
        if (rootNode == null) {
            return getDefaultSiteConfig();
        }
        try {
            for (SiteConfig site : getSites()) {
                if (StringUtils.equalsIgnoreCase(site.getSitePageMap(), rootNode.getIdentifier())) {
                    siteConfig = site;
                }
            }
        } catch (RepositoryException e) {
            log.debug("getCurrentSiteConfig for root node loading error", e);
        }
        return siteConfig;
    }

    public SiteConfig getCurrentSiteConfig() {
        Node mainNode = aggregationStateProvider.get().getCurrentContentNode();
        SiteConfig siteConfig = getDefaultSiteConfig();

        try {
            siteConfig = getCurrentSiteConfig(templatingFunctions.root(mainNode));
        } catch (RepositoryException e) {
            log.debug("getCurrentSiteConfig loading error", e);
        }
        return siteConfig;

    }

    public Collection<URI2RepositoryMapping> getCurrentSiteUriMappings() {
        Collection<URI2RepositoryMapping> mappings = new HashSet<URI2RepositoryMapping>(0);

        SiteConfig bestMatchedSiteConfig = getBestMatchedSite();
        if ( StringUtils.isNotBlank(bestMatchedSiteConfig.getMappedServerName())) {
            Node siteNode = templatingFunctions.nodeById(bestMatchedSiteConfig.getSitePageMap());
            try {
                // Make uriPrefix depend on configured siteUriMapping
                String uriPrefix = getMappedServerPath(bestMatchedSiteConfig);
                log.debug("urimap {} to: {}", uriPrefix, siteNode.getPath());
                URI2RepositoryMapping uriMapping = new URI2RepositoryMapping(uriPrefix, RepositoryConstants.WEBSITE, siteNode.getPath());
                mappings.add(uriMapping);
            } catch (RepositoryException e) {
                log.debug("Problem during urimapping generation", e);
            }
        }
       return mappings;
    }

    private SiteConfig getBestMatchedSite() {
        Map<Integer, SiteConfig> matchers = new HashMap<Integer, SiteConfig>(0);
        String originalBrowserUrl = aggregationStateProvider.get().getOriginalBrowserURL();
        String browserUrl = StringUtils.replaceOnce(originalBrowserUrl,SiteProxyBasedVirtualURIMapping.PAGES_PROXY,"");
        String defaultBaseUrl = StringUtils.substringBefore(
                StringUtils.replaceOnce(browserUrl,aggregationStateProvider.get().getCurrentURI(),"")
                ,"?");
        if (!StringUtils.endsWith(defaultBaseUrl,"/")) {
            defaultBaseUrl = defaultBaseUrl + "/";
        }
        log.debug("Default base Url: " + defaultBaseUrl + " based on " + originalBrowserUrl);
        // Setting default base url
        serverConfig.setDefaultBaseUrl(defaultBaseUrl);

        for (SiteConfig site : getSites()) {
            if (StringUtils.isNotBlank(site.getMappedServerName())) {
                String siteUri = StringUtils.stripEnd(site.getMappedServerName(),"/") + getMappedServerPath(site);
                int bestMatch = StringUtils.getLevenshteinDistance(browserUrl, siteUri);
                log.debug("Site {} match: {} with index {} on {}", site.getName(), siteUri, bestMatch, browserUrl);
                matchers.put(bestMatch, site);
            }
        }
        TreeMap<Integer, SiteConfig> treeMap = new TreeMap<Integer, SiteConfig>(matchers);
        if (treeMap.size() == 0) {
            return getDefaultSiteConfig();
        }
        log.debug("Site best match: {}", treeMap.firstEntry().getValue().getMappedServerName());
        return treeMap.firstEntry().getValue();
    }

    private String getMappedServerPath(SiteConfig siteconfig) {
        String mappedServerPath = StringUtils.defaultIfEmpty(siteconfig.getMappedServerPath(), "/");
        String siteMappingPath = "/" + StringUtils.stripStart(mappedServerPath,"/");
        return siteMappingPath;
    }

    private SiteConfig getDefaultSiteConfig() {
        SiteConfig siteConfig = new SiteConfig();
        siteConfig.setName("default");
        return siteConfig;
    }
}