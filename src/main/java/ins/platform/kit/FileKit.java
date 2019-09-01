package ins.platform.kit;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;

/**
 * 文件操作类
 * @author CSL
 *
 */
public final class FileKit {
	
	/**
	 * 通过类路径获取输入流, 注意：路径前面不加/
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public final static InputStream getInputStreamByClassPath(String path) throws IOException{
		
		InputStream is = new ClassPathResource(path).getInputStream();
		
		return is;
	}
	
	/**
	 * 通过类路径获取输入流
	 * @param path
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	public final static File getFileByClassPath(String path) throws IOException {
		
		File file = new ClassPathResource(path).getFile();
		
		return file;
	}
    
}
