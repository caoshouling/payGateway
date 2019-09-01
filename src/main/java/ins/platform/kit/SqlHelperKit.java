package ins.platform.kit;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SqlHelperKit {

	public static boolean isLikePattern(Object value) {
		if (value == null || value.toString().isEmpty()) {
			return false;
		}
		if (value instanceof Date) {
			return false;
		}

		if (value.toString().startsWith("%")) {
			return false;
		}

		if (value.toString().endsWith("%")) {
			return true;
		}

		return false;
	}
	
	public static boolean isLikePatternStar(Object value) {
		if (value == null || value.toString().isEmpty()) {
			return false;
		}
		if (value instanceof Date) {
			return false;
		}

		if (value.toString().startsWith("*")) {
			return false;
		}

		if (value.toString().endsWith("*")) {
			return true;
		}

		return false;
	}
	
	public static boolean isEqualPattern(Object value) {
		return value == null ? false : !value.toString().isEmpty();
	}
	
	
	
	public static String changeLikeStar(Object value){
		if (value == null || value.toString().isEmpty()) {
			return null;
		}
		if (!value.toString().startsWith("*") && value.toString().endsWith("*")) {
			return value.toString().replace("*", "%");
		}
		return value.toString();
	}
	
	public static Boolean isStartWith(Object value,String str){
		if (value == null || value.toString().isEmpty()) {
			return false;
		}
		if (str == null || str.toString().isEmpty()) {
			return false;
		}
		if (value.toString().startsWith(str)) {
			return true;
		}
		return false;
	}
	/**
	 * 分割集合，处理 in 后面的集合中记录数不能大于1000条
	 * @param items
	 * @return
	 */
	public static <T> List<Set<T>> split(Iterable<T> items){
		List<Set<T>> resultList = new ArrayList<Set<T>>();
		if (items==null) {
			return resultList;
		}
		Set<T> tempSet = new HashSet<T>();
		for(T item : items){
			if (item!=null) {
				tempSet.add(item);
			}
		}
		
		Set<T> result = new HashSet<T>();
		resultList.add(result);
		
		for (T item : tempSet) {
			if(result.size() == 1000 ) {
				result = new HashSet<T>();
				resultList.add(result);
			}
			
			if (item!=null) {
				result.add(item);
			}
			
		}
		
		return resultList;
		
		
	}
}
