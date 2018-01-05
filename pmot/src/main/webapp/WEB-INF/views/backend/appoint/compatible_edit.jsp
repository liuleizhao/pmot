<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${appName }-修改预约兼容配置</title>
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
		<h2>修改预约兼容配置</h2>
	</div>
	<form class="classA mui-input-group" action="${ctx }/backend/appoint/compatible/edit" method="post" name="form" id="form">
		<input type="hidden" name="id" value="${bookInfoCompatible.id}" />
		<table cellpadding="8" cellspacing="1">
			<tr>
				<td class="field"><span class="txt-imp">*</span>检测站</td>
				<td class="text select_td">
					<div class="mui-input-row">
						<select name="stationId" id="stationId" class="q_select">
							<option value="${bookInfoCompatible.stationId}">${bookInfoCompatible.stationName}</option>
						</select>
						<div class="triangle-down"></div>
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>兼容时长（分钟）</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" name="compatibleValue" id="compatibleValue" value="${bookInfoCompatible.compatibleValue}"/>
					</div>
				</td>
			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>生效开始日期</td>
				<td class="text">
					<div class="mui-input-row">
						<input value="<fmt:formatDate value="${bookInfoCompatible.startDate }" pattern="yyyy-MM-dd" />" id="startDate" name="startDate" class="Wdate" type="text" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}'})" />
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>生效结束日期</td>
				<td class="text">
					<div class="mui-input-row">
						<input value="<fmt:formatDate value="${bookInfoCompatible.endDate }" pattern="yyyy-MM-dd" />" id="endDate" name="endDate" class="Wdate" type="text" onclick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" />
					</div>
				</td>
			</tr>
		</table>
		<div class="mui-button-row">
			<button type="button" id="editBtn" class="mui-btn mui-btn-green">保存</button>&nbsp;&nbsp;
			<button type="button" class="mui-btn" style="background: #E6F4FF;" onClick="javascript:history.back(-1);">返回</button>
		</div>
		
		
	</form>
</body>
<script type="text/javascript" src="${ctx}/static/backend/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
<script type="text/javascript">
	$(function() {
		$("#editBtn").click(
			function() {
				try {
					$(this).validate.isNull($('#stationId').val(),'检测站不能为空');
					$(this).validate.isNull($('#compatibleValue').val(),'兼容时长不能为空');
					$(this).validate.isNumber($('#compatibleValue').val(),'兼容时长必须是大于0的整数');
					$(this).validate.isNull($('#startDate').val(),'开始日期不能为空');
					$(this).validate.isNull($('#endDate').val(),'结束日期不能为空');
					$(this).validate.submin_form();
				} catch (e) {
					openErrorDialog(e,true,false,{time:3,title:"3秒后自动关闭"});
				}
		});
	});
</script>
</html>
