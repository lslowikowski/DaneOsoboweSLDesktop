package com.company;

import java.awt.*;

public class MyGridBagConstraints extends GridBagConstraints {
    public MyGridBagConstraints(int gridx, int gridy, int gridwidth, int gridheight) {
        super();
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;
        this.weightx = 1.0;
        this.weighty = 1.0;
        this.anchor =  GridBagConstraints.CENTER;
        this.fill = GridBagConstraints.HORIZONTAL;
        this.insets = new Insets(5,5,5,5);
        this.ipadx = 0;
        this.ipady = 0;
    }
}