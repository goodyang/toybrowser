package com.goodyang.toybrowser.layout;

/**
 * Created by Administrator on 2017/7/6.
 */
public class Dimensions {
    public Rect content = new Rect();
    public EdgeSizes padding = new EdgeSizes();
    public EdgeSizes border = new EdgeSizes();
    public EdgeSizes margin = new EdgeSizes();


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
