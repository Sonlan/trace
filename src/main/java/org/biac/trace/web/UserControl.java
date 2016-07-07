package org.biac.trace.web;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.biac.trace.entity.User;
import org.biac.trace.service.UserService;
import org.biac.trace.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 用户相关后台处理
 * @author Songsong
 *
 */
@Controller
@RequestMapping(value = "/user")
public class UserControl {
	@Autowired
	private UserService userService;
	
	/**
	 * 用户登录处理
	 * @throws IOException 
	 */
	@RequestMapping(value = "/logon") 
	public  void logon(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		response.setContentType("application/json;charset=utf-8");
		if (!(null==username && "".equals(username) && null==password && "".equals(password))) {
			User user= userService.logon(username, password);
			if(null !=user){
				request.getSession().setAttribute("_LOGIN", "OK");
				request.getSession().setAttribute("_USER", user);
				response.getWriter().write(JsonUtil.statusResponse(0, "登录成功", "user/toIndex"));
			}else response.getWriter().write(JsonUtil.statusResponse(1, "用户名或密码错误", ""));
		}else
		response.getWriter().write(JsonUtil.statusResponse(1, "请检查输入", "")); 
	}
	/**
	 * 登出
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/logout") 
	public void logout(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.getSession().invalidate();	
		String PATH = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + "/";
		response.sendRedirect(PATH);
	}
	/**
	 * 用户注册，默认注册用户等级为1
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/login") 
	public void login(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		response.setContentType("application/json;charset=utf-8");
		try {
			User user = (User) request.getSession().getAttribute("_USER");	
			if(0!=user.getLevel()){
				response.getWriter().write(JsonUtil.statusResponse(1, "您无此权限", ""));
			}else if (!(null==username || "".equals(username) || null==password || "".equals(password) )) {
				if(userService.userRepeat(username)){
					response.getWriter().write(JsonUtil.statusResponse(1, "注册失败,用户名重复", "")); 
				}
				else if(userService.login(username, password,1)){
					response.getWriter().write(JsonUtil.statusResponse(0, "注册成功", ""));
				}
			}
			else response.getWriter().write(JsonUtil.statusResponse(1, "注册失败,请检查输入", "")); 
		} catch (Exception e) {
			response.getWriter().write(JsonUtil.statusResponse(1, "注册失败,请检查输入", ""));
		}
		

	}
	/**
	 * 用户注销
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value = "/delete")
	public void delete(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String username = request.getParameter("username");
		response.setContentType("application/json;charset=utf-8");
		User user = (User) request.getSession().getAttribute("_USER");
		if(null == username) response.getWriter().write(JsonUtil.statusResponse(1, "请输入用户名", ""));
		else if(0!=user.getLevel()){
			response.getWriter().write(JsonUtil.statusResponse(1, "您无此权限", ""));
		}
		else{
			if(userService.userDelete(username)){
				response.getWriter().write(JsonUtil.statusResponse(0, "用户注销成功", ""));
			}else response.getWriter().write(JsonUtil.statusResponse(1, "后台错误", ""));
		}
	}
	@RequestMapping(value = "/query") 
	public  void   query(HttpServletResponse response,HttpServletRequest request) throws IOException{
		String username = null==request.getParameter("username")?"":request.getParameter("username");
		int page = null==request.getParameter("page")?0:Integer.parseInt(request.getParameter("page"));  //页数，从0开始
		response.setContentType("application/json;charset=utf-8");
		List<User> users = userService.query(username,page);
		response.setContentType("application/json;charset=utf-8");
		if(null==users || 0==users.size())
			response.getWriter().write(JsonUtil.statusResponse(1, "查询失败", ""));
		else response.getWriter().write(JsonUtil.statusResponse(0, userService.queryForSize(username), users));
	}
	/**
	 * 用户修改密码，传入参数为username 和 newPassword
	 * @throws IOException 
	 */
	@RequestMapping(value = "/edit")
	public void edit(HttpServletResponse response,HttpServletRequest request) throws IOException{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		response.setContentType("application/json;charset=utf-8");
		User user = (User) request.getSession().getAttribute("_USER");	
		if(0!=user.getLevel()){
			response.getWriter().write(JsonUtil.statusResponse(1, "您无此权限", ""));
		}else if(null != username && !"".equals(username) && null != password && !"".equals(password)){
			if(userService.userEdit(username, password)){
				response.getWriter().write(JsonUtil.statusResponse(0, "修改密码成功", ""));
			}else{
				response.getWriter().write(JsonUtil.statusResponse(1, "修改密码失败", ""));
			}
		}else{
			response.getWriter().write(JsonUtil.statusResponse(1, "输入信息不能为空", ""));
		}
		
	}
	/**
	 * 返回到登录页面
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toLogon") 
	public  String   toLogon(HttpServletResponse response) throws IOException{
		return "logon";
	}
	/**
	 * 跳转到主页面
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toIndex") 
	public  String   toMain(HttpServletResponse response) throws IOException{
		return "/main/index";
	}
	
}
