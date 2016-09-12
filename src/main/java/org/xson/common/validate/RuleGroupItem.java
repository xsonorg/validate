package org.xson.common.validate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.xson.common.object.XCO;

public class RuleGroupItem {

	private String		fieldName;
	private TypeEnum	type;
	private List<Rule>	rules;
	private boolean		require;
	private String		message;
	private Object		defaultValue;
	private String		desc;

	public RuleGroupItem(String fieldName, TypeEnum type, List<Rule> rules, boolean require, String message, String defaultValue, String desc) {
		this.fieldName = fieldName;
		this.type = type;
		this.rules = rules;
		this.require = require;
		this.message = message;
		this.desc = desc;
		parseDefaultValue(defaultValue);
	}

	public boolean check(XCO xco) {
		boolean result = false;
		Object value = xco.getObjectValue(fieldName);
		// 需要做非必填的判断
		if (null == value) {
			if (require) {
				result = false;
			} else {
				if (null != defaultValue) {
					setDefaultValue(xco);
				}
				return true;
			}
		} else {
			for (Rule rule : rules) {
				Checker checker = rule.findChecker(type);
				result = checker.check(xco, this.fieldName, rule.getValue());
				if (!result) {
					break;
				}
			}
		}
		if (!result && Container.onValidateFailedThrowException) {
			throw new XCOValidateException(Container.errorCode, (null != this.message) ? this.message : Container.errorMessage);
		}
		return result;
	}

	private void parseDefaultValue(String value) {
		if (null == value) {
			return;
		}
		if (this.type == TypeEnum.INTEGER) {
			this.defaultValue = Integer.parseInt(value);
		} else if (this.type == TypeEnum.LONG) {
			this.defaultValue = Long.parseLong(value);
		} else if (this.type == TypeEnum.FLOAT) {
			this.defaultValue = Float.parseFloat(value);
		} else if (this.type == TypeEnum.DOUBLE) {
			this.defaultValue = Double.parseDouble(value);
		} else if (this.type == TypeEnum.STRING) {
			this.defaultValue = value;
		} else if (this.type == TypeEnum.BIGINTEGER) {
			this.defaultValue = new BigInteger((String) defaultValue);
		} else if (this.type == TypeEnum.BIGDECIMAL) {
			this.defaultValue = new BigDecimal((String) defaultValue);
		}
	}

	private void setDefaultValue(XCO xco) {
		if (this.type == TypeEnum.INTEGER) {
			xco.setIntegerValue(this.fieldName, (Integer) defaultValue);
		} else if (this.type == TypeEnum.LONG) {
			xco.setLongValue(this.fieldName, (Long) defaultValue);
		} else if (this.type == TypeEnum.FLOAT) {
			xco.setFloatValue(this.fieldName, (Float) defaultValue);
		} else if (this.type == TypeEnum.DOUBLE) {
			xco.setDoubleValue(this.fieldName, (Double) defaultValue);
		} else if (this.type == TypeEnum.STRING) {
			xco.setStringValue(this.fieldName, (String) defaultValue);
		} else if (this.type == TypeEnum.BIGINTEGER) {
			xco.setBigIntegerValue(this.fieldName, (BigInteger) defaultValue);
		} else if (this.type == TypeEnum.BIGDECIMAL) {
			xco.setBigDecimalValue(this.fieldName, (BigDecimal) defaultValue);
		}
	}

	public String getFieldName() {
		return fieldName;
	}

	public TypeEnum getType() {
		return type;
	}

	public String getDesc() {
		return desc;
	}
}
