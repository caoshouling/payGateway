package ins.platform.dcode.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sinosoft.payGateway.exception.BusinessException;

import ins.platform.annotation.Limit;
import ins.platform.common.SessionUtils;
import ins.platform.dcode.dao.UserMapper;
import ins.platform.dcode.po.User;
import ins.platform.dcode.po.UserPageQueryVo;
import ins.platform.dcode.vo.RegisterVo;
import ins.platform.dcode.vo.UserVo;

@Service("UserService")
public class UserService {

	@Autowired
	UserMapper userMapper;
	
	@Cacheable(value = "user1", key = "#id")
	public User getUserById(Integer id) {
		
		return userMapper.selectByPrimaryKey(id);
	}
	@Cacheable(value = "user2", key = "#id")
	public User getUserById2(Integer id) {
		
		return userMapper.selectByPrimaryKey(id);
	}
	public User getUserByUserNameAndPassword(String userName, String password) {
		return userMapper.selectUserByUserNameAndPassword( userName,  password);
	}
    /**
     * 分页查询用户
     * @param rowBounds
     * @return
     */
	public PageInfo<User> findUserListWithPage(UserPageQueryVo vo,int pageNum,int pageSize ) {
		
		 PageHelper.startPage(pageNum, pageSize);
		 List<User> userList =  userMapper.findUserListWithPage(vo);
		 PageInfo<User> pageInfo = new PageInfo<User>(userList);
		 
		 return pageInfo;
	}

	public void register(RegisterVo registerVo) {
		User user = userMapper.selectUserByUserName(registerVo.getUserName());
		if(user != null){
			throw new BusinessException("用户名["+registerVo.getUserName()+"]已存在");
		}
		user = new User();
		user.setUserName(registerVo.getUserName());
		user.setPassword(registerVo.getPassword());
		user.setPhone(registerVo.getPhone());
		user.setMail(registerVo.getMail());
		user.setDeptId(1);
		user.setStatus("0");
		
		userMapper.insert(user);
	}

	public User saveUser(UserVo userVo) {
		
		User user = userMapper.selectUserByUserName(userVo.getUserName());
		if(user != null){
			throw new BusinessException("用户名["+userVo.getUserName()+"]已存在");
		}
	    user = new User();
	    BeanUtils.copyProperties(userVo, user);
	    user.setUpdateTime(new Date());
	    user.setPassword("123456");
	    user.setDeptId(1);
	    user.setStatus("0");
	    user.setCreateTime(new Date());
		userMapper.insert(user);
		return user;
	}

	public User updateUser(UserVo userVo) {
		User user = userMapper.selectByPrimaryKey(userVo.getId());
		if(user == null){
			throw new BusinessException("用户名["+userVo.getUserName()+"]不存在");
		}
		user.setAvatar(userVo.getAvatar());
		user.setPhone(userVo.getPhone());
		user.setUserName(userVo.getUserName());
		user.setMail(userVo.getMail());
		user.setUpdateTime(new Date());
		user.setOperator(Optional.ofNullable(SessionUtils.getCurrentUser()).map(User::getUserName).orElse("admin"));
	   
		userMapper.updateByPrimaryKey(user);
		return user;
	}
	@Limit(key = "test", period = 10, count = 10)
	@Transactional(rollbackFor=Exception.class)
	public void updateUser(){
		User user = userMapper.selectByPrimaryKey(1);
		user.setPassword("123456");
		userMapper.updateByPrimaryKey(user);
		
		User user2 = userMapper.selectByPrimaryKey(2);
		
		user2.setPassword("123456");
		userMapper.updateByPrimaryKey(user2);
		
	}
}
