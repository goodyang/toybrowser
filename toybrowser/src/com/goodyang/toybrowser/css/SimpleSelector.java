package com.goodyang.toybrowser.css;

import java.util.ArrayList;

/**
 * Created by Saga on 2017/7/5.
 */
public class SimpleSelector extends Selector {
	private String tag_name;
	private String id;
	private ArrayList<String> cls;
	
	public SimpleSelector(String tag_name, String id, ArrayList<String> cls) {
		this.tag_name = tag_name;
		this.id = id;
		this.cls = cls;
	}
	
	@Override
	Specificity specificity() {
		int a = tag_name!=null?tag_name.length():0;
		int b = id!=null?id.length():0;
		int c = cls!=null?cls.size():0;
		return new Specificity(a, b, c);
	}
}
