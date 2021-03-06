<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="m" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>宁康园管理平台</title>
<link rel="stylesheet" href="css/all.css" />
<link rel="stylesheet" href="css/jquery/easyui.css" />
<script type="text/javascript" src="js/common/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/common/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/common/jquery/datagrid-detailview.js"></script>
<script type="text/javascript" src="js/common/common.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/resizeimg/lrz.pc.min.js?v=20160714"></script>
<script type="text/javascript" src="js/resizeimg/localResizeIMG.js?v=20160714"></script>
<script type="text/javascript" src="js/openDialog.js"></script>
<script type="text/javascript" src="js/vip/vipmedirec.js"></script>
<style type="text/css">
.tabs-title {
    font-size: 14px !important;
    font-weight: bold;
}
textarea{ resize:none; width:600px; height:150px;}
</style>
</head>
<body class="easyui-layout">

<div data-options="region:'center',title:'电子病历'" class="regionCenter">	
<input type="hidden" id="examVipCode" name="examVipCode" value="${vipCode}" />	
<input type="hidden" id="examId" name="examId" value="" />	
<input type="hidden" id="type" name="type" value="${type}" />	
<button id="mr_add" class="btn btn-success">创建病历</button> 	
<button id="mr_back" class="btn btn-success">返&nbsp;&nbsp;回</button> 
<div id="medi_table" style="width: 100%; height: 80%;" >   
<table id="medirec_list_table"></table>	
</div>	
<div id="medirec_list_dialog">
    <div class="easyui-tabs">
      <div title="基础资料">
        <table style="border-collapse: separate; border-spacing: 10px;">
          <tr>
            <td>体检医院</td>
            <td><input type="text" id="exam_hos" style="width:200px;"/></td>
          </tr>
          <tr>
            <td>体检时间</td>
            <td> <input class="easyui-datebox" data-options="formatter:myformatter,parser:myparser" style="width: 200px;" name="exam_date" id="exam_date" />
            </td>
          </tr>
          <tr>
            <td>体检描述</td>
            <td><input type="text" id="exam_desc" style="width:200px;"/></td>
          </tr>
          <tr>
            <td><button id="submit_exam" class="btn btn-success">提交</button> </td>
          </tr>
        </table>
      </div>
      <div title="体检数据" style="padding: 10px">
      
       <table style="border-collapse: separate; border-spacing: 10px;">
          <tr>
            <td>体检指标</td>
            <td> 
            <select id="exam_norm" onchange="showNorm()" style="width: 200px;" >
            	<option value=''>--请选择--</option>
           		<c:forEach items="${norms}" var="list">
         			<option value ="${list.dicName}" lowVal ="${list.dicRemark}" highVal ="${list.dicRemark1}" normUint="${list.belongName}" >${list.dicValue}</option> 
          		</c:forEach>
       		</select>
			 </td>
          	 <td >检测结果</td>
	         <td><input type="text" id="exam_value" style="width:200px;" onchange="updateNormStatus()" /> 
			 </td>
			 <td>
			 </td>
          </tr>
          <tr>
			<td>指标分析</td>
            <td><select id="exam_status" name="exam_status" style="width:200px;">
            		<option value=''>--请选择--</option>
					<m:getItems name="inspect_is_normal" />
				</select>
			</td>
			<td>指标区间</td>
			<td><span id="normscope"></span></td>
			<td>
				<button id="add_exam_data" class="btn btn-success">添加指标</button> 
			</td>
          </tr>
        </table>

        <br> <br>
        <table class="easyui-datagrid" id="exam_norm_table"></table>
      </div>
      <div title="诊断意见" style="padding: 10px">
      	<table>
          <tr>
            <td>诊断意见汇总</td>
            <td><textarea rows="8" id="sum_up" style="max-width: 600px;esize:none;overflow:auto;" onchange="saveExamInfo();"></textarea></td>
          </tr>
          <tr>
            <td><button id="submit_sumup" class="btn btn-success">提交</button> </td>
          </tr>
        </table>
      </div>
      <div title="体检图片" style="padding: 10px">
        <table>
         <tr>
            <td>图片名称</td>
            <td><input type="text" id="exam_pic_name" style="width:200px;"/></td>
          </tr>
          <tr>
            <td colspan='2'><input type="file" name="exam_img" id="exam_img" style="display: none;" onchange="imgUpload(this,imgCallback);" /> <a id="add_exam_image" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加图片</a></td>
          </tr>
        </table>
        <table class="easyui-datagrid" id="exam_image_table">
        </table>
      </div>
    </div>
  </div>
  </div>
  
    <div class="cover" onclick="closepic()" style="position: fixed; top: 0px; left: 0px; width: 100%; height: 100%; z-index: 999999999999999999; background: rgba(0, 0, 0, .5); display: none;"></div>
  <div class="img-con" style="position: fixed; top: 0px; left: 0px; z-index: 999999999999999999999999;  display: none; height:600px; overflow:auto;">
    <img src="" />
  </div>	
</body>
</html>
