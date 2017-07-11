package com.goodyang.toybrowser.style;

import com.goodyang.toybrowser.css.*;
import com.goodyang.toybrowser.html.Element;
import com.goodyang.toybrowser.html.ElementData;
import com.goodyang.toybrowser.html.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Saga on 2017/7/10.
 */
public class StyleTreeBuilder {
    public static StyleNode style_tree(Node root, Stylesheet stylesheet) {

        return new StyleNode(root,
                root.node_type instanceof Element
                        ? getSpecified_values(((Element) root.node_type).elementData, stylesheet)
                        : new HashMap<String, Value>(),
                getChildrenStyleNode(root.children, stylesheet));
    }

    private static ArrayList<StyleNode> getChildrenStyleNode(ArrayList<Node> nodes, Stylesheet stylesheet) {
        ArrayList<StyleNode> styleNodes = new ArrayList<StyleNode>();
        for(Node node : nodes) {
            styleNodes.add(style_tree(node, stylesheet));
        }
        return styleNodes;
    }

    private static HashMap<String, Value> getSpecified_values(ElementData elem, Stylesheet stylesheet) {
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

    private static ArrayList<MatchedRule> matchingRules(ElementData elem, Stylesheet stylesheet) {
        ArrayList<MatchedRule> matchedRules = new ArrayList<MatchedRule>();
        for(Rule rule : stylesheet.rules) {
            MatchedRule matchedRule = matchRule(elem, rule);
            if(matchedRule != null) {
                matchedRules.add(matchedRule);
            }
        }
        return matchedRules;
    }

    private static MatchedRule matchRule(ElementData elem, Rule rule) {
        for (Selector selector : rule.getSelectors()) {
            if(matches(elem, selector)) {
                return new MatchedRule(selector.specificity(), rule);
            }
        }
        return null;
    }

    private static boolean matches(ElementData elem, Selector selector) {
        if(selector instanceof SimpleSelector) {
            return matchesSimpleSelector(elem, (SimpleSelector)selector);
        }
        return false;
    }

    private static boolean matchesSimpleSelector(ElementData elem, SimpleSelector selector) {
        if(selector.getTag_name().length()!=0 && !selector.getTag_name().equals(elem.tag_name)){
            return false;
        }
        if(selector.getId().length()!=0 && !selector.getId().equals(elem.getId())) {
            return false;
        }

        if(selector.getCls()!=null && selector.getCls().size()>0) {
            for (String cls : elem.getCls()) {
                for (String cls_s : selector.getCls()) {
                    if (cls.equals(cls_s)) {
                        return true;
                    }
                }
            }
            return false;
        }

        return true;
    }

    static class MatchedRule implements Comparable<MatchedRule>{
        Specificity specificity;
        Rule rule;

        public MatchedRule(Specificity specificity, Rule rule) {
            this.specificity = specificity;
            this.rule = rule;
        }

        public int compareTo(MatchedRule o) {
            return specificity.compareTo(o.specificity);
        }
    }
}
