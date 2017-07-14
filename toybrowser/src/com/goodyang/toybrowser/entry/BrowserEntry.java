package com.goodyang.toybrowser.entry;

import com.goodyang.toybrowser.css.CSSParser;
import com.goodyang.toybrowser.css.Color;
import com.goodyang.toybrowser.css.Stylesheet;
import com.goodyang.toybrowser.html.HtmlParser;
import com.goodyang.toybrowser.html.Node;
import com.goodyang.toybrowser.layout.Dimensions;
import com.goodyang.toybrowser.layout.LayoutBox;
import com.goodyang.toybrowser.layout.LayoutBoxBuilder;
import com.goodyang.toybrowser.layout.Rect;
import com.goodyang.toybrowser.painting.*;
import com.goodyang.toybrowser.painting.Canvas;
import com.goodyang.toybrowser.style.StyleNode;
import com.goodyang.toybrowser.style.StyleTreeBuilder;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import java.nio.file.Files;

import java.nio.file.Paths;


/**
 * Created by Saga on 2017/7/7.
 */
public class BrowserEntry {
    public static void main(String[] args) throws Exception{
        File html = new File("/examples/test.html");

        File css = new File("/examples/test.css");

        Dimensions viewport = new Dimensions();
        viewport.content = new Rect();
        viewport.content.width = 800;
        viewport.content.height = 600;

        Node root = null;
        if(html.exists()) {
            String htmlStr = new String(Files.readAllBytes(Paths.get("/examples/test.html")));

            HtmlParser htmlParser = new HtmlParser(htmlStr);
            root = htmlParser.parse();

            System.out.println(root.toString());

        }

        Stylesheet stylesheet = null;
        if(css.exists()) {
            String cssStr = new String(Files.readAllBytes(Paths.get("/examples/test.css")));

            CSSParser cssParser = new CSSParser(0, cssStr);
            stylesheet = cssParser.parse();

            System.out.println(stylesheet.toString());
        }

        StyleNode styleNode = StyleTreeBuilder.style_tree(root, stylesheet);

        System.out.println(styleNode);

        LayoutBox layoutBox = null;
        if(styleNode != null) {
            layoutBox = LayoutBoxBuilder.getLayoutTree(styleNode, viewport);
            System.out.println(layoutBox);
        }

        if(layoutBox != null) {
//            viewport.content.height = 1600;
            Canvas canvas = PaintProcess.paint(layoutBox, viewport.content);

            BufferedImage bufferedImage = new BufferedImage(canvas.width, canvas.height, BufferedImage.TYPE_INT_ARGB);


            for(int i=0; i<canvas.width; i++) {
                for(int j=0; j<canvas.height; j++) {
                    Color color = canvas.pixels.get(j*canvas.width+i);
                    bufferedImage.setRGB(i, j, new java.awt.Color(color.r, color.g, color.b, color.a).getRGB());
                }
            }

            Graphics graphics = bufferedImage.getGraphics();
            graphics.setColor(java.awt.Color.BLUE);
            graphics.fillRect(0,0, 200, 50);
            graphics.setColor(java.awt.Color.BLACK);
            graphics.setFont(new Font("Arial Black", Font.BOLD, 20));
            graphics.drawString("Test", 10, 25);

            File output = new File("/test-rainbow.png");
            ImageIO.write(bufferedImage, "png", output);
        }

    }

}
