package nl.gertontenham.magnolia.templating;

import com.google.inject.AbstractModule;
import nl.gertontenham.magnolia.templating.beans.AvailabilityConfig;
import nl.gertontenham.magnolia.templating.beans.SiteConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is optional and represents the configuration for the magnolia-templating-foundation module.
 * By exposing simple getter/setter/adder methods, this bean can be configured via content2bean
 * using the properties and node from <tt>beans:/modules/magnolia-templating-foundation</tt>.
 * If you don't need this, simply remove the reference to this class in the module descriptor xml.
 */
public class FoundationTemplatingModule {
    /* you can optionally implement info.magnolia.module.ModuleLifecycle */
    private String mode;
    private Map<String,SiteConfig> sites = new HashMap<>();
    private Map<String, AvailabilityConfig> componentAvailability = new HashMap<>();


    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Map<String, SiteConfig> getSites() {
        return sites;
    }

    public void setSites(Map<String, SiteConfig> sites) {
        this.sites = sites;
    }

    public void addSite(String name, SiteConfig site){
        this.sites.put(name, site);
    }


    public Map<String, AvailabilityConfig> getComponentAvailability() {
        return componentAvailability;
    }

    public void setComponentAvailability(Map<String, AvailabilityConfig> componentAvailability) {
        this.componentAvailability = componentAvailability;
    }
    public void addComponentAvailability(String name, AvailabilityConfig componentAvailability){
        this.componentAvailability.put(name, componentAvailability);
    }


}
