package com.goodyang.toybrowser.html;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Saga on 2017/7/4.
 */
public class HtmlParser {
	int pos;
	String input;
	
	public ArrayList<Node> parse_nodes() {
		ArrayList<Node> nodes = new ArrayList<>();
		
		while(true){
			consume_whitespace();
			if(eof() || input.startsWith("</")) {
				break;
			}
			nodes.add(parse_node());
		}
		
		return  nodes;
	}
	
	private Node parse_node() {
		char ch = next_char();
		if(ch == '<') {
			return parse_element();
		} else {
			return parse_text();
		}
	}
	
	private Node parse_element() {
		if(consume_char() != '<') {
			throw new RuntimeException("'<' not found");
		}
		String tag_name = parse_tag_name();
		HashMap<String, String> atts = parse_attributes();
		if(consume_char() != '>') {
			throw new RuntimeException("'>' not found");
		}
		
		ArrayList<Node> children = parse_nodes();
		
		if(consume_char() != '<') {
			throw new RuntimeException("'<' not found");
		}
		if(consume_char() != '/') {
			throw new RuntimeException("'/' not found");
		}
		if(!parse_tag_name().equals(tag_name)) {
			throw new RuntimeException("end tagname is not same as begin tagname");
		}
		if(consume_char() != '>') {
			throw new RuntimeException("'>' not found");
		}
		return new Node(tag_name, atts, children);
	}
	
	private String parse_tag_name() {
		return consume_while(new Validate() {
			@Override
			public boolean valide(char ch) {
				if(Character.isAlphabetic(ch) || Character.isDigit(ch)) {
					return true;
				}else{
					return false;
				}
			}
		});
	}
	
	private HashMap<String, String> parse_attributes() {
		HashMap<String, String> attributes = new HashMap<>();
		while(true) {
			consume_whitespace();
			if(next_char() == '>') {
				break;
			}
			parse_attr(attributes);
		}
		return attributes;
	}
	
	private void parse_attr(HashMap<String, String> hashMap) {
		String name = parse_tag_name();
		if(consume_char() != '=') {
			throw new RuntimeException("'=' not found");
		}
		String value = parse_attr_value();
		hashMap.put(name, value);
	}
	
	private String parse_attr_value() {
		final char open_quote = consume_char();
		if(open_quote != '\"' || open_quote !='\'') {
			throw new RuntimeException("open quote not found");
		}
		String value = consume_while(new Validate() {
			@Override
			public boolean valide(char ch) {
				if(ch != open_quote) {
					return true;
				}else{
					return false;
				}
			}
		});
		if(consume_char() != open_quote) {
			throw new RuntimeException("close quote not found");
		}
		return value;
	}
	
	private Node parse_text() {
		String text = consume_while(new Validate() {
			@Override
			public boolean valide(char ch) {
				if(ch != '<') {
					return true;
				}else{
					return false;
				}
			}
		});
		return new Node(text);
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
