package com.goodyang.toybrowser.util;

import com.goodyang.toybrowser.css.Length;
import com.goodyang.toybrowser.css.Value;

/**
 * Created by Administrator on 2017/7/6.
 */
public class Utils {
    public static float sumLength(Value... args) {
        float sum = 0.0f;
        for(Value value : args) {
            if(value instanceof Length) {
                sum += ((Length)value).getLength();
            }
        }
        return sum;
    }
}
