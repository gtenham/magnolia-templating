package nl.gertontenham.magnolia.templating.rendering.template;

import info.magnolia.cms.beans.config.ServerConfiguration;
import info.magnolia.objectfactory.Components;
import info.magnolia.rendering.template.ComponentAvailability;
import info.magnolia.rendering.template.TemplateAvailability;
import info.magnolia.rendering.template.configured.ConfiguredAreaDefinition;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.resources.app.action.RemoveHotfixAction;
import nl.gertontenham.magnolia.templating.FoundationTemplatingModule;
import nl.gertontenham.magnolia.templating.beans.AvailabilityConfig;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A {@link info.magnolia.rendering.template.AreaDefinition} configured in the configuration workspace.
 */
public class EnhancedConfiguredAreaDefinition extends ConfiguredAreaDefinition {

    private static final Logger log = LoggerFactory.getLogger(EnhancedConfiguredAreaDefinition.class);

    private String availabilityKey;
    //private final FoundationTemplatingModule module;

    @Inject
    public EnhancedConfiguredAreaDefinition(TemplateAvailability templateAvailability) {
        super(templateAvailability);
        //this.addAddtionalComponents();
        //this.module = module;
    }

    @Override
    public Map<String, ComponentAvailability> getAvailableComponents() {
        if (StringUtils.isNotBlank(availabilityKey)) {
            super.getAvailableComponents().putAll(getAddtionalComponents());
        }

        return super.getAvailableComponents();
    }

    @Override
    public void setAvailableComponents(Map<String, ComponentAvailability> availableComponents) {
        if (StringUtils.isNotBlank(availabilityKey)) {
            availableComponents.putAll(getAddtionalComponents());
        }
        super.setAvailableComponents(availableComponents);
    }

    private Map<String, ComponentAvailability> getAddtionalComponents() {
        Map<String, ComponentAvailability> additionalComponents = new LinkedHashMap<String, ComponentAvailability>();
        if (StringUtils.isNotBlank(availabilityKey)) {
            FoundationTemplatingModule module = Components.getComponent(FoundationTemplatingModule.class);
            if (module.getComponentAvailability().containsKey(availabilityKey)) {
                AvailabilityConfig availabilityConfig = module.getComponentAvailability().get(availabilityKey);
                additionalComponents.putAll(availabilityConfig.getComponents());
            }
        }
        return additionalComponents;
    }


    public String getAvailabilityKey() {
        return availabilityKey;
    }

    public void setAvailabilityKey(String availabilityKey) {
        this.availabilityKey = availabilityKey;
    }
}
