package search.view;

import search.Advertisement;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the panel for the gui that displays the search results to the user.
 */
public class PanelViewResults extends AbstractPanel {
    //the list of Advertisement returned from the search
    private List<Advertisement> advertisements;
    //the list model component to display the advertisement titles
    private DefaultListModel<String> listModelTitle;
    //the list component to display the advertisement titles
    private JList<String> listTitle;
    //the button to open the selected listing
    private JButton buttonOpen;
    //the button to open all listings
    private JButton buttonOpenAll;
    //the button to remove a displayed Advertisement
    private JButton buttonRemove;
    //the button to start a new search
    private JButton buttonSearch;
    //the button to load listing images
    private JButton buttonLoadImages;
    //the scroll pane to contain the listings
    private JScrollPane scrollPane;
    //the text area to display the listing description/body
    private JTextArea textAttributes;
    //the image of the selected listing
    private JLabel image;
    //the current index of the selected listing
    private int currentListingIndex;
    //the current index of the selected image of this listings
    private int currentImageIndex;

    /**
     * Constructs the results panel with the given with and height.
     *
     * @param width the width of the panel
     * @param height the height of the panel
     */
    public PanelViewResults(int width, int height) {
        super(width, height);

        this.setBackground(Color.WHITE);
        this.currentListingIndex = 0;
        this.currentImageIndex = 0;

        this.advertisements = new ArrayList<Advertisement>();
        this.buttonOpen = new Button("Open Selection", "cmd:open");
        this.buttonOpenAll = new Button("Open All", "cmd:openall");
        this.buttonRemove = new Button("Remove", "cmd:remove");
        this.buttonSearch = new Button("New Search", "cmd:newsearch");
        this.buttonLoadImages = new Button("Load Images", "cmd:loadimages");
        this.listModelTitle = new DefaultListModel<String>();
        this.listTitle = new JList<String>(this.listModelTitle);
        this.scrollPane = new JScrollPane(this.listTitle);
        this.textAttributes = new JTextArea();
        this.image = new JLabel();

        this.adjustAddComponents();
    }

    /**
     * Updates the displayed Advertisement based on the current selected listing.
     */
    public void updateListingSelection() {
        if (this.listModelTitle.size() > 0) {
            int index = this.listTitle.getSelectedIndex();
            this.currentImageIndex = 0;
            this.currentListingIndex = (index >= 0) ? index : 0;
            Advertisement ad = this.advertisements.get(this.currentListingIndex);

            this.textAttributes.setText(ad.getAttributes());
            this.textAttributes.append("Days Since Posting: " + ad.getAge() + System.lineSeparator());
            if (ad.getPrice() > 1000) {
                this.textAttributes.append("Price: $" + ad.getPrice() + System.lineSeparator());
            }
            this.textAttributes.append(ad.getLocation());
            this.textAttributes.append(System.lineSeparator() + ad.getBody());

            if (ad.getImages().size() > 0) {
                this.image.setIcon(new ImageIcon(ad.getImages().get(this.currentImageIndex)));
            } else {
                this.image.setIcon(new ImageIcon());
            }
        }
    }

    /**
     * Updates the displayed image for the Advertisement based on the pressed key. The right and left
     * arrow keys cycle through the list of pictures (right increments index, left decrements index).
     *
     * @param key the key pressed by the user
     */
    public void updateImageSelection(int key) {
        List<Image> imageList = this.advertisements.get(this.currentListingIndex).getImages();
        if (key == KeyEvent.VK_RIGHT && this.currentImageIndex < imageList.size() - 1) {
            //increment index if right arrow key is pressed
            this.currentImageIndex++;
            this.image.setIcon(new ImageIcon(imageList.get(this.currentImageIndex)));
        } else if (key == KeyEvent.VK_LEFT && this.currentImageIndex > 0) {
            //decrement index if the left arrow key is pressed
            this.currentImageIndex--;
            this.image.setIcon(new ImageIcon(imageList.get(this.currentImageIndex)));
        }
    }

