package search.model;

import search.Search;
import search.Advertisement;

import java.io.IOException;
import java.util.List;

/**
 * Represents the model to conduct the web search and parse the results.
 */
public interface IModelSearch {

    /**
     * Executes the given Search and returns the resulting listings.
     *
     * @param search the Search containing all parameters desired in the results
     * @return the list of Advertisements found matching the search parameters
     * @throws IOException if the search URLS cannot be opened or the web page cannot be accessed
     */
    List<Advertisement> executeSearch(Search search) throws IOException;
}
