<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
<meta charset="utf-8">
<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
<title>用户授权</title>
</head>

<body>
	<div class="title">
		<h2>用户授权</h2>
	</div>
	<div class="main">
		<div class="context">
			<form action="${ctx}/backend/system/user/userRoleAuthorize"method="post" name="form" id="form">
				<input type="hidden" id="message" value="${message }" /> 
				<input type="hidden" name="userId" id="userId" value="${user.id }">
				<div class="query">
					<div class="query-conditions ue-clear">
						<div class="conditions staff ue-clear">
							<label>账号名称：</label> <input name="account" id="account"
								type="text" disabled="disabled" value="${user.account }" />
						</div>
						<div class="conditions staff ue-clear">
							<label>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</label> <input
								name="name" id="name" type="text" disabled="disabled"
								value="${user.name }" />
						</div>
						<br>
						<br>
						<div style="display:none">
						<c:forEach items="${userRoleList }" var="uRole">
						<input  id="role${statu.index }" name="oldRoleIds" value="${uRole.id}">
						</c:forEach>
						</div>
						<div class="mui-input-row" style="margin-top: 30px;margin-bottom: 40px">
							<label>角色：</label>
							<c:forEach items="${roleList}" var="role" varStatus="statu">
								<input style="width: 20px;" height="40px" type="checkbox"
									id="role${statu.index }" name="newRoleIds" value="${role.id}"
									<c:forEach items="${userRoleList }" var="uRole">
										<c:if test="${uRole.id==role.id}">checked</c:if></c:forEach> />
								<label for="role${statu.index }">${role.name}</label>
								<c:if test="${statu.count%6==0}">
									<br>
								</c:if>
							</c:forEach>
						</div>
					</div>
					<div class="conditions name ue-clear" style="margin-top: -15px">
						<input id="editBtn" type="button" value="确定" class="input_button">
						<input onClick="javascript:history.back(-1);" type="button"
							value="返回" class="input_button">
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		$("#userRole").css({
			"color" : "red"
		});
		var mess = $("#message").val();
		if (mess != null && $.trim(mess) != "") {
			$.dialog({
				title : '3秒后自动关闭',
				time : 3,
				content : mess,
				icon : 'succeed',
				yesFn : true
			});
		}
		$("#editBtn").click(function() {
			try {
			/* 	var count = $("input:checkbox[name='newRoleIds']:checked").length;
				if (count <= 0) {
					throw ("请选择角色");
				} */
				//提交表单
				$(this).validate.submin_form();
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


