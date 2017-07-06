package com.goodyang.toybrowser.layout;

/**
 * Created by Administrator on 2017/7/6.
 */
public class Dimensions {
    public Rect content;
    public EdgeSizes padding;
    public EdgeSizes border;
    public EdgeSizes margin;

    public  Rect getPaddingBox() {
        return content.expandBy(padding);
    }

    public Rect getBorderBox() {
        return getPaddingBox().expandBy(border);
    }

    public Rect getMarginBox() {
        return getBorderBox().expandBy(margin);
    }
}
