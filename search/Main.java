package search;

import search.controller.ControllerSearch;
import search.controller.IControllerSearch;
import search.model.IModelSearch;
import search.model.ModelSearch;
import search.view.IViewSearch;
import search.view.ViewSearch;

public class Main {

    public static void main(String[] args) {
        IModelSearch model = new ModelSearch();
        Search search = new Search();
        IViewSearch view = new ViewSearch();

        IControllerSearch controller = new ControllerSearch(model, view, search);
        controller.run();
    }
}