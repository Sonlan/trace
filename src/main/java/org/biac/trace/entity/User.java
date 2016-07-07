package org.biac.trace.entity;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 用户基本信息
 * @author zmh
 *
 */
public class User {
	private int id;  //用户Id
	private int level;  //用户级别
	private String username;  //用户名
	private String password;  //采用md5 base16加密
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String create_time;//创建日期
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")	
	private String update_time;//修改日期
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
}
