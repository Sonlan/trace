package org.biac.trace.service;

import org.biac.trace.entity.User;

import java.util.List;



/**
 * 用户相关服务
 * @author Songsong
 *
 */
public interface UserService {
	/**
	 * 登录判断，失败返回false
	 * @param username
	 * @param password
	 * @return
	 */
	User logon(String username, String password);
	/**
	 * 注册用户
	 * @param username
	 * @param password
	 * @param level
	 * @return
	 */
	boolean login(String username, String password, int level);

	/**
	 *
	 * @param username
	 * @return
     */
	boolean userRepeat(String username);
	
	List<User> query(String username, int page);
	
	int queryForSize(String username);
	boolean userDelete(String username);
	boolean userEdit(String username, String password);
}
