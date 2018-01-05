<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ page import="java.util.Date"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>${appName }-取消验证码</title>
	<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
</head>
<style>
	html,body{
		overflow-y: hidden;
	}
</style>
	<body>
		<form  method="post" name="form" id="form" style="height: 80%">
		<table cellpadding="8" cellspacing="5" align="center" style="margin: 20px;border: 0px;width: 80%;height: 80%">
			<tr height="30px;">
				<td class="field"><span class="txt-imp">*</span>验证码</td>
				<td class="text" style="border: 0px;">
					<div class="mui-input-row">
						<input name="verifyCode" id="verifyCode" type="text" 
						value="${verifyCode }"/>
					</div>
				</td>
			</tr>
			<tr  height="30px;">
				<td width="100%" align="left" valign="top" colspan="4" style="margin-top: 10px;border: 0px;">
					<ul>
						<li>
							<span style="color: red;">*该验证为预约成功时收到的预约验证码</span>
						</li>
					</ul>
				</td>
		 	</tr>
			</table>
		</form>
	</body>
	<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
	<script type='text/javascript' src='${ctx}/static/backend/js/zDialog/zDrag.js'></script>
	<!--artDialog-->
	<script type="text/javascript" src="${ctx }/static/backend/js/validator.js"></script>
	<script type="text/javascript" src="${ctx }/static/backend/js/artDialog/jquery.artDialog.min.js"></script>
	<script type="text/javascript" src="${ctx }/static/backend/js/artDialog/artDialog.iframeTools.min.js"></script>
</html>
