<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${appName }-新增全局参数</title>
<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/mui.min.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-reg.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
<link href="${ctx }/static/backend/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/static/backend/css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/backend/css/backend_common.css" />
</head>

<body>
	<%@include file="../common/message.jsp" %>
	<div class="title">
		<h2>添加全局参数</h2>
	</div>
	<form class="classA mui-input-group" action="${ctx }/backend/argument/globalConfig/add" method="post" name="form" id="form">
		<table cellpadding="8" cellspacing="1">
			<tr>
				<td class="field"><span class="txt-imp">*</span>参数名称</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" name="name" id="name" value="${globalConfig.name}"/> 
					</div>
				</td>
				<td class="field">描述</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" name="description" id="description" value="${globalConfig.description}"/>
					</div>
				</td>
			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>参数Key</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="dataKey" id="dataKey" type="text" value="${globalConfig.dataKey}"/>
						<span style="color:red;marign-left:20px;display:none;">参数Key已存在</span> 
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>参数值</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" name="dataValue" id="dataValue" value="${globalConfig.dataValue}"/>
					</div>
				</td>
			</tr>
		</table>
		<div class="mui-button-row">
			<button type="button" id="addBtn" class="mui-btn mui-btn-green">添加</button>&nbsp;&nbsp;
			<button type="button" class="mui-btn" style="background: #E6F4FF;"  onClick="javascript:history.back(-1);">返回</button>
		</div>
		
		
	</form>
</body>
<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
		
<script type="text/javascript">
	$(function() {
		$("#addBtn").click(
			function() {
				try {
					$(this).validate.isNull($('#name').val(),'参数名称不能为空');
					$(this).validate.isNull($('#dataKey').val(),'参数Key不能为空');
					$(this).validate.isNull($('#dataValue').val(),'参数值不能为空');
					if($("#dataKey").next().is(":hidden")){
						$(this).validate.submin_form();
					}
				} catch (e) {
					openErrorDialog(e,true,false,{time:3,title:"3秒后自动关闭"});
				}
		});

		$("#dataKey").focus(function(){
			$("#dataKey").next().hide();
		});
		//ajax验证类型Key不能相同
		$("#dataKey").bind("blur", function(e) {
			var a = $(this).val();
			if(!(a=$.trim(a))){
				return false;
			}
			$.ajax({
				url : "${ctx}/backend/argument/globalConfig/checkDataKeyIsExisted",
				data : {
					"dataKey" : encodeURI(encodeURI(a))
				},
				success : function(data) {
					if(data){
						$("#dataKey").next().show();
					}
				}
			});
		});
	});
</script>
</html>
