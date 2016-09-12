package org.xson.common.validate.rule;

import java.math.BigInteger;

import org.xson.common.object.XCO;
import org.xson.common.validate.Checker;

/**
 * BigInteger型最大值校验
 */
public class BigIntegerMaxChecker implements Checker {

	@Override
	public boolean check(XCO xco, String fieldName, Object value) {
		long max = ((BigInteger) value).longValue();
		long val = xco.getBigIntegerValue(fieldName).longValue();
		return max >= val;
	}

	public static Object parseValue(String value) {
		return new BigInteger(value);
	}
}
