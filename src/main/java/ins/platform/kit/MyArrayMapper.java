package ins.platform.kit;

import java.util.Arrays;
import java.util.Collection;

import com.thoughtworks.xstream.mapper.ArrayMapper;
import com.thoughtworks.xstream.mapper.Mapper;

/**
 * @author Administrator
 * 
 */
public class MyArrayMapper extends ArrayMapper {

	/**
	 * @param wrapped
	 */
	public MyArrayMapper(Mapper wrapped) {
		super(wrapped);
	}

	@Override
	public String serializedClass(Class type) {
		StringBuffer arraySuffix = new StringBuffer();
		for (; type.isArray(); arraySuffix.append("-array"))
			type = type.getComponentType();

		String name = boxedTypeName(type);
		if (name == null)
			name = super.serializedClass(type);
		if (name.indexOf(".") > -1) {
			name = name.substring(name.lastIndexOf(".") + 1);
		}
		if (arraySuffix.length() > 0)
			return name + arraySuffix;
		else
			return name;
	}
	

	private String boxedTypeName(Class type) {
		return BOXED_TYPES.contains(type) ? type.getName() : null;
	}

	private static final Collection BOXED_TYPES;

	static {
		BOXED_TYPES = Arrays.asList(new Class[] { java.lang.Boolean.class,
				java.lang.Byte.class, java.lang.Character.class,
				java.lang.Short.class, java.lang.Integer.class,
				java.lang.Long.class, java.lang.Float.class,
				java.lang.Double.class });
	}
}
