package org.xson.common.validate.rule;

import org.xson.common.object.XCO;
import org.xson.common.validate.Checker;

/**
 * Date匹配校验校验
 */
public class DateMatchChecker implements Checker {

	@Override
	public boolean check(XCO xco, String fieldName, Object value) {
		return false;
	}

	public static Object parseValue(String value) {
		return null;
	}

}
