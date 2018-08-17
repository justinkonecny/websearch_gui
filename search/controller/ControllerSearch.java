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

public class ControllerSearch implements IControllerSearch, ActionListener, ListSelectionListener, KeyListener {

    private List<Advertisement> advertisementList;
    private IModelSearch model;
    private IViewSearch view;
    private Search search;

    public ControllerSearch(IModelSearch model, IViewSearch view, Search search) {
        this.model = model;
        this.view = view;
        this.search = search;
        this.advertisementList = new ArrayList<Advertisement>();
        this.view.addListeners(this, this, this);
    }

    @Override
    public void run() {
        this.view.setModelSelectorVisibility(true);
    }

    private void executeSearch(String model) throws IOException {
        this.search.setModel(model);
        this.view.setModelSelectorVisibility(false);
        this.advertisementList = this.model.executeSearch(this.search);
        this.printResults();
        this.view.updateResultsVisibility(this.advertisementList, true);
    }

    private void printResults() {
        for (int i = 0; i < this.advertisementList.size(); i++) {
            Advertisement ad = advertisementList.get(i);
            System.out.println(String.format("[%02d]:[%02d]: %s", i + 1, ad.getAge(), ad.getTitle()));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command != null && !command.isEmpty()) {
            if (command.equals("open")) {
                this.view.openSelected();
            } else if (command.equals("openall")) {
                this.view.openAll();
            } else {
                try {
                    this.executeSearch(command);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            this.view.updateListingSelection();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getExtendedKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            this.view.updateImageSelection(key);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
