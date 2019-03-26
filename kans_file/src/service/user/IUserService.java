package service.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import pojo.Role;
import pojo.User;
import pojo.UserRole;
import pojo.UserTable;
import vo.UserMenuVo;
import vo.UserTableVo;

public interface IUserService {
	
//	/**
//	 * 通过手机号查找用户
//	 * @param userPhone
//	 * @return
//	 */
//	public List<UserTable> getUserByPhone(String userPhone);
//	
//	/**
//	 * 功能：添加一个新的用户
//	 * @param orderVo
//	 */
//	public void saveUser(UserTable userTable);
//	
//	/**
//	 * 获取用户
//	 * @param obj
//	 * @return
//	 */
//	public List<UserTable> getUsers(UserTableVo obj);
	
	/**
	 * 
	 * @param userId
	 * @param status --菜单状态 是否是启用状态	
	 * @param type --菜单类型
	 * @param parentName --父菜单
	 * @return
	 */
	public List<UserMenuVo> getMenu(Integer userId,Integer status,Integer type,Integer parentName);
	
	
	/**
	 * 
	 * @param userId
	 * @param userName
	 * @param status
	 * @return  list
	 * 
	 * 获取用户集合
	 */
	public List<User> getUsers(Integer userId,String userName,Integer status);
	
	
	/**
	 * 通过用户ID
	 * 获取当前用户角色
	 * @param userId
	 * @param status
	 * @return
	 */
	public Role getRole(int userId);
	
	/**
	 * 通过角色状态  
	 * 获取角色所有集合
	 * @param status
	 * @return
	 */
	public List<Role> getRoles(int status);
	
	
	
	/**
	 * 插入当前用户配置的角色
	 * @param userId
	 * @param roleId
	 * @return int
	 */
	public int saveUserRole(Integer userId,Integer roleId);
	
	/**
	 * 
	 * 删除用户相关的 角色
	 * @param id --用户id
	 * @return
	 */
	public int deleteUserRole(Integer userId);

}
