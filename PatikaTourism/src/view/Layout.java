package view;

import core.Helper;

import javax.swing.*;

public class Layout extends JFrame {
    public void initView(int width, int height, String title) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Patika Tourism Agency " + title);
        this.setSize(width, height);
        this.setLocation(Helper.getScreenCenter("x", getSize()), Helper.getScreenCenter("y", getSize()));
        this.setVisible(true);
    }

}
