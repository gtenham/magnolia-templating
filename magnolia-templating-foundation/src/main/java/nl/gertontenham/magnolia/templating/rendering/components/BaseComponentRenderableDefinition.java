package nl.gertontenham.magnolia.templating.rendering.components;

import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.template.RenderableDefinition;
import nl.gertontenham.magnolia.templating.functions.FoundationTemplatingFunctions;
import nl.gertontenham.magnolia.templating.rendering.AbstractRenderableDefinition;
import org.apache.commons.lang.StringUtils;
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
}
