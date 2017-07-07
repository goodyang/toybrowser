package com.goodyang.toybrowser.entry;

import com.goodyang.toybrowser.css.CSSParser;
import com.goodyang.toybrowser.css.Stylesheet;
import com.goodyang.toybrowser.html.HtmlParser;
import com.goodyang.toybrowser.html.Node;
import com.goodyang.toybrowser.layout.Dimensions;
import com.goodyang.toybrowser.layout.LayoutBox;
import com.goodyang.toybrowser.layout.Rect;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

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

        if(html.exists()) {
            String htmlStr = new String(Files.readAllBytes(Paths.get("/examples/test.html")));

            HtmlParser htmlParser = new HtmlParser(htmlStr);
            Node root = htmlParser.parse();

            System.out.println(root.toString());

        }

        if(css.exists()) {
            String cssStr = new String(Files.readAllBytes(Paths.get("/examples/test.css")));

            CSSParser cssParser = new CSSParser(0, cssStr);
            Stylesheet stylesheet = cssParser.parse();

            System.out.println(stylesheet.toString());
        }
    }

}
