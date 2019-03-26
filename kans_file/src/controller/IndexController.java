package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.user.IUserService;
import vo.Menus;
import vo.UserMenuVo;
import vo.UserVo;

@Controller
@RequestMapping("/index")
public class IndexController {
	protected static Logger logger = Logger.getLogger("controller");
	
//	@Autowired
//	private IUserDao userDao;
	
	@Autowired
	private IUserService userService;

	/**
	 * 跳转到index页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String getCommonPage() {
		logger.debug("index page");
		
//		获取session 用户
		UserVo user= (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return "/index/index";
	}
	
	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getMenuList(){
		
		UserVo user= (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<UserMenuVo> menuList  = userService.getMenu(user.getId(), 1, 1,null);
		
		Map<String, Object> m = new HashMap<String,Object>();
		List<Menus> ml =  new ArrayList<>();
		
		for(int i = 0; i<menuList.size(); i++){
			List<UserMenuVo> menu2 = userService.getMenu(user.getId(), 1, 2,menuList.get(i).getId());

			Menus menu = new Menus();
			
			//封装菜单
			menu.setMenuList(menu2);
			menu.setUmv(menuList.get(i));
			ml.add(menu);
			
		}
		
		m.put("menus", ml);
		
		return m;
	}
}
