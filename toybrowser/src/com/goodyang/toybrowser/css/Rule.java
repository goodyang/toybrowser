package com.goodyang.toybrowser.css;

import java.util.ArrayList;

/**
 * Created by Saga on 2017/7/5.
 */
public class Rule {
	private ArrayList<Selector> selectors;
	private ArrayList<Declaration> declarations;
	
	public Rule(ArrayList<Selector> selectors, ArrayList<Declaration> declarations) {
		this.selectors = selectors;
		this.declarations = declarations;
	}
	
	public ArrayList<Declaration> getDeclarations() {
		return declarations;
	}
	
	public ArrayList<Selector> getSelectors() {
		return selectors;
	}
}
