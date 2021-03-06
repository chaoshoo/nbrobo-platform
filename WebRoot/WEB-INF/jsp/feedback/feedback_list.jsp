<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="m" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>宁康园管理平台</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<link rel="stylesheet" href="css/jquery/easyui-icon/icon.css"/>
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/feedback/feedback_list.js"></script>

<style type="text/css">

	#feedback_name,#feedback_linkman,#feedback_linkphone {
		width: 350px;
	}
	
	textarea {
		resize: none;
		width: 350px;
	}
</style>

</head>
<body class="easyui-layout">
		<div id="common_search" class="common_search common_search_nopadding">
			&nbsp;&nbsp;&nbsp;&nbsp;建议内容：<input type="text"
				id="feedback_content1" /> &nbsp;&nbsp;&nbsp;&nbsp;是否处理：<select id="is_handle1">
				                <option value="">--请选择--</option>
								<m:getItems name="isPopular" />
						</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
        <button type="button" id="feedback_search" class="btn btn-success" ><i class="icon-search"></i>&nbsp;查询</button>
        <button type="button" id="feedback_reset"
				class="btn btn-success"><i class="icon-refresh"></i>&nbsp;重置</button>
		</div>
		<table id="feedback_table"></table>
		
		<div id="feedback_add_dialog">
			<form action="" id="feedback_form">
				<input type="hidden" id="id">
				<table style="border-collapse: separate;border-spacing: 10px;">
					<tr>
						<td>类型：</td>
						<td><input disabled="disabled" class="easyui-combobox" id="feedback_type">
						</td>
					</tr>
					<tr>
						<td>建议内容：</td>
						<td><textarea disabled="disabled" style="width:300px;height:110px"
							id="feedback_content"></textarea>
						</td>
					</tr>
					<tr>
						<td>联系方式：</td>
						<td><input disabled="disabled" type="text" id="contact_way">
						</td>
					</tr>
					<tr>
					<td>备注：</td>
					<td><textarea style="width:300px;height:110px"
							id="remark"></textarea></td>
				</tr>
				</table>
			</form>
		</div>
</body>
</html>