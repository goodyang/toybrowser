package com.goodyang.toybrowser.css;

/**
 * Created by Saga on 2017/7/5.
 */
public class Length extends Value {
	private float length;
	private Unit unit;
	
	public Length(float length, Unit unit) {
		this.length = length;
		this.unit = unit;
	}
	
	public void setLength(float length) {
		this.length = length;
	}
	
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	public float getLength() {
		return length;
	}
	
	public Unit getUnit() {
		return unit;
	}
	
	@Override
	public float to_px() {
		return length;
	}
}
