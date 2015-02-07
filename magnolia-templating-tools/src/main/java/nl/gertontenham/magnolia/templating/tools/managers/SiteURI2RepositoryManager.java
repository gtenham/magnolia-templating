package nl.gertontenham.magnolia.templating.tools.managers;

import info.magnolia.cms.beans.config.URI2RepositoryManager;
import info.magnolia.cms.beans.config.URI2RepositoryMapping;
import info.magnolia.link.Link;
import nl.gertontenham.magnolia.templating.managers.SiteManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Collection;
import java.util.TreeSet;

/**
 * Magnolia Uri2RepositoryManager implementation. Will be used in /server/URI2RepositoryMapping/class
 *
 * This implementation extends the normal Uri2RepositoryMappings with the ones configured in the SiteManager app.
 *
 */
public class SiteURI2RepositoryManager extends URI2RepositoryManager {

    private static Logger log = LoggerFactory.getLogger(SiteURI2RepositoryManager.class);

    private SiteManager sitemanager;

    @Inject
    public SiteURI2RepositoryManager(SiteManager sitemanager) {
        super();
        this.sitemanager = sitemanager;
    }

    public SiteURI2RepositoryManager() { super(); }

    /**
     * Override to use getMappings method instead of mappings property.
     * @param uri
     * @return
     */
    @Override
    public URI2RepositoryMapping getMapping(String uri) {
        for (URI2RepositoryMapping mapping : getMappings()) {
            if (mapping.matches(uri)) {
                return mapping;
            }
        }
        return getDefaultMapping();
    }

    /**
     * Override to use getMappings method instead of mappings property. Changed deprecated usage of getRepository
     * @param uuidLink
     * @return
     */
    @Override
    public String getURI(Link uuidLink) {
        for (URI2RepositoryMapping mapping : getMappings()) {
            if (StringUtils.equals(mapping.getRepository(), uuidLink.getWorkspace()) && uuidLink.getPath().startsWith(mapping.getHandlePrefix())) {
                final String uri = mapping.getURI(uuidLink);
                return uri;
            }
        }
        return getDefaultMapping().getURI(uuidLink);
    }

    /**
     * Override method to add own configured URI2RepositoryMappings before standard mappings
     *
     * @return
     */
    @Override
    public Collection<URI2RepositoryMapping> getMappings() {
        TreeSet<URI2RepositoryMapping> mappings = new TreeSet<URI2RepositoryMapping>(getMappingComparator());

        // Get beans domain mappings if exists for current browser url and add them to mappings
        mappings.addAll(sitemanager.getCurrentSiteUriMappings());

        // Add URI2Repository mappings defined in server beans
        mappings.addAll(super.getMappings());
        return mappings;
    }
}
