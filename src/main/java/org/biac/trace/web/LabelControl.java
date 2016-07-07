package org.biac.trace.web;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.biac.trace.entity.Label;
import org.biac.trace.entity.User;
import org.biac.trace.service.LabelService;
import org.biac.trace.utils.JsonUtil;
import org.biac.trace.utils.LabelValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/label")
public class LabelControl {
	@Autowired
	private LabelService labelService;
	/**
	 * 标签激活绑定
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/bind")
	public void bind(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String data = request.getParameter("data");
		response.setContentType("application/json;charset=utf-8");
		List<String> erroMsg = new ArrayList<String>();
		if(null != data && !"".equals(data)){
			try {
				List<String> labels = JsonUtil.toObject(data.replace("[", "['").replace(",", "','").replace("]", "']"), List.class);
				if(null != labels){
					for(int i = 0; i<labels.size();i++){
						if(LabelValidate.validate(labels.get(i)+"")){
							int flag= labelService.labelBind(labels.get(i));
							if(1==flag){
								erroMsg.add(labels.get(i)+": 激活失败，请检查滤芯id");
							}else if(2==flag){
								erroMsg.add(labels.get(i)+": 此次激活失败，不能重复激活");
							}
						}else erroMsg.add(labels.get(i)+": 激活失败，滤芯id不合法");
					}
					if(0 == erroMsg.size()) response.getWriter().write(JsonUtil.statusResponse(0, "激活绑定成功", ""));
					else response.getWriter().write(JsonUtil.statusResponse(1, erroMsg.toString(), ""));
				}else response.getWriter().write(JsonUtil.statusResponse(1, "请检查输入参数", ""));
				
			} catch (Exception e) {
				e.printStackTrace();
				response.getWriter().write(JsonUtil.statusResponse(2, "激活绑定失败,后台错误", ""));
			}
		}else response.getWriter().write(JsonUtil.statusResponse(1, "请检查输入参数", ""));
		
	}
	/**
	 * 滤芯安装
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value="/deploy")
	public void deploy(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("application/json;charset=utf-8");
		String data = request.getParameter("data"); 
		List<String> erroMsg = new ArrayList<String>();
		if(null != data && !"".equals(data)){
			try {
				List<String> labels = JsonUtil.toObject(data.replace("[", "['").replace(",", "','").replace("]", "']"), List.class);
				if(null != labels || 0!=labels.size()){
					for(int i=0;i<labels.size();i++){
						int flag = labelService.labelDepoy(labels.get(i));
						if(1==flag){
							erroMsg.add(labels.get(i)+": 该滤芯未激活");
						}else if(2==flag){
							erroMsg.add(labels.get(i)+": 该滤芯已报废");
						}else if(3==flag){
							erroMsg.add(labels.get(i)+": 安装失败，不可重复安装");
						}else if(4==flag){
							erroMsg.add(labels.get(i)+": 安装失败，后台错误");
						}
					}
					if(0 == erroMsg.size()) response.getWriter().write(JsonUtil.statusResponse(0, "安装成功", ""));
					else response.getWriter().write(JsonUtil.statusResponse(1, erroMsg.toString(), ""));
				}else response.getWriter().write(JsonUtil.statusResponse(1, "请检查输入参数", ""));
			} catch (Exception e) {
				e.printStackTrace();
				response.getWriter().write(JsonUtil.statusResponse(2, "后台错误，请检查输入参数", ""));
			}
		}
	}
	/**
	 * 滤芯拆卸操作
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		response.setContentType("application/json;charset=utf-8");
		String data = request.getParameter("data"); 
		List<String> erroMsg = new ArrayList<String>();
		if(null != data && !"".equals(data)){
			List<String> labels = JsonUtil.toObject(data.replace("[", "['").replace(",", "','").replace("]", "']"), List.class);
			if(null != labels){
				for(int i=0;i<labels.size();i++){
					Label label = labelService.labelRemove(labels.get(i));
					if(null != label){
						if(label.getWashRemain()<0){
							erroMsg.add(labels.get(i)+": 已达到清洗次数上限");
						}
					}else{
						erroMsg.add(labels.get(i)+": 移除失败");
					}
				}
				if(0 == erroMsg.size()) response.getWriter().write(JsonUtil.statusResponse(0, "移除成功", ""));
				else response.getWriter().write(JsonUtil.statusResponse(1, erroMsg.toString(), ""));
			}else response.getWriter().write(JsonUtil.statusResponse(1, "请检查输入参数", ""));
			
		}else response.getWriter().write(JsonUtil.statusResponse(1, "请检查输入参数", ""));
	}
	/**
	 * 手动报废
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value="/scrap")
	public void scrap(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("application/json;charset=utf-8");
		String data = request.getParameter("data"); 
		List<String> erroMsg = new ArrayList<String>();
		if(null != data && !"".equals(data)){
			List<String> labels = JsonUtil.toObject(data.replace("[", "['").replace(",", "','").replace("]", "']"), List.class);
			if(null != labels){
				for(int i=0;i<labels.size();i++){
					int flag= labelService.labelScrap(labels.get(i));
					if(1==flag){
						erroMsg.add(labels.get(i)+": 手动报废失败,不存在该空气滤芯");
					}else if(1==flag){
						erroMsg.add(labels.get(i)+": 手动报废失败,后台错误");
					}
				}
				if(0 == erroMsg.size()) response.getWriter().write(JsonUtil.statusResponse(0, "手动报废成功", ""));
				else response.getWriter().write(JsonUtil.statusResponse(1, erroMsg.toString(), ""));
			}else response.getWriter().write(JsonUtil.statusResponse(1, "请检查输入参数", ""));
			
		}else response.getWriter().write(JsonUtil.statusResponse(1, "请检查输入参数", ""));
	}
	/**
	 * 标签信息查询，用于移动端和web端,輸入参数为空时查询所有信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="/query")
	public void query(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String id = null==request.getParameter("id")?"":request.getParameter("id");  //滤芯id
		String inuse = null==request.getParameter("inuse")?"":request.getParameter("inuse");  //是否在使用
		String alive = null==request.getParameter("alive")?"":request.getParameter("alive");  //是否报废
		String ac_id = null==request.getParameter("ac_id")?"":request.getParameter("ac_id");  //空调id，id前两位
		String level = null==request.getParameter("level")?"":request.getParameter("level"); //空调滤芯等级id第三位
		String aliveTime_day = (null==request.getParameter("aliveTime_day") || "".equals(request.getParameter("aliveTime_day")))?"0":request.getParameter("aliveTime_day");  //距离报废的时间，单位小时
		String aliveTime_hour = (null==request.getParameter("aliveTime_hour") || "".equals(request.getParameter("aliveTime_hour")))?"0":request.getParameter("aliveTime_hour");  //距离报废的时间，单位小时
		String aliveTime_min = (null==request.getParameter("aliveTime_min") || "".equals(request.getParameter("aliveTime_min")))?"0":request.getParameter("aliveTime_min");  //距离报废的时间，单位小时
		String washRemain =  null==request.getParameter("washRemain")?"":request.getParameter("washRemain");  //剩余清洗次数
		int page = null==request.getParameter("page")?0:Integer.parseInt(request.getParameter("page"));  //页数，从0开始
		
		Properties prop=new Properties();
		prop.load(new InputStreamReader(TimingControl.class.getClassLoader().getResourceAsStream("workConfig.properties"), "UTF-8"));
		String  rate =prop.getProperty("rate");
		String limitTime = prop.getProperty("limitTime").replace(":", "-");
		List<String> washCountLimit = JsonUtil.toObject(prop.getProperty("washCountLimit"), List.class);
		String ac_ids = prop.getProperty("ac_id");
		
		response.setContentType("application/json;charset=utf-8");
		long aliveTime = Long.parseLong(aliveTime_day)*24*3600000+Long.parseLong(aliveTime_hour)*3600000+Long.parseLong(aliveTime_min)*60000;
		
		List<Label> list = labelService.labelQuery(id,inuse,alive,ac_id,level,aliveTime,washRemain,page);
		int length= 0;
		if(null != list){
			length = labelService.getlabelSizeQuery(id, inuse, alive, ac_id,level,aliveTime,washRemain);
		}
		response.getWriter().write(JsonUtil.statusResponse(0, "{\"limitTime\":"+limitTime+",\"pageSize\":"+length+",\"rate\":"+rate+",\"ac_ids\":"+ac_ids+",\"washCountLimit\":"+washCountLimit+"}", list));
		
		
	}
	/**
	 * 空气滤芯信息修改
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value="/edit")
	public void edit(HttpServletRequest request, HttpServletResponse response) throws IOException{
		try {
			response.setContentType("application/json;cha rset=utf-8");
			
			User user = (User) request.getSession().getAttribute("_USER");
			if(0!=user.getLevel()){
				response.getWriter().write(JsonUtil.statusResponse(1, "您无此权限", ""));
			}else{
				String id = request.getParameter("id");
				String inuse = (null == request.getParameter("inuse")|| "".equals(request.getParameter("inuse")))?"-1":request.getParameter("inuse");
				String alive = (null == request.getParameter("alive")|| "".equals(request.getParameter("alive")))?"-1":request.getParameter("alive");
				String aliveTime = request.getParameter("aliveTime");
				String washRemain = (null == request.getParameter("washRemain")|| "".equals(request.getParameter("washRemain")))?"-1":request.getParameter("washRemain");
				Label label = new Label();
				label.setId(id);
				label.setAlive(Integer.parseInt(alive));
				label.setInuse(Integer.parseInt(inuse));
				label.setWashRemain(Integer.parseInt(washRemain));
				label.setAliveTime(aliveTime);
				if(labelService.editLabel(label)){
					response.getWriter().write(JsonUtil.statusResponse(0, "修改成功", ""));
				}else{
					response.getWriter().write(JsonUtil.statusResponse(1, "修改失败", ""));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write(JsonUtil.statusResponse(2, "输入参数不合法", ""));
		}
	}
	/**
	 * 空气滤芯信息删除
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value="/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("application/json;charset=utf-8");
		
		User user = (User) request.getSession().getAttribute("_USER");	
		if(0!=user.getLevel()){
			response.getWriter().write(JsonUtil.statusResponse(1, "您无此权限", ""));
		}else{
			String id = request.getParameter("id");
			if(labelService.deleteLabelInfo(id)){
				response.getWriter().write(JsonUtil.statusResponse(0, "删除成功", ""));
			}else{
				response.getWriter().write(JsonUtil.statusResponse(1, "删除失败", ""));
			}
		}
	}
	
}
