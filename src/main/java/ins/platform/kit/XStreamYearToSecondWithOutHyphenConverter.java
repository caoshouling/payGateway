package ins.platform.kit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.thoughtworks.xstream.converters.SingleValueConverter;
/**
 * XStream对于日期格式的处理：yyyy-MM-dd
 * @author sinosoft
 *
 */
public class XStreamYearToSecondWithOutHyphenConverter  implements SingleValueConverter {
	
	private static ThreadLocal<SimpleDateFormat> local_SimpleDateFormat = new ThreadLocal<SimpleDateFormat>();
	@Override
	public boolean canConvert(Class arg0) {
		return Date.class == arg0;
	}

	@Override
	public Object fromString(String arg0) {
		if(StrKit.isEmpty(arg0)){
			return null;
		}
		try {
			SimpleDateFormat simpleDateFormat = local_SimpleDateFormat.get();
			if(simpleDateFormat == null){
				simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			}
			// 这里将字符串转换成日期
			return simpleDateFormat.parse(arg0);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String toString(Object arg0) {
		if(StrKit.isEmpty(arg0)){
			return "";
		}
		// 这里将日期转换成字符串
		SimpleDateFormat simpleDateFormat = local_SimpleDateFormat.get();
		if(simpleDateFormat == null){
			simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		}
		return simpleDateFormat.format((Date) arg0);

	}

 }
