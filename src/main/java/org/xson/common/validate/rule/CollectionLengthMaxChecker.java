package org.xson.common.validate.rule;

import java.util.List;

import org.xson.common.object.XCO;
import org.xson.common.validate.Checker;

/**
 * List size 最大值
 */
public class CollectionLengthMaxChecker implements Checker {

	@Override
	public boolean check(XCO xco, String fieldName, Object value) {
		List<?> list = (List<?>) xco.getObjectValue(fieldName);
		int val = list.size();
		int max = ((Integer) value).intValue();
		return max >= val;
	}

	public static Object parseValue(String value) {
		return Integer.parseInt(value);
	}
}
