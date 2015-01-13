package nl.gertontenham.magnolia.templating.tools.column;

import com.vaadin.ui.Table;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.repository.RepositoryConstants;
import info.magnolia.ui.workbench.column.AbstractColumnFormatter;
import info.magnolia.ui.workbench.column.definition.PropertyColumnDefinition;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Item;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

/**
 * Column formatter class for getting page path based on selected node identifier in website workspace
 */
public class PagePathColumnFormatter extends AbstractColumnFormatter<PropertyColumnDefinition> {

    private static final Logger log = LoggerFactory.getLogger(PagePathColumnFormatter.class);

    public PagePathColumnFormatter(PropertyColumnDefinition definition) {
        super(definition);
    }

    @Override
    public Object generateCell(Table source, Object itemId, Object columnId) {
        final Item jcrItem = getJcrItem(source, itemId);
        if (jcrItem != null && jcrItem.isNode()) {
            final Node node = (Node) jcrItem;

            try {
                if (NodeUtil.isNodeType(node, NodeTypes.ContentNode.NAME)) {
                    // Get identifier from the items name propertyName: eg. sitePageMap
                    final String pageId = PropertyUtil.getString(node, definition.getPropertyName(), StringUtils.EMPTY);
                    // Find page in website and return page path
                    if (StringUtils.isNotEmpty(pageId)) {
                        final Node page = NodeUtil.getNodeByIdentifier(RepositoryConstants.WEBSITE, pageId);
                        final String pagePath = page.getPath();
                        final StringBuilder nameBuilder = new StringBuilder();
                        nameBuilder.append(pagePath);
                        return nameBuilder.toString().trim();
                    }
                }
            } catch (RepositoryException e) {
                log.warn("Unable to get name of contact for column", e);
            }
        }
        return StringUtils.EMPTY;
    }
}
