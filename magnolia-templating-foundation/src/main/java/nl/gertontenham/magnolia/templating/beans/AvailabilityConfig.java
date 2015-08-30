package nl.gertontenham.magnolia.templating.beans;

import info.magnolia.config.registry.DefinitionProvider;
import info.magnolia.config.registry.Registry;
import info.magnolia.registry.RegistrationException;
import info.magnolia.rendering.template.AreaDefinition;
import info.magnolia.rendering.template.ComponentAvailability;
import info.magnolia.rendering.template.TemplateDefinition;
import info.magnolia.rendering.template.registry.TemplateDefinitionRegistry;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * AvailabilityConfig node2bean
 */
public class AvailabilityConfig {

    private static final Logger log = LoggerFactory.getLogger(AvailabilityConfig.class);

    private String targetDefinitionId;
    private String targetAreaName;
    private Map<String,ComponentAvailability> components = new HashMap<>();
    //private Map<String, AreaDefinition> areas = new HashMap<>();

    private TemplateDefinitionRegistry templateDefinitionRegistry;

    @Inject
    public AvailabilityConfig(TemplateDefinitionRegistry templateDefinitionRegistry) {
        this.templateDefinitionRegistry = templateDefinitionRegistry;
    }

    public Map<String, ComponentAvailability> getComponents() {
        return components;
    }

    public void setComponents(Map<String, ComponentAvailability> components) {
        try {
            String[] areaNames = StringUtils.split(getTargetAreaName(),":");
            final DefinitionProvider<TemplateDefinition> templateDefinitionProvider = templateDefinitionRegistry.getProvider("magnolia-templating-foundation:pages/genericPage");
            AreaDefinition areaDefinition = templateDefinitionProvider.get().getAreas().get(areaNames[0]);
            for (int i = 1; i < areaNames.length; i++) {
                areaDefinition = areaDefinition.getAreas().get(areaNames[i]);
            }
            areaDefinition.getAvailableComponents().putAll(components);
        } catch (IllegalArgumentException | Registry.NoSuchDefinitionException | Registry.InvalidDefinitionException e) {
            log.debug("Error", e);
        }
        this.components = components;
    }

    public void addComponent(String name, ComponentAvailability component){
        this.components.put(name, component);
    }

    public String getTargetDefinitionId() {
        return targetDefinitionId;
    }

    public void setTargetDefinitionId(String targetDefinitionId) {
        this.targetDefinitionId = targetDefinitionId;
    }

    public String getTargetAreaName() {
        return targetAreaName;
    }

    public void setTargetAreaName(String targetAreaName) {
        this.targetAreaName = targetAreaName;
    }
}
