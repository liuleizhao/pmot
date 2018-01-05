<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>${appName }-接口日志详情</title>
	<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/mui.min.css" />
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-reg.css" />
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
	<link rel="stylesheet" href="${ctx}/static/backend/css/backend_common.css" />
	<script type="text/javascript" src="${ctx}/static/backend/js/laydate-v1.1/laydate/laydate.js"></script>
</head>
<style>
.testresult span {
	padding: 0px 10px 0px 0px;
}
</style>

<body>
	<div class="title">
		<h2>接口日志详情</h2>
	</div>
	<input type="hidden" id="allowLoginTime" name="allowLoginTime"/>
	<input type="hidden"  name="id" value="${user.id }">
		<table cellpadding="8" cellspacing="1">
			<tr>
				<td class="field">接口编号</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" maxlength="30" value="${interfaceLog.jkid }" readonly>
						 <span id="account_info" class="Prompt"></span>
					</div>
				</td>
				<td class="field">访问ip</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" value="${interfaceLog.ip }" maxlength="50" readonly> 
						<span id="name_info" class="Prompt"></span>
					</div>
				</td>
			</tr>

			<tr>
				<td class="field">请求时间</td>
				<td class="text">
					<div class="mui-input-row">
						 <input type="text" 
							value="<fmt:formatDate value="${interfaceLog.requestDate }" pattern="yyyy-MM-dd" />"  readonly/>
					</div>
				</td>
				<td class="field">响应时间</td>
				<td class="text">
					<div class="mui-input-row">
						  <input type="text" 
							value="<fmt:formatDate value="${interfaceLog.responseDate }" pattern="yyyy-MM-dd" />"  readonly/>
					</div>
				</td>
			</tr>
			
			<tr>
				<td class="field">处理时长</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" value="${interfaceLog.runTime }" readonly/>
					</div>
				</td>
				<td class="text" colspan="2">
				</td>
			</tr>
			
			<tr height="200">
				<td class="field">请求内容</td>
				<td class="text" colspan="3" >
					<div >
						 <textarea  readonly="readonly" style="vetical-align:top;float:left;" rows="10"><c:out value="${fn:trim(interfaceLog.requestXml)}"/></textarea>
					</div>
				</td>
			</tr>
 			<tr height="200">
				<td class="field">响应内容</td>
				<td class="text" colspan="3" >
					<div>
						 <textarea readonly="readonly" style="vetical-align:top;float:left;"  rows="10"><c:out value="${fn:trim(interfaceLog.responseXml)}"/></textarea>
					</div>
				</td>
			</tr>
		</table>
		<div class="mui-button-row">
			<input type="button" class="input_button" onClick="javascript:history.back(-1);" value="返回">
		</div>
</body>
<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/passwordStrength/password_strength_plugin.js"></script>
</html>