package nl.gertontenham.magnolia.templating.rendering.components;

import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.template.RenderableDefinition;
import nl.gertontenham.magnolia.templating.functions.FoundationTemplatingFunctions;
import nl.gertontenham.magnolia.templating.rendering.AbstractRenderableDefinition;
import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.commons.predicate.NodeTypePredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

/**
 * Component renderable definition which can be used as the base renderable definition class for components
 */
public class BaseComponentRenderableDefinition<RD extends RenderableDefinition> extends AbstractRenderableDefinition<RD> {

    private static final Logger log = LoggerFactory.getLogger(BaseComponentRenderableDefinition.class);
    private static final String TAG_WORKSPACE = "collaboration";

    @Inject
    public BaseComponentRenderableDefinition(Node content, RD definition, RenderingModel<?> parent, FoundationTemplatingFunctions templatingFunctions) {
        super(content, definition, parent, templatingFunctions);
    }

    public String getTagFilterName()  {
        String tagname = PropertyUtil.getString(content, "tagFilterLink", StringUtils.EMPTY);
        if (StringUtils.isNotBlank(tagname)) {
            Node tag = templatingFunctions.nodeById(tagname, TAG_WORKSPACE);
            try {
                tagname = tag.getName();
            } catch (RepositoryException e) {
                tagname = StringUtils.EMPTY;
            }
        }
        return tagname;
    }

    public String getTagFilterData() {
        String tagname = getTagFilterName();
        if (StringUtils.isBlank(tagname)) {
            return StringUtils.EMPTY;
        }

        StringBuilder dataTag = new StringBuilder("data-tagfilter=\"");
        dataTag.append(tagname);
        dataTag.append("\"");
        return dataTag.toString();
    }

    /**
     * Check if current component is the last component with same template definition on the page.
     * @return true if last component based on mgnl:template
     */
    public Boolean isLastOnPage() {
        int counter = 0;
        int currentPos = 0;
        try {
            Node page = templatingFunctions.page(content);
            String currentNodeTemplate = PropertyUtil.getString(content, "mgnl:template");
            Iterable<Node> pageComponents = NodeUtil.collectAllChildren(page, new NodeTypePredicate(NodeTypes.Component.NAME, true));

            for (Node component : pageComponents) {
                if (StringUtils.equalsIgnoreCase(PropertyUtil.getString(component, "mgnl:template"), currentNodeTemplate)) {
                    counter++;
                    if (currentPos > 0 && counter > currentPos) {
                        return false;
                    }
                    if (StringUtils.equalsIgnoreCase(content.getIdentifier(), component.getIdentifier())) {
                        currentPos = counter;
                    }
                }
            }
        } catch (RepositoryException e) {
            log.warn("Repository error thrown during fetch page child components", e);
        }
        return true;
    }
}
