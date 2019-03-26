package service.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dao.IUserDao;
import pojo.Role;
import pojo.User;
import pojo.UserRole;
import pojo.UserTable;
import service.user.IUserService;
import vo.UserMenuVo;
import vo.UserTableVo;

@Service
public class UserServiceImpl implements IUserService{
	
	@Autowired
	private IUserDao userDao;

	public List<UserTable> getUserByPhone(String userPhone) {
		
//		List<UserTable> userTableList = userDao.getUserByPhone(userPhone);
		// TODO Auto-generated method stub
		return null;
	}
	

	public void saveUser(UserTable userTable) {
//		userDao.saveUser(userTable);
	}


	public List<UserTable> getUsers(UserTableVo obj) {
		
		return userDao.getUsers(obj);
	}


	@Override
	public List<UserMenuVo> getMenu(Integer userId,Integer status, Integer type,Integer parentName) {
		// TODO Auto-generated method stub
		return userDao.getMenu(userId,status, type,parentName);
	}

	@Override
	public List<User> getUsers(Integer userId, String userName, Integer status) {
		
		
		return userDao.getUser(userId, userName, status);
	}


	@Override
	public Role getRole(int userId) {
		
		List<Role> roleList = userDao.getRole(null, null,userId);
		
		if(roleList.size()>0){
			return roleList.get(0);
		}
		
		return null;
	}


	@Override
	public List<Role> getRoles(int status) {
		// TODO Auto-generated method stub
		return userDao.getRoles(null, status);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int saveUserRole(Integer userId,Integer roleId){
		
		//先删除当前用户的角色关系
		userDao.deleteUserRole(userId);
		
		//添加 用户角色
		UserRole userRole = new UserRole();
		userRole.setUserId(userId);
		userRole.setRoleId(roleId);
		
		int i = userDao.saveUserRole(userRole);
		
		return i;
	}

	@Override
	public int deleteUserRole(Integer userId) {
		
		return userDao.deleteUserRole(userId);
	}

}
