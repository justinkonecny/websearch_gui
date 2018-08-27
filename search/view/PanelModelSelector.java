package search.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Represents the panel for the vehicle model selection gui.
 */
public class PanelModelSelector extends AbstractPanel implements FocusListener {
    //the button to run the search for "bmw"
    private JButton buttonBmw;
    //the button to run the search for "mercedes"
    private JButton buttonMercedes;
    //the button to run the search for "wrangler"
    private JButton buttonJeep;
    //the button to run the search for "infiniti"
    private JButton buttonInfiniti;
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

        this.buttonBmw = new Button("Search: BMW", "bmw");
        this.buttonMercedes = new Button("Search: Mercedes", "mercedes");
        this.buttonInfiniti = new Button("Search: Infiniti", "infiniti");
        this.buttonJeep = new Button("Search: Jeep", "jeep");
        this.textModelOther = new JTextField("Other (enter to continue)");

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
        this.buttonInfiniti.addActionListener(listener);
        this.buttonJeep.addActionListener(listener);
        this.textModelOther.addActionListener(listener);
    }

    /**
     * Adjusts this panel's components to fit to specific boundaries.
     */
    @Override
    protected void adjustAddComponents() {
        int objPosX = 10;
        int objPosY = this.height / 5 - 5;
        int objWidth = this.width - objPosX * 2 - 15;
        int objHeight = objPosY - 8;
        int space = 4;

        this.buttonBmw.setBounds(objPosX, space,objWidth, objHeight);
        this.buttonMercedes.setBounds(objPosX, objHeight + (space * 2), objWidth, objHeight);
        this.buttonInfiniti.setBounds(objPosX, objHeight * 2 + (space * 3), objWidth, objHeight);
        this.buttonJeep.setBounds(objPosX, objHeight * 3 + (space * 4), objWidth, objHeight);
        this.textModelOther.setBounds(objPosX, objHeight * 4 + (space * 5), objWidth, objHeight);

        this.textModelOther.setFont(new Font("Tahoma", Font.ITALIC, 13));
        this.textModelOther.addFocusListener(this);

        this.add(this.buttonBmw);
        this.add(this.buttonMercedes);
        this.add(this.buttonInfiniti);
        this.add(this.buttonJeep);
        this.add(this.textModelOther);
    }

    @Override
    public void focusGained(FocusEvent e) {
        this.textModelOther.setText("");
        this.textModelOther.setFont(new Font("Tahoma", Font.BOLD, 13));
    }

    @Override
    public void focusLost(FocusEvent e) {}
}
