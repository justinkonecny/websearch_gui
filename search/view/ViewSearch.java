package search.view;

import search.Advertisement;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.List;

public class ViewSearch extends JFrame implements IViewSearch {

    private PanelModelSelector panelModelSelector;
    private PanelViewResults panelViewResults;
    private int widthModel;
    private int heightModel;
    private int widthResults;
    private int heightResults;

    public ViewSearch() {
        super("WebSearch");
        this.widthModel = 300;
        this.heightModel = 200;
        this.widthResults = 900;
        this.heightResults = 700;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(this.widthModel, this.heightModel);
        this.setResizable(false);

        this.panelModelSelector = new PanelModelSelector(this.widthModel, this.heightModel - 30);
        this.panelViewResults = new PanelViewResults(this.widthResults - 15, this.heightResults - 30);
    }

    @Override
    public void setModelSelectorVisibility(boolean visible) {
        this.setContentPane(this.panelModelSelector);
        this.setVisible(visible);
    }

    @Override
    public void updateResultsVisibility(List<Advertisement> advertisements, boolean visible) {
        this.panelViewResults.populateResults(advertisements);
        this.setSize(this.widthResults, this.heightResults);
        this.setContentPane(this.panelViewResults);
        this.setVisible(visible);
    }

    @Override
    public void showAttributeEditor() {
        // TODO
    }

    @Override
    public void addListeners(ActionListener actionListener, ListSelectionListener listListener, KeyListener keyListener) {
        this.panelModelSelector.addActionListener(actionListener);
        this.panelViewResults.addActionListener(actionListener);
        this.panelViewResults.addListeners(listListener, keyListener);
    }

    @Override
    public void openSelected() {
        this.panelViewResults.openSelected();
    }

    @Override
    public void openAll() {
        this.panelViewResults.openAll();
    }

    @Override
    public void updateListingSelection() {
        this.panelViewResults.updateListingSelection();
    }

    @Override
    public void updateImageSelection(int key) {
        this.panelViewResults.updateImageSelection(key);
    }
}
