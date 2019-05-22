# mystruts自定义框架
该代码初步实现了Struts的基本功能，目的是理解Struts框架的思想和实现方法。
# 知识点
1. ActionMappingManager.java文件中解析xml的方法；
2. ActionServlet.java文件中的反射创建对象的方法。
# 说明
如果用servlet实现注册和登录的功能，web.xml应该配置servlet包中的两个文件。但使用了properties配置文件和反射后，servlet包中的功能已经被framework包中的ActionServlet.java替代。因此，servlet包中的两个java文件已经无效了。
