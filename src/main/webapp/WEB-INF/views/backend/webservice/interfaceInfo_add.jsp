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
<body>
	<%@include file="../common/message.jsp" %>
	<div class="title">
		<h2>添加接口</h2>
	</div>
	<form class="classA mui-input-group" action="${ctx }/backend/webservice/interfaceInfo/add" method="post" name="form" id="form">
		<table cellpadding="8" cellspacing="1">
			<tr>
				<td class="field"><span class="txt-imp">*</span>接口编号</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="jkid" id="jkid" type="text" value="${interfaceInfo.jkid }"/>
						<span style="color:red;marign-left:20px;display:none;">接口编号已存在</span> 
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>接口名称</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="name" id="name" type="text" value="${interfaceInfo.name }"/>
					</div>
				</td>
			</tr>
			
			<tr>
				<td class="field"><span class="txt-imp">*</span>对应类名</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="className" id="className" type="text" value="${interfaceInfo.className }"/>
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>方法名</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="methodName" id="methodName" type="text" value="${interfaceInfo.methodName }"/>
					</div>
				</td>
			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>是否保存日志</td>
				<td class="text select_td" >
					<div class="mui-input-row">
						<select name=recordable id="recordable">
						<option value="">请选择</option>
						 <c:forEach items="${recordableList }" var="entity">
							<option value="${entity.value }" 
							<c:if test="${interfaceInfo.recordable.value == entity.value }">selected="selected"</c:if>>
							${entity.description}</option>
						</c:forEach>
						</select>
						<div class="triangle-down"></div>
					</div>
				</td>
				
				<td class="field">描述</td>
				<td class="text select_td" >
					<div class="mui-input-row">
						<input name="description" id="description" type="text" value="${interfaceInfo.description }"/>
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
		$("#addBtn").click(function() {
				try {
					$(this).validate.isNull($('#jkid').val(),'接口编号不能为空');
					$(this).validate.isNull($('#name').val(),'接口名称不能为空');
					$(this).validate.isNull($('#className').val(),'类名不能为空');
					$(this).validate.isNull($('#methodName').val(),'方法名不能为空');
					$(this).validate.isNull($('#recordable').val(),'请选择是否保存日志');
					if($("#jkid").next().is(":hidden")){
						//提交表单
						$(this).validate.submin_form();
					}
				} catch (e) {
					openErrorDialog(e,true,false,{time:3,title:"3秒后自动关闭"});
				}
			});
			
		$("#jkid").focus(function(){
			$("#jkid").next().hide();
		});
		//ajax验证类型code不能相同
		$("#jkid").bind("blur", function(e) {
			var a = $(this).val();
			if(!(a=$.trim(a))){
				return false;
			}
			$.ajax({
				url : "${ctx}/backend/webservice/interfaceInfo/checkJkIdIsExisted",
				data : {
					"jkid" : encodeURI(encodeURI(a))
				},
				success : function(data) {
					if(data){
						$("#jkid").next().show();
					}
				}
			});
		});
	});
</script>
</html>
