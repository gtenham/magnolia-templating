package nl.gertontenham.magnolia.templating.search;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gtenham on 2015-04-07.
 */
public class SearchResult implements Serializable {

    private int totalCount;
    private int numPages;
    private List<SearchEntry> results;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

    public List<SearchEntry> getResults() {
        return results;
    }

    public void setResults(List<SearchEntry> results) {
        this.results = results;
    }
}
