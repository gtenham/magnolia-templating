package nl.gertontenham.magnolia.templating.search;

/**
 * Created by gtenham on 2015-04-17.
 */
public interface SearchService {

    public SearchResult search(String query, int pageNumber, int maxResultsPerPage) throws SearchException;
}
