package com.goodyang.toybrowser.painting;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Saga on 2017/7/14.
 */
public class AdvancePaint extends JPanel {
    ArrayList<BufferedImage> layers;

    public  AdvancePaint(){
        layers = new ArrayList<BufferedImage>();
    }

    public void addLayer(BufferedImage layer){
        layers.add(layer);
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        for(BufferedImage buf: layers) {

        }
    }
}
