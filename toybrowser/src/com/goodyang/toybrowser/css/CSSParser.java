package com.goodyang.toybrowser.css;

import com.goodyang.toybrowser.util.Validate;

import java.util.ArrayList;
import java.util.Collections;

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
		ArrayList<Rule> rules = new ArrayList<>();
		while (true) {
			consume_whitespace();
			if(eof()) {
				break;
			}
			rules.add(parse_rule());
		}
		return rules;
	}
	
	private Rule parse_rule() {
		return new Rule(parse_selectors(), parse_declarations());
	}
	
	private ArrayList<Selector> parse_selectors() {
		ArrayList<Selector> selectors = new ArrayList<>();
		while (true) {
			selectors.add(parse_simple_selector());
			consume_whitespace();
			char ch = next_char();
			if(ch == ',') {
				consume_char();
				consume_whitespace();
			} else if (ch == '{') {
				break;
			} else {
				throw new RuntimeException("unexpected character "+ch +" in selector list");
			}
		}

		Collections.sort(selectors);
		return selectors;
	}
	
	private SimpleSelector parse_simple_selector() {
		SimpleSelector selector = new SimpleSelector("", "", new ArrayList<>());
		while(!eof()) {
			char ch = 	next_char();
			if(ch == '#') {
				consume_char();
				selector.setId(parse_identifier());
			} else if(ch == '.') {
				consume_char();
				selector.getCls().add(parse_identifier());
			} else if(ch == '*') {
				consume_char();
			} else if(valid_identifier_char(ch)) {
				selector.setTag_name(parse_identifier());
			} else {
				break;
			}
		}
		return selector;
	}
	
	private ArrayList<Declaration> parse_declarations() {
		ArrayList<Declaration> declarations = new ArrayList<>();
		if(consume_char() != '{') {
			throw new RuntimeException(" '{' not found");
		}
		while (true) {
			consume_whitespace();
			if(next_char() == '}') {
				consume_char();
				break;
			}
			declarations.add(parse_declaration());
		}

		return declarations;
	}
	
	private Declaration parse_declaration() {
		String property_name = parse_identifier();
		consume_whitespace();
		if(consume_char() != ':') {
			throw new RuntimeException(" ':' not found");
		}
		consume_whitespace();
		Value value = parse_value();
		consume_whitespace();
		if(consume_char() != ';') {
			throw new RuntimeException(" ';' not found");
		}
		return new Declaration(property_name, value);
	}
	
	private Value parse_value() {
		char ch = next_char();
		if(Character.isDigit(ch)) {
			return parse_length();
		} else if(ch == '#') {
			return parse_color();
		} else {
			return new Keyword(parse_identifier());
		}
	}
	
	private Value parse_length() {
		return new Length(parse_float(), parse_unit());
	}
	
	private float parse_float() {
		String val = consume_while(new Validate() {
			@Override
			public boolean valide(char ch) {
				if(Character.isDigit(ch) || ch == '.') {
					return true;
				}
				return false;
			}
		});
		return Float.valueOf(val);
	}
	
	private Unit parse_unit() {
		String val = parse_identifier().toLowerCase();
		if("px".equals(val)) {
			return Unit.Px;
		}else{
			throw new RuntimeException("unrecgnized unit");
		}
	}
	
	private Value parse_color() {
		if(consume_char() != '#') {
			throw new RuntimeException("'#' not found");
		}
		return new ColorValue(new Color(parse_hex_pair(), parse_hex_pair(), parse_hex_pair(), 255));
	}

	private int parse_hex_pair() {
		String val = input.substring(pos, pos+2);
		pos += 2;
		return Integer.valueOf(val);
	}

	private String parse_identifier() {
		return consume_while(new Validate() {
			@Override
			public boolean valide(char ch) {
				return valid_identifier_char(ch);
			}
		});

	}

	private boolean valid_identifier_char(char ch) {
		if(Character.isDigit(ch) || Character.isAlphabetic(ch) || ch == '-' || ch == '_') {
			return true;
		}
		return false;
	}

	private void consume_whitespace() {
		consume_while(new Validate() {
			@Override
			public boolean valide(char ch) {
				if(ch == ' ') {
					return true;
				}else{
					return false;
				}
			}
		});
	}

	private String consume_while(Validate validate) {
		StringBuffer buffer = new StringBuffer();
		if(validate.valide(next_char())) {
			buffer.append(consume_char());
		}
		return buffer.toString();
	}

	private char next_char() {
		return input.charAt(pos+1);
	}

	private char consume_char() {
		char ch = input.charAt(pos+1);
		pos += 1;
		return ch;
	}

	private boolean eof() {
		return pos >= input.length();
	}
}
