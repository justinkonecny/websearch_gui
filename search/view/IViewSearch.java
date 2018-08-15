package search.view;

import search.Advertisement;

import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.List;

public interface IViewSearch {

    void showModelSelector(boolean visible);

    void showAttributeEditor();

    void showResults(List<Advertisement> advertisements, boolean visible);

    void addListeners(ActionListener actionListener, ListSelectionListener listListener, KeyListener keyListener);

    void updateListingSelection();

    void updateImageSelection(int key);

    void close();

    void openSelected();

    void openAll();
}
