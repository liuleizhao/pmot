<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${appName }-修改证件类型</title>
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
		<h2>修改证件类型基本信息</h2>
	</div>
	<input type="hidden" id="message" value="${message }" />
	<form class="classA mui-input-group" action="${ctx }/backend/argument/idType/edit" method="post" name="form" id="form">
		<input type="hidden" name="id" value="${idType.id }" />
		<table cellpadding="8" cellspacing="1">
			<tr>
				<td class="field"><span class="txt-imp">*</span>证件类型代码</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="hidden" id="preCode" value="${idType.code }" />
						<input name="code" id="code" type="text" type="text" value="${idType.code }" /> 
						<span style="color:red;marign-left:20px;display:none;">证件类型代码已存在</span> 
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>证件类型名称</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" name="name" id="name" value="${idType.name }"> 
					</div>
				</td>
			</tr>
			<tr>
				<td class="field">描述</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" name="description" id="description" value="${idType.description }"> 
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
<script type="text/javascript">
	$(function() {
		$("#editBtn").click(function() {
			try {
				$(this).validate.isNull($('#code').val(),'证件类型代码不能为空');
				$(this).validate.isNull($('#name').val(),'证件名称不能为空');
				//提交表单
				if($("#code").next().is(":hidden")){
					$(this).validate.submin_form();
				}
			} catch (e) {
				openErrorDialog(e,true,false,{time:3,title:"3秒后自动关闭"});
			}
		});

		$("#code").focus(function(){
			$("#code").next().hide();
		});
		//ajax验证类型code不能相同
		$("#code").bind("blur", function(e) {
			var preCode = $("#preCode").val();
			var a = $(this).val();
			if(!(a=$.trim(a)) || a==preCode){
				return false;
			}
			$.ajax({
				url : "${ctx}/backend/argument/idType/checkIdTypeCodeIsExisted",
				data : {
					"code" : encodeURI(encodeURI(a))
				},
				success : function(data) {
					if(data){
						$("#code").next().show();
					}
				}
			});
		});
	});
</script>
</html>
