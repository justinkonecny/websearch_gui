package search.view;

import search.Advertisement;
import search.Search;

import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.List;

/**
 * Represents the view to interact with the user. The view consists of the gui for selecting the vehicle model and
 * the gui to the display the search results.
 */
public interface IViewSearch {

    /**
     * Displays the model selection gui for the user to select a model to search for.
     */
    void displayModelGUI();

    /**
     * Displays the search option edit gui to alter search parameters.
     *
     * @param search the search with parameters to display and edit
     */
    void displayOptionsGUI(Search search);

    /**
     * Displays the results gui with the given list of Advertisement
     *
     * @param advertisements the list of Advertisements to display
     */
    void displayResultsGUI(List<Advertisement> advertisements);

    /**
     * Adds the given listeners to the appropriate Components in the view. Used for handling user
     * input from the gui (e.g. clicking buttons, list selections, key presses)
     *
     * @param actionListener the action listener
     * @param listListener   the list selection listener
     * @param keyListener    the key listener
     */
    void addListeners(ActionListener actionListener, ListSelectionListener listListener, KeyListener keyListener);

    /**
     * Refreshes the gui displaying the listing results (description and images), based on
     * the currently selected listing.
     */
    void updateListingSelection();

    /**
     * Refreshes the gui displaying the listing image (which image in the Advertisement's list of images), given
     * what key was pressed.
     *
     * @param key the key the user pressed
     */
    void updateImageSelection(int key);

    /**
     * Opens the currently selected listing in the system's default web browser.
     */
    void openSelected();

    /**
     * Opens all listings previously passed to the view in the system's default web browser.
     */
    void openAll();

    /**
     * Removes the selected listing from the Advertisements displayed to the user.
     */
    void removeSelectedListing();

    /**
     * Updates the view's Search based on user input.
     */
    void updateSearchFromInput();

    /**
     * Hides this view from screen.
     */
    void hideFrame();

    /**
     * Returns the current displayed Advertisement.
     */
    int getCurrentAdvertisement();
}
