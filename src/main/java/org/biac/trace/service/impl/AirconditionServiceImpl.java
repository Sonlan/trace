package org.biac.trace.service.impl;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.biac.trace.dao.AirconditionMapper;
import org.biac.trace.service.AirconditionService;
import org.biac.trace.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AirconditionServiceImpl implements AirconditionService {
	@Autowired
	private AirconditionMapper airDao;

	public Map<String, Float> getWorkValue(BigDecimal workMode) {
		try {
			Properties prop=new Properties();
			prop.load(new InputStreamReader(AirconditionServiceImpl.class.getClassLoader().getResourceAsStream("config/workConfig.properties"), "UTF-8"));
			List<String> ac_ids = JsonUtil.toObject(prop.getProperty("ac_id"), List.class);
			String tableName = prop.getProperty("ac_table_name");
			List<String> valueNames = new ArrayList<String>();
			for(int i=0;i<ac_ids.size();i++){
				valueNames.add("value"+(i+1));
			}
			String teString = valueNames.toString().replace('[', ' ').replace(']', ' ');
			Map<Object, Object> map = new HashMap<Object,Object>();
			map.put("tableName", tableName);
			map.put("valueNames",teString);
			List<Map<String, Float>> result = airDao.getWorkStatus(map);
			return result.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
