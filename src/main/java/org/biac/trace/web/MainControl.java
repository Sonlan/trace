package org.biac.trace.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/main")
public class MainControl {
	@RequestMapping(value="/index")
	public String toIndex(){
		return "/main/index";
	}
	@RequestMapping(value="/valid")
	public String toValid(){
		return "/main/valid";
	}
	@RequestMapping(value="/invalid")
	public String toInvalid(){
		return "/main/invalid";
	}
	@RequestMapping(value="/user")
	public String toUser(){
		return "/main/user";
	}
	
}
