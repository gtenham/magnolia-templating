package nl.gertontenham.magnolia.templating.managers;

import info.magnolia.cms.core.AggregationState;
import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.templating.functions.TemplatingFunctions;
import nl.gertontenham.magnolia.templating.FoundationTemplatingModule;
import nl.gertontenham.magnolia.templating.config.SiteConfig;
import nl.gertontenham.magnolia.templating.functions.FoundationTemplatingFunctions;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.*;

/**
 * Site manager class
 */
public class SiteManager {

    private static final Logger log = LoggerFactory.getLogger(SiteManager.class);

    private FoundationTemplatingModule module;
    private Provider<AggregationState> aggregationStateProvider;
    private TemplatingFunctions templatingFunctions;

    @Inject
    public SiteManager(FoundationTemplatingModule module, Provider<AggregationState> aggregationStateProvider, TemplatingFunctions templatingFunctions) {
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
        try {
            for (SiteConfig site : getSites()) {
                if (StringUtils.equalsIgnoreCase(site.getSitePageMap(), rootNode.getIdentifier())) {
                    siteConfig = site;
                }
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return siteConfig;
    }

    public SiteConfig getCurrentSiteConfig() {
        Node mainNode = aggregationStateProvider.get().getMainContentNode();
        SiteConfig siteConfig = new SiteConfig();
        try {
            siteConfig = getCurrentSiteConfig(templatingFunctions.root(mainNode));
            return siteConfig;
        } catch (RepositoryException e) {
            log.debug("", e);
        }
        return siteConfig;

    }
}