    /**
     * Updates the list of Advertisement displayed in this panel to the given list.
     *
     * @param advertisementList the new list of Advertisement to display
     */
    public void populateResults(List<Advertisement> advertisementList) {
        if (advertisementList != null) {
            this.advertisements = advertisementList;
            this.listModelTitle.clear();
            for (Advertisement ad : this.advertisements) {
                this.listModelTitle.addElement(ad.getTitle());
            }
        }
        this.listTitle.setSelectedIndex(0);
        this.updateListingSelection();
    }

    /**
     * Adds the given ListSelectionListener and KeyListener to the list's set of listeners to allow
     * users to select items on the list of Advertisement.
     *
     * @param listListener the list selection listener for the list
     * @param keyListener the key listener for the list
     */
    public void addListeners(ListSelectionListener listListener, KeyListener keyListener) {
        this.listTitle.addListSelectionListener(listListener);
        this.listTitle.addKeyListener(keyListener);
    }


    /**
     * Updates the current selected listing with the given list of images.
     */
    public void updateSelectionImages(List<Image> listImages) {
        this.advertisements.get(this.currentListingIndex).setImages(listImages);
    }

    /**
     * Returns the current displayed Advertisement.
     */
    public Advertisement getCurrentAdvertisement() {
        return this.advertisements.get(this.currentListingIndex);
    }

    /**
     * Opens the link of the selected Advertisement in the system's default web browser.
     */
    public void openSelected() {
        if (this.currentListingIndex < this.advertisements.size()) {
            try {
                Desktop.getDesktop().browse(new URI(this.advertisements.get(this.currentListingIndex).getLink()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Opens all links of in the list of Advertisement in the system's default web browser.S
     */
    public void openAll() {
        for (Advertisement ad : this.advertisements) {
            try {
                Desktop.getDesktop().browse(new URI(ad.getLink()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch( IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Removes the selected listing from the Advertisements displayed to the user.
     */
    public void removeSelectedListing() {
        if (this.currentListingIndex >= 0) {
            int index = this.currentListingIndex;
            this.listModelTitle.remove(index);
            this.advertisements.remove(index);
            this.currentListingIndex = index;
            if (this.currentListingIndex > 0) {
                this.currentListingIndex--;
            }
            this.listTitle.setSelectedIndex(this.currentListingIndex);
        }
    }

    /**
     * Adds the given ActionListener to this panel's relevant components.
     *
     * @param actionListener the action listener to add
     */
    @Override
    public void addActionListener(ActionListener actionListener) {
        this.buttonOpen.addActionListener(actionListener);
        this.buttonOpenAll.addActionListener(actionListener);
        this.buttonRemove.addActionListener(actionListener);
        this.buttonSearch.addActionListener(actionListener);
        this.buttonLoadImages.addActionListener(actionListener);
    }

    /**
     * Adjusts this panel's components to fit to specific boundaries.
     */
    @Override
    protected void adjustAddComponents() {
        int objWidth = this.width / 2 - 2;
        int objHeight = this.height / 3 - 10;
        int buttonWidth = this.width / 5 - 2;
        int buttonHeight = 30;
        int space = 2;

        this.textAttributes.setLineWrap(true);
        this.textAttributes.setWrapStyleWord(true);

        this.buttonOpen.setBounds(space, 4, buttonWidth, buttonHeight);
        this.buttonOpenAll.setBounds(buttonWidth + (space * 2), 4, buttonWidth, buttonHeight);
        this.buttonRemove.setBounds(buttonWidth * 2 + (space * 3), 4, buttonWidth, buttonHeight);
        this.buttonSearch.setBounds(buttonWidth * 3 + (space * 4), 4, buttonWidth, buttonHeight);
        this.buttonLoadImages.setBounds(buttonWidth * 4 + (space * 5), 4, buttonWidth, buttonHeight);

        this.scrollPane.setBounds(space, buttonHeight + (space * 4), (buttonWidth * 5) + (space * 4), objHeight);
        this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        this.textAttributes.setBounds(10, objHeight + 50, objWidth - 20, objHeight * 2 - 20);
        this.image.setBounds(objWidth, objHeight + 40, objWidth, objHeight * 2);

        this.add(this.buttonRemove);
        this.add(this.buttonOpen);
        this.add(this.buttonOpenAll);
        this.add(this.buttonSearch);
        this.add(this.buttonLoadImages);
        this.add(this.scrollPane);
        this.add(this.textAttributes);
        this.add(this.image);
    }
}
