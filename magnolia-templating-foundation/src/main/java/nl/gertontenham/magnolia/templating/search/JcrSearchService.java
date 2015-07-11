package nl.gertontenham.magnolia.templating.search;

import info.magnolia.cms.util.QueryUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.jcr.wrapper.I18nNodeWrapper;
import info.magnolia.repository.RepositoryConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.query.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gtenham on 2015-04-17.
 */
public class JcrSearchService implements SearchService {

    private static final Logger log = LoggerFactory.getLogger(JcrSearchService.class);

    private static final String RETURN_ITEM_TYPE = "mgnl:page";

    private List<Node> allResults;
    private List<SearchEntry> allJcrResults;

    @Override
    public SearchResult search(String query, int pageNumber, int maxResultsPerPage) {
        SearchResult searchResult = new SearchResult();
        try {
            allResults = executeQuery(query, RepositoryConstants.WEBSITE, RETURN_ITEM_TYPE);
            allJcrResults = getQueryResultItems(allResults);
            searchResult.setTotalCount(allResults.size());
            searchResult.setNumPages(getNumberOfPages( allResults.size(), maxResultsPerPage) );
            searchResult.setResults(getPagedResults(allJcrResults, pageNumber, maxResultsPerPage));

        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return searchResult;
    }


    /**
     * Execute JCR query returning Nodes matching given statement and return node type
     *
     * @param statement      JCR query string
     * @param workspace      Search in JCR workspace like website
     * @param returnItemType Return nodes based on primary node type
     * @return List<Node>
     * @throws javax.jcr.RepositoryException
     */
    private List<Node> executeQuery(String statement, String workspace, String returnItemType) throws RepositoryException {
        List<Node> nodeList = new ArrayList<Node>(0);

        NodeIterator items = QueryUtil.search(workspace, statement, Query.JCR_SQL2, returnItemType);
        log.debug("Query Executed: {}", statement);
        while (items.hasNext()) {
            Node node = items.nextNode();
            if (!filterNode(node)) {
                nodeList.add(new I18nNodeWrapper(node));
            }

        }
        return nodeList;
    }

    /**
     * Check if node is valid to return as a result.
     *
     * @param node
     * @return True when node needs to be filtered out of the result set
     */
    private Boolean filterNode(Node node) {
        // Check on noindex robots value
        Boolean containsNoIndex = StringUtils.containsIgnoreCase(PropertyUtil.getString(node, "robots", "index"), "noindex");

        return containsNoIndex;
    }

    private int getNumberOfPages(int total, int maxResultsPerPage) {
        int calcNumPages = total/maxResultsPerPage;
        if((total % maxResultsPerPage) > 0 ) {
            calcNumPages++;
        }
        return calcNumPages;
    }

    private List<SearchEntry> getQueryResultItems(List<Node> nodes) {
        List<SearchEntry> resultList = new ArrayList<SearchEntry>(0);
        for (Node node : nodes) {
            resultList.add(new JcrSearchEntry(node));
        }
        return resultList;
    }

    private List<SearchEntry> getPagedResults(List<SearchEntry> results, int pageNumber, int maxResultsPerPage) {
        List<SearchEntry> nodeListPaged = new ArrayList<SearchEntry>(0);
        final int total = results.size();
        final int startRow = (maxResultsPerPage * (pageNumber - 1));
        int newLimit = maxResultsPerPage;
        if(total > startRow) {
            if(total < startRow + maxResultsPerPage) {
                newLimit = total - startRow;
            }
            nodeListPaged = results.subList(startRow, startRow + newLimit);
        }
        return nodeListPaged;
    }
}
