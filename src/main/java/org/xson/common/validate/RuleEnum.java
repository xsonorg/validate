package org.xson.common.validate;

public enum RuleEnum {

	ENUM("枚举值"),
	//
	INTERVAL("区间值"),
	//
	FILTER("过滤"),
	//
	MAX_LENGTH("最大长度"),
	//
	MIN_LENGTH("最小长度"), 
	//
	INTERVAL_LENGTH("区间长度"),
	//
	MATCH("匹配"),
	//
	MISMATCH("不匹配"),
	//
	MIN("最小值"),
	//
	MAX("最大值");

	private String	value;

	RuleEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static RuleEnum getEnum(String value) {
		RuleEnum[] all = values();
		for (RuleEnum ruleEnum : all) {
			if (ruleEnum.value.equalsIgnoreCase(value)) {
				return ruleEnum;
			}
		}
		return null;
	}
}
