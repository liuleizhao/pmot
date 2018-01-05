<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${appName }-新增车辆类型</title>
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
		<h2>添加车辆类型</h2>
	</div>
	<form class="classA mui-input-group" action="${ctx }/backend/webservice/interfaceControlGeneral/add" method="post" name="form" id="form">
		<input type="hidden" name="stationName" id="stationName" />
		<table cellpadding="8" cellspacing="1">
			<tr>
				<td class="field"><span class="txt-imp">*</span>检测站名称</td>
				<td class="text select_td" >
					<div class="mui-input-row">
						<select name="stationId" id="stationId">
							<option value="">请选择</option>
						 	<c:forEach items="${stationList }" var="entity">
								<option value="${entity.id }"
									<c:if test="${interfaceControlGeneral.id == entity.id}">
										selected="selected"
									</c:if>
								>${entity.name}</option>
							</c:forEach>
						</select>
						<div class="triangle-down"></div>
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>检测站序列号</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" name="serialNumber" id="serialNumber" value="${interfaceControlGeneral.serialNumber }"> 
						<span id="check_account_exist" class="Prompt"></span>
					</div>
				</td>
			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>IP地址</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" name="ips" id="ips" placeholder="每个IP地址用$分开" value="${interfaceControlGeneral.ips }" /> 
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>状态</td>
				<td class="text select_td">
					<div class="mui-input-row">
						<select name="state">
							<option value="SYZ"
								<c:if test="${interfaceControlGeneral.state == SYZ}">selected="selected"</c:if>
							>启用</option>
							<option value="TY"
								<c:if test="${interfaceControlGeneral.state == TY}">selected="selected"</c:if>
							>停用</option>
						</select>
						<div class="triangle-down"></div> 
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
		$("#addBtn")
				.click(
						function() {
							try {
								$(this).validate.isNull($('#stationId').val(),'检测站名称不能为空');
								$(this).validate.isNull($('#serialNumber').val(),'检测站序列号不能为空');
								var ips = $("#ips").val();
								$(this).validate.isNull(ips, 'ip地址不能为空');
								$("#stationName").val($("#stationId option:selected").text());
								$(this).validate.submin_form();
							} catch (e) {
								openErrorDialog(e,true,false,{time:3,title:"3秒后自动关闭"});
							}
						});

	});
	
		// 验证用户名是否存在
 		 $("#account").bind('blur', function(e) {
			var a = $(this).val();
			$.ajax({
				url : '${ctx}/backend/webservice/interfaceControlGeneral/checkAccontIsExist',
				data : {
					'account' : encodeURI(encodeURI(a)),
				},
				success : function(data) {
					var msg = data.errorMessage;
					if ($.trim(msg) != "") {
						$("#check_account_exist").html("账号已存在");
						$("#check_account_exist").css('color', 'red');
						$("#account").val('');
					}  else {
						$("#check_account_exist").html("提交后，账号无法更改！");
						$("#check_account_exist").css('color', 'green');
					}
				}
			});
		});  
</script>
</html>
