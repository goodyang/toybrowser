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
 */
public class StyleNode {
    public Node node;
    public HashMap<String, Value> specified_values;
    public ArrayList<StyleNode> children;

    public StyleNode(Node node, HashMap<String, Value> specified_values, ArrayList<StyleNode> children) {
        this.node = node;
        this.specified_values =specified_values;
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

    public StyleNode style_tree(Node root, Stylesheet stylesheet) {

        return new StyleNode(root,
                root.node_type instanceof Element
                        ? getSpecified_values(((Element) root.node_type).elementData, stylesheet)
                        : new HashMap<String, Value>(),
                getChildrenStyleNode(root.children, stylesheet));
    }

    private ArrayList<StyleNode> getChildrenStyleNode(ArrayList<Node> nodes, Stylesheet stylesheet) {
        ArrayList<StyleNode> styleNodes = new ArrayList<StyleNode>();
        for(Node node : nodes) {
            styleNodes.add(style_tree(node, stylesheet));
        }
        return styleNodes;
    }

    private HashMap<String, Value> getSpecified_values(ElementData elem, Stylesheet stylesheet) {
        HashMap<String, Value> valueMap = new HashMap<String, Value>();

        ArrayList<MatchedRule> rules = matchingRules(elem, stylesheet);

        Collections.sort(rules);

        for(MatchedRule matchedRule : rules) {
            for(Declaration declaration : matchedRule.rule.getDeclarations()) {
                valueMap.put(declaration.getName(), declaration.getValue());
            }
        }

        return  valueMap;
    }

    private ArrayList<MatchedRule> matchingRules(ElementData elem, Stylesheet stylesheet) {
        ArrayList<MatchedRule> matchedRules = new ArrayList<MatchedRule>();
        for(Rule rule : stylesheet.rules) {
            MatchedRule matchedRule = matchRule(elem, rule);
            if(matchedRule != null) {
                matchedRules.add(matchedRule);
            }
        }
        return matchedRules;
    }

    private MatchedRule matchRule(ElementData elem, Rule rule) {
        for (Selector selector : rule.getSelectors()) {
            if(matches(elem, selector)) {
                return new MatchedRule(selector.specificity(), rule);
            }
        }
        return null;
    }

    private boolean matches(ElementData elem, Selector selector) {
        if(selector instanceof SimpleSelector) {
            return matchesSimpleSelector(elem, (SimpleSelector)selector);
        }
        return false;
    }

    private boolean matchesSimpleSelector(ElementData elem, SimpleSelector selector) {
        if(!selector.getTag_name().equals(elem.tag_name)){
            return false;
        }
        if(!selector.getId().equals(elem.getId())) {
            return false;
        }

        for(String cls: elem.getCls()) {
            for(String cls_s : selector.getCls()) {
                if(cls.equals(cls_s)) {
                    return true;
                }
            }
        }

        return false;
    }

    class MatchedRule implements Comparable<MatchedRule>{
        Specificity specificity;
        Rule rule;

        MatchedRule(Specificity specificity, Rule rule) {
            this.specificity = specificity;
            this.rule = rule;
        }

        public int compareTo(MatchedRule o) {
            return specificity.compareTo(o.specificity);
        }
    }
}
