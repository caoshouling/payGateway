package ins.platform.kit;

import java.util.ArrayList;
import java.util.List;

import ins.framework.utils.Beans;

public class CollectionKit {
	/**
	 * List中的对象复制：复制source到target
	 * @param source 源对象
	 * @param clazzV 目标对象的类
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
    public static <T, V> List<V>  BeansListCopy(List<T> source,Class<V> clazzV) throws InstantiationException, IllegalAccessException{
    	
    	  if(source == null  ){
    		  
    		  return null;
    	  }
    	  List<V>  target = new ArrayList<>();
    	  V v = null;
    	  for(int i= 0 ; i < source.size();i++){
    		  v = clazzV.newInstance();  
    		  Beans.copy().from(source.get(i)).to(v); 
    		  target.add(v);
    	  }
    	  return target;
    	
    }
}
