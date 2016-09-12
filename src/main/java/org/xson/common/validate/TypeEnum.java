package org.xson.common.validate;

public enum TypeEnum {
	//
	INTEGER("int"),
	//
	LONG("long"),
	//
	FLOAT("float"),
	//
	DOUBLE("double"),
	//
	STRING("string"),
	//
	DATE("date"),
	//
	TIME("time"),
	//
	DATETIME("dateTime"),
	//
	BIGINTEGER("bigInteger"),
	//
	BIGDECIMAL("bigDecimal"),
	//
	ARRAY("array"),
	//
	COLLECTION("collection");

	private String	value;

	TypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static TypeEnum getEnum(String value) {
		TypeEnum[] all = values();
		for (TypeEnum typeEnum : all) {
			if (typeEnum.value.equalsIgnoreCase(value)) {
				return typeEnum;
			}
		}
		return null;
	}
}