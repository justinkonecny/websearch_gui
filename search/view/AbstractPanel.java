package search.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Represents an abstract JPanel used to display information to the user (as a gui)
 */
public abstract class AbstractPanel extends JPanel {
    //the panel width
    protected int width;
    //the panel height
    protected int height;

    /**
     * Constructs a panel object with the given width and height.
     *
     * @param width the width the of the panel
     * @param height the height of the panel
     * @throws IllegalArgumentException if the height or width are non-positive
     */
    protected AbstractPanel(int width, int height) throws IllegalArgumentException{
        super();
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException("Width and height must be positive");
        } else {
            this.width = width;
            this.height = height;
            this.setLayout(null);
            this.setBackground(Color.WHITE);
            this.setSize(this.width, this.height);
        }
    }

    /**
     * Adds the given ActionListener to this panel's relevant components.
     *
     * @param actionListener the action listener to add
     */
    public abstract void addActionListener(ActionListener actionListener);

    /**
     * Adjusts this panel's components to fit to specific boundaries.
     */
    protected abstract void adjustAddComponents();

    /**
     * Represents a clickable button with a command.
     */
    protected class Button extends JButton {

        /**
         * Constructs a Button object.
         *
         * @param name the button name
         * @param command the command issued when clicked
         */
        protected Button(String name, String command) {
            super(name);
            this.setActionCommand(command);
            this.setBackground(new Color(240, 238, 236));
        }
    }
}
