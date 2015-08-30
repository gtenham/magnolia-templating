package nl.gertontenham.magnolia.templating.rendering.template;

import info.magnolia.rendering.template.TemplateAvailability;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Todo: Automatic register component to areas with availabilityKey provided in availableFor property!
 */
public class ComponentTemplateDefinition extends ConfiguredTemplateDefinition {

    private String availableFor;

    @Inject
    public ComponentTemplateDefinition(TemplateAvailability templateAvailability) {

        super(templateAvailability);
    }


    public String getAvailableFor() {
        return availableFor;
    }

    public void setAvailableFor(String availableFor) {
        this.availableFor = availableFor;
    }
}
