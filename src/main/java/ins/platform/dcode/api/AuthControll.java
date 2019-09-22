package ins.platform.dcode.api;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sinosoft.payGateway.common.JsonResponse;

import cn.hutool.core.util.RandomUtil;
import ins.platform.common.WebConstant;
import ins.platform.dcode.po.User;
import ins.platform.dcode.service.MenuService;
import ins.platform.dcode.service.UserService;
import ins.platform.dcode.vo.RegisterVo;
import ins.platform.kit.CodeUtils;

@Controller
@RequestMapping("/auth")
public class AuthControll {
    @Autowired
	private UserService userService;
    @Autowired
    private MenuService menuService;
    
    /**注意：要使用slf4j的API,SLF4J，即简单日志门面，方便日后切换日志实现*/
    Logger logger = LoggerFactory.getLogger(AuthControll.class);
    
    @GetMapping(value = "/register")
    public String registerPage(Model model){
        return  "register";
    }
    @RequestMapping(value = "/loginPage")
    public String toLogin(HttpServletRequest request){
    	if(request.getSession().getAttribute(WebConstant.USER_SESSION) != null){
    		return  "main";
    	}
        return  "user/login";
    }
    @RequestMapping(value = "/main")
    public String mainPage(Model model){
    	HttpServletRequest request =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    	HttpSession session = request.getSession(false);
    	if(session != null){
    		User user = ((User)request.getSession().getAttribute(WebConstant.USER_SESSION));
    		if(user != null){
    			model.addAttribute("userName", user.getUserName());
    			model.addAttribute("menuArray", menuService.MenuList(user.getId()));
    		}
    	}
    	
        return  "main";
    }
//    
//    /**
//     * 返回页面
//     * @param redirectAttributes
//     * @param model
//     * @param user
//     * @param session
//     * @param request
//     * @param forwardUrl
//     * @return
//     */
//	@RequestMapping(value = "/login2",method = RequestMethod.POST,consumes = "application/x-www-form-urlencoded")
//	public String login(RedirectAttributes redirectAttributes,Model model, User user,HttpSession session,HttpServletRequest request
//			,@RequestParam(value = "forwardUrl",required = false) String forwardUrl) {
//	
//		 //获取用户名和密码
//        String username = user.getUserName();
//        String password = user.getPassword();
//        //
//        User user2=  userService.getUserByUserNameAndPassword(username,password);       
//        if(user2 != null){
//            //将用户对象添加到Session中
//            session.setAttribute(WebConstant.USER_SESSION,user);
//            
//            /** HttpServletRequest request =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();*/
//            //方法1:通过request获取到flashMap，把参数设置到flashMap中，spring会自动将其设置到model中
//            FlashMap flashMap = (FlashMap)request.
//                    getAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE);
//            flashMap.put("userName", user2.getUserName());
//            System.out.println("------login----loginning-----user2.getUserName() = "+user2.getUserName() );
//            //方法2：通过传入的attr参数的addFlashAttribute方法保存到FlashMap中，和方法一效果一样
//            redirectAttributes.addFlashAttribute("ordersId", "1234");
//            //方法3：通过attr参数的addAttribute方法设置，这样设置的参数不会保存在FlashMap中，而是会保存到url中
//            redirectAttributes.addAttribute("local","zh-cn");
//            
//            if(forwardUrl == null || "".equals(forwardUrl)){
//            	 //重定向到主页面的跳转方法
//                return "redirect:main";
//            }else{
//            	return "redirect:"+forwardUrl;
//            }
//            
//        }
//        model.addAttribute("error", "用户名或密码错误！");
//		return "login";
//	}
//	/**
//	 * 返回json方式
//	 * @param user
//	 * @param session
//	 * @param forwardUrl
//	 * @return
//	 */
//	@PostMapping(value = "/login")
//	@ResponseBody
//	public JsonResponse login( User user,HttpServletRequest request,HttpSession session, @RequestParam(value = "forwardUrl",required = false)String forwardUrl) {
//		JsonResponse jsonResponse = new JsonResponse();
//		String vcode = (String) session.getAttribute(WebConstant.USER_LOGIN_VALID_CODE);
//     	String requestVcode = request.getParameter("vercode");
//     	
//     	if(requestVcode == null){
//     		jsonResponse.error("请先录入验证码!");
//     	}else if(!requestVcode.equalsIgnoreCase(vcode)){
//     		jsonResponse.error( "验证码错误！");
//     	}else{
//     		//获取用户名和密码
//    		String username = user.getUserName();
//    		String password = user.getPassword();
//    		//
//    	
//    		User user2=  userService.getUserByUserNameAndPassword(username,password);       
//    		if(user2 != null){
//    			//将用户对象添加到Session中
//    			session.setAttribute(WebConstant.USER_SESSION,user2);
//    			jsonResponse.data(user2).success();
//    		}else{
//    			jsonResponse.error("用户名或密码错误！");
//    		}
//     	}
//		
//		return jsonResponse;
//	}
	
	@PostMapping(value = "/register")
	@ResponseBody
    public JsonResponse register(Model model,RegisterVo registerVo,HttpServletRequest request,HttpSession session){
		JsonResponse jsonResponse = new JsonResponse();
		String smsCode = (String) session.getAttribute(WebConstant.USER_Register_SmsCode);
		if(smsCode == null){
			jsonResponse.error("验证码无效！");
		}else if(!smsCode.equals(registerVo.getSmsCode())){
			jsonResponse.error("短信验证码错误！");
		}else if(!registerVo.getRepass().equals(registerVo.getPassword())){
			jsonResponse.error("两次输入的密码不一样！");
			
		}else{
			userService.register(registerVo);
		}
		
        return  jsonResponse;
    }
	@GetMapping(value = "/smsCode")
	@ResponseBody
    public JsonResponse smsCode(HttpSession session,@RequestParam("phone") String phone){
		
		int smsCode = RandomUtil.randomInt(1000, 9999);
		logger.info("模拟给手机【"+phone+"】发送验证码："+smsCode);
		session.setAttribute(WebConstant.USER_Register_SmsCode,""+smsCode);
		
        return  new JsonResponse().success();
    }
	
	@RequestMapping("/logout")
	@ResponseBody
	public JsonResponse logout(HttpSession session) {
		//清除session
        session.invalidate();
        //重定向到登录页面的跳转方法
        return new JsonResponse().success();
        
	}
	/**
	 * 验证码
	 * @param session
	 * @throws IOException 
	 */
	@RequestMapping("/vcode")
	public void vcode(HttpSession session,HttpServletResponse response) throws IOException {
		
        String vcode = CodeUtils.createValidCode(response.getOutputStream());
        session.setAttribute(WebConstant.USER_LOGIN_VALID_CODE, vcode);
        logger.info("验证码:{}", vcode);
	}
	
	@RequestMapping("/index")
	public String  index() {
		
		return "index" ;
	}
	@RequestMapping("/hello")
	@ResponseBody
	public String  hello(Principal principal) {
		
		return "hello," +principal.getName();
	}
}
