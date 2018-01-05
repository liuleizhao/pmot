<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${appName }-编辑接口参数</title>
<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/mui.min.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-reg.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
<link href="${ctx }/static/backend/css/password_strength_style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/backend/css/backend_common.css" />
</head>
<body>
	<%@include file="../common/message.jsp" %>
	<div class="title">
		<h2>编辑接口</h2>
	</div>
	<form class="classA mui-input-group" action="${ctx }/backend/webservice/interfaceParameter/edit" method="post" name="form" id="form">
		<input type="hidden" name="id" value="${interfaceParameter.id }"/>
		<table cellpadding="8" cellspacing="1">
			<tr>
				<td class="field"><span class="txt-imp">*</span>参数名称</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="name" id="name" type="text" value="${interfaceParameter.name }"/>
						<span id="account_info" class="Prompt"></span>
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>参数类型</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="type" id="type" type="text" value="${interfaceParameter.type }"/>
					</div>
				</td>
			</tr>
			<tr>
				<td class="field">描述</td>
				<td class="text select_td" colspan="3">
					<div class="mui-input-row">
						<input name="description" id="description" type="text" value="${interfaceParameter.description }"/>
					</div>
				</td>
			</tr>
			
		</table>
			<div class="mui-button-row">
			<button type="button" id="editBtn" class="mui-btn mui-btn-green">编辑</button>&nbsp;&nbsp;
			<button type="button" class="mui-btn" style="background: #E6F4FF;"  onClick="javascript:history.back(-1);">返回</button>
		</div>
	</form>
</body>
<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
<script type="text/javascript">
	$(function() {
		$("#editBtn").click(function(){
			try {
				$(this).validate.isNull($('#name').val(),'参数名称不能为空');
				$(this).validate.isNull($('#type').val(),'参数类型不能为空');
				//提交表单
				$(this).validate.submin_form();
			} catch (e) {
				openErrorDialog(e,true,false,{time:3,title:"3秒后自动关闭"});
			}
		});
		
	});
</script>
</html>