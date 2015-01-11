package nl.gertontenham.magnolia.templating;

/**
 * This class is optional and represents the configuration for the magnolia-templating-foundation module.
 * By exposing simple getter/setter/adder methods, this bean can be configured via content2bean
 * using the properties and node from <tt>config:/modules/magnolia-templating-foundation</tt>.
 * If you don't need this, simply remove the reference to this class in the module descriptor xml.
 */
public class FoundationTemplatingModule {
    /* you can optionally implement info.magnolia.module.ModuleLifecycle */

    private String mode;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

}
