package ins.platform.dcode.vo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class RegisterVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String phone;
	
    private String userName;

    private String password;

    private String repass;
    
    private String smsCode;
    
    private String agreement;
    
    private String mail;
	
   

}