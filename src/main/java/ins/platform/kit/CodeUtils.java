package ins.platform.kit;

import java.io.OutputStream;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;

public class CodeUtils {

	/**
	 * 圆圈干扰验证码     CircleCaptcha
	 * 扭曲干扰验证码     ShearCaptcha
	 * 线段干扰的验证码 LineCaptcha
	 */
	public static String createValidCode(OutputStream outputStream){
		//定义图形验证码的长、宽、验证码字符数、干扰元素个数
		CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);
		//图形验证码写出，可以写出到文件，也可以写出到流
		captcha.write(outputStream);
		//验证图形验证码的有效性，返回boolean值
		//captcha.verify("1234");
		return captcha.getCode();
	}
	
}
