package ins.platform.dcode.vo;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ins.platform.dcode.po.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=false)

public class UserVo extends User implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<MultipartFile> images;
	
	
}
