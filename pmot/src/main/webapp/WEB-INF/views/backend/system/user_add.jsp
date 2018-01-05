<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${appName }添加用户</title>
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
		<h2>添加用户</h2>
	</div>
	<form class="classA mui-input-group" action="${ctx }/backend/system/user/add" method="post" name="form" id="form">
		<table cellpadding="8" cellspacing="1">
			<tr>
				<td class="field"><span class="txt-imp">*</span>账号名称</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="account" id="account" type="text" value="${user.account }"/> <span id="account_info" class="Prompt"></span>
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>姓名</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="name" id="name" type="text" value="${user.name }"/> <span id="name_info" class="Prompt"></span>
					</div>
				</td>
			</tr>

			<tr>
				<td class="field"><span class="txt-imp">*</span>密码</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="password" id="password" type="password"> <span id="password_info" class="Prompt"></span>
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>确认密码</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="password" name="confirm_pwd" id="confirm_pwd"> <span id="confirm_pwd_info" class="Prompt"></span>
					</div>
				</td>
			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>手机号</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="mobile" id="mobile" type="text" value="${user.mobile }"/> <span id="mobile_info" class="Prompt"></span>
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>身份证号</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="idNumber" id="idNumber" type="text" value="${user.idNumber }"/> <span id="idNumber_info" class="Prompt"></span>
					</div>
				</td>
			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>性别</td>
				<td class="text">
					<div class="mui-input-row">
						<span class="chose_span">	
							<input class="" type="radio" name="sex" value="1" <c:if test="${user.sex ==1 }">checked="checked"</c:if> />男
						</span>
						<span class="chose_span">
							<input class="" type="radio" name="sex" value="0" <c:if test="${user.sex ==0 }">checked="checked"</c:if>/>女
						</span>
					</div>
				</td>
				<td class="field">职务</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="post" id="post" type="text" value="${user.post }"/>
					</div>
				</td>
			</tr>
			<tr>
				<td class="field">邮箱</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="email" id="email" type="text" value="${user.email }"/>
					</div>
				</td>

				<c:choose>
					<c:when test="${organizationList!=null&&organizationList!=''}">
						<td class="field"><span class="txt-imp">*</span>组织机构</td>
						<td class="text select_td">
							<div class="mui-input-row">
								<select name="orgId" id="selectedStation">
									<option value="">请选择</option>
									<c:forEach items="${organizationList }" var="entity">
										<option value="${entity.id }">${entity.code}：${entity.name}</option>
									</c:forEach>
								</select>
								<div class="triangle-down"></div>
							</div>
						</td>
					</c:when>
					<c:otherwise>
						<input name="orgId" id="selectedStation" value="2" type="hidden" />
					</c:otherwise>
				</c:choose>
			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>用户类型</td>
				<td class="text select_td" >
					<div class="mui-input-row">
						<select name="userType" id="userType">
						<c:if test="${backend_user_session.userType == 'ADMIN' }">
						<option value="">请选择</option>
							 <c:forEach items="${userTypeList }" var="entity">
									<option value="${entity.value }" <c:if test="${user.userType.value == entity.value }">selected="selected"</c:if>>
										${entity.description}</option>
							</c:forEach>
						</c:if>
						<c:if test="${ backend_user_session.userType == 'STATION'}">
							<option value="STATION">检测站用户</option>
						</c:if>
						</select>
						<div class="triangle-down"></div>
					</div>
				</td>
				<td class="field">所属检测站</td>
				<td class="text select_td" >
					<div class="mui-input-row">
						<select name="stationId" id="stationId">
							<option value="">请选择</option>
						 <c:forEach items="${stationInfoList }" var="entity">
										<option value="${entity.id }" <c:if test="${user.stationId == entity.id }">
										selected="selected"</c:if>>${entity.name}</option>
									</c:forEach>
						</select>
						<div class="triangle-down"></div>
					</div>
				</td>
			</tr>
			
		</table>
