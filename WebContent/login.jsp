<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
</head>
<body>
	<form action="${pageContext.request.contextPath }/login.action" method="post">
		用户名： <input type="text" name="name"/><br/>
		密	码：<input type="password" name="password"/><br/>
		<button type="submit">登录</button>
	</form>
</body>
</html>