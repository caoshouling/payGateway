package ins.platform.kit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.sinosoft.payGateway.exception.BusinessException;


public class StrKit {

	public static final String Empty = "";
	
	public static final String Zero = "0";

	public static boolean isEmpty(Object value) {
		return value == null ? true : value.toString().isEmpty();
	}

	public static boolean isNoEmpty(Object value) {
		return !isEmpty(value);
	}

	public static String leftPad(String value, int size) {
		return StringUtils.leftPad(value, size, " ");
	}
	/**
	 * 一共size位，如果不足，那么在左边使用pad填充
	 * @param value
	 * @param size
	 * @param pad
	 * @return
	 */
	public static String leftPad(String value, int size,String pad) {
		return StringUtils.leftPad(value, size, pad);
	}
	/***
	 * 字符串重复次数
	 * @param value
	 *  @param size
	 * @return
	 */
	public static String repeat(String value, int size) {
		return StringUtils.repeat(value, size);
	}
	/***
	 * 首字母大写
	 * @param value
	 * @return
	 */
	public static String capitalize(String value) {
		return StringUtils.capitalize(value);
	}
	/***
	 * 首字母大小写
	 * @param value
	 * @return
	 */
	public static String uncapitalize(String value) {
		return StringUtils.uncapitalize(value);
	}
    /**
     * 替换所有的匹配字符
     * @param value
     * @param src
     * @param dst
     * @return
     */
	public static String replace(String value, String src, String dst) {
		return StringUtils.replace(value, src, dst);
	}
	
	public static String[] split(String express,String seperator){
		return StringUtils.split(express,seperator);
	}
	
	public static String join(String[] values,String seperator){
		return StringUtils.join(values, seperator);
	}
	
	public static String rightTrim(String value,int size,String pad){
		if(value.length()>size){
			return value.substring(0,size-1);
		}else{
			return StringUtils.rightPad(value, size, pad);
		}
	}
	
	public static String leftTrim(String value,int size,String pad){
		if(value.length()>size){
			return value.substring(0,size);
		}else{
			return StringUtils.leftPad(value, size, pad);
		}
	}
	
	public static String leftInterct(String value,int size){
		if(value.length()>size){
			return value.substring(0,size);
		}else{
			return value;
		}
	}
	
	public static String rightInterct(String value,int size){
		if(value.length()>size){
			return value.substring(value.length()-size,value.length());
		}else{
			return value;
		}
	}
	
	public static String join(String... values){
		return StringUtils.join(values, ",");
	}
	
	public static String str(Exception e){
		return ExceptionUtils.getStackTrace(e);
	}
	/**
	 * UTF8字符串的字节长度 :一个中文3个字节
	 * iString.getBytes()默认采用操作系统的默认编码
	 * @param iString
	 * @return
	 * @throws  
	 * @throws Exception 
	 */
	public static int getUTF8BytesLength(String iString)     {
		if(iString==null){
			return 0;
		} 
		try {
			return iString.getBytes("UTF-8").length;
		} catch (UnsupportedEncodingException e) {
			throw new BusinessException("不支持UTF-8编码！");
		}
	}
	
	/**
	 * GBK字符串的字节长度:一个中文2个字节
	 * @throws Exception 
	 * 
	 */
	public static int getGBKBytesLength(String iString) throws Exception {
		if(iString==null){
			return 0;
		}
		return iString.getBytes("GBK").length;
	}
	

	public static String unicodeToGBK(String iString) throws UnsupportedEncodingException {
		String oString = iString;
		if (iString != null) {
			oString = new String(iString.getBytes("ISO8859_1"), "GBK");
		}
		return oString;
	}

	public static String GBKToUnicode(String iString) throws UnsupportedEncodingException {
		String oString = iString;
		if (iString != null) {
			oString = new String(iString.getBytes("GBK"), "ISO8859_1");
		}
		return oString;
	}

	/**
	 * 复制文件
	 * 
	 * @param fromFile
	 * @param toFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws Exception
	 */
	public static void copyFile(String fromFile, String toFile) throws FileNotFoundException, IOException, Exception {
		FileInputStream in = new FileInputStream(fromFile);
		FileOutputStream out = new FileOutputStream(toFile);
		byte[] b = new byte[1024];
		int len;
		while ((len = in.read(b)) != -1) {
			out.write(b, 0, len);
		}
		out.close();
		in.close();
	}

