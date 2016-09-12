package test.xson.common.validate;

import org.junit.Before;
import org.junit.Test;
import org.xson.common.object.XCO;
import org.xson.common.validate.Container;
import org.xson.common.validate.XCOValidate;

public class ValidateTest {

	@Before
	public void init() throws Exception {
		try {
			String resource = "validate-config.xml";
			Container.init(resource);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	public void test01() {
		XCO xco = new XCO();
		xco.setIntegerValue("age", 100);
		xco.setStringValue("name", "12345678");
		String ruleGroupId = "abc.userInfoChecker";
		boolean result = XCOValidate.validate(ruleGroupId, xco);
		System.out.println(result);
		System.out.println(xco);
	}

}
