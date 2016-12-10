
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>宁康园管理平台</title>
<link rel="stylesheet" type="text/css" href="css/common.css">
<link rel="stylesheet" type="text/css" href="css/main.css">
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/common/jquery/jquery.easyui.min.js"></script>
</head>
<body>
	<div class="mbx">
		<div class="inn">
			<span class="fcB">个人信息</span>
		</div>
	</div>
	<div class="nrP">
		<div class="xgP" id="content">
			<ul class="xgC">
				<li><label class="label">用户ID:</label>&nbsp; ${user.userId}  </li>
				<li><label class="label">登录账号:</label>&nbsp; ${user.userMail}</li>
				<li><label class="label">用户名称:</label>&nbsp; ${user.userName}</li>
				<li><label class="label">部门总称:</label>&nbsp; ${user.group_name} </li>
				<li><label class="label">创建时间:</label>&nbsp; ${createtimestr}</li>
				<li><label class="label">备注信息:</label>&nbsp; ${user.remark}</li>
			</ul>
		</div>
	</div>
</body>
</html>