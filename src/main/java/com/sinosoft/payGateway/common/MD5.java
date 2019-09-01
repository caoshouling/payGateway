package com.sinosoft.payGateway.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringUtils;

public class MD5
{
  private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5", 
    "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

  private static String byteArrayToHexString(byte[] b)
  {
    StringBuffer buf = new StringBuffer();
    for (int i = 0; i < b.length; ++i)
      buf.append(byteToHexString(b[i]));

    return buf.toString();
  }

  private static String byteToHexString(byte b)
  {
    return hexDigits[((b & 0xF0) >> 4)] + hexDigits[(b & 0xF)];
  }

  public static String MD5Encode(String origin) {
    String resultString = null;
    resultString = new String(origin);
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      resultString = byteArrayToHexString(md.digest(resultString.getBytes
        ()));
    }
    catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    return StringUtils.upperCase(resultString);
  }
	/**
	 * 获取大写或者小写MD5串
	 * @param origin
	 * @param upperCase
	 * @return
	 * added by zhanghaowen 2018-5-11 15:10:04
	 */
	public static String MD5Encode(String origin, boolean upperCase) {
		return upperCase?MD5Encode(origin):StringUtils.lowerCase(MD5Encode(origin));
	}
	public static void main(String[] args) {
		System.out.println(MD5Encode("admin35534|535?DFGDdfgfd=gdfgdfgd,||fjh&h43545435dfgdfk&l=akfhhkh",false));
	}
}