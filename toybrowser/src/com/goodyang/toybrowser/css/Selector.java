package com.goodyang.toybrowser.css;

import java.util.Comparator;

/**
 * Created by Saga on 2017/7/5.
 */
public abstract class Selector implements Comparable<Selector>{
	abstract Specificity specificity();
}
