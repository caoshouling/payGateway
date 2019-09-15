package ins.platform.dcode.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.sinosoft.payGateway.common.JsonResponse;

import cn.hutool.core.date.DateUtil;
import ins.platform.common.SessionUtils;
import ins.platform.dcode.po.User;
import ins.platform.dcode.po.UserPageQueryVo;
import ins.platform.dcode.service.UserService;
import ins.platform.dcode.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@Api(value="/user", tags="用户管理接口")
@Controller
@RequestMapping("/user")
public class UserControll {
    @Autowired
	private UserService userService;
    @Autowired
    private JavamelodyMonitoringTest javamelodyMonitoringTest;
    
	@RequestMapping("/getuser/{id}")
	public String getUserById(Model model,@ApiParam(value="客户ID")@PathVariable Integer id) {
		 model.addAttribute("message", "hello word33453433");
		 User user=  userService.getUserById(id);
		 model.addAttribute("username", user.getUserName());
		return "hello" ;
	}
	@RequestMapping("/info")
	public String  userInfo(Model model) {
		model.addAttribute("user",SessionUtils.getCurrentUser());
		return "set/info" ;
	}
	@RequestMapping("/userlistPage")
	public String  userlistPage() {
		
		return "user/userlist" ;
	}	
	@RequestMapping("/userform")
	public String  userform() {
		
		return "user/userform" ;
	}	
	@ApiOperation(value = "/用户查询接口", notes = "返回所有用户")
	@RequestMapping("/userlist")
	@ApiImplicitParams({
	    @ApiImplicitParam(name="vo",value="查询条件",required=false,paramType="form",dataTypeClass=UserPageQueryVo.class),
	    @ApiImplicitParam(name="pageNum",value="第几页",required=true,paramType="query"),
	    @ApiImplicitParam(name="pageSize",value="每页数量",required=true,paramType="query",dataTypeClass = Integer.class)
	})
	@ResponseBody
	public JsonResponse  findAll(UserPageQueryVo vo,@RequestParam(value = "pageNum",required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
	
		PageInfo<User> userPage = userService.findUserListWithPage(vo,pageNum ,pageSize);
		
		return  new JsonResponse(userPage);
	}
	@ApiOperation(value = "/保存用户接口", notes = "添加一个用户")
	@ApiImplicitParam(name = "userVo",value = "用户实体信息",required = true,dataType = "UserVo",paramType = "body")
	@RequestMapping("/save")
	@ResponseBody
	public JsonResponse addUser (UserVo userVo,HttpServletRequest request ) throws IllegalStateException, IOException {
		
		System.out.println("user = "+userVo);
		
		List<MultipartFile> images = userVo.getImages();
		List<String> fileNames = new ArrayList<>();
		if(images != null){
			for(MultipartFile imgMultipartFile :images){
				String fileName = imgMultipartFile.getOriginalFilename();
				fileNames.add(fileName);
				File imageFile = new File(request.getServletContext().getRealPath("/static/img"),fileName);
				
				imgMultipartFile.transferTo(imageFile);
			}
		}
		User user = userService.saveUser(userVo);
		
		return new JsonResponse(user).success();
	}
	@ApiOperation(value = "/更新用户接口", notes = "更新用户")
	@ApiImplicitParam(name = "userVo",value = "用户实体信息",required = true,dataType = "UserVo",paramType = "body")
	@RequestMapping("/update")
	@ResponseBody
	public JsonResponse updateUser (UserVo userVo,HttpServletRequest request ) throws IllegalStateException, IOException {
		
		System.out.println("user = "+userVo);
		
		List<MultipartFile> images = userVo.getImages();
		List<String> fileNames = new ArrayList<>();
		if(images != null){
			for(MultipartFile imgMultipartFile :images){
				String fileName = imgMultipartFile.getOriginalFilename();
				fileNames.add(fileName);
				File imageFile = new File(request.getServletContext().getRealPath("/static/img"),fileName);
				
				imgMultipartFile.transferTo(imageFile);
			}
		}
		User user = userService.updateUser(userVo);
		
		return new JsonResponse(user).success();
	}
	@RequestMapping("/loadImage")
	@ResponseBody
	public JsonResponse uploadImage (@ApiParam(value = "用户头像",name = "avatarImg", required = true) @RequestParam("avatarImg")MultipartFile avatarFile,HttpServletRequest request ) throws IllegalStateException, IOException {
		
		System.out.println("avatar = "+avatarFile);
		
		List<String> fileNames = new ArrayList<>();
		Map<String,String> map = new HashMap<>();
		if(avatarFile != null){
			   String dateStr = DateUtil.formatDateTime(new Date())
			   .replaceAll(" ", "")
			   .replaceAll("-", "")
			   .replaceAll(":", "");
				String fileName = dateStr+"_"+avatarFile.getOriginalFilename();
				fileNames.add(fileName);
				File imageFile = new File(request.getServletContext().getRealPath("/static/img"),fileName);
				avatarFile.transferTo(imageFile);
				map.put("src", request.getServletContext().getContextPath()+"/static/img/"+fileName);
			
		}
		
		return new JsonResponse(map).success();
	}
	@RequestMapping("/javamelody/method")
	@ResponseBody
	public JsonResponse javamelodyDemo () throws Exception {
		
		int time = javamelodyMonitoringTest.JavamelodyMonitoringMethod();
		userService.updateUser();
		return new JsonResponse(time).success();
	}
	
}
