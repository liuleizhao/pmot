<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<title>${appName }-编辑用户</title>
		<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/mui.min.css" />
		<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
		<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-reg.css" />
		<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
		<link rel="stylesheet" href="${ctx}/static/backend/css/backend_common.css" />
	</head>
	
	<body>
	 
		<div class="title">
			<h2>编辑用户</h2>
		</div>
			<form class="classA mui-input-group" action="${ctx }/backend/system/user/edit" method="post" name="form" id="form">
				<table cellpadding="8" cellspacing="1">
					<tr>
					<input  name="id" id="id" type="hidden" value="${userVO.id }">
						<td class="field"><span class="txt-imp">*</span>账号名称</td>
						<td class="text">
							<div >
								<input name="account" id="account" type="text"  readonly="readonly" value="${userVO.account }">
								<span class="Prompt"></span>
							</div>
						</td>
						<td class="field"><span class="txt-imp">*</span>姓名</td>
						<td class="text">
							<div class="mui-input-row">
								<input  name="name" id="name" type="text" value="${userVO.name }">
								<span class="Prompt"></span>
							</div>
						</td>
					</tr>
					<tr>
						<td class="field"><span class="txt-imp">*</span>手机号码</td>
						<td class="text">
							<div class="mui-input-row">
								<input name="mobile" id="mobile" type="text" value="${userVO.mobile }">
								<span class="Prompt"></span>
							</div>
						</td>
						<td class="field"><span class="txt-imp">*</span>身份证号</td>
						<td class="text">
							<div class="mui-input-row">
								<input name="idNumber" id="idNumber" type="text" value="${userVO.idNumber }">
								<span class="Prompt"></span>
							</div>
						</td>
					</tr>
					<tr>
						<td class="field"><span class="txt-imp">*</span>性别</td>
						<td class="mui-input-row">
						<span class="chose_span">
							<input class="" type="radio" name="sex" value="1" <c:if test="${userVO.sex == 1}">checked="checked"</c:if>/>男
						</span>
						<span class="chose_span">
							<input class="" type="radio" name="sex" value="0" <c:if test="${userVO.sex == 0}">checked="checked"</c:if>/>女
						</span>
						</td>
						<td class="field">职务</td>
						<td class="text">
							<div class="mui-input-row">
								<input name="post" id="post" type="text" value="${userVO.post}">
								<span class="Prompt"></span>
							</div>
						</td>
					</tr>
					<tr>
						<td class="field">邮箱</td>
						<td class="text">
							<div class="mui-input-row">
								<input name="email" id="email" type="text" placeholder="" value="${userVO.email }">
								<span class="Prompt"></span>
							</div>
						</td>
						<td class="field"><span class="txt-imp">*</span>用户状态</td>
						<td class="text select_td" >
							<div class="mui-input-row">
								<select name="status" id="status">
								 <c:forEach items="${userStatusList }" var="entity">
									<option value="${entity.value }" 
									<c:if test="${userVO.status.value == entity.value }">selected="selected"</c:if>>${entity.description}</option>
								 </c:forEach>
								</select>
								<div class="triangle-down"></div>
							</div>
						</td>
					</tr>
					<tr>
						<td class="field"><span class="txt-imp">*</span>用户类型</td>
						<td class="text select_td">
							<div class="mui-input-row">
								<select name="userType" id="userType">
									<c:if test="${backend_user_session.userType == 'ADMIN' }">
									<option value="">请选择</option>
										 <c:forEach items="${userTypeList }" var="entity">
												<option value="${entity.value }" <c:if test="${userVO.userType.value == entity.value }">selected="selected"</c:if>>
													${entity.description}</option>
										</c:forEach>
									</c:if>
									<c:if test="${ backend_user_session.userType == 'STATION'}">
										<option value="STATION" selected="selected">检测站用户</option>
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
												<option value="${entity.id }" <c:if test="${userVO.stationId == entity.id }">
												selected="selected"</c:if>>${entity.name}</option>
											</c:forEach>
								</select>
								<div class="triangle-down"></div>
							</div>
						</td>
					</tr>
					<tr>
						<td class="field"><span class="txt-imp">*</span>组织机构</td>
						<td class="text" colspan="3">
							<div >
							   	<input name="orgId" id="orgId" type="hidden"  value="${userVO.orgId }">
								<input name="orgName" id="orgName" type="text" value="${userVO.orgName}" readonly="readonly">
								<span class="Prompt"></span>
							</div>
						</td>
					</tr>
					<tr>
						<td class="field">登录IP</td>
						<td class="text" colspan="3">
							<div class="mui-input-row">
								<input name="ip" id="ip" type="text" value="${userVO.ip }"/>
								 <span class="Prompt">如需添加多个ip，用$符合隔开</span>
							</div>
						</td>
					</tr>
				</table>
				<div class="mui-button-row">
					<button type="button" id="editBtn" class="mui-btn mui-btn-green">保存</button>&nbsp;&nbsp;
					<button type="button" class="mui-btn" style="background: #E6F4FF;"  onClick="javascript:history.back(-1);">返回</button>
				</div>
			</form>
	</body>
<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/passwordStrength/password_strength_plugin.js"></script>
<script type="text/javascript">
	$("#editBtn")
				.click(
						function() {
							try {
							    var reg = /^([A-Za-z]|[\u4E00-\u9FA5])+$/;
								$(this).validate.isNull($('#account').val(),'账户名称不能为空');
								$(this).validate.isNull($('#name').val(),'姓名不能为空');
								if(!reg.test($('#name').val())){
								throw ('姓名只能是中文和字母');}
								$(this).validate.isNull($('#mobile').val(),'手机号码不能为空');
							    $(this).validate.isMobile($('#mobile').val(), '手机号码格式有误，请重新输入');
							    $(this).validate.isNull($('#idNumber').val(),'身份证号不能为空');
							    $(this).validate.isCardFormat($('#idNumber').val(), '身份证号格式有误，请重新输入');
							    
								if ($('#email').val() != null&& $.trim($('#email').val()) != "") {
									$(this).validate.isMail($('#email').val(),'email格式有误，请重新输入');
								}
								
								var count = $("input:radio[name='sex']:checked").length;
								if(count<=0){
									throw ("请选择性别");
								}
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
</script>
</html>