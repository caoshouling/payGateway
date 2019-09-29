package ins.platform.kit;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
/**
 * 对称加密
 * @author CSL
 *
 */
public class AESEncryptUtil {

	 /**
     * 加密
     *
     * @param sSrc 要加密的内容
     * @param sKey 秘钥
     * @return 加密后的文本
     */
    public static String encrypt(String sSrc, String sKey) throws NoSuchAlgorithmException, NoSuchPaddingException,
    	UnsupportedEncodingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(sKey.getBytes("UTF-8"));
        keyGenerator.init(128, random);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        byte[] byteContent = sSrc.getBytes("UTF-8");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(byteContent);
        return parseByte2HexStr(result);
    }

    /**
     * 解密
     *
     * @param sSrc 已加密文本
     * @param sKey key
     * @return 解密后的铭文
     */
    public static String decrypt(String sSrc, String sKey) throws InvalidKeyException, NoSuchAlgorithmException, 
    	NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(sKey.getBytes("UTF-8"));
        keyGenerator.init(128, random);
        SecretKey secretKey = keyGenerator.generateKey();// 根据用户密码，生成一个密钥
        byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
        Cipher cipher = Cipher.getInstance("AES");// 创建密码器
        cipher.init(Cipher.DECRYPT_MODE, key);// 初始化为解密模式的密码器
        byte[] result = cipher.doFinal(parseHexStr2Byte(sSrc));
        return new String(result, "UTF-8"); // 明文
    }

    /**
     * String 转 byte
     *
     * @param hexStr String
     * @return byte
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * byte 转 String
     *
     * @param buf byte
     * @return String
     */
    public static String parseByte2HexStr(byte buf[]) {
    	StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }
	
    public static void main(String[] a) throws Exception {
    	 // SECRET_AES_KEY
        String SECRET_AES_KEY = "abcdefghijklmnop";
        //原始报文
        String content = "CONTENT_XML";
        
        System .out.println("原始报文 ： " + content);
        String encryptContent = AESEncryptUtil.encrypt(content,SECRET_AES_KEY);
        System.out.println("加密后报文 ： " +encryptContent );
        String decryptContent = AESEncryptUtil.decrypt(encryptContent,SECRET_AES_KEY);
        System.out.println("解密后报文 ： " +decryptContent );
    }
}
