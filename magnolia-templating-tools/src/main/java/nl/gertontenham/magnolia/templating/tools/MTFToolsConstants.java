package nl.gertontenham.magnolia.templating.tools;

import info.magnolia.jcr.util.NodeTypes;

/**
 * Magnolia Templating Foundation tools constants
 */
public class MTFToolsConstants {

    /**
     * Represents the nodeType mgnl:contentTag.
     */
    public static class ContentTag {
        // Node Type Name
        public static final String NAME = NodeTypes.MGNL_PREFIX + "contentTag";

        // Node Type Folder
        public static final String FOLDER = NodeTypes.MGNL_PREFIX + "contentTagsFolder";

        // Property Name
        public static final String PROPERTY_NAME = "name";
    }
}
