package org.xson.common.validate;

import org.xson.common.object.XCO;

/**
 * XCO数据验证
 */
public class XCOValidate {

	/**
	 * XCO参数校验入库
	 * 
	 * @param groupName
	 *            校验名称
	 * @param xco
	 *            入参
	 * @return 校验结果
	 */
	public static boolean validate(String ruleGroupId, XCO xco) {
		RuleGroup group = Container.ruleGroupsMap.get(ruleGroupId);
		if (group == null) {
			throw new RuntimeException("校验模板不存在: " + ruleGroupId);
		}
		return group.check(xco);
	}
}
