package nl.gertontenham.magnolia.templating.rendering.components.search;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.template.RenderableDefinition;
import nl.gertontenham.magnolia.templating.search.SearchException;
import nl.gertontenham.magnolia.templating.search.SearchResult;
import nl.gertontenham.magnolia.templating.functions.FoundationTemplatingFunctions;
import nl.gertontenham.magnolia.templating.rendering.ResultsRenderingModel;
import nl.gertontenham.magnolia.templating.rendering.components.BaseComponentRenderableDefinition;
import nl.gertontenham.magnolia.templating.search.SearchService;
import nl.gertontenham.magnolia.templating.utils.RenderingUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Search results renderable definition which can be used as the modelClass for presenting search results in freemarker template.
 */
public class ResultsRenderableDefinition<RD extends RenderableDefinition> extends ResultsAreaRenderableDefinition<RD> implements ResultsRenderingModel<RD> {

    private static final Logger log = LoggerFactory.getLogger(ResultsRenderableDefinition.class);

    private static final String SEARCH_FUZZY_FACTOR = "~0.8";
    private static final String DEFAULT_MAX_RESULTS_PER_PAGE = "10";


    private static final String BASE_QUERY = "SELECT p.* FROM [%s] AS p " +
            "WHERE ISDESCENDANTNODE(p, '%s') %s ORDER BY %s";

    private SearchResult searchResult;
    private SearchService jcrSearchService;

    @Inject
    public ResultsRenderableDefinition(Node content, RD definition,
                                       RenderingModel<?> parent, FoundationTemplatingFunctions templatingFunctions,
                                       RenderingUtils renderingUtils,
                                       SearchService jcrSearchService) {
        super(content, definition, parent, templatingFunctions, renderingUtils);
        this.jcrSearchService = jcrSearchService;

    }

    @Override
    public String execute() {

        if (content != null) {
            String uuid;
            try {
                uuid = content.getIdentifier();
                // Fetch pre-executed search results
                searchResult = MgnlContext.getAttribute(ResultsAreaRenderableDefinition.SEARCH_RESULT_ATTRIBUTE_PREFIX + uuid);
                // Check if search result are available and remove from context for later executions of the results area
                if (searchResult != null) {
                    MgnlContext.removeAttribute(ResultsAreaRenderableDefinition.SEARCH_RESULT_ATTRIBUTE_PREFIX + uuid);
                }
            } catch (RepositoryException e) {
                log.debug("Error obtaining uuid during execute of component model", e);
            }

        }
        // No search results, execute new search!
        if (searchResult == null) {
            executeSearch();
        }

        return super.execute();
    }

    public void executeSearch() {
        List<String> orderBy = new ArrayList<String>(0);
        // Order by score, descending is default
        orderBy.add("score()");

        if (StringUtils.isNotBlank(getSearchTerm())) {
            final String jcrQuery = buildQuery("nt:base", getSearchPath(), getAdvancedSearchTermPredicate(), orderBy);
            log.debug("executeSearch: buildQuery result {}",jcrQuery);
            try {
                searchResult = jcrSearchService.search(jcrQuery, getPageNumber(), getMaxResultsPerPage());
            } catch (SearchException e) {
                log.debug("Error search website content", e);
            }
        } //else {
         //   searchResult = new SearchResult();
        //    searchResult.setTotalCount(0);
        //    searchResult.setNumPages(0);
        //}

    }

    public int getTotalCount() {
        if (searchResult == null) {
            return 0;
        }
        return searchResult.getTotalCount();
    }

    public String getSubtitle() {
        return PropertyUtil.getString(content, "subtitle", "");
    }

    public SearchResult getSearchResult() {

        return searchResult;
    }


    /**
     * Build JCR query string.
     *
     * @param from         JCR from clause eg. nt:base
     * @param searchPath   Start path where to start searches in eg /home
     * @param customFilter JCR based where clause
     * @param orderBy      JCR based order by (defaults to score() desc)
     * @return Full JCR query string
     */
    private String buildQuery(String from, String searchPath, String customFilter, List<String> orderBy) {
        return String.format(BASE_QUERY, from, StringUtils.defaultIfEmpty(searchPath, "/"),
                customFilter, getOrderString(orderBy));
    }

