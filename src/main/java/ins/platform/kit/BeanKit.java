package ins.platform.kit;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;

/**
 * 
 * 
 * apache的 BeanUtils: 性能最差,不建议使用 spring的 BeanUtils: spring支持忽略某些属性不进行映射 cglib的
 * BeanCopier:基于ASM 最快 Dozer :在定制化的属性映射方面做得比较好的有
 * DozerDozer支持简单属性映射、复杂类型映射、双向映射、隐式映射以及递归映射。可使用xml或者注解进行映射的配置，支持自动类型转换，使用方便。
 * 但Dozer底层是使用reflect包下 Field类的 set(Object obj, Object value)，性能低下 Orika：
 * 底层采用了javassist类库生成Bean映射的字节码 cglib BeanCopier > Orika> spring > Dozer >
 * apache
 * 
 * 总结：当属性名和属性类型完全相同时，选择cglib BeanCopier 和 Spring BeanUtils 均可。 极端性能要求，当然是用cglib
 * BeanCopier。 当存在属性名称不同或者属性名称相同但属性类型不同的情况时，选择Orika
 * 
 * 这里选用cglib BeanCopier 和 Orika
 * 
 *  注意：BeanCopier属于浅拷贝（属性为引用时只拷贝引用，这样修改时会相互影响）
 *      上面的拷贝，只有Orika属于深拷贝。
 *  Orika 非常强大，不一样的字段都能映射。List加不一样的字段也能映射。
 *  
 *  List<Student> students = mapperFactory.getMapperFacade().mapAsList(personList, Student.class);
 * 
 * 
 * @author CSL
 *
 */
public final class BeanKit {

	/**
	 * The map to store {@link BeanCopier} of source type and class type for
	 * conversion.
	 */
	private static final Map<String, BeanCopier> BEAN_COPIER_MAP = new ConcurrentHashMap<>();
      
	public final static MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
	
	
	
	/**
	 * 深度拷贝：
	 * 对于基本类型，不同类型之间能转换。
	 * 对于为空的，也会拷贝null。
	 * @param source
	 * @param target
	 */
	public static void deepCopy(Object source, Object target) {
		
		deepCopy(source,target,null);
	}
	/**
	 * 深度拷贝
	 * 对于基本类型，不同类型之间能转换。
	 * 对于为空的，也会拷贝null。
	 *  
	 * @param source
	 * @param clazz
	 * @return
	 */
	public static <T> T deepCopy(Object source, Class<T> clazz) {
		
		return deepCopy(source, clazz,null);
	}
	
	/**
	 * 深度拷贝：
	 * 对于基本类型，不同类型之间能转换。
	 * 对于为空的，也会拷贝null。
	 * 注意：如果目标对象的属性在原对象不存在，也没有配置映射关系，那么目标对象的属性值会保留。
	 * 扩张：
	 *    这里的fileld默认是能相互映射的。如果要单向，可以使用
	 *    .fieldAToB("name", "fullName")
	 *   或 .fieldBToA("name", "fullName")
	 * @param source
	 * @param target
	 * @param filedMapping 类型不同时的映射
	 */
	public static void deepCopy(Object source, Object target,Map<String,String> filedMapping ) {
		
		ClassMapBuilder<? extends Object, ? extends Object> builder = 
				mapperFactory.classMap(source.getClass(), target.getClass());
		if(filedMapping != null){
			filedMapping.forEach((key ,value) ->{
				builder.field(key, value);
			});
		}
		builder.byDefault().register();
		
		MapperFacade mapper = mapperFactory.getMapperFacade();
		mapper.map(source, target);
	}
	
	
	/**
	 * 深度拷贝
	 * 对于基本类型，不同类型之间能转换。
	 * 对于为空的，也会拷贝null。
	 * @param source
	 * @param clazz
	 * @return
	 */
	public static <T> T deepCopy(Object source, Class<T> clazz,Map<String,String> filedMapping) {
		
		ClassMapBuilder<? extends Object, ? extends Object> builder 
		   = mapperFactory.classMap(source.getClass(), clazz);
		
		if(filedMapping != null){
			filedMapping.forEach((key ,value) ->{
				builder.field(key, value);
			});
		}
		builder.byDefault().register();
		MapperFacade mapper = mapperFactory.getMapperFacade();
		return mapper.map(source, clazz);
	}
	
