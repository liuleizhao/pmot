<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="true" errorPage="error.jsp" %>
<%@ page info="我心情不太好"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
	<head>
		<title>SWFObject 2 static publishing example page</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
	</head>
	<body>
		<%
			pageContext.setAttribute("age", "22");
			pageContext.setAttribute("name", "suchaochen", PageContext.REQUEST_SCOPE);
			
			out.println("name:"+pageContext.getAttributesScope("name"));
			out.println("age:"+pageContext.getAttributesScope("age"));
		 %>
		 
		 <form action="#" method="post">
		 	<input name="name" />
		 	<input type="submit" />
		 </form>
	</body>
</html>
<%@ page import="com.cs.appoint.entity.BookInfo" %>