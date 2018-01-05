<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${appName }大客户预约查看详情</title>
<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/mui.min.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-reg.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
<link href="${ctx }/static/backend/css/password_strength_style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/backend/css/backend_common.css" />

</head>
<style>
.testresult span {
	padding: 0px 10px 0px 0px;
}
</style>

<body>
	<div class="title">
		<h2>大客户预约查看详情</h2>
	</div>
	<div class="classA mui-input-group" >
		<table cellpadding="8" cellspacing="1">
			<tr>
				<td class="field">企业名称</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="companyName" id="companyName" type="text" value="${compApplyFrom.companyName }" readonly="readonly"/> <span id="companyName_info" class="Prompt"></span>
					</div>
				</td>
				<td class="field">申请数量</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" name="applyCount" id="applyCount" value="${compApplyFrom.applyCount }" readonly="readonly" /> <span id="applyCount_info" class="Prompt"></span>
					</div>
				</td>
			</tr>

			<tr>
				<td class="field">企业社会信用代码</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="enterpriseCrediteCode" id="enterpriseCrediteCode" type="text" value="${compApplyFrom.enterpriseCrediteCode }" readonly="readonly"/> <span id="compApplyFrom_info" class="Prompt"></span>
					</div>
				</td>
				<td class="field">组织机构代码</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="organizationCode" id="organizationCode" type="text" value="${compApplyFrom.organizationCode }" readonly="readonly"> <span id="organizationCode_info" class="Prompt"></span>
					</div>
				</td>
				
			</tr>
			<tr>
				<td class="field">起始日期</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="startDate" id="startDate" type="text" value="<fmt:formatDate value="${compApplyFrom.startDate }" pattern="yyyy-MM-dd" />" readonly="readonly"/> <span id="startDate_info" class="Prompt"></span>
					</div>
				</td>
				<td class="field">终止日期</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="endDate" id="endDate" type="text" value="<fmt:formatDate value="${compApplyFrom.endDate }" pattern="yyyy-MM-dd" />" readonly="readonly"/> <span id="endDate_info" class="Prompt"></span>
					</div>
				</td>
			</tr>
			
			<tr>
				<td class="field">申请备注</td>
				<td class="text" colspan="3">
					<textarea rows="5" cols="50" name="discription" id="discription" readonly="readonly">${compApplyFrom.discription }</textarea>
				</td>
			</tr>
			<tr>
				<td class="field">审批人</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="startDate" id="startDate" type="text" value="${compApplyFrom.approverName}" readonly="readonly"/> <span id="startDate_info" class="Prompt"></span>
					</div>
				</td>
				<td class="field">审批日期</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="endDate" id="endDate" type="text" value="<fmt:formatDate value="${compApplyFrom.approveDate }" pattern="yyyy-MM-dd" />" readonly="readonly"/> <span id="endDate_info" class="Prompt"></span>
					</div>
				</td>
			</tr>
			<tr>
				<td class="field">审批结果</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="startDate" id="startDate" type="text" value="${compApplyFrom.approveRemart.description }" readonly="readonly"/> <span id="startDate_info" class="Prompt"></span>
					</div>
				</td>
				<td class="field">审批备注</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="endDate" id="endDate" type="text" value="${compApplyFrom.approveDiscription }" readonly="readonly"/> <span id="endDate_info" class="Prompt"></span>
					</div>
				</td>
			</tr>
		</table>
	</div>
	
 	<c:if test="${!empty compApplyFrom.addedNum  && compApplyFrom.addedNum > 0 && backend_user_session.userType == 'STATION'}">
			<div class="mui-button-row">
			<button type="button" onClick="window.location.href='${ctx }/backend/appoint/comApllyBookList?compApplyFromId=${compApplyFrom.id }'" class="mui-btn mui-btn-green">查看已预约列表->></button>&nbsp;&nbsp;
		</div>
	</c:if> 
	
	
</body>
<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
<script type="text/javascript">
	$(function(){
		$("#addBtn").click(
			function() {
				try {
					$(this).validate.isNull($("#companyName").val(),"企业名称不能为空");
					$(this).validate.isNull($("#applyCount").val(), "申请数量");
					$(this).validate.isNumber($("#applyCount").val(), "申请数量只能是数字");
					
					$(this).validate.isNull($("#enterpriseCrediteCode").val(),"企业社会信用代码");
					$(this).validate.isNull($("#organizationCode").val(), "组织机构代码");
					$(this).validate.isNull($("#startDate").val(), "起始日期");
					$(this).validate.isNull($("#endDate").val(), "终止日期");
					$(this).validate.isNull($("#discription").val(), "终止日期"); 
					//提交表单
					$(this).validate.submin_form();
				} catch (e) {
					openErrorDialog(e,true,false,{time:3,title:"3秒后自动关闭"});
				}
		});	
				
	});
	
</script>
</html>