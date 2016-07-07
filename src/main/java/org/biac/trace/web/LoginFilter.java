package org.biac.trace.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		String login=null;
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		String uri = request.getRequestURI();
		if(!uri.toUpperCase().contains("LOGON")){
			login = (String) request.getSession().getAttribute("_LOGIN");
			if(login==null || !login.equals("OK")){
				String PATH = request.getScheme() + "://"
						+ request.getServerName() + ":" + request.getServerPort()
						+ request.getContextPath() + "/";
				System.out.println(PATH);
				response.sendRedirect(PATH);
			}
			else
				chain.doFilter(arg0, arg1);
		}else
			chain.doFilter(arg0, arg1);

		
	}

	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("Login detect start");
	}

}
