package nl.gertontenham.magnolia.templating.rendering;

import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.template.RenderableDefinition;
import nl.gertontenham.magnolia.templating.search.SearchResult;

/**
 * Created by gtenham on 2015-04-07.
 */
public interface ResultsRenderingModel<RD extends RenderableDefinition> extends RenderingModel<RD> {

    void executeSearch();

    String getSubtitle();

    SearchResult getSearchResult();
}
