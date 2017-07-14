package com.goodyang.toybrowser.style;

import com.goodyang.toybrowser.css.*;
import com.goodyang.toybrowser.html.Element;
import com.goodyang.toybrowser.html.ElementData;
import com.goodyang.toybrowser.html.Node;
import com.goodyang.toybrowser.html.NodeType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/5.
 * building style tree
 */
public class StyleNode {
    /**
     * dom node
     */
    public Node node;

    /**
     * This hashmap used for saving property key and value of every style node
     */
    public HashMap<String, Value> specified_values;

    public ArrayList<StyleNode> children;

    public StyleNode(Node node, HashMap<String, Value> specified_values, ArrayList<StyleNode> children) {
        this.node = node;
        this.specified_values = specified_values;
        this.children = children;
    }

    public Value value(String name) {
        return specified_values.get(name);
    }

    public Value lookup(String name, String fallback_name, Value defaultValue) {
        if(specified_values.containsKey(name)) {
            return specified_values.get(name);
        } else if(specified_values.containsKey(fallback_name)) {
            return specified_values.get(fallback_name);
        } else {
            return defaultValue;
        }
    }

    public Display display() {
        Value value = value("display");
        if(value instanceof Keyword) {
            Keyword keyword = (Keyword)value;
            String key = keyword.getKeyword();
            if("block".equals(key)) {
                return Display.Block;
            } else if("none".equals(key)) {
                return Display.None;
            } else {
                return Display.Inline;
            }
        } else {
            return Display.Inline;
        }
    }
}
