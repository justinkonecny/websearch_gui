package search.view;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Represents the panel for the vehicle model selection gui.
 */
public class PanelModelSelector extends AbstractPanel {
    //the button to run the search for "bmw"
    private JButton buttonBmw;
    //the button to run the search for "mercedes"
    private JButton buttonMercedes;
    //the button to run the search for "wrangler"
    private JButton buttonJeep;
    //the text field to enter other models
    private JTextField textModelOther;

    /**
     * Constructs the vehicle model selection panel with the given width and height.
     *
     * @param width the width of the panel.
     * @param height the height of the panel.
     */
    public PanelModelSelector(int width, int height) {
        super(width, height);

        this.buttonBmw = new Button("BMW", "bmw");
        this.buttonMercedes = new Button("Mercedes", "mercedes");
        this.buttonJeep = new Button("Jeep", "jeep");
        this.textModelOther = new JTextField("[Enter Other Model]");

        this.adjustAddComponents();
    }

    /**
     * Adds the given ActionListener to this panel's relevant components.
     *
     * @param listener the action listener to add
     */
    @Override
    public void addActionListener(ActionListener listener) {
        this.buttonBmw.addActionListener(listener);
        this.buttonMercedes.addActionListener(listener);
        this.buttonJeep.addActionListener(listener);
        this.textModelOther.addActionListener(listener);
    }

    /**
     * Adjusts this panel's components to fit to specific boundaries.
     */
    @Override
    protected void adjustAddComponents() {
        int objPosX = 10;
        int objPosY = this.height / 4;
        int objWidth = this.width - objPosX * 2;
        int objHeight = objPosY - 8;

        this.buttonBmw.setBounds(objPosX, 0,objWidth, objHeight);
        this.buttonMercedes.setBounds(objPosX, objPosY, objWidth, objHeight);
        this.buttonJeep.setBounds(objPosX, objPosY * 2, objWidth, objHeight);
        this.textModelOther.setBounds(objPosX, objPosY * 3, objWidth, objHeight);

        this.add(this.buttonBmw);
        this.add(this.buttonMercedes);
        this.add(this.buttonJeep);
        this.add(this.textModelOther);
    }
}
