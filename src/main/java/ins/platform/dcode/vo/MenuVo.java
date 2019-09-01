package ins.platform.dcode.vo;

import java.util.ArrayList;
import java.util.List;

import ins.platform.dcode.po.Menu;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MenuVo extends Menu {

	
	private List<MenuVo> childList = new ArrayList<>();
}
