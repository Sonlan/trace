package org.biac.trace.entity;


import org.springframework.format.annotation.DateTimeFormat;

public class Label {
	private String id;  //唯一空气滤芯标识,13位，前两位为空调id(ac_id),第三位为type，初级中级
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private String activate_date; //激活绑定时间
	
	private String aliveTime;  //上次清洗后累计剩余使用时间
	
	private int washRemain;  //剩余清洗次数

	private int inuse ;  //是否在使用
	
	private int alive;  //是否已报废
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private String scrap_time;  //报废时间
	
	public String getScrap_time() {
		return scrap_time;
	}

	public void setScrap_time(String scrap_time) {
		this.scrap_time = scrap_time;
	}

	public int getInuse() {
		return inuse;
	}

	public void setInuse(int inuse) {
		this.inuse = inuse;
	}

	public int getAlive() {
		return alive;
	}

	public void setAlive(int alive) {
		this.alive = alive;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActivate_date() {
		return activate_date;
	}

	public void setActivate_date(String activate_date) {
		this.activate_date = activate_date;
	}

	public String getAliveTime() {
		return aliveTime;
	}

	public void setAliveTime(String aliveTime) {
		this.aliveTime = aliveTime;
	}

	public int getWashRemain() {
		return washRemain;
	}

	public void setWashRemain(int washRemain) {
		this.washRemain = washRemain;
	}

}
