package com.goodyang.toybrowser.painting;

import com.goodyang.toybrowser.css.Color;
import com.goodyang.toybrowser.layout.Rect;
import com.goodyang.toybrowser.style.Display;

/**
 * Created by Saga on 2017/7/7.
 */
public class SolidColor extends DisplayCommand{
    public Color color;
    public Rect rect;

    public SolidColor(Color color, Rect rect) {
        this.color = color;
        this.rect = rect;
    }
}