<!-- 		<div class="mui-button-row">
			<input type="button" class="input_button" value="添加" id="addBtn"> 
			<input type="button" class="input_button" onClick="javascript:history.back(-1);" value="返回">
		</div> -->
		<div class="mui-button-row">
			<button type="button" id="addBtn" class="mui-btn mui-btn-green">添加</button>&nbsp;&nbsp;
			<button type="button" class="mui-btn" style="background: #E6F4FF;"  onClick="javascript:history.back(-1);">返回</button>
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
		var mess = $("#mess").val();
		if (mess != null && $.trim(mess) != "") {
			$.dialog({
				title : '3秒后自动关闭',
				time : 3,
				content : mess,
				icon : 'succeed',
				yesFn : true
			});
		}
		 var reg = /^([A-Za-z]|[0-9])+$/;
		$("#addBtn")
				.click(
						function() {
							try {
								$(this).validate.isNull($('#account').val(),
										'账户名称不能为空');
										debugger;
								if(!reg.test($('#account').val())){
								throw ('账号只能是字母和数字');
										
										}
								$(this).validate.isNull($('#name').val(),
										'姓名不能为空');
								/* if(!reg.test($('#name').val())){
								throw ('姓名只能是中文和字母');} */
								$(this).validate.isNull($('#password').val(),
										'密码不能为空');
								$(this).validate.strLength(
										$('#password').val(), 6, 50,
										'密码长度应为大于6位');
								var confirm_pwd = $('#confirm_pwd').val();
								$(this).validate.isNull(confirm_pwd, '请再次输入密码');
								if ($.trim($('#password').val()) != $
										.trim(confirm_pwd)) {
									throw ('两次输入的密码不一致');
								}
								$(this).validate.isNull($('#mobile').val(),
										'手机号码不能为空');
								$(this).validate.isMobile($('#mobile').val(),
										'手机号码格式有误，请重新输入');
								$(this).validate.isNull($('#idNumber').val(),
										'身份证号不能为空');
								/* $(this).validate.isCardFormat($('#idNumber')
										.val(), '身份证号格式有误，请重新输入'); */

								if ($('#email').val() != null
										&& $.trim($('#email').val()) != "") {
									$(this).validate.isMail($('#email').val(),
											'email格式有误，请重新输入');
								}

								var count = $("input:radio[name='sex']:checked").length;
								if (count <= 0) {
									throw ("请选择性别");
								}
								/* var orgId = $("#selectedStation").val();
								if (orgId == null || $.trim(orgId) == "") {
									throw ('此类型用户需选择对应的组织');
								} */
								var userType = $('#userType').val();
								$(this).validate.isNull(userType,'用户类型不能为空');
								if(userType == 'STATION'){
									$(this).validate.isNull($('#stationId').val(),'所属检测站不能为空');
								}
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
						
		$("#confirm_pwd").bind('blur', function(e) {
			if($(this).val()!=$("#password").val()){
				$("#confirm_pwd_info").css('color', 'red');
				$("#confirm_pwd_info").html("两次输入密码不同");
			}
		})
		
		$("#name").bind('blur', function(e) {
			var a = $(this).val();
			var reg1 = /^([A-Za-z]|[\u4E00-\u9FA5])+$/;
			if(reg1.test(a)){
				$("#name_info").css('color', 'green');
				$("#name_info").html("姓名可以使用");
			}else{
				$("#name_info").css('color', 'red');
				$("#name_info").html("只能是中文和字母");
				$("#name").val('');
			}
		})
		var accountReg = /^[A-Za-z0-9]+$/;
		//ajax验证类型code不能相同
		$("#account").bind('blur', function(e) {
			var a = $(this).val();
			if(accountReg.test(a)){
			 $.ajax({
				url : '${ctx}/backend/system/user/checkAccount',
				data : {
					'account' : encodeURI(encodeURI(a))
				},
				success : function(data) {
					var isBlank = data.isBlank;
					var isExist = data.isExist;
					if (!isBlank && isExist) {
						$("#account_info").css('color', 'red');
						$("#account_info").html("账户名已存在");
						$("#account").val('');
					} else if (isBlank) {
						$("#account_info").css('color', 'red');
						$("#account_info").html("请输入代码");
					} else {
						$("#account_info").css('color', 'green');
						$("#account_info").html("账户名可以使用");
					}
				}
			}); }else{
					$("#account_info").css('color', 'red');
					$("#account_info").html("账户由字母和数字组成");
					$("#account").val('');
			}
		});
	});
</script>
</html>