	/**
	 * 深度拷贝List
	 * 
	 * @param sourceList
	 * @param sourceClazz  获取不到参数V的实参类型，所以只能多定义一个参数
	 * @param clazz
	 * @param filedMapping
	 * @return
	 */
	public static <T> List<T> deepCopyList(List<T> sourceList,Class<T> sourceClazz) {
		
		return  deepCopyList(sourceList, sourceClazz, sourceClazz, null);
	}
	/**
	 * 深度拷贝List
	 * 
	 * @param sourceList
	 * @param sourceClazz  获取不到参数V的实参类型，所以只能多定义一个参数
	 * @param clazz
	 * @param filedMapping
	 * @return
	 */
	public static <V,T> List<T> deepCopyList(List<V> sourceList,Class<V> sourceClazz, Class<T> resultClazz ) {
		
		return deepCopyList(sourceList, sourceClazz, resultClazz, null);
	}
	/**
	 * 深度拷贝List
	 * 
	 * @param sourceList
	 * @param sourceClazz  获取不到参数V的实参类型，所以只能多定义一个参数
	 * @param clazz
	 * @param filedMapping
	 * @return
	 */
	public static <V,T> List<T> deepCopyList(List<V> sourceList,Class<V> sourceClazz, Class<T> resultClazz,Map<String,String> filedMapping) {
		
		ClassMapBuilder<? extends Object, ? extends Object> builder =  mapperFactory.classMap(sourceClazz, resultClazz);
		
		if(filedMapping != null){
			filedMapping.forEach((key ,value) ->{
				builder.field(key, value);
			});
		}
		builder.byDefault().register();
		MapperFacade mapper = mapperFactory.getMapperFacade();
		return mapper.mapAsList(sourceList, resultClazz);
	}
	/**
	 * 拷贝对象： 浅层次的拷贝 
	 * （1）如果是基本类型数据，那么拷贝数据
	 * （2）如果属性是对象或者数组等引用类型，那么拷贝的是引用。即：拷贝后，复制对象修改这种属性会影响原对象；同样原对象修改也会影响复制对象。
	 * 注意点：属性类型不同拷贝不了，这时候可以用转换器。见测试用例。
	 * @param source源对象
	 * @param target 目标对象
	 */
	public static void copy(Object source, Object target) {

		copy(source, target, null);
	}
    /**
     * 带转换器
     * @param source
     * @param target
     * @param converter
     */
	public static void copy(Object source, Object target, Converter converter) {
		Objects.requireNonNull(source, "source must not be null");
		Objects.requireNonNull(target, "target must not be null");

		BeanCopier beanCopier = getBeanCopier(source.getClass(), target.getClass());
		if (converter == null) {
			beanCopier.copy(source, target, null);
		} else {
			beanCopier.copy(source, target, converter);
		}

	}

	/**
	 * /**根据原对象生成一个对象： 同样需要注意浅层次复制的问题
	 * 
	 * @param source
	 * @param clazz
	 * @return
	 */
	public static <T> T copy(Object source, Class<T> clazz) {

		return copy(source, clazz, null);
	}

	/**
	 * 根据原对象生成一个对象： 同样需要注意浅层次复制的问题
	 * 
	 * @param source
	 *            源对象
	 * @param clazz
	 *            要转换的类
	 * @return
	 */
	public static <T> T copy(Object source, Class<T> clazz, Converter converter) {
		// Initialize a new instance of the target type.
		T result;
		try {
			result = clazz.newInstance();
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException("fail to create instance of type" + clazz.getCanonicalName(), e);
		}

		copy(source, result, converter);
		return result;
	}

	private static BeanCopier getBeanCopier(Class<?> source, Class<?> target) {
		String key = generateKey(source, target);
		return BEAN_COPIER_MAP.computeIfAbsent(key, x -> BeanCopier.create(source, target, false));
	}

	
	private static String generateKey(Class<?> source, Class<?> target) {
		return source.getCanonicalName().concat(target.getCanonicalName());
	}

	public static void main(String[] args) {

	}

}
