package com.zhangt.framework.bean;

import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 加载配置文件，封装整个mystruts.xml
 * @author Administrator
 *
 */
public class ActionMappingManager {
	//保存action集合
	private Map<String, ActionMapping> allActions;
	
	public ActionMappingManager() {
		allActions = new HashMap<String, ActionMapping>();
		this.init();
	}
	
	private void init() {
		// DOM4J读取配置文件
		// 1. 得到解析器
		SAXReader reader = new SAXReader();
		// 得到src/mystruts.xml  文件流
		InputStream inputStream = this.getClass().getResourceAsStream("/myStruts.xml");
		try {
			// 2. 加载文件
			Document document = reader.read(inputStream);
			// 3. 获取根
			Element root = document.getRootElement();
			// 4. 得到package节点
			Element ele_package = root.element("package");
			// 5. 得到package节点下，  所有的action子节点
			List<Element> listAction = ele_package.elements("action");
			
			// 6.遍历 ，封装
			for (Element ele_action : listAction) {
				// 6.1 封装一个ActionMapping对象
				ActionMapping actionMapping = new ActionMapping();
				actionMapping.setName(ele_action.attributeValue("name"));
				actionMapping.setClassName(ele_action.attributeValue("class"));
				actionMapping.setMethod(ele_action.attributeValue("method"));
				
				// 6.2 封装当前aciton节点下所有的结果视图
				Map<String,Result> results = new HashMap<String, Result>();
				
				// 得到当前action节点下所有的result子节点
				 Iterator<Element> it = ele_action.elementIterator("result");
				 while (it.hasNext()) {
					 // 当前迭代的每一个元素都是 <result...>
					 Element ele_result = it.next();
					 
					 // 封装对象
					 Result res = new Result();
					 res.setName(ele_result.attributeValue("name"));
					 res.setType(ele_result.attributeValue("type"));
					 res.setPage(ele_result.getTextTrim());
					 
					 // 添加到集合
					 results.put(res.getName(), res);
				 }
				
				// 设置到actionMapping中
				actionMapping.setResults(results);
				
				// 6.x actionMapping添加到map集合
				allActions.put(actionMapping.getName(), actionMapping);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据请求路径名称，返回Action的映射对象
	 * @param actionName	当前请求路径
	 * @return				返回配置文件中代表action节点的ActionMapping对象
	 */
	public ActionMapping getActionMapping(String actionName) {
		if(!allActions.containsKey(actionName)) {
			throw new RuntimeException("传入参数无法匹配到xml配置的name路径");
		}
		if (allActions.get(actionName) == null) {
			throw new RuntimeException("找不到xml配置的class路径");
		}
		
		return allActions.get(actionName);
	}
}