	/**
	 * 转换成HTML格式
	 * 
	 * @param strInValue
	 * @return
	 */
	public static String toHTMLFormat(String strInValue) {
		String strOutValue = "";

		for (int i = 0; i < strInValue.length(); i++) {
			char c = strInValue.charAt(i);
			switch (c) {
			case '<':
				strOutValue = strOutValue + "&lt;";
				break;
			case '>':
				strOutValue = strOutValue + "&gt;";
				break;
			case '\n':
				strOutValue = strOutValue + "<br>";
				break;
			case '\r':
				break;
			case ' ':
				strOutValue = strOutValue + "&nbsp;";
				break;
			default:
				strOutValue = strOutValue + c;
			}
		}

		return strOutValue;
	}

	/**
	 * 转义字符转换
	 * 
	 * @param strInValue
	 * @return
	 */
	public static String encode(String strInValue) {
		String strOutValue = "";
		if(!StrKit.isEmpty(strInValue)){
			for (int i = 0; i < strInValue.length(); i++) {
				char c = strInValue.charAt(i);
				switch (c) {
				case ':':
					strOutValue = strOutValue + "：";
					break;
				case '|':
					strOutValue = strOutValue + "┃";
					break;
				case '\n':
					strOutValue = strOutValue + "\\n";
					break;
				case '\r':
					strOutValue = strOutValue + "\\r";
					break;
				case '"':
					strOutValue = strOutValue + "\\\"";
					break;
				case '\'':
					strOutValue = strOutValue + "\\'";
					break;
				case '\b':
					strOutValue = strOutValue + "\\b";
					break;
				case '\t':
					strOutValue = strOutValue + "\\t";
					break;
				case '\f':
					strOutValue = strOutValue + "\\f";
					break;
				case '\\':
					strOutValue = strOutValue + "\\\\";
					break;
				case '<':
					strOutValue = strOutValue + "\\<";
					break;
				case '>':
					strOutValue = strOutValue + "\\>";
					break;
				default:
					strOutValue = strOutValue + c;
				}

			}
		}
		return strOutValue;
	}

	/**
	 * 查询符号转换成SQL
	 * 
	 * @param strName
	 * @param strValue
	 * @param strSign
	 * @return
	 */
	public static String convertString(String strName, String strValue, String strSign) {
		String strReturn = "";
		if ((strValue == null) || (strValue.equals(""))) {
			return "";
		}

		if (strSign.equals(":")) {
			String strValueStart = "";
			String strValueEnd = "";
			int index = strValue.indexOf(':');
			if (index > -1) {
				strValueStart = strValue.substring(0, index);
				strValueEnd = strValue.substring(index + 1);
				strReturn = " and " + strName + " between '" + strValueStart + "' and '" + strValueEnd + "' ";
			} else {
				return "";
			}
		} else if (strSign.equals("=")) {
			strReturn = " and " + strName + "='" + strValue + "' ";
		} else {
			strValue = replace(strValue, "*", "%");
			strReturn = " and " + strName + " like '%" + strValue + "%' ";
		}

		return strReturn;
	}

	/**
	 * 查询日期 符号转换成SQL
	 * 
	 * @param strName
	 * @param strValue
	 * @param strSign
	 * @return
	 */
	public static String convertDate(String strName, String strValue, String strSign)

	{
		String strReturn = "";
		String strDbType = "ORACLE";

		if ((strValue == null) || (strValue.equals(""))) {
			return "";
		}

		if (strSign.equals(":")) {
			String strValueStart = "";
			String strValueEnd = "";
			int index = strValue.indexOf(':');
			if (index > -1) {
				strValueStart = strValue.substring(0, index);
				strValueEnd = strValue.substring(index + 1);
				if (strDbType.equals("ORACLE"))
					strReturn = " and " + strName + " between to_date('" + strValueStart + "') and to_date('"
							+ strValueEnd + "') ";
				else
					strReturn = " and " + strName + " between '" + strValueStart + "' and '" + strValueEnd + "' ";
			} else {
				return "";
			}

		} else if (strDbType.equals("ORACLE")) {
			strReturn = " and " + strName + strSign + "to_date('" + strValue + "') ";
		} else {
			strReturn = " and " + strName + strSign + "'" + strValue + "' ";
		}

		return strReturn;
	}

