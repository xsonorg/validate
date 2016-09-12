package org.xson.common.validate.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.xson.common.validate.Checker;
import org.xson.common.validate.Container;
import org.xson.common.validate.RuleDef;
import org.xson.common.validate.RuleGroup;

public class XMLConfigBuilder {

	private Logger					logger				= Logger.getLogger(XMLConfigBuilder.class);
	private XPathParser				parser				= null;
	private XmlNodeWrapper			root				= null;
	private Map<String, Checker>	customCheckerMap	= new HashMap<String, Checker>();

	public XMLConfigBuilder(InputStream inputStream) {
		this.parser = new XPathParser(inputStream);
		this.root = this.parser.evalNode("/validate");
	}

	public void parseNode() {
		try {
			buildConfigNodes(this.root.evalNodes("config-property"));
			buildCheckerNodes(this.root.evalNodes("checker"));
			buildPluginNodes(this.root.evalNodes("plugin"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void buildConfigNodes(List<XmlNodeWrapper> contexts) throws Exception {
		// <config-property name="A" value="B" />
		Map<String, String> configMap = new HashMap<String, String>();
		for (XmlNodeWrapper context : contexts) {
			String name = StringUtils.trim(context.getStringAttribute("name"));
			String value = StringUtils.trim(context.getStringAttribute("value"));
			if (null == name || null == value) {
				throw new RuntimeException("<config-property> missing name or value");
			}
			configMap.put(name.toUpperCase(), value);
		}
		if (configMap.size() > 0) {
			Container.config(configMap);
		}
	}

	private void buildCheckerNodes(List<XmlNodeWrapper> contexts) throws Exception {
		for (XmlNodeWrapper context : contexts) {
			String id = StringUtils.trim(context.getStringAttribute("id"));
			String className = StringUtils.trim(context.getStringAttribute("class"));
			if (customCheckerMap.containsKey(id)) {
				throw new RuntimeException("Duplicate <checker> node");
			}
			Class<?> clazz = Class.forName(className);
			if (!clazz.isAssignableFrom(Checker.class)) {
				throw new RuntimeException("Custom checker must implement the interface Checker: " + className);
			}
			customCheckerMap.put(id, (Checker) clazz.newInstance());
			logger.info("Add <checker> :" + className);
		}
	}

	private void buildPluginNodes(List<XmlNodeWrapper> contexts) throws Exception {
		// <plugin resource="xxx.xml" />
		List<String> resourceList = new ArrayList<String>();
		for (XmlNodeWrapper context : contexts) {
			String resource = StringUtils.trim(context.getStringAttribute("resource"));
			if (null == resource) {
				throw new RuntimeException("<plugin> missing resource");
			}
			resourceList.add(resource);
		}

		XMLRuleBuilder[] builders = new XMLRuleBuilder[resourceList.size()];
		Map<String, RuleDef> globleDefMap = new HashMap<String, RuleDef>();
		Map<String, RuleGroup> ruleGroupMap = new HashMap<String, RuleGroup>();
		int i = 0;
		for (String resource : resourceList) {
			logger.info("Start parsing(step 1): " + resource);
			InputStream inputStream = Container.getResourceAsStream(null, resource);
			builders[i] = new XMLRuleBuilder(inputStream, globleDefMap, ruleGroupMap, customCheckerMap);
			builders[i].parseDefNode();
			i++;
		}
		i = 0;
		for (String resource : resourceList) {
			logger.info("Start parsing(step 2): " + resource);
			builders[i].parseRuleGroupNode();
			builders[i].clear();
			i++;
		}

		globleDefMap.clear();
		globleDefMap = null;
		Container.setRuleGroupsMap(ruleGroupMap);
		ruleGroupMap = null;
		customCheckerMap.clear();
		customCheckerMap = null;
	}
}
