package com.goodyang.toybrowser.css;

/**
 * Created by Saga on 2017/7/5.
 */
public class Specificity implements Comparable<Specificity>{
	public int a;
	public int b;
	public int c;
	
	public Specificity(int a, int b, int c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	@Override
	public int compareTo(Specificity o) {
		if(a != o.a) {
			return a - o.a;
		} else if(b != o.b) {
			return b - o.b;
		} else if(c != o.c) {
			return c - o.c;
		}else {
			return 0;
		}
	}
}
