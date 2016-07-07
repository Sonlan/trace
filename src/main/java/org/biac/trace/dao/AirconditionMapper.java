package org.biac.trace.dao;

import java.util.List;
import java.util.Map;

public interface AirconditionMapper {
	/**
	 * 从第三方数据库查询得到空调变频。非0代表空调在工作
	 * @return
	 */
	List<Map<String, Float>> getWorkStatus(Map<Object, Object> map);
}
