package com.goodyang.toybrowser.painting;

import com.goodyang.toybrowser.css.Color;
import com.goodyang.toybrowser.css.ColorValue;
import com.goodyang.toybrowser.css.Value;
import com.goodyang.toybrowser.layout.*;

import java.util.ArrayList;

/**
 * Created by Saga on 2017/7/7.
 */
public class PaintProcess {
    public static Canvas paint(LayoutBox layoutRoot, Rect bounds) {
        ArrayList<DisplayCommand> displayList = buildDisplayList(layoutRoot);
        Canvas canvas = new Canvas((int)bounds.width, (int)bounds.height);
        for(DisplayCommand item : displayList) {
            canvas.paintItem(item);
        }
        return canvas;
    }

    private static ArrayList<DisplayCommand> buildDisplayList(LayoutBox layoutBox) {
        ArrayList<DisplayCommand> list = new ArrayList<>();

        renderLayoutBox(list, layoutBox);

        return list;
    }

    private static void renderLayoutBox(ArrayList<DisplayCommand> list, LayoutBox layoutBox) {
        renderBackground(list, layoutBox);
        renderBorders(list, layoutBox);
        for(LayoutBox child : layoutBox.children) {
            renderLayoutBox(list, child);
        }
    }

    private static void renderBackground(ArrayList<DisplayCommand> list, LayoutBox layoutBox) {
        Color color = getColor(layoutBox, "background");
        if(color != null) {
            list.add(new SolidColor(color, layoutBox.dimensions.getBorderBox()));
        }
    }

    private static void renderBorders(ArrayList<DisplayCommand> list, LayoutBox layoutBox) {
        Color color = getColor(layoutBox, "border-color");
        if(color == null) {
            return;
        }

        Dimensions d = layoutBox.dimensions;
        Rect borderBox = d.getBorderBox();

        SolidColor leftBorder = new SolidColor(color, new Rect(
                borderBox.x,
                borderBox.y,
                d.border.left,
                borderBox.height
        ));

        list.add(leftBorder);

        SolidColor rightBorder = new SolidColor(color, new Rect(
                borderBox.x + borderBox.width - d.border.right,
                borderBox.y,
                d.border.right,
                borderBox.height
        ));

        list.add(rightBorder);

        SolidColor topBorder = new SolidColor(color, new Rect(
                borderBox.x,
                borderBox.y,
                borderBox.width,
                d.border.top
        ));

        list.add(topBorder);

        SolidColor bottomBorder = new SolidColor(color, new Rect(
                borderBox.x,
                borderBox.y + borderBox.height - d.border.bottom,
                borderBox.width,
                d.border.bottom
        ));

        list.add(bottomBorder);
    }

    private static Color getColor(LayoutBox layoutBox, String name) {
        BoxType boxType = layoutBox.boxType;
        Value value = null;
        if(boxType instanceof BlockNode) {
            value = ((BlockNode)boxType).styleNode.value(name);
        }else if(boxType instanceof InlineNode) {
            value = ((InlineNode)boxType).styleNode.value(name);
        }

        if(value != null && value instanceof ColorValue) {
            return ((ColorValue)value).color;
        }
        return null;
    }
}
