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
 * ���������ļ�����װ����mystruts.xml
 * @author Administrator
 *
 */
public class ActionMappingManager {
	//����action����
	private Map<String, ActionMapping> allActions;
	
	public ActionMappingManager() {
		allActions = new HashMap<String, ActionMapping>();
		this.init();
	}
	
	private void init() {
		// DOM4J��ȡ�����ļ�
		// 1. �õ�������
		SAXReader reader = new SAXReader();
		// �õ�src/mystruts.xml  �ļ���
		InputStream inputStream = this.getClass().getResourceAsStream("/myStruts.xml");
		try {
			// 2. �����ļ�
			Document document = reader.read(inputStream);
			// 3. ��ȡ��
			Element root = document.getRootElement();
			// 4. �õ�package�ڵ�
			Element ele_package = root.element("package");
			// 5. �õ�package�ڵ��£�  ���е�action�ӽڵ�
			List<Element> listAction = ele_package.elements("action");
			
			// 6.���� ����װ
			for (Element ele_action : listAction) {
				// 6.1 ��װһ��ActionMapping����
				ActionMapping actionMapping = new ActionMapping();
				actionMapping.setName(ele_action.attributeValue("name"));
				actionMapping.setClassName(ele_action.attributeValue("class"));
				actionMapping.setMethod(ele_action.attributeValue("method"));
				
				// 6.2 ��װ��ǰaciton�ڵ������еĽ����ͼ
				Map<String,Result> results = new HashMap<String, Result>();
				
				// �õ���ǰaction�ڵ������е�result�ӽڵ�
				 Iterator<Element> it = ele_action.elementIterator("result");
				 while (it.hasNext()) {
					 // ��ǰ������ÿһ��Ԫ�ض��� <result...>
					 Element ele_result = it.next();
					 
					 // ��װ����
					 Result res = new Result();
					 res.setName(ele_result.attributeValue("name"));
					 res.setType(ele_result.attributeValue("type"));
					 res.setPage(ele_result.getTextTrim());
					 
					 // ��ӵ�����
					 results.put(res.getName(), res);
				 }
				
				// ���õ�actionMapping��
				actionMapping.setResults(results);
				
				// 6.x actionMapping��ӵ�map����
				allActions.put(actionMapping.getName(), actionMapping);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��������·�����ƣ�����Action��ӳ�����
	 * @param actionName	��ǰ����·��
	 * @return				���������ļ��д���action�ڵ��ActionMapping����
	 */
	public ActionMapping getActionMapping(String actionName) {
		if(!allActions.containsKey(actionName)) {
			throw new RuntimeException("��������޷�ƥ�䵽xml���õ�name·��");
		}
		if (allActions.get(actionName) == null) {
			throw new RuntimeException("�Ҳ���xml���õ�class·��");
		}
		
		return allActions.get(actionName);
	}
}
