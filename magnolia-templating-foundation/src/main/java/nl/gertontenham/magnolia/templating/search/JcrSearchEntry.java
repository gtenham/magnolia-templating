package nl.gertontenham.magnolia.templating.search;

import javax.jcr.Node;

/**
 * Created by gtenham on 2015-05-15.
 */
public class JcrSearchEntry extends SearchEntry {
    private Node node;

    public JcrSearchEntry() {}

    public JcrSearchEntry(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
