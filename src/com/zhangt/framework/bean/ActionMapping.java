package com.zhangt.framework.bean;

import java.util.Map;

/**
 * ��װaction�ڵ�
 * <action name="login" class="com.zhangt.Servlet.ActionServlet" method="login">
		<result name="success" type="redirect">./index.jsp</result>
		<result name="false">/login.jsp</result>
	</action>
 * @author Administrator
 *
 */
public class ActionMapping {
	// ����·������
	private String name;
	// ����action�������
	private String className;
	// ������
	private String method;
	// ������ͼ����
	private Map<String, Result> results;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Map<String, Result> getResults() {
		return results;
	}
	public void setResults(Map<String, Result> results) {
		this.results = results;
	}
}
