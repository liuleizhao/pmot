<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${appName }-修改密码</title>
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
		<h2>修改密码</h2>
	</div>
	<span id="errorMessage" style="display:none;">${errorMessage }</span>
	<form class="classA mui-input-group" action="${ctx}/backend/changePwd" method="post" name="form" id="form">
		<table cellpadding="8" cellspacing="1">
			<tr>
				<td class="field"><span class="txt-imp">*</span>原密码</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="password" name="password" id="password"/> <span id="password_info" class="Prompt"></span>
					</div>
				</td>
			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>新密码</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="password" name="newPwd" id="newPwd"/> <span id="password_info" class="Prompt"></span>
					</div>
				</td>
			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>确认新密码</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="password" name="confirm_pwd" id="confirm_pwd"/> <span id="confirm_pwd_info" class="Prompt"></span>
					</div>
				</td>
			</tr>
		</table>
		<div class="mui-button-row">
			<button type="button" id="updateBtn" class="mui-btn mui-btn-green">修改</button>&nbsp;&nbsp;
		</div>
	</form>
</body>
<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/passwordStrength/password_strength_plugin.js"></script>
<script type="text/javascript">
	$(function() {
		$("#password").passStrength({
			userid : "#account"
		});
		var mess = $("#errorMessage").html();
		if (mess != null && $.trim(mess) != "") {
			$.dialog({
				title : '3秒后自动关闭',
				time : 3,
				content : mess,
				icon : 'error',
				yesFn : true
			});
		}
		$("#updateBtn").click(
						function() {
							try {
								var password = $.trim($('#password').val());
								$(this).validate.isNull(password,'原密码不能为空');
								$(this).validate.strLength(password, 6, 50,'密码长度应为大于6位');
								
								$(this).validate.isNull($('#newPwd').val(),'新密码不能为空');
								$(this).validate.strLength($('#newPwd').val(), 6, 50,'新密码长度应为大于6位');
								var confirm_pwd = $('#confirm_pwd').val();
								$(this).validate.isNull(confirm_pwd, '请再次输入新密码');
								if ($.trim($('#newPwd').val()) != $.trim(confirm_pwd)) {
									throw ('两次输入的密码不一致');
								}
								$.post('${ctx}/backend/changePwd', {
									password : password,
									newPwd: $.trim($('#newPwd').val())
								}, function(data) {
									if(data.code == '1'){
									   try {
										 	throw(data.errorMessage);
										} catch (e1) {
											 $.dialog({
												title : '3秒后自动关闭',
												time : 3,
												content : e1,
												icon : 'error',
												yesFn : function(){
													window.parent.location = "${ctx}/backend/login";
												}
											});
										} 
									   }else
									   {
										    try {
											 	throw(data.errorMessage);
											} catch (e2) {
												 $.dialog({
													title : '3秒后自动关闭',
													time : 3,
													content : e2,
													icon : 'error',
													yesFn :true  
												});
											} 
									   }
								}, 'json');
							} catch (e) {
								$.dialog({
									title : '3秒后自动关闭',
									time : 3,
									content : e,
									icon : 'error',
									yesFn : true
								});
							}
						});
						
	});
</script>
</html>