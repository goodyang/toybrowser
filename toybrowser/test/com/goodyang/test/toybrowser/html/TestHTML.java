package com.goodyang.test.toybrowser.html;

import com.goodyang.toybrowser.html.HtmlParser;
import com.goodyang.toybrowser.html.Node;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Saga on 2017/7/10.
 */
public class TestHTML {

    public static void main(String[] args) {
        File html = new File("/examples/test.html");

        try {
            if (html.exists()) {
                String htmlStr = new String(Files.readAllBytes(Paths.get("/examples/test.html")));

                HtmlParser htmlParser = new HtmlParser(htmlStr);
                Node root = htmlParser.parse();

                System.out.println(root.toString());

            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
