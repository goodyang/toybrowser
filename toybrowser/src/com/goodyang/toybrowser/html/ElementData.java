package com.goodyang.toybrowser.html;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Saga on 2017/7/4.
 */
public class ElementData {
	public String tag_name;
	public HashMap<String, String> attributes;
	
	public ElementData(String tag_name, HashMap<String, String> attributes) {
		this.tag_name = tag_name;
		this.attributes = attributes;
	}
	
	public String getId() {
		return this.attributes.get("id");
	}
	
	public ArrayList<String> getCls() {
		String str = this.attributes.get("class");
		ArrayList<String> ret = new ArrayList<>();
		if(str != null) {
			String[] arr = str.split(" ");
			for(String cls: arr) {
				ret.add(cls);
			}
		}
		return ret;
	}
}
