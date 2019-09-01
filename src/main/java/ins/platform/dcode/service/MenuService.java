package ins.platform.dcode.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.hutool.core.bean.BeanUtil;
import ins.platform.dcode.dao.MenuMapper;
import ins.platform.dcode.po.Menu;
import ins.platform.dcode.vo.MenuVo;



@Service("MenuService")
public class MenuService {

	@Autowired
	MenuMapper menuMapper;
	
	public List<MenuVo> MenuList(Integer userId) {
		List<Menu> list = menuMapper.selectMenuListByUserCode(userId);
		
//		list = new ArrayList<>();
//		
//		Menu menu = new Menu();
//		menu.setId(1);
//		menu.setParentId(-1);
//		menu.setName("主菜单1");
//		list.add(menu);
//		
//		menu = new Menu();
//		menu.setId(2);
//		menu.setParentId(-1);
//		menu.setName("主菜单2");
//		list.add(menu);
//		
//		menu = new Menu();
//		menu.setId(3);
//		menu.setParentId(1);
//		menu.setName("二级菜单1-1");
//		list.add(menu);
//
//		menu = new Menu();
//		menu.setId(4);
//		menu.setParentId(1);
//		menu.setName("二级菜单1-2");
//		list.add(menu);
//
//		menu = new Menu();
//		menu.setId(5);
//		menu.setParentId(2);
//		menu.setName("二级菜单2-1");
//		list.add(menu);
//		
//
//		menu = new Menu();
//		menu.setId(6);
//		menu.setParentId(2);
//		menu.setName("二级菜单2-2");
//		list.add(menu);


		
		return listToTree(list);
	}
	/*
	- listToTree
	- <p>方法说明<p>
	- 将JSONArray数组转为树状结构
	- @param arr 需要转化的数据
	- @param id 数据唯一的标识键值
	- @param pid 父id唯一标识键值
	- @param child 子节点键值
	- @return JSONArray
	*/
	public static  List<MenuVo> listToTree(List<Menu> list){
	   Map<Integer,MenuVo> map_id_MenuVo = new HashMap<>();
	   //将数组转为Object的形式，key为数组中的id
	   List<MenuVo> listVo = new ArrayList<>();
	   List<MenuVo> listVo_result = new ArrayList<>();
	   MenuVo menuVo = null;
	   for(int i=0;i<list.size();i++){
	    menuVo = new  MenuVo();
	    BeanUtil.copyProperties(list.get(i), menuVo);
	    listVo.add(menuVo);
	    map_id_MenuVo.put(menuVo.getId(), menuVo);
	   }
	   //遍历结果集
	   for(int j=0;j<listVo.size();j++){
		  //单条记录
		  menuVo = listVo.get(j);
		  //取出父级元素
		   MenuVo parentMenuVo = map_id_MenuVo.get(menuVo.getParentId());
		 
		  if(parentMenuVo!=null){
			  parentMenuVo.getChildList().add(menuVo);
		  }else{
			  //只加没有父节点的
			  listVo_result.add(menuVo);
		  }
	   }
	   
	  
	   return listVo_result;
	}
	
	public static void main(String[] args) {
		List<Menu> list = new ArrayList<>();
		Menu menu = new Menu();
		menu.setId(1);
		menu.setParentId(-1);
		menu.setName("主菜单1");
		list.add(menu);
		
		menu = new Menu();
		menu.setId(2);
		menu.setParentId(-1);
		menu.setName("主菜单2");
		list.add(menu);
		
		menu = new Menu();
		menu.setId(3);
		menu.setParentId(1);
		menu.setName("二级菜单1-1");
		list.add(menu);

		menu = new Menu();
		menu.setId(4);
		menu.setParentId(1);
		menu.setName("二级菜单1-2");
		list.add(menu);

		menu = new Menu();
		menu.setId(5);
		menu.setParentId(2);
		menu.setName("二级菜单2-1");
		list.add(menu);
		

		menu = new Menu();
		menu.setId(6);
		menu.setParentId(2);
		menu.setName("二级菜单2-2");
		list.add(menu);

		menu = new Menu();
		menu.setId(7);
		menu.setParentId(6);
		menu.setName("二级菜单2-2-2");
		list.add(menu);
		
		
		menu = new Menu();
		menu.setId(8);
		menu.setParentId(3);
		menu.setName("二级菜单1-1-1");
		list.add(menu);
		
		List<MenuVo> listVo_result = listToTree(list);
		System.out.println(JSON.toJSON(listVo_result).toString());
	}
	

}
