package search.controller;

import search.Advertisement;
import search.Search;
import search.model.IModelSearch;
import search.view.IViewSearch;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the controller implementation to handle program execution, flow of information, and user input. Acts
 * as an ActionListener, ListSelectionListener, and KeyListener.
 */
public class ControllerSearch implements IControllerSearch, ActionListener, ListSelectionListener, KeyListener {
    //the list of advertisements resulting from a search
    private List<Advertisement> advertisementList;
    //the model to perform the search
    private IModelSearch model;
    //the view to display information
    private IViewSearch view;
    //the search to execute
    private Search search;

    /**
     * Constructs a ControllerSearch with the given mode, view, and search. Adds this controller as
     * an ActionListener, ListSelectionListener, and KeyListener for the view to handle user input.
     *
     * @param model the model to handle search execution
     * @param view the view to handle displaying information and results
     * @param search the search containing all desired search parameters
     */
    public ControllerSearch(IModelSearch model, IViewSearch view, Search search) {
        this.model = model;
        this.view = view;
        this.search = search;
        this.advertisementList = new ArrayList<Advertisement>();
        this.view.addListeners(this, this, this);
    }

    /**
     * Displays the model selection gui to the user to initialize the web search.
     */
    @Override
    public void run() {
        this.view.setModelSelectorVisibility(true);
    }

    /**
     * Processes user input from the given ActionEvent. Handles input to execute a search,
     * open the selected listing urls, or open all listing urls.
     *
     * @param actionEvent the action event to process
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        if (command != null && !command.isEmpty()) {
            this.processActionCommand(command);
        }
    }

    /**
     * Processes user input from an action command. Handles input to execute a search,
     * open the selected listing urls, or open all listing urls.
     *
     * @param command the command to process
     */
    private void processActionCommand(String command) {
        if (command.equals("open")) {
            //opens the selected listing
            this.view.openSelected();
        } else if (command.equals("openall")) {
            //opens all listings
            this.view.openAll();
        } else {
            //attempts to execute a search otherwise (post model selection)
            try {
                this.executeSearch(command);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Calls the model's search execution method, given an updated vehicle model to search for, hides the model
     * selection gui, and prints/displays the search results.
     *
     * @param model the updated vehicle model to search for
     * @throws IOException if the model fails to connect to the search URLs
     */
    private void executeSearch(String model) throws IOException {
        this.search.setModel(model);
        this.view.setModelSelectorVisibility(false);
        this.advertisementList = this.model.executeSearch(this.search);
        this.printResults();
        this.view.updateResultsVisibility(this.advertisementList, true);
    }

    /**
     * Prints all search results to the console.
     */
    private void printResults() {
        for (int i = 0; i < this.advertisementList.size(); i++) {
            Advertisement ad = advertisementList.get(i);
            System.out.println(String.format("[%02d]:[%02d]: %s", i + 1, ad.getAge(), ad.getTitle()));
        }
    }

    /**
     * Processes changes in the selected listing from the view to update which listing is displayed to the user.
     *
     * @param selectionEvent the list selection event to process
     */
    @Override
    public void valueChanged(ListSelectionEvent selectionEvent) {
        if (!selectionEvent.getValueIsAdjusting()) {
            this.view.updateListingSelection();
        }
    }

    /**
     * Processes key presses from the view to update which listing image is displayed to the user.
     *
     * @param keyEvent the key event to process
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int key = keyEvent.getExtendedKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            this.view.updateImageSelection(key);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
