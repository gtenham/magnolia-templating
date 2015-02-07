package nl.gertontenham.magnolia.templating.tools.beans;

import info.magnolia.cms.beans.config.RegexpVirtualURIMapping;
import info.magnolia.cms.beans.config.URI2RepositoryMapping;
import info.magnolia.repository.RepositoryConstants;
import nl.gertontenham.magnolia.templating.managers.SiteManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Site proxy based virtual uri mapper. Maps a uri to the Configured url/page
 *
 */
public class SiteProxyBasedVirtualURIMapping extends RegexpVirtualURIMapping {

    private static Logger log = LoggerFactory.getLogger(SiteProxyBasedVirtualURIMapping.class);

    public final static String PAGES_PROXY = "/pages-proxy";

    private SiteManager sitemanager;
    private String fromURI;
    private Pattern regexp;

    @Inject
    public SiteProxyBasedVirtualURIMapping(SiteManager sitemanager) {
        super();
        this.sitemanager = sitemanager;
    }

    @Override
    public MappingResult mapURI(final String uri, String queryString) {
        if (regexp != null) {

            final Matcher matcher;
            if(queryString != null){
                matcher = regexp.matcher(uri + "?" + queryString);
            }else{
                matcher = regexp.matcher(uri);
            }

            if (matcher.find()) {
                final MappingResult r = new MappingResult();
                final int matcherCount = matcher.groupCount();
                try {
                    final String replaced = matcher.replaceAll(getToURI());

                    r.setLevel(matcherCount + 1);
                    r.setToURI(replaced);
                    return r;
                } catch (IndexOutOfBoundsException e) {
                    log.warn("{} misconfigured: {}", toString(), e.getMessage());
                }
            }


        }
        return null;
    }

    @Override
    public void setFromURI(String fromURI) {
        this.fromURI = StringUtils.defaultIfEmpty(fromURI, "/pages-proxy/(.*)");
        this.regexp = Pattern.compile(fromURI);
    }

    @Override
    public String toString() {
        return "SiteProxyBasedVirtualURIMapping[" + fromURI + " --> " + getToURI() + "]";
    }

    @Override
    public String getToURI() {
        Iterator<URI2RepositoryMapping> hostIt = sitemanager.getCurrentSiteUriMappings().iterator();
        while (hostIt.hasNext()) {
            URI2RepositoryMapping hk = hostIt.next();
            if (StringUtils.equalsIgnoreCase(hk.getRepository(), RepositoryConstants.WEBSITE)) {
                return hk.getHandlePrefix() + "/$1";
            }
        }
        return StringUtils.EMPTY;
    }
}
