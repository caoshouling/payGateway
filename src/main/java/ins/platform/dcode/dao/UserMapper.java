package ins.platform.dcode.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import ins.platform.dcode.dao.provider.UserSqlProvider;
import ins.platform.dcode.po.User;
import ins.platform.dcode.po.UserPageQueryVo;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    @SelectProvider(type = UserSqlProvider.class, method = "selectUserByUserNameAndPassword")
    @Results({
		@Result(column="user_name",property="userName")
    })
    User selectUserByUserNameAndPassword(@Param("userName")String userName,@Param("password") String password);
    
    @Select("select * from sys_user where user_name = #{userName}")
    @Results({
		@Result(column="user_name",property="userName")
    })
    User selectUserByUserName(@Param("userName")String userName);
    
    List<User> findUserListWithPage(UserPageQueryVo userPageQueryVo);

    @Select("select * from sys_user order by id")
    @Results({
		@Result(column="user_name",property="userName")
    })
    List<User> findAllWithParam(@Param("pageNum") int pageNum,
                                  @Param("pageSize") int pageSize);
}