package nl.gertontenham.magnolia.templating.tools.column;

import com.vaadin.ui.Table;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.ui.workbench.column.AbstractColumnFormatter;
import nl.gertontenham.magnolia.templating.tools.MTFToolsConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Item;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

/**
* Column formatter that displays either the name of a tag or a folder.
*
*/
public class TagNameColumnFormatter extends AbstractColumnFormatter<TagNameColumnDefinition> {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(TagNameColumnFormatter.class);

    public TagNameColumnFormatter(TagNameColumnDefinition definition) {
        super(definition);
    }

    @Override
    public Object generateCell(Table source, Object itemId, Object columnId) {
        final Item jcrItem = getJcrItem(source, itemId);
        if (jcrItem != null && jcrItem.isNode()) {
            Node node = (Node) jcrItem;

            try {
                if (NodeUtil.isNodeType(node, NodeTypes.Folder.NAME)) {
                    return node.getName();
                }
            } catch (RepositoryException e) {
                log.warn("Unable to get name of folder for column", e);
            }

            try {
                if (NodeUtil.isNodeType(node, MTFToolsConstants.ContentTag.NAME)) {
                    if(node.hasProperty("name")){
                        return PropertyUtil.getString(node, "name", node.getName()) ;
                    } else {
                        return node.getName();
                    }
                }
            } catch (RepositoryException e) {
                log.warn("Unable to get name of tag for column", e);
            }
        }
        return "";
    }
}
