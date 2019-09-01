package ins.platform.dcode.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ins.platform.dcode.service.MenuService;

@Controller
@RequestMapping("menu")
public class MenuControll {
    @Autowired
	private MenuService menuService;
    
    /**注意：要使用slf4j的API,SLF4J，即简单日志门面，方便日后切换日志实现*/
    Logger logger = LoggerFactory.getLogger(MenuControll.class);
    
//	@GetMapping(value = "/menuJson")
//	@ResponseBody
//    public JsonResponse menuJson(HttpSession session){
//		
//		User user = SessionUtils.getCurrentUser();
//		List<Menu> menuList= menuService.MenuList(user.getId());
//        return  new JsonResponse(menuList).success();
//    }
	
	
}
