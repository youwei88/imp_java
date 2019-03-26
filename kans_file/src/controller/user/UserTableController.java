package controller.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import pojo.Role;
import pojo.User;
import pojo.UserTable;
import service.user.IUserService;
import vo.Tree;
import vo.UserVo;
import net.sf.json.JSONArray;

@Controller
@RequestMapping({ "/user" })
public class UserTableController {
	protected static Logger logger = Logger.getLogger(UserTableController.class);

	// @Autowired
	// private IUserDao userDao;

	@Autowired
	private IUserService userService;

	@RequestMapping(value = { "/index" }, method = { RequestMethod.GET })
	public String saveUser() {
		try {

			logger.debug("首页");
			return "/user/user";
		} catch (Exception e) {
		}
		return "fail";
	}

	@RequestMapping(value = { "/userList" })
	@ResponseBody
	public Map<String, Object> getUserList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, defaultValue = "1") Integer page, // 第几页
			@RequestParam(required = false, defaultValue = "5") Integer rows // 页数大小
	) {

		PageHelper.startPage(page, rows);
		List<User> userList2 = userService.getUsers(null, null, 1);
		PageInfo<User> p = new PageInfo<User>(userList2);

		Map<String, Object> m = new HashMap<>();

		m.put("total", p.getTotal());
		m.put("rows", p.getPages());

		return m;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * 
	 * 		获得用户角色
	 * 
	 */
	@RequestMapping(value = {"/role"})
	public String getRole(HttpServletRequest request, HttpServletResponse response) {

		// 得到当前用户 的角色
		// 获取session 用户
		UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (user != null) {

			Role role = userService.getRole(user.getId());
			List<Role> roleList = userService.getRoles(1);

			request.setAttribute("role", role);
			request.setAttribute("roleList", roleList);
		}

		return "/user/user_role";
	}

	@ResponseBody
	@RequestMapping(value = { "/saveRole" })
	public String saveRole(HttpServletRequest request, HttpServletResponse response, Integer roleId) {

		try {
			// 得到当前用户 的角色
			// 获取session 用户
			UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			userService.saveUserRole(user.getId(), roleId);

			return "1";

		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}

	}
	
	/**
	 * 查询权限
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = { "/getOpertion"})
	public void getOpertion(HttpServletRequest request, HttpServletResponse response){
		
		List<Tree> treeList = new ArrayList<Tree>();
		List<Tree> chilTreeList = null;
		
        Tree all = new Tree();
        all = new Tree();
        all.setId(0);
        all.setState("状态");
        all.setText("全部");
        all.setChecked(true);
        
        Tree father = null;
        Tree child = null;
        
        for(int i = 0; i<5;i++){
        	father = new Tree();
        	father.setId(i);
        	father.setState(i+"状态");
        	father.setText("标题"+i);
        	father.setChecked(true);
        	chilTreeList = new ArrayList<>();
        	for(int j = 10; j>5; j--){
        		
        		child = new Tree();
        		child.setId(j);
        		child.setState(j+"子状态");
        		child.setText("子标题"+j);
        		child.setChecked(true);
            	chilTreeList.add(child);
        	}
        	
        	father.setChildren(chilTreeList);
        	
        	treeList.add(father);
        }
        all.setChildren(treeList);
        
		String jsonStr = "" + JSONArray.fromObject(all).toString() + "";
		
		try {
			response.setContentType("text/json;charset=UTF-8");
			response.getWriter().write(jsonStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} 
	

	/*@ResponseBody
	@RequestMapping({ "/insertUser" })
	public void insertUser(HttpServletRequest request, HttpServletResponse response, UserTable userTable) {
		try {
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("utf-8");

			userTable.setUserName(userTable.getUserPhone());
			// userService.saveUser(userTable);

			System.out.println("添加的手机号码:" + userTable.getUserPhone());
			logger.debug("添加的手机号码:" + userTable.getUserPhone());
			logger.error("配置查看错误信息:" + userTable.getUserPhone());
			response.getWriter().print("1");
		} catch (Exception ex) {
			try {
				response.getWriter().print("0");
			} catch (IOException e) {
				e.printStackTrace();
			}
			logger.debug("添加用户手机 出现问题!!!");
			ex.printStackTrace();
		}
	}*/

	/*@ResponseBody
	@RequestMapping({ "/checkUserByPhone" })
	public void checkUserByPhone(HttpServletRequest request, HttpServletResponse response, UserTable userTable) {

		try {

			// List<UserTable> userList =
			// userService.getUserByPhone(userTable.getUserPhone());
			List<UserTable> userList = new ArrayList<>();
			logger.debug("查询手机号码!");

			if (userList.size() > 0) {
				response.getWriter().print("1");
			} else {
				response.getWriter().print("0");
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}*/

}