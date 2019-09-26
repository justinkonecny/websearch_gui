package search;

import search.controller.ControllerSearch;
import search.controller.IControllerSearch;
import search.model.IModelSearch;
import search.model.ModelSearchMT;
import search.view.IViewSearch;
import search.view.ViewSearch;

/**
 * Starts the web-searching program. The program first prompts the user with an interactive gui for a vehicle model
 * to search craigslist.org for, and then it parses the resulting advertisements (using the external library JSoup),
 * filtering out irrelevant listings based on certain criteria, including posting location and posting date
 * (i.e. filters out old listings that are from nearby cities).
 * [Project requires jsoup-1.11.3+]
 */
public class Main {

    /**
     * Starts the web-searching program; no arguments are needed.
     *
     * @param args program arguments (not needed)
     */
    public static void main(String[] args) {
        //the model to search and parse the website

        //IModelSearch model = new ModelSearchST(); //use this model for single-threaded processing
        IModelSearch model = new ModelSearchMT(); //use this model for multi-threaded processing

        //the search query containing the vehicle search parameters (i.e. price, miles, year, etc.)
        Search search = new Search();

        //the interactive view to display results and accept user input
        IViewSearch view = new ViewSearch();

        //the controller to process user input
        IControllerSearch controller = new ControllerSearch(model, view, search);

        //initializes the model selection gui
        controller.run();
    }
}