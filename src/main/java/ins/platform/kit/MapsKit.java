package ins.platform.kit;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import ins.platform.common.TableKey;

public class MapsKit {

	

	public static Set<String> paramsSet(String paramJions, String split) {
		Set<String> hashSet = new HashSet<String>();
		String[] params = paramJions.split(split);
		for (String param : params) {
			if (StringUtils.isEmpty(param)) {
				continue;
			}
			hashSet.add(param);
		}
		return hashSet;
	}

	public static Set<Object> paramsSet(Object[] values) {
		Set<Object> hashSet = new HashSet<Object>();
		for (Object param : values) {
			if (ObjectUtils.isEmpty(param)) {
				continue;
			}
			hashSet.add(param);
		}
		return hashSet;
	}

	public static Map<String, Object> paramsMap(Object... values) {
		Map<String, Object> params = new HashMap<String, Object>();
		for (int i = 0; i < values.length; i++) {
			params.put("param" + (i + 1), values[i]);
		}
		return params;
	}

	public static Map<String, Object> paramsMap(Object target, TableKey tableKey) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		int i = 0;
		for (String key : tableKey.keys()) {
			Field field = target.getClass().getDeclaredField(key);
			field.setAccessible(true);
			Object keyValue = field.get(target);
			params.put("param" + (i + 1), keyValue);
			params.put(key, keyValue);
		}
		return params;
	}
	
	/**
	 * 新建一个Map，必须是偶数个参数
	 * 
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> newMap(Object... args) {
		Map<K, V> map = new HashMap<K, V>();
		if (args != null) {
			if (args.length % 2 != 0) {
				throw new IllegalArgumentException(
						"The number of arguments must be an even number");
			}
			for (int i = 0; i < args.length; i += 2) {
				map.put((K) args[i], (V) args[i + 1]);
			}
		}
		return map;
	}
	
	/**
	 * 判断是否为空map 
	 * @param map
	 * @return
	 */
	public static boolean isEmpty(Map map){
		return map==null||map.isEmpty();
	}

}
