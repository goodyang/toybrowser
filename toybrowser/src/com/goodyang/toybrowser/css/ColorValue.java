package com.goodyang.toybrowser.css;

/**
 * Created by Saga on 2017/7/5.
 */
public class ColorValue extends Value{
	public Color color;
	
	public ColorValue(Color color) {
		this.color = color;
	}
	
	@Override
	public float to_px() {
		return 0;
	}
}
