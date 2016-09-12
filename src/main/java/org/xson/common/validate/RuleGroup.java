package org.xson.common.validate;

import org.xson.common.object.XCO;

import java.util.List;

public class RuleGroup {

	private String				id;
	private List<RuleGroupItem>	items;
	private String				desc;	// 描述

	public RuleGroup(String id, List<RuleGroupItem> items, String desc) {
		this.id = id;
		this.items = items;
		this.desc = desc;
	}

	public String getId() {
		return id;
	}

	protected List<RuleGroupItem> getItems() {
		return this.items;
	}

	public String getDesc() {
		return desc;
	}

	public boolean check(XCO xco) {
		for (RuleGroupItem item : this.items) {
			boolean result = item.check(xco);
			if (!result) {
				return false;
			}
		}
		return true;
	}

}
