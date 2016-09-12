package org.xson.common.validate.rule;

import org.xson.common.object.XCO;
import org.xson.common.validate.Checker;

/**
 * float型区间值校验
 */
public class DoubleIntervalChecker implements Checker {

	@Override
	public boolean check(XCO xco, String fieldName, Object value) {
		double val = xco.getDoubleValue(fieldName);
		double[] result = (double[]) value;
		return val >= result[0] && val <= result[1];
	}

	public static Object parseValue(String value) {
		String[] array = value.split(",");
		double[] result = { 0d, 0d };
		result[0] = Double.parseDouble(array[0].trim());
		result[1] = Double.parseDouble(array[1].trim());
		return result;
	}
}
