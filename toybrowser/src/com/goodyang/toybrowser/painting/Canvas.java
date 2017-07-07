package com.goodyang.toybrowser.painting;

import com.goodyang.toybrowser.css.Color;
import com.goodyang.toybrowser.layout.Rect;

import java.util.ArrayList;

/**
 * Created by Saga on 2017/7/7.
 */
public class Canvas {
    public ArrayList<Color> pixels;
    public int width;
    public int height;

    public Canvas(int width, int height) {
        Color white = null;
        this.pixels = new ArrayList<>(width * height);
        for(int i=0; i<width*height; i++) {
            white = new Color(255, 255, 255, 255);
            this.pixels.add(white);
        }
        this.width = width;
        this.height = height;
    }

    public void paintItem(DisplayCommand item) {
        if(item instanceof SolidColor) {
            Rect rect = ((SolidColor) item).rect;
            Color color = ((SolidColor) item).color;
            int x0 = clamp(rect.x, 0, this.width);
            int y0 = clamp(rect.y, 0, this.height);
            int x1 = clamp((rect.x + rect.width), 0, this.width);
            int y1 = clamp((rect.y + rect.height), 0, this.height);

            for(int i=y0 ; i<y1 ;i++) {
                for(int j=x0; j<x1; j++) {
                    Color temp = this.pixels.get(i * width + j);

                    temp.r = color.r;
                    temp.g = color.g;
                    temp.b = color.b;
                    temp.a = color.a;
                }
            }
        }
    }

    private int clamp(float original, int lower, int upper) {
        float ret = original;
        if(original < lower) {
            ret = original;
        }
        if(ret > upper) {
            ret = upper;
        }
        return (int)ret;
    }
}
