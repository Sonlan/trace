package org.biac.trace.utils;

/**
 * hhh:mm:ss类型与long的相互转化
 * @author Administrator
 *
 */
public class TimeRevert {
	public static Long toLong(String time){
		String [] res = time.split(":");
		try {
			return (long)(Long.parseLong(res[res.length-1])*60+Long.parseLong(res[res.length-2])*3600+Long.parseLong(res[res.length-3])*3600*24)*1000;
		} catch (Exception e) {
			e.printStackTrace();
			return -1l;
		}
	}
	public static String toString(long time){
		try {
			if(time>=0){
				String string =  strFormat(time/(3600000*24)+"")+":"+strFormat((time%(3600000*24))/3600000+"")+":"+strFormat((time%3600000)/60000+"");
				return string;
			}
				
			else return "00:00:00";
		} catch (Exception e) {
			e.printStackTrace();
			return "00:00:00";
		}
	}
	/**
	 * 不足两位的时间补齐为2位
	 * @param str
	 * @return
	 */
	public static String strFormat(String str){
		if(1>=str.length()){
			return "0"+str;
		}else return str;
	}
}
