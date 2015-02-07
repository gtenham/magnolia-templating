package nl.gertontenham.magnolia.templating.managers;

import info.magnolia.cms.beans.config.URI2RepositoryMapping;
import nl.gertontenham.magnolia.templating.beans.SiteConfig;

import javax.jcr.Node;
import java.util.Collection;

/**
 * Site manager Interface
 */
public interface SiteManager {

    /**
     * Get all available site configurations
     * @return list of site configurations
     */
    public Collection<SiteConfig> getSites();

    /**
     * Get mapped site configuration based on the root node given
     *
     * @param rootNode Pages root node
     * @return Site configuration
     */
    public SiteConfig getCurrentSiteConfig(Node rootNode);

    /**
     * Get mapped site configuration based on aggregation state main content node.
     *
     * @return Site configuration
     */
    public SiteConfig getCurrentSiteConfig();

    /**
     * Obtain additional uri mappings based on site configuration
     *
     * @return
     */
    public Collection<URI2RepositoryMapping> getCurrentSiteUriMappings();
}
