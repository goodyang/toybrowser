package com.goodyang.toybrowser.style;

import com.goodyang.toybrowser.css.Value;
import com.goodyang.toybrowser.html.Node;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/5.
 */
public class StyleNode {
    public Node node;
    public HashMap<String, Value> specified_values;
    public ArrayList<StyleNode> children;


}
