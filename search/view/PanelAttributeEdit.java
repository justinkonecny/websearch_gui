package search.view;

import search.Search;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Represents the panel to display the search parameters to the user. Allows the user to alter the parameters
 * through the displayed gui.
 */
class PanelAttributeEdit extends AbstractPanel {
    //the search containing the parameters to display to the user
    private Search search;
    //the text field containing the search minimum price
    private JTextField fieldPriceMinimum;
    //the text field containing the search maximum price
    private JTextField fieldPriceMaximum;
    //the text field containing the search minimum year
    private JTextField fieldYearMinimum;
    //the text field containing the search minimum miles
    private JTextField fieldMilesMinimum;
    //the text field containing the search maximum miles
    private JTextField fieldMilesMaximum;
    //the text label for the minimum/maximum price fields
    private JLabel labelPrice;
    //the text label for the minimum/maximum miles fields
    private JLabel labelMiles;
    //the text label for the minimum year field
    private JLabel labelYear;
    //the button to continue the search
    private JButton buttonContinue;

    /**
     * Constructs the attribute editor panel with the given width and height
     * @param width the panel width
     * @param height the panel height
     */
    public PanelAttributeEdit(int width, int height) {
        super(width, height);

        this.buttonContinue = new Button("Continue", "cmd:continue");
        this.fieldPriceMinimum = new AttributeField();
        this.fieldPriceMaximum = new AttributeField();
        this.fieldYearMinimum = new AttributeField();
        this.fieldMilesMinimum = new AttributeField();
        this.fieldMilesMaximum = new AttributeField();

        this.labelPrice = new JLabel("Minimum/Maximum Price");
        this.labelMiles = new JLabel("Minimum/Maximum Miles");
        this.labelYear = new JLabel("Minimum Year");

        this.adjustAddComponents();
    }

    /**
     * Sets this panels text field's text to their respective values from the given Search.
     *
     * @param search the Search containing the parameters to display in the text fields
     */
    public void setDisplayedSearch(Search search) {
        this.search = search;
        this.fieldPriceMinimum.setText(String.valueOf(this.search.getPriceMinimum()));
        this.fieldPriceMaximum.setText(String.valueOf(this.search.getPriceMaximum()));
        this.fieldYearMinimum.setText(String.valueOf(this.search.getYearMinimum()));
        this.fieldMilesMinimum.setText(String.valueOf(this.search.getMilesMinimum()));
        this.fieldMilesMaximum.setText(String.valueOf(this.search.getMilesMaximum()));
    }

    /**
     * Updates this panel's search with the integer values parsed from the text fields.
     */
    public void updateSearchFromTextFields() {
        this.search.setPriceMinimum(this.getInteger(this.fieldPriceMinimum.getText(), this.search.getPriceMinimum()));
        this.search.setPriceMaximum(this.getInteger(this.fieldPriceMaximum.getText(), this.search.getMilesMaximum()));
        this.search.setYearMinimum(this.getInteger(this.fieldYearMinimum.getText(), this.search.getYearMinimum()));
        this.search.setMilesMinimum(this.getInteger(this.fieldMilesMinimum.getText(), this.search.getMilesMinimum()));
        this.search.setMilesMaximum(this.getInteger(this.fieldMilesMaximum.getText(), this.search.getMilesMaximum()));
    }

    /**
     * Adds the given ActionListener to this panel's relevant components.
     *
     * @param actionListener the action listener to add
     */
    @Override
    public void addActionListener(ActionListener actionListener) {
        this.buttonContinue.addActionListener(actionListener);
    }

    /**
     * Adjusts this panel's components to fit to specific boundaries.
     */
    @Override
    protected void adjustAddComponents() {
        int fieldWidth = (this.width / 3) - 5;
        int fieldHeight = (this.height / 3) - 20;
        int fieldX = 10;
        int fieldY = 10;

        int labelX = (fieldWidth * 2) + 30;
        int labelY = fieldY;
        int labelWidth = (this.width / 3) - 20;
        int labelHeight = (this.height / 3) - 20;

        this.buttonContinue.setBounds(fieldX * 2 + fieldWidth, fieldHeight * 2 + fieldY, fieldWidth, fieldHeight);
        this.fieldPriceMinimum.setBounds(fieldX, fieldY, fieldWidth, fieldHeight);
        this.fieldPriceMaximum.setBounds(fieldX * 2 + fieldWidth, fieldY, fieldWidth, fieldHeight);
        this.fieldYearMinimum.setBounds(fieldX, fieldHeight * 2 + fieldY, fieldWidth, fieldHeight);
        this.fieldMilesMinimum.setBounds(fieldX, fieldY + fieldHeight, fieldWidth, fieldHeight);
        this.fieldMilesMaximum.setBounds(fieldX * 2 + fieldWidth, fieldY + fieldHeight, fieldWidth, fieldHeight);

        this.labelPrice.setBounds(labelX, labelY, labelWidth, labelHeight);
        this.labelMiles.setBounds(labelX, labelHeight + labelY, labelWidth, labelHeight);
        this.labelYear.setBounds(labelX, labelHeight * 2 + labelY, labelWidth, labelHeight);

        this.add(this.buttonContinue);
        this.add(this.fieldPriceMinimum);
        this.add(this.fieldPriceMaximum);
        this.add(this.fieldYearMinimum);
        this.add(this.fieldMilesMinimum);
        this.add(this.fieldMilesMaximum);
        this.add(this.labelMiles);
        this.add(this.labelPrice);
        this.add(this.labelYear);
    }

    /**
     * Tries to parse an integer from the given String, returns the defaultInt if the given String is not an integer.
     *
     * @param strInt the String to be converted into an integer
     * @param defaultInt the integer to return otherwise
     * @return strInt as an integer, or the defaultInt if strInt is not an integer
     */
    private int getInteger(String strInt, int defaultInt) {
        try {
            int value = Integer.valueOf(strInt);
            return value;
        } catch (NumberFormatException e) {
            return defaultInt;
        }
    }

    /**
     * Represents a text field to enter search option data.
     */
    private class AttributeField extends JTextField {
        /**
         * Constructs an search attribute text field with the action command "cmd:editattribute"
         */
        private AttributeField() {
            super();
            this.setActionCommand("cmd:editattribute");
            this.setBackground(new Color(250, 245, 240));
        }
    }
}
