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
			// 1. 获取请求uri, 得到请求路径名称   【login】
			String uri = request.getRequestURI();
			// 得到 login
			String actionName = uri.substring(uri.lastIndexOf("/") + 1, uri.indexOf(".action"));
			// 2. 根据路径名称，读取配置文件，得到类的全名   【com.zhangt.LoginAction】
			ActionMapping actionMapping = actionMappingManager.getActionMapping(actionName);
			String className = actionMapping.getClassName();
			// 当前请求的处理方法   【method="login"】
			String method = actionMapping.getMethod();
			
			// 3. 反射： 创建对象，调用方法； 获取方法返回的标记
			Class<?> clazz = Class.forName(className);
			Object object = clazz.newInstance();// LoginAction loginAction = new LoginAction();
			Method m = clazz.getDeclaredMethod(method, HttpServletRequest.class, HttpServletResponse.class);
			// 调用方法返回的标记
			String returnFlag = (String) m.invoke(object, request, response);
			
			// 4. 拿到标记，读取配置文件得到标记对应的页面 、 跳转类型
			Result result = actionMapping.getResults().get(returnFlag);
			// 类型
			String type = result.getType();
			// 页面
			String page = result.getPage();
			
			// 跳转
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
