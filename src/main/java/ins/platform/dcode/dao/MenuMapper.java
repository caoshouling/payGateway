package ins.platform.dcode.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ins.platform.dcode.po.Menu;

public interface MenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);
    
	List<Menu> selectMenuListByUserCode(@Param("userId") Integer userId);
	
}