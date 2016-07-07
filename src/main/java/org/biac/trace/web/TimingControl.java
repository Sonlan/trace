package org.biac.trace.web;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.biac.trace.entity.Label;
import org.biac.trace.service.AirconditionService;
import org.biac.trace.service.LabelService;
import org.biac.trace.utils.JsonUtil;
import org.biac.trace.utils.TimeRevert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/timing")
public class TimingControl {
	
	private static Timer timer = null;
	private static long rate = 5*60*1000;
	private static List<Long> lastWorkTime = new ArrayList<Long>();
	private static List<String> ac_id = null; 
	
	@Autowired
	private AirconditionService airService;
	@Autowired
	private LabelService labelService;
	/**
	 * 开启定时器
	 * @param response
	 */
	@RequestMapping(value = "/start") 
	public  void   start(HttpServletResponse response) throws IOException{
		try {
			this.start();
			if(null != timer){
				response.getWriter().write(JsonUtil.statusResponse(0, "开启成功", ""));
			}else
				response.getWriter().write(JsonUtil.statusResponse(1, "开启失败", ""));
		} catch (Exception e) {
			response.getWriter().write(JsonUtil.statusResponse(2, "开启失败,后台错误", ""));
		}
		
			
	}
	/**
	 * 关闭定时器
	 */
	@RequestMapping(value = "/stop") 
	public void  stop(HttpServletResponse response) throws IOException{
		try {
			this.stop();
			if(null != timer){
				response.getWriter().write(JsonUtil.statusResponse(1, "关闭失败", ""));
			}else
				response.getWriter().write(JsonUtil.statusResponse(0, "关闭成功", ""));
		} catch (Exception e) {
			response.getWriter().write(JsonUtil.statusResponse(2, "关闭失败,后台错误", ""));
		}
		
	}
	/**
	 * 定时器状态查询
	 */
	@RequestMapping(value = "/query") 
	public void  query(HttpServletResponse response) throws IOException{
		try {
			if(null != timer){
				response.getWriter().write(JsonUtil.statusResponse(0, "开启状态", ""));
			}else
				response.getWriter().write(JsonUtil.statusResponse(1, "关闭状态", ""));
		} catch (Exception e) {
			response.getWriter().write(JsonUtil.statusResponse(2, "后台错误", ""));
		}
		
	}
	@PostConstruct
	public void start(){
		if(null != timer){
			stop();
		}
		try {
			Properties prop=new Properties();
			prop.load(new InputStreamReader(TimingControl.class.getClassLoader().getResourceAsStream("config/workConfig.properties"), "UTF-8"));
			rate = (long) (Float.parseFloat(prop.getProperty("rate"))*60*1000);
			ac_id = JsonUtil.toObject(prop.getProperty("ac_id"), List.class);
			for(int i=0;i<ac_id.size();i++){
				lastWorkTime.add( 0l);  //初始化
			}
		} catch (Exception e) {
			e.printStackTrace();
			rate = 5*60*1000;
			ac_id.add("01");
			ac_id.add("02");
			ac_id.add("03");
		}
		timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask() {
				
			@Override
			public void run() {
				Map<String, Float> values = airService.getWorkValue(new BigDecimal(0));
				for(int i=0;i<ac_id.size();i++){
					Boolean isWork = values.get("value"+(i+1))==0?false:true;
					if(isWork && 0==lastWorkTime.get(i)){
						lastWorkTime.set(i,new Date().getTime());
					}
					else if(0!=lastWorkTime.get(i)){
						long sub = new Date().getTime()-lastWorkTime.get(i);
						List<Label> list = labelService.getLabelByAcId(ac_id.get(i));
						for(int j=0;j<list.size();j++){
							labelService.updateTimeofLabel(list.get(j).getId(), TimeRevert.toString(TimeRevert.toLong(list.get(j).getAliveTime())-sub));
						}
						lastWorkTime.set(i, 0l);
					}
				}
			}
		}, 0, rate);
	}
	public void stop(){
		if(null != timer){
			timer.cancel();
			timer = null;
		}
	}
}
