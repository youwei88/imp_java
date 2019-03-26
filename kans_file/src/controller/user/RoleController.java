package controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import pojo.Role;
import service.user.IUserService;
@Controller
@RequestMapping({ "/role" })
public class RoleController {
	protected static Logger logger = Logger.getLogger(RoleController.class);

	@Autowired
	private IUserService userService;

	@RequestMapping(value = { "/index" }, method = { RequestMethod.GET })
	public String saveUser() {
		try {

			logger.debug("首页");
			return "/user/role";
		} catch (Exception e) {
		}
		return "fail";
	}
	
	
	@RequestMapping(value = { "/roleList" })
	@ResponseBody
	public Map<String, Object> getRoleList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, defaultValue = "1") Integer page, // 第几页
			@RequestParam(required = false, defaultValue = "5") Integer rows // 页数大小
	) {

		PageHelper.startPage(page, rows);
		List<Role> roleList = userService.getRoles(1);
		PageInfo<Role> p = new PageInfo<Role>(roleList);

		Map<String, Object> m = new HashMap<>();

		m.put("total", p.getTotal());
		m.put("rows", p.getPages());

		return m;
	}
}