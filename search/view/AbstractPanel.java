package search.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class AbstractPanel extends JPanel {

    protected int width;
    protected int height;

    protected AbstractPanel(int width, int height) {
        super();
        this.width = width - 15;
        this.height = height;
        this.setLayout(null);
    }

    public abstract void addActionListener(ActionListener actionListener);

    protected abstract void adjustComponentBoundaries();

    protected class Button extends JButton {
        protected Button(String name, String command) {
            super(name);
            this.setActionCommand(command);
            this.setBackground(Color.WHITE);
        }
    }
}
