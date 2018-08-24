package search.view;

import search.Advertisement;
import search.Search;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.List;

/**
 * Represents the implementation of the view. The view displays the model selector gui to the user
 * and displays the search results to the user (providing buttons to interact with the results).
 */
public class ViewSearch extends JFrame implements IViewSearch {
    //the panel to display the model selection gui
    private PanelModelSelector panelModelSelector;
    //the panel to display the search results
    private PanelViewResults panelViewResults;
    //the panel to display the search options editor
    private PanelAttributeEdit panelAttributeEdit;
    //the search containing the search options
    private Search search;
    //the width of the model selection gui
    private int widthModel;
    //the height of the model selection gui
    private int heightModel;
    //the width of the results gui
    private int widthResults;
    //the height of the results gui
    private int heightResults;
    //the width of the search option gui
    private int widthAttribute;
    //the height of the search option gui
    private int heightAttribute;

    /**
     * Constructs the interactive view with default sizes for the gui panels.
     */
    public ViewSearch() {
        super("Web Car Search");
        this.widthModel = 300;
        this.heightModel = 200;
        this.widthResults = 900;
        this.heightResults = 700;
        this.widthAttribute = 600;
        this.heightAttribute = 185;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(this.widthModel, this.heightModel);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.panelModelSelector = new PanelModelSelector(this.widthModel, this.heightModel - 30);
        this.panelViewResults = new PanelViewResults(this.widthResults - 10, this.heightResults - 30);
        this.panelAttributeEdit = new PanelAttributeEdit(this.widthAttribute, this.heightAttribute);
    }

    /**
     * Displays the model selection gui for the user to select a model to search for.
     */
    @Override
    public void displayModelGUI() {
        this.setSize(this.widthModel, this.heightModel);
        this.setContentPane(this.panelModelSelector);
        this.setVisible(true);
    }

    /**
     * Displays the search option edit gui to alter search parameters.
     *
     * @param search the search with parameters to display and edit
     */
    @Override
    public void displayOptionsGUI(Search search) {
        this.search = search;
        this.panelAttributeEdit.setDisplayedSearch(this.search);
        this.setSize(this.widthAttribute, this.heightAttribute);
        this.setContentPane(this.panelAttributeEdit);
        this.setVisible(true);
    }

    /**
     * Displays the results gui with the given list of Advertisement
     *
     * @param advertisements the list of Advertisements to display
     */
    @Override
    public void displayResultsGUI(List<Advertisement> advertisements) {
        this.panelViewResults.populateResults(advertisements);
        this.setSize(this.widthResults, this.heightResults);
        this.setContentPane(this.panelViewResults);
        this.setVisible(true);
    }

    /**
     * Adds the given listeners to the appropriate Components in the view. Used for handling user
     * input from the gui (e.g. clicking buttons, list selections, key presses)
     *
     * @param actionListener the action listener
     * @param listListener the list selection listener
     * @param keyListener the key listener
     */
    @Override
    public void addListeners(ActionListener actionListener, ListSelectionListener listListener, KeyListener keyListener) {
        if (actionListener == null || listListener == null || keyListener == null) {
            throw new IllegalArgumentException("Given listeners cannot by null");
        } else {
            this.panelModelSelector.addActionListener(actionListener);
            this.panelViewResults.addActionListener(actionListener);
            this.panelViewResults.addListeners(listListener, keyListener);
            this.panelAttributeEdit.addActionListener(actionListener);
        }
    }

    /**
     * Opens the currently selected listing in the system's default web browser.
     */
    @Override
    public void openSelected() {
        this.panelViewResults.openSelected();
    }


    /**
     * Opens all listings previously passed to the view in the system's default web browser.
     */
    @Override
    public void openAll() {
        this.panelViewResults.openAll();
    }

    /**
     * Refreshes the gui displaying the listing results (description and images), based on
     * the currently selected listing.
     */
    @Override
    public void updateListingSelection() {
        this.panelViewResults.updateListingSelection();
    }

    /**
     * Refreshes the gui displaying the listing image (which image in the Advertisement's list of images), given
     * what key was pressed.
     *
     * @param key the key the user pressed
     */
    @Override
    public void updateImageSelection(int key) {
        this.panelViewResults.updateImageSelection(key);
    }

    /**
     * Removes the selected listing from the Advertisements displayed to the user.
     */
    @Override
    public void removeSelectedListing() {
        this.panelViewResults.removeSelectedListing();
    }

    /**
     * Updates the view's Search based on user input.
     */
    @Override
    public void updateSearchFromInput() {
        this.panelAttributeEdit.updateSearchFromTextFields();
    }

    /**
     * Hides this view from screen.
     */
    @Override
    public void hideFrame() {
        this.setVisible(false);
    }
}
