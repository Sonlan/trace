package org.biac.trace.dao;

import org.biac.trace.entity.Label;

import java.util.List;
import java.util.Map;



public interface LabelMapper {
	/**
	 *
	 * @param map
     */
	void labelInsert(Map<Object, Object> map);
	/**
	 * 安装label时更新对应标签
	 * @param map
	 */
	void deployLabel(Map<Object, Object> map);
	/**
	 * 根据标签id获取label信息
	 * @param id
	 * @return
	 */
	Label getLabelById(String id);
	/**
	 * 滤芯拆除
	 * @param id
	 */
	void removeLabel(String id);
	/**
	 * 滤芯手动报废
	 * @param id
	 */
	void scrapLabel(String id);
	/**
	 * 当id 为空时返回全部label信息
	 * @param id
	 * @return
	 */
	List<Label> getLablesById(Map<Object, Object> map);
	/**
	 * 根据空调id(滤芯id前两位)返回所有滤芯信息
	 * @param map 空调id
	 * @return
	 */
	List<Label> getLabelByAcId(Map<Object, Object> map);
	/**
	 * 更新累计使用时间
	 * @param id
	 */
	void updateTimeOfLabel(Map<Object, Object> map);
	/**
	 * 不限行数
	 * @param map
	 * @return
	 */
	List<Label> getLablesSizeById(Map<Object, Object> map);
	/**
	 * 删除对应记录
	 * @param id
	 */
	void deleteById(String id);
	/**
	 * 修改label记录
	 * @param map
	 */
	void editLabel(Map<Object, Object> map);
}
