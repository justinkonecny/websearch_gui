package search.view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class PanelModelSelector extends AbstractPanel {

    private JButton buttonBmw;
    private JButton buttonMercedes;
    private JButton buttonJeep;
    private JTextField textModelOther;

    public PanelModelSelector(int width, int height) {
        super(width, height);

        this.buttonBmw = new Button("BMW", "bmw");
        this.buttonMercedes = new Button("Mercedes", "mercedes");
        this.buttonJeep = new Button("Jeep", "jeep");
        this.textModelOther = new JTextField("[Enter Other Model]");

        this.adjustComponentBoundaries();
    }

    @Override
    public void addActionListener(ActionListener listener) {
        this.buttonBmw.addActionListener(listener);
        this.buttonMercedes.addActionListener(listener);
        this.buttonJeep.addActionListener(listener);
        this.textModelOther.addActionListener(listener);
    }

    @Override
    protected void adjustComponentBoundaries() {
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
