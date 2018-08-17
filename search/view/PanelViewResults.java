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

public class PanelViewResults extends AbstractPanel {

    private List<Advertisement> advertisements;
    private DefaultListModel<String> listModelTitle;
    private JList<String> listTitle;
    private JButton buttonOpen;
    private JButton buttonOpenAll;
    private JScrollPane scrollPane;
    private JTextArea textAttributes;
    private JLabel image;
    private int currentListingIndex;
    private int currentImageIndex;

    public PanelViewResults(int width, int height) {
        super(width, height);
        this.currentListingIndex = 0;
        this.currentImageIndex = 0;

        this.advertisements = new ArrayList<Advertisement>();
        this.buttonOpen = new Button("Open Selection", "open");
        this.buttonOpenAll = new Button("Open All", "openall");
        this.listModelTitle = new DefaultListModel<String>();
        this.listTitle = new JList<String>(this.listModelTitle);
        this.scrollPane = new JScrollPane(this.listTitle);
        this.textAttributes = new JTextArea();
        this.textAttributes.setLineWrap(true);
        this.textAttributes.setWrapStyleWord(true);
        this.image = new JLabel();

        this.adjustComponentBoundaries();
    }

    public void updateListingSelection() {
        this.currentImageIndex = 0;
        this.currentListingIndex = this.listTitle.getSelectedIndex();
        Advertisement ad = this.advertisements.get(this.currentListingIndex);

        this.textAttributes.setText(ad.getAttributes() + System.lineSeparator());
        this.textAttributes.append(ad.getBody());
        this.image.setIcon(new ImageIcon(ad.getImages().get(this.currentImageIndex)));
    }

    public void updateImageSelection(int key) {
        List<Image> imageList = this.advertisements.get(this.currentListingIndex).getImages();
        if (key == KeyEvent.VK_RIGHT && this.currentImageIndex < imageList.size() - 1) {
            this.currentImageIndex++;
            this.image.setIcon(new ImageIcon(imageList.get(this.currentImageIndex)));
        } else if (key == KeyEvent.VK_LEFT && this.currentImageIndex > 0) {
            this.currentImageIndex--;
            this.image.setIcon(new ImageIcon(imageList.get(this.currentImageIndex)));
        }
    }

    public void populateResults(List<Advertisement> advertisementList) {
        if (advertisementList != null) {
            this.advertisements = advertisementList;

            for (Advertisement ad : this.advertisements) {
                this.listModelTitle.addElement(ad.getTitle());
            }
        }
        this.listTitle.setSelectedIndex(0);
        this.updateListingSelection();
    }

    public void addListeners(ListSelectionListener listener, KeyListener keyListener) {
        this.listTitle.addListSelectionListener(listener);
        this.listTitle.addKeyListener(keyListener);
    }

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

    @Override
    public void addActionListener(ActionListener actionListener) {
        this.buttonOpen.addActionListener(actionListener);
        this.buttonOpenAll.addActionListener(actionListener);
    }

    @Override
    protected void adjustComponentBoundaries() {
        int objWidth = this.width / 2;
        int objHeight = this.height / 3 - 10;

        int buttonWidth = this.width / 4;
        int buttonHeight = 30;

        this.buttonOpen.setBounds(2, 2, buttonWidth, buttonHeight);
        this.buttonOpenAll.setBounds(buttonWidth + 4, 2, buttonWidth, buttonHeight);

        this.scrollPane.setBounds(0, 40, this.width, objHeight);
        this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        this.textAttributes.setBounds(10, objHeight + 50, objWidth - 20, objHeight * 2 - 20);
        this.image.setBounds(objWidth, objHeight + 40, objWidth, objHeight * 2);

        this.add(this.buttonOpen);
        this.add(this.buttonOpenAll);
        this.add(this.scrollPane);
        this.add(this.textAttributes);
        this.add(this.image);
    }
}
