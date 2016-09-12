package org.xson.common.validate;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.xson.common.validate.rule.ArrayLengthIntervalChecker;
import org.xson.common.validate.rule.ArrayLengthMaxChecker;
import org.xson.common.validate.rule.ArrayLengthMinChecker;
import org.xson.common.validate.rule.BigDecimalIntervalChecker;
import org.xson.common.validate.rule.BigDecimalMaxChecker;
import org.xson.common.validate.rule.BigDecimalMinChecker;
import org.xson.common.validate.rule.BigIntegerIntervalChecker;
import org.xson.common.validate.rule.BigIntegerMaxChecker;
import org.xson.common.validate.rule.BigIntegerMinChecker;
import org.xson.common.validate.rule.CollectionLengthIntervalChecker;
import org.xson.common.validate.rule.CollectionLengthMaxChecker;
import org.xson.common.validate.rule.CollectionLengthMinChecker;
import org.xson.common.validate.rule.DateMatchChecker;
import org.xson.common.validate.rule.DateTimeMatchChecker;
import org.xson.common.validate.rule.DoubleEnumChecker;
import org.xson.common.validate.rule.DoubleIntervalChecker;
import org.xson.common.validate.rule.DoubleMaxChecker;
import org.xson.common.validate.rule.DoubleMinChecker;
import org.xson.common.validate.rule.FloatEnumChecker;
import org.xson.common.validate.rule.FloatIntervalChecker;
import org.xson.common.validate.rule.FloatMaxChecker;
import org.xson.common.validate.rule.FloatMinChecker;
import org.xson.common.validate.rule.IntegerEnumChecker;
import org.xson.common.validate.rule.IntegerIntervalChecker;
import org.xson.common.validate.rule.IntegerMaxChecker;
import org.xson.common.validate.rule.IntegerMinChecker;
import org.xson.common.validate.rule.LongEnumChecker;
import org.xson.common.validate.rule.LongIntervalChecker;
import org.xson.common.validate.rule.LongMaxChecker;
import org.xson.common.validate.rule.LongMinChecker;
import org.xson.common.validate.rule.StringEnumChecker;
import org.xson.common.validate.rule.StringFilterChecker;
import org.xson.common.validate.rule.StringLengthIntervalChecker;
import org.xson.common.validate.rule.StringLengthMaxChecker;
import org.xson.common.validate.rule.StringLengthMinChecker;
import org.xson.common.validate.rule.StringMatchChecker;
import org.xson.common.validate.rule.StringNoMatchChecker;
import org.xson.common.validate.rule.TimeMatchChecker;
import org.xson.common.validate.util.XMLConfigBuilder;

public class Container {

	private static Logger					logger							= Logger.getLogger(Container.class);

	protected static Map<String, RuleGroup>	ruleGroupsMap					= new HashMap<>();

	protected static Map<String, Checker>	checkerMap						= new HashMap<>();

	private static boolean					initialization					= false;

	// 验证失败抛异常
	protected static boolean				onValidateFailedThrowException	= false;
	// 验证失败的异常码
	protected static int					errorCode						= -1;
	// 验证失败的异常信息
	protected static String					errorMessage					= "数据验证错误";

	public static String					nsSeparator						= ".";

	protected static Checker getChecker(String key) {
		return checkerMap.get(key);
	}

	public static void setRuleGroupsMap(Map<String, RuleGroup> ruleGroupsMap) {
		if (Container.ruleGroupsMap.size() == 0) {
			Container.ruleGroupsMap = ruleGroupsMap;
		}
	}

	public static InputStream getResourceAsStream(ClassLoader loader, String resource) throws IOException {
		if (null == loader) {
			loader = Container.class.getClassLoader();
		}
		InputStream returnValue = loader.getResourceAsStream(resource);
		if (null == returnValue) {
			returnValue = loader.getResourceAsStream("/" + resource);
		}
		if (null != returnValue) {
			return returnValue;
		}
		return null;
	}

	private static String createCheckerKey(String a, String b) {
		return (a + "_" + b).toUpperCase();
	}

