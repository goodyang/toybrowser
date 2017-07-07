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
	
	public Node(String data) {
		children = new ArrayList<>();
		node_type = new Text(data);
	}
	
	public Node(String name, HashMap<String, String> attrs, ArrayList<Node> children) {
		this.children = children;
		node_type = new Element( new ElementData(name, attrs));
	}

	public Node(ArrayList<Node> children, NodeType node_type) {
		this.children = children;
		this.node_type = node_type;
	}
}
