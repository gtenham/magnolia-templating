package nl.gertontenham.magnolia.templating.tools.form.action;

import info.magnolia.cms.core.Path;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.ui.form.EditorCallback;
import info.magnolia.ui.form.EditorValidator;
import info.magnolia.ui.form.action.SaveFormAction;
import info.magnolia.ui.form.action.SaveFormActionDefinition;
import info.magnolia.ui.vaadin.integration.jcr.JcrNewNodeAdapter;
import info.magnolia.ui.vaadin.integration.jcr.JcrNodeAdapter;
import org.apache.commons.lang.StringUtils;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

/**
 * Save action for content tags and folders
 */
public class SaveTagFormAction extends SaveFormAction {

    public SaveTagFormAction(SaveFormActionDefinition definition, JcrNodeAdapter item, EditorCallback callback,	EditorValidator validator) {
        super(definition, item, callback, validator);
    }

    @Override
    protected void setNodeName(Node node, JcrNodeAdapter item) throws RepositoryException {
        JcrNodeAdapter itemChanged = item;
        // Set the Node Composite Name
        if (itemChanged instanceof JcrNewNodeAdapter || !node.getName().startsWith(defineNodeName(node))) {
            final String newNodeName = generateUniqueNodeNameForTag(node);
            itemChanged.setNodeName(newNodeName);
            NodeUtil.renameNode(node, newNodeName);
        }
    }

    /**
     * Create a new Node Unique NodeName.
     */
    private String generateUniqueNodeNameForTag(final Node node) throws RepositoryException {
        String newNodeName = defineNodeName(node);
        return Path.getUniqueLabel(node.getSession(), node.getParent().getPath(), newNodeName);
    }

    /**
     * Define the Node Name.
     */
    private String defineNodeName(final Node node) throws RepositoryException {
        String parentPathName = node.getParent().getName();
        String nodeName = node.getProperty("name").getString().trim();
        StringBuilder newLabel = new StringBuilder(parentPathName.replaceAll("[aeiou]\\B", "").toLowerCase()).
                append(StringUtils.isNotBlank(parentPathName) ? "-" : "").
                append(nodeName.replaceAll("\\s+", "-").toLowerCase());
        return Path.getValidatedLabel(newLabel.toString());
    }
}