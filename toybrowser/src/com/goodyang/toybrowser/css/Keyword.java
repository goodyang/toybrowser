package com.goodyang.toybrowser.css;

/**
 * Created by Saga on 2017/7/5.
 */
public class Keyword extends Value {
	private String keyword;
	
	public Keyword(String keyword) {
		this.keyword = keyword;
	}
	
	public String getKeyword() {
		return keyword;
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	@Override
	public float to_px() {
		return 0.0f;
	}
}
