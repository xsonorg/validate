package org.xson.common.validate;

import org.xson.common.object.XCO;

public interface Checker {

	boolean check(XCO xco, String fieldName, Object value);

}