	/**
	 * 查询日期 符号转换成SQL
	 * 
	 * @param strName
	 * @param strValue
	 * @param strSign
	 * @return
	 */
	public static String convertNumber(String strName, String strValue, String strSign) {
		String strReturn = "";
		if ((strValue == null) || (strValue.equals(""))) {
			return "";
		}

		if (strSign.equals(":")) {
			String strValueStart = "";
			String strValueEnd = "";
			int index = strValue.indexOf(':');
			if (index > -1) {
				strValueStart = strValue.substring(0, index);
				strValueEnd = strValue.substring(index + 1);
				strReturn = " and " + strName + " between '" + strValueStart + "' and '" + strValueEnd + "' ";
			} else {
				return "";
			}
		} else {
			strReturn = " and " + strName + strSign + "'" + strValue + "' ";
		}

		return strReturn;
	}

	public static String decode(String strInValue) {
		String strOutValue = strInValue;
		strOutValue = replace(strOutValue, "\\n", "\n");
		strOutValue = replace(strOutValue, "\\r", "\r");
		strOutValue = replace(strOutValue, "\\\\", "\\");
		strOutValue = replace(strOutValue, "\\b", "\b");
		strOutValue = replace(strOutValue, "\\t", "\t");
		strOutValue = replace(strOutValue, "\\\"", "\"");
		strOutValue = replace(strOutValue, "\\'", "'");
		strOutValue = replace(strOutValue, "\\f", "\f");
		strOutValue = replace(strOutValue, "\\<", "<");
		strOutValue = replace(strOutValue, "\\>", ">");
		return strOutValue;
	}

	/**
	 * 空转换成0
	 * 
	 * @param iValue
	 * @return
	 */
	public static String chgStrZero(String iValue) {
		String value = null;

		if (iValue == null)
			value = "0";
		else if (iValue.trim().length() == 0)
			value = "0";
		else {
			value = iValue;
		}
		return value.trim();
	}

	/**
	 * null转换成空，并去掉右边的空格
	 * 
	 * @param strIn
	 * @return
	 */
	public static String rightTrim(String strIn) {
		String strReturn = "";
		int intLength = 0;

		if ((strIn == null) || (strIn.equals("")) || (strIn.equals("null")))
			return "";
		intLength = strIn.length();

		while ((intLength > 0) && (strIn.substring(intLength - 1, intLength).equals(" "))) {
			strIn = strIn.substring(0, intLength - 1);
			intLength = strIn.length();
		}

		strReturn = strIn;
		return strReturn;
	}
	/**
	 * 是否匹配正则表达式
	 * @param value
	 * @param regex
	 * @return
	 */
	public static boolean matches(String value, String regex) {
		
		if(value != null && Pattern.matches(regex, value)){
			 return true;
		}
		return false;
	}
	public static void main(String[] args){
		String a="abc";
		System.out.println(StrKit.leftInterct(a,5));
		System.out.println(StrKit.leftPad(a,5));
		System.out.println(StrKit.leftPad(a,5,"0"));
		System.out.println(Double.toString(2.895745));
		System.out.println(String.valueOf(2.895745));
		System.out.println(StrKit.repeat(a,4));
	
	}
    /**
     * 判断是否是数字
     */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 去掉最后一个分隔符
	 * @param value
	 * @param mark
	 * @return
	 */
	public static String removeLastMark(String value,String mark){
		if(!StrKit.isEmpty(value)){
			return value.substring(0,value.length()-1);
		}
		return value;
	}
	
	
	/**
	 * null转换成空
	 * 
	 * @param strIn
	 * @return
	 */
	public static String nullToEmpty(String strIn) {
		if(strIn == null){
			return "";
		}else{
			return strIn;
		}
	}
	
	public static String otherToString(Date date) {
		if(date == null) {
			return "";
		}else {
			return date.toString();
		}
	}
	public static String otherToString(Object obj) {
		if(obj == null) {
			return "";
		}else {
			return obj.toString();
		}
	}
	
	public static String newString(String iString, int iTimes){
	    StringBuffer buffer = new StringBuffer();
	    for (int i = 0; i < iTimes; i++) {
	      buffer.append(iString);
	    }
	    return buffer.toString();
	}
	
	public static String space(int iLength){
		return newString(" ", iLength);
	}
	
	public static boolean equalsWithNullToBlank(String a,String b){
		
		if(a == null){
			a = "";
		}
		if(b == null){
			b = "";
		}
		return a.equals(b);
	}
}
