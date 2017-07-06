package com.goodyang.toybrowser.layout;

/**
 * Created by Administrator on 2017/7/6.
 */
public class Rect {
    public float x;
    public float y;
    public float width;
    public float height;

    public Rect(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rect expandBy(EdgeSizes edge) {
        return new Rect(
                x - edge.left,
                y - edge.top,
                width + edge.left + edge.right,
                height + edge.top + edge.bottom
        );
    }
}
