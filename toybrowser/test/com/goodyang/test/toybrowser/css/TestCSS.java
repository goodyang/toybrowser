package com.goodyang.test.toybrowser.css;

import com.goodyang.toybrowser.css.CSSParser;
import com.goodyang.toybrowser.css.Stylesheet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Saga on 2017/7/10.
 */
public class TestCSS {
    public static void main(String[] args) {
        File css = new File("/examples/test.css");
        try {
            if(css.exists()) {
                String cssStr = new String(Files.readAllBytes(Paths.get("/examples/test.css")));

                CSSParser cssParser = new CSSParser(0, cssStr);
                Stylesheet stylesheet = cssParser.parse();

                System.out.println(stylesheet.toString());
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
