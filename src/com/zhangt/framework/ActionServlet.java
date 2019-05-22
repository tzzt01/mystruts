package com.zhangt.framework;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhangt.framework.bean.ActionMapping;
import com.zhangt.framework.bean.ActionMappingManager;
import com.zhangt.framework.bean.Result;

public class ActionServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ActionMappingManager actionMappingManager;
	
	@Override
	public void init() throws ServletException {
		actionMappingManager = new ActionMappingManager();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 1. ��ȡ����uri, �õ�����·������   ��login��
			String uri = request.getRequestURI();
			// �õ� login
			String actionName = uri.substring(uri.lastIndexOf("/") + 1, uri.indexOf(".action"));
			// 2. ����·�����ƣ���ȡ�����ļ����õ����ȫ��   ��com.zhangt.LoginAction��
			ActionMapping actionMapping = actionMappingManager.getActionMapping(actionName);
			String className = actionMapping.getClassName();
			// ��ǰ����Ĵ�����   ��method="login"��
			String method = actionMapping.getMethod();
			
			// 3. ���䣺 �������󣬵��÷����� ��ȡ�������صı��
			Class<?> clazz = Class.forName(className);
			Object object = clazz.newInstance();// LoginAction loginAction = new LoginAction();
			Method m = clazz.getDeclaredMethod(method, HttpServletRequest.class, HttpServletResponse.class);
			// ���÷������صı��
			String returnFlag = (String) m.invoke(object, request, response);
			
			// 4. �õ���ǣ���ȡ�����ļ��õ���Ƕ�Ӧ��ҳ�� �� ��ת����
			Result result = actionMapping.getResults().get(returnFlag);
			// ����
			String type = result.getType();
			// ҳ��
			String page = result.getPage();
			
			// ��ת
			if ("redirect".equals(type)) {
				response.sendRedirect(request.getContextPath() + page);
			} else {
				request.getRequestDispatcher(page).forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
