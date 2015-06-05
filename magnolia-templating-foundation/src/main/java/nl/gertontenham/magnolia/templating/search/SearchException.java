package nl.gertontenham.magnolia.templating.search;

/**
 * Created by gtenham on 2015-04-17.
 */
public class SearchException extends Exception {

    public SearchException(String message) {
        super(message);
    }

    public SearchException(String message, Throwable throwable) {
        super(message, throwable);
    }
}