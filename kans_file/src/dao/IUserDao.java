package dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import pojo.Role;
import pojo.User;
import pojo.UserRole;
import pojo.UserTable;
import vo.UserMenuVo;
import vo.UserTableVo;

public interface IUserDao {
	
	public List<UserTable> getUsers(UserTableVo obj);
	
	/**
	 * 功能：添加一个新的用户
	 * @param orderVo
	 */
//	public void saveUser(@Param("userTable")UserTable userTable);
//	public List<UserTable> getUsers(@Param("userTable")UserTable userTable);
	
	/**
	 * 查询 用户列表
	 * @param userId
	 * @param userName
	 * @param status
	 * @return
	 */
	public List<User> getUser(@Param("userId")Integer userId,
						@Param("userName")String userName,
						@Param("status")Integer status);
	
	/**
	 * 权限菜单
	 * @param userId
	 * @param type
	 * @return
	 */
	public List<UserMenuVo> getMenu(@Param("userId")Integer userId,@Param("status")Integer status,
			@Param("type")Integer type,@Param("parentName")Integer parentName);
	
	
	/**
	 * 用户 和 角色集合
	 * @param status
	 * @return
	 * 
	 * 此接口考虑到
	 * 后期拓展 可能要用到多重角色
	 * 
	 */
	public List<Role> getRole(@Param("id")Integer id,@Param("status")Integer status,@Param("userId")Integer userId);
	
	/**
	 * 
	 * 角色集合
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	public List<Role> getRoles(@Param("id")Integer id,@Param("status")Integer status);
	
	
	/**
	 * 给用户添加角色
	 * @param role
	 * @return
	 */
	@Transactional
	public int saveUserRole(@Param("userRole")UserRole userRole);
	
	/**
	 * 
	 * 删除用户相关的 角色
	 * @param id --用户id
	 * @return
	 */
	@Transactional
	public int deleteUserRole(@Param("userId")Integer userId);
	
}
