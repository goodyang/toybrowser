package com.goodyang.toybrowser.css;

import java.util.ArrayList;

/**
 * Created by Saga on 2017/7/5.
 */
public class CSSParser {
	private int pos;
	private String input;
	
	public CSSParser(int pos, String input) {
		this.pos = pos;
		this.input = input;
	}
	
	public StyleSheet parse() {
		return new StyleSheet(parse_rules());
	}
	
	private ArrayList<Rule> parse_rules() {
	
	}
	
	private Rule parse_rule() {
	
	}
	
	private ArrayList<Selector> parse_selectors() {
	
	}
	
	private SimpleSelector parse_simple_selector() {
	
	}
	
	private ArrayList<Declaration> parse_declarations() {
	
	}
	
	private Declaration parse_declaration() {
	
	}
	
	private Value parse_value() {
	
	}
	
	private Value parse_length() {
	
	}
	
	private float parse_float() {
	
	}
	
	private Unit parse_unit() {
	
	}
	
	private Value parse_color() {
	
	}
}