	private static void initChecker() {

		checkerMap.put(createCheckerKey("Array", "区间长度"), new ArrayLengthIntervalChecker());
		checkerMap.put(createCheckerKey("Array", "最大长度"), new ArrayLengthMaxChecker());
		checkerMap.put(createCheckerKey("Array", "最小长度"), new ArrayLengthMinChecker());

		checkerMap.put(createCheckerKey("Collection", "区间长度"), new CollectionLengthIntervalChecker());
		checkerMap.put(createCheckerKey("Collection", "最大长度"), new CollectionLengthMaxChecker());
		checkerMap.put(createCheckerKey("Collection", "最小长度"), new CollectionLengthMinChecker());

		checkerMap.put(createCheckerKey("Double", "枚举值"), new DoubleEnumChecker());
		checkerMap.put(createCheckerKey("Double", "区间值"), new DoubleIntervalChecker());
		checkerMap.put(createCheckerKey("Double", "最大值"), new DoubleMaxChecker());
		checkerMap.put(createCheckerKey("Double", "最小值"), new DoubleMinChecker());

		checkerMap.put(createCheckerKey("Float", "枚举值"), new FloatEnumChecker());
		checkerMap.put(createCheckerKey("Float", "区间值"), new FloatIntervalChecker());
		checkerMap.put(createCheckerKey("Float", "最大值"), new FloatMaxChecker());
		checkerMap.put(createCheckerKey("Float", "最小值"), new FloatMinChecker());

		checkerMap.put(createCheckerKey("Int", "枚举值"), new IntegerEnumChecker());
		checkerMap.put(createCheckerKey("Int", "区间值"), new IntegerIntervalChecker());
		checkerMap.put(createCheckerKey("Int", "最大值"), new IntegerMaxChecker());
		checkerMap.put(createCheckerKey("Int", "最小值"), new IntegerMinChecker());

		checkerMap.put(createCheckerKey("Long", "枚举值"), new LongEnumChecker());
		checkerMap.put(createCheckerKey("Long", "区间值"), new LongIntervalChecker());
		checkerMap.put(createCheckerKey("Long", "最大值"), new LongMaxChecker());
		checkerMap.put(createCheckerKey("Long", "最小值"), new LongMinChecker());

		checkerMap.put(createCheckerKey("String", "枚举值"), new StringEnumChecker());
		checkerMap.put(createCheckerKey("String", "过滤"), new StringFilterChecker());
		checkerMap.put(createCheckerKey("String", "区间长度"), new StringLengthIntervalChecker());
		checkerMap.put(createCheckerKey("String", "最大长度"), new StringLengthMaxChecker());
		checkerMap.put(createCheckerKey("String", "最小长度"), new StringLengthMinChecker());
		checkerMap.put(createCheckerKey("String", "匹配"), new StringMatchChecker());
		checkerMap.put(createCheckerKey("String", "不匹配"), new StringNoMatchChecker());

		checkerMap.put(createCheckerKey("BigInteger", "区间值"), new BigIntegerIntervalChecker());
		checkerMap.put(createCheckerKey("BigInteger", "最大值"), new BigIntegerMaxChecker());
		checkerMap.put(createCheckerKey("BigInteger", "最小值"), new BigIntegerMinChecker());

		checkerMap.put(createCheckerKey("BigDecimal", "区间值"), new BigDecimalIntervalChecker());
		checkerMap.put(createCheckerKey("BigDecimal", "最大值"), new BigDecimalMaxChecker());
		checkerMap.put(createCheckerKey("BigDecimal", "最小值"), new BigDecimalMinChecker());

		checkerMap.put(createCheckerKey("Date", "匹配"), new DateMatchChecker());
		checkerMap.put(createCheckerKey("Time", "匹配"), new TimeMatchChecker());
		checkerMap.put(createCheckerKey("DateTime", "匹配"), new DateTimeMatchChecker());

		// System.out.println(checkerMap);
	}

	public static void config(Map<String, String> properties) {
		if (properties.containsKey("errorCode".toUpperCase())) {
			errorCode = Integer.parseInt(properties.get("errorCode".toUpperCase()));
		}
		if (properties.containsKey("errorMessage".toUpperCase())) {
			errorMessage = properties.get("errorMessage".toUpperCase());
		}
		if (properties.containsKey("nsSeparator".toUpperCase())) {
			nsSeparator = properties.get("nsSeparator".toUpperCase());
		}
		if (properties.containsKey("throwException".toUpperCase())) {
			onValidateFailedThrowException = Boolean.parseBoolean(properties.get("throwException".toUpperCase()));
		}
		logger.info("config setting success...");
	}

	public static void init(String resource) throws Exception {
		if (!initialization) {
			logger.info("Start parsing: " + resource);
			InputStream inputStream = getResourceAsStream(null, resource);
			XMLConfigBuilder builder = new XMLConfigBuilder(inputStream);
			builder.parseNode();
			// 初始化checker
			initChecker();
			initialization = true;
			logger.info("xco-validate init success, version: " + Version.getVersion());
		}
	}

}
