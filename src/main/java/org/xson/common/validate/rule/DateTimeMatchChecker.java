package org.xson.common.validate.rule;

import org.xson.common.object.XCO;
import org.xson.common.validate.Checker;

/**
 * DateTime匹配校验校验
 */
public class DateTimeMatchChecker implements Checker {

	@Override
	public boolean check(XCO xco, String fieldName, Object value) {
		return false;
	}

	public static Object parseValue(String value) {
		return null;
	}

}
