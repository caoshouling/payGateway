package ins.platform.dcode.po;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserPageQueryVo  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String  userName;
	
	private String mail;
	

}
