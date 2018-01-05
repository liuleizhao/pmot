<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>编辑接口账户</title>
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
		<h2>编辑接口账户信息</h2>
	</div>
	<form class="classA mui-input-group" action="${ctx }/backend/webservice/interfaceControlGeneral/edit" method="post" name="form" id="form">
		<input value="${interfaceControlGeneral.id }" name="id" type="hidden"/>
		<table cellpadding="8" cellspacing="1">
			<tr>
				<td class="field"><span class="txt-imp">*</span>检测站名称</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" value="${interfaceControlGeneral.stationName }" readonly>
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>检测站序列号</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="serialNumber" id="serialNumber" type="text" value="${interfaceControlGeneral.serialNumber }"> 
					</div>
				</td>
			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>IP地址</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" name="ips" id="ips" value="${interfaceControlGeneral.ips }" placeholder="每个IP地址用$分开"> 
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>状态${interfaceControlGeneral.state}</td>
				<td class="text select_td">
					<div class="mui-input-row">
						<select name="state">
							<option value="SYZ"
								<c:if test="${interfaceControlGeneral.state == 'SYZ'}">selected="selected"</c:if>
							>启用</option>
							<option value="TY"
								<c:if test="${interfaceControlGeneral.state == 'TY'}">selected="selected"</c:if>
							>停用</option>
						</select>
						<div class="triangle-down"></div> 
					</div>
				</td>
			</tr>
	</table>
		<div class="mui-button-row">
			<button type="button" id="addBtn" class="mui-btn mui-btn-green">修改</button>&nbsp;&nbsp;
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
					$(this).validate.isNull($("#serialNumber").val(),"检测站序列号不能为空");
					$(this).validate.isNull($("#ips").val(), "ip地址不能为空");
					$(this).validate.submin_form();
				} catch (e) {
					openErrorDialog(e,true,false,{time:3,title:"3秒后自动关闭"});
				}
		});
	});
</script>
</html>
