package search.model;

import search.Search;
import search.Advertisement;

import java.io.IOException;
import java.util.List;

public interface IModelSearch {

    List<Advertisement> executeSearch(Search search) throws IOException;
}
