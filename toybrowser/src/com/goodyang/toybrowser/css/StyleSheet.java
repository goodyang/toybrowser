package com.goodyang.toybrowser.css;

import javax.swing.text.Style;
import java.util.ArrayList;

/**
 * Created by Saga on 2017/7/5.
 */
public class Stylesheet {
	public ArrayList<Rule> rules;
	
	public Stylesheet(ArrayList<Rule> rules) {
		this.rules = rules;
	}
	
	public ArrayList<Rule> getRules() {
		return rules;
	}
}
