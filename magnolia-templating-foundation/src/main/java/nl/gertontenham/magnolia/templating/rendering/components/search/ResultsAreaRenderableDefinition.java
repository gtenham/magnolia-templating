package nl.gertontenham.magnolia.templating.rendering.components.search;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.ContentMap;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.rendering.model.ModelExecutionFilter;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.template.RenderableDefinition;
import nl.gertontenham.magnolia.templating.functions.FoundationTemplatingFunctions;
import nl.gertontenham.magnolia.templating.rendering.ResultsRenderingModel;
import nl.gertontenham.magnolia.templating.rendering.components.BaseComponentRenderableDefinition;
import nl.gertontenham.magnolia.templating.utils.RenderingUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * Search results area renderable definition which can be used as the modelClass for presenting
 * separate search result components in freemarker template.
 */
public class ResultsAreaRenderableDefinition<RD extends RenderableDefinition> extends BaseComponentRenderableDefinition<RD> {

    private static final Logger log = LoggerFactory.getLogger(ResultsAreaRenderableDefinition.class);

    public static final String SEARCH_RESULT_ATTRIBUTE_PREFIX = ModelExecutionFilter.class.getName() + "-searchResult-";
    private static final String QUERY_PARAM = "s";
    private static final String PAGE_PARAM = "page";
    private static final String CONTENT_PARAM = "content";
    private static final String FILTER_PARAM = "filter";

    private Multimap<String, String> paramFilter;

    private RenderingUtils renderingUtils;

    @Inject
    public ResultsAreaRenderableDefinition(Node content, RD definition, RenderingModel<?> parent, FoundationTemplatingFunctions templatingFunctions, RenderingUtils renderingUtils) {
        super(content, definition, parent, templatingFunctions);
        this.renderingUtils = renderingUtils;

        paramFilter = LinkedListMultimap.create();
        Set<String> parameters = webContext.getParameters().keySet();
        for (String parameterKey : parameters) {
            if (allowedParameters().contains(parameterKey)) {
                String[] parameterValues = webContext.getParameterValues(parameterKey);
                for (String parameterValue : parameterValues) {
                    if (StringUtils.isNotEmpty(parameterValue)) {
                        paramFilter.get(parameterKey).add(parameterValue);
                    }
                }
            }
            webContext.remove(parameterKey);
        }
    }

    @Override
    public String execute() {
        // Do not cache this response!
        // More info: http://documentation.magnolia-cms.com/display/DOCS/Cache+module#Cachemodule-Cacheheadernegotiation
        webContext.getResponse().setHeader("Cache-Control", "no-cache");

        return super.execute();
    }

    /**
     * Get request parameter for search term.
     *
     * @return search term
     */
    public String getSearchTerm() {
        if (paramFilter.containsKey(QUERY_PARAM)) {
            return paramFilter.get(QUERY_PARAM).iterator().next().replaceAll("'", "''");
        }
        return StringUtils.EMPTY;
    }

    /**
     * Get request parameter for current page.
     *
     * @return pagenumber
     */
    public int getPageNumber() {
        int pageNumber = 1;
        if (paramFilter.containsKey(PAGE_PARAM)) {
            pageNumber = Integer.parseInt(paramFilter.get(PAGE_PARAM).iterator().next());
        }
        return pageNumber;
    }

    /**
     * Get total results count of all child components results count
     *
     * @return Total result count
     * @throws RepositoryException
     */
    public int getTotalCount() throws RepositoryException {
        int grandTotal = 0;
        for (Node node : NodeUtil.getNodes(content,"mgnl:component")) {
            grandTotal += getComponentTotal(node);
        }
        return grandTotal;
    }


    /**
     * Get total results count for single component.
     *
     * @param node
     * @return total component results count
     */
    public int getComponentTotal(Node node) {
        int total = 0;
        ResultsRenderableDefinition model = getComponentModel(node);

        if (model != null) {
            model.executeSearch();

            try {
                MgnlContext.setAttribute(SEARCH_RESULT_ATTRIBUTE_PREFIX + node.getIdentifier(), model.getSearchResult());
            } catch (RepositoryException e) {
                log.debug("Error getting node identifier during during set of MgnlContext attribute", e);
            }
            total = model.getTotalCount();
        }
        return total;
    }

    /**
     * Get subtitle for single component.
     *
     * @param node
     * @return component subtitle
     */
    public String getComponentSubtitle(Node node) {
        String subtitle = StringUtils.EMPTY;
        ResultsRenderableDefinition model = getComponentModel(node);

        if (model != null) {
            subtitle = model.getSubtitle();
        }
        return subtitle;
    }

    /**
     * Get total results count for single component.
     *
     * @param content
     * @return total component results count
     */
    public int getComponentTotal(ContentMap content) throws ServletException {

        return getComponentTotal(content.getJCRNode());
    }

    /**
     * Get subtitle for single component.
     *
     * @param content
     * @return component subtitle
     */
    public String getComponentSubtitle(ContentMap content) {
        return getComponentSubtitle(content.getJCRNode());
    }



    /**
     * Provide whitelisted query-string parameters.
     * When not in this list, parameters will be ignored.
     *
     * @return Set of whitelisted query parameters
     */
    private Set<String> allowedParameters() {
        return Sets.newHashSet(QUERY_PARAM, PAGE_PARAM, CONTENT_PARAM, FILTER_PARAM);
    }

    private ResultsRenderableDefinition getComponentModel(Node node) {
        ResultsRenderableDefinition model = null;
        RenderingModel renderingModel = null;

        try {
            renderingModel = renderingUtils.getRenderingModel(node);
        } catch (ServletException e) {
            log.debug("Error getting node renderingmodel", e);
        }
        if (renderingModel instanceof ResultsRenderingModel) {
            model = ((ResultsRenderableDefinition)renderingModel);
        }
        return model;
    }
}
