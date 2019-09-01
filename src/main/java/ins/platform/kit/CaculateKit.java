package ins.platform.kit;

import java.math.BigDecimal;
import java.util.Map;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;

public final class CaculateKit {

	protected static class Unit {

		protected String unitOne = "0";

		protected String unitTwo = "0";

		Unit(Object unitOne, Object unitTwo) {
			if (unitOne != null && unitTwo != null) {
				this.unitOne = ChgDataKit.prettyNumber(unitOne);
				this.unitTwo = ChgDataKit.prettyNumber(unitTwo);
			} else if (unitTwo != null) {
				this.unitTwo = ChgDataKit.prettyNumber(unitTwo);
			} else if (unitOne != null) {
				this.unitOne = ChgDataKit.prettyNumber(unitOne);
			}
		}

		public BigDecimal unitOneDecimal() {
			return new BigDecimal(unitOne);
		}

		public BigDecimal unitTwoDecimal() {
			return new BigDecimal(unitTwo);
		}
	}
    /**
     * 返回fraction位小数 返回double
     * @param value
     * @param fraction
     * @return
     */
	public static Double round(Double value,int fraction) {
		if(value == null){
			return null;
		}else{
			return  new BigDecimal(value+"").setScale(fraction, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
	}
	public static BigDecimal add(Object a, Object b) {
		Unit unit = new Unit(a, b);
		return unit.unitOneDecimal().add(unit.unitTwoDecimal());
	}

	public static BigDecimal add(Object a, Object b, int fraction) {
		BigDecimal result = add(a, b);
		return result.setScale(fraction, BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal subtract(Object a, Object b) {
		Unit unit = new Unit(a, b);
		return unit.unitOneDecimal().subtract(unit.unitTwoDecimal());
	}

	public static BigDecimal subtract(Object a, Object b, int fraction) {
		BigDecimal result = subtract(a, b);
		return result.setScale(fraction, BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal multiply(Object a, Object b) {
		Unit unit = new Unit(a, b);
		return unit.unitOneDecimal().multiply(unit.unitTwoDecimal());
	}

	public static BigDecimal multiply(Object a, Object b, int fraction) {
		BigDecimal result = multiply(a, b);
		return result.setScale(fraction, BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal divide(Object a, Object b) {
		Unit unit = new Unit(a, b);
		return unit.unitOneDecimal().divide(unit.unitTwoDecimal(),6,BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal divide(Object a, Object b, int fraction) {
		BigDecimal result = divide(a, b);
		return result.setScale(fraction, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * ql runner 为线程安全，构造相关引擎耗时，所以采用单例
	 */
	private final static ExpressRunner runner = new ExpressRunner(true, false);

	/**
	 * 计算表达式引擎
	 * 
	 * @param expressStr
	 *            表达式 (2.0-1.8/100)*1000等 或者 通过上下文注入动态参数值(保费*(1+税率/100))
	 * @param scale
	 *            精度（例如保留小数点后两位则设置为2，保留4位则设置为4）
	 * @return 字符串计算结果
	 */
	public static String caculate(String expressStr, int scale) {
		BigDecimal result = caculateInterl(expressStr, null);
		return result.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 计算表达式引擎
	 * 
	 * @param expressStr
	 *            表达式 (2.0-1.8/100)*1000等 或者 通过上下文注入动态参数值(保费*(1+税率/100))
	 * @param scale
	 *            精度（例如保留小数点后两位则设置为2，保留4位则设置为4）
	 * @param context
	 *            上下文信息 Map{ map.put("保费",2000.0); map.put("税率",17) }
	 * @return 字符串计算结果
	 */
	public static String caculate(String expressStr, int scale, Map<String, Object> context) {
		BigDecimal result = caculateInterl(expressStr, context);
		return result.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 计算表达式引擎
	 * 
	 * @param expressStr
	 *            表达式 (2.0-1.8/100)*1000等 或者 通过上下文注入动态参数值(保费*(1+税率/100))
	 * @param scale
	 *            精度（例如保留小数点后两位则设置为2，保留4位则设置为4）
	 * @param t需要返回的结果类型
	 *            Integer.class、Long.class、Double.class、 Float.class、
	 *            String.class、 BigDecimal.class
	 * @param context
	 *            上下文信息 Map{ map.put("保费",2000.0); map.put("税率",17) }
	 * @return 需要的类型
	 */
	@SuppressWarnings("unchecked")
	public static <T> T caculate(String expressStr, int scale, Class<T> t, Map<String, Object> context) {
		BigDecimal result = caculateInterl(expressStr, context);
		return (T) convert(t, result, scale);
	}

	/**
	 * 计算表达式引擎
	 * 
	 * @param expressStr
	 *            表达式 (2.0-1.8/100)*1000等
	 * @param scale
	 *            精度（例如保留小数点后两位则设置为2，保留4位则设置为4）
	 * @param t需要返回的结果类型
	 *            Integer.class、Long.class、Double.class、 Float.class、
	 *            String.class、 BigDecimal.class
	 * @return 需要的类型
	 */
	public static <T> T caculate(String expressStr, int scale, Class<T> t) {
		return caculate(expressStr, scale, t, null);
	}

	private static Object convert(Class<?> t, BigDecimal result, int scale) {
		if (t.getName().indexOf("java.lang.Integer") != -1) {
			return result.setScale(scale, BigDecimal.ROUND_HALF_UP).intValue();
		} else if (t.getName().indexOf("java.lang.Double") != -1) {
			return result.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		} else if (t.getName().indexOf("java.lang.Float") != -1) {
			return result.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
		} else if (t.getName().indexOf("java.lang.String") != -1) {
			return result.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
		} else if (t.getName().indexOf("java.lang.Long") != -1) {
			return result.setScale(scale, BigDecimal.ROUND_HALF_UP).longValue();
		} else {
			return result;
		}
	}

	private static BigDecimal caculateInterl(String expressStr, Map<String, Object> context) {
		DefaultContext<String, Object> expressContext = null;
		if (context != null && !context.isEmpty()) {
			expressContext = new DefaultContext<String, Object>();
			expressContext.putAll(context);
		}
		try {
			return new BigDecimal(runner.execute(expressStr, expressContext, null, true, false) + "");
		} catch (Exception e) {
			e.printStackTrace();
			return new BigDecimal(0);
		}
	}

	public static void main(String[] args) {
//		long c = System.currentTimeMillis();
//		Map<String, Object> context = new HashMap<String, Object>();
//		context.put("x", 6);
//		context.put("y", 3.0);
		BigDecimal result = CaculateKit.caculate("5*3-4", 6, BigDecimal.class,null);
		System.out.println(result);
		
	}
	
	/**
     * 提供精确的小数位四舍五入处理。
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static BigDecimal roundBigDecimal(double v,int scale)
    {
    	BigDecimal b = new BigDecimal(Double.toString(v));
    	BigDecimal one = new BigDecimal("1");
    	return b.divide(one,scale,BigDecimal.ROUND_HALF_UP);
    }
    /**
     * 直接取Int部分的值
     * @param a
     * @param b
     * @param fraction
     * @return
     */
    public static Integer multiplyReturnInt(Object a, Object b) {
    	if(a == null || b == null){
    		return null;
    	}
		BigDecimal result = multiply(a, b);
		return result.intValue();
	}

}
