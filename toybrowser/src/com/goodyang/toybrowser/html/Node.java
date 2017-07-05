package com.goodyang.toybrowser.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Saga on 2017/7/4.
 */
public class Node {
	public ArrayList<Node> children;
	public NodeType node_type;
	public String text;
	public ElementData elementData;
	
	public Node(String data) {
		children = new ArrayList<>();
		node_type = NodeType.Text;
		text = data;
	}
	
	public Node(String name, HashMap<String, String> attrs, ArrayList<Node> children) {
		this.children = children;
		node_type = NodeType.Element;
		elementData = new ElementData(name, attrs);
	}
}