    /**
     * Provides the (advanced) jcr where clause (part) based on queryStr in url and several configuration settings.
     *
     * @return
     */
    private String getAdvancedSearchTermPredicate() {
        String searchTermPredicate = StringUtils.EMPTY;
        //ResultsAreaRenderableDefinition resultsAreaModel = ((ResultsAreaRenderableDefinition)getParent());

        if (StringUtils.isNotBlank(getSearchTerm())) {
            // Standard fuzzy predicate
            StringBuilder predicate = new StringBuilder(MessageFormat.format(" AND contains(p.*, ''{0}'') ", new Object[]{getLuceneSearchTermPredicate("^0.4")}));

//            // Boosting predicate
//            predicate.append(MessageFormat.format(" AND ( (contains(p.title, ''{0}'') AND p.[jcr:primaryType] = ''mgnl:page'')", new Object[]{ getLuceneSearchTermPredicate(SEARCH_BOOST_VERY_IMPORTANT_FACTOR)} ));
//            predicate.append( MessageFormat.format(" OR (contains(p.windowTitle, ''{0}'') AND p.[jcr:primaryType] = ''mgnl:page'') ", new Object[]{ getLuceneSearchTermPredicate(SEARCH_BOOST_VERY_IMPORTANT_FACTOR) } ));
//            predicate.append( MessageFormat.format(" OR (contains(p.abstract, ''{0}'') AND p.[jcr:primaryType] = ''mgnl:page'')", new Object[]{ getLuceneSearchTermPredicate(SEARCH_BOOST_MEDIUM_IMPORTANT_FACTOR) } ));
//            predicate.append( MessageFormat.format(" OR (contains(p.description, ''{0}'') AND p.[jcr:primaryType] = ''mgnl:page'')", new Object[]{ getLuceneSearchTermPredicate(SEARCH_BOOST_MEDIUM_IMPORTANT_FACTOR) } ));
//            predicate.append( MessageFormat.format(" OR contains(p.description, ''{0}'') ", new Object[]{ getLuceneSearchTermPredicate(SEARCH_BOOST_LESS_IMPORTANT_FACTOR) } ));
//            predicate.append( MessageFormat.format(" OR contains(p.fileDescription, ''{0}'') ", new Object[]{ getLuceneSearchTermPredicate(SEARCH_BOOST_VERY_IMPORTANT_FACTOR) } ));
//            predicate.append( MessageFormat.format(" OR contains(p.title, ''{0}'') ", new Object[]{ getLuceneSearchTermPredicate(SEARCH_BOOST_VERY_IMPORTANT_FACTOR) } ));
//            predicate.append( MessageFormat.format(" OR contains(p.link, ''{0}{1}'') )", new Object[]{ getSearchTerm(), SEARCH_BOOST_MIN_IMPORTANT_FACTOR} ));
//
//            // Excluding pages predicate
//            predicate.append(" AND p.[mgnl:template] <> 'adyen-templating-kit:pages/overview'");
//            predicate.append(" AND p.[mgnl:template] <> 'adyen-templating-kit:pages/redirect'");

            searchTermPredicate = predicate.toString();
        }
        return searchTermPredicate;
    }

    /**
     * Provides the jcr where clause (part) based on Lucene parameters, like boost factor and Fuzzy factor.
     *
     * @return
     */
    private String getLuceneSearchTermPredicate(String boostFactor) {
        StringBuffer searchTermPredicate = new StringBuffer();
        //ResultsAreaRenderableDefinition resultsAreaModel = ((ResultsAreaRenderableDefinition)getParent());

        String[] searchTermArray = StringUtils.split(getSearchTerm());

        for (int i = 0; i < searchTermArray.length; i++) {
            searchTermPredicate.append(searchTermArray[i]);
            searchTermPredicate.append(boostFactor);
            searchTermPredicate.append(SEARCH_FUZZY_FACTOR);
            searchTermPredicate.append(" ");
        }
        return StringUtils.trim(searchTermPredicate.toString());
    }

    /**
     * Build up jcr Order by string
     *
     * @param orderBy List of order by statements like "score() desc"
     * @return JCR based order by string
     */
    private String getOrderString(List<String> orderBy) {
        final String defaultOrderStr = "desc";

        String orderStr = "";
        //first iterate the order items
        int iteratorNum = 0;
        if (orderBy != null) {
            for (String item : orderBy) {
                orderStr += String.format("%s ", item);
                iteratorNum++;
                if (iteratorNum < orderBy.size()) {
                    orderStr += ", ";
                }
            }
        }
        //at the end append the default order string
        orderStr = orderStr + defaultOrderStr;
        return orderStr;
    }

    /**
     * Get searchPath content property. If not set then "/" (root) is used.
     *
     * @return search path
     */
    private String getSearchPath() {
        String searchPath = "/";
        try {
            if (content.hasProperty("searchPath")) {
                searchPath = templatingFunctions.nodeById(content.getProperty("searchPath").getString()).getPath();
            }
        } catch (Exception e) {
            log.info("no searchPath property set on content", e);
        }
        return searchPath;
    }

    public int getMaxResultsPerPage() {
        int maxResultsPerPage;
        try {
            String maxResultsStringValue = PropertyUtil.getString(content, "maxResultsPerPage", DEFAULT_MAX_RESULTS_PER_PAGE);

            maxResultsPerPage = Integer.parseInt(maxResultsStringValue);

        } catch (NumberFormatException e) {
            maxResultsPerPage = Integer.parseInt(DEFAULT_MAX_RESULTS_PER_PAGE);
        }
        return maxResultsPerPage;
    }

}
