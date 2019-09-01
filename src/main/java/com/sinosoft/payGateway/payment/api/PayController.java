package com.sinosoft.payGateway.payment.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="/", tags="缴费支持接口")
@RestController
@RequestMapping("/pay")
public class PayController {
	
	@ApiOperation(value="缴费查询接口", notes = "用于查询缴费金额")
	@RequestMapping(value ="/payQuery",method = {RequestMethod.POST,RequestMethod.GET})
    public Map<String,String >  test(){
    	Map<String,String > map = new HashMap<>();
    	map.put("user", "11c84def-afd7-4e79-b59a-e64a289fa294");
        return map;
    }
    
    @PostMapping("/payConfirm")
    public String  index(Model model){
    	model.addAttribute("message", "Thymeleaf");
        return "index";
    }
}
