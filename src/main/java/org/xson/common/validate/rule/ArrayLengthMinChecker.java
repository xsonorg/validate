package org.xson.common.validate.rule;

import java.lang.reflect.Array;

import org.xson.common.object.XCO;
import org.xson.common.validate.Checker;

/**
 * List size 最小值
 */
public class ArrayLengthMinChecker implements Checker {

	@Override
	public boolean check(XCO xco, String fieldName, Object value) {
		Object arrayObject = xco.getObjectValue(fieldName);
		int val = Array.getLength(arrayObject);
		int min = ((Integer) value).intValue();
		return min <= val;
	}

	public static Object parseValue(String value) {
		return Integer.parseInt(value);
	}
}
