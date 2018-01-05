<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${appName }-编辑系统参数</title>
<%@ include file="/static/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/xtbg/css/mui.min.css" />
<link rel="stylesheet" href="${ctx}/static/xtbg/css/base.css" />
<link rel="stylesheet" href="${ctx}/static/xtbg/css/info-reg.css" />
<link rel="stylesheet" href="${ctx}/static/xtbg/css/info-mgt.css" />
<link href="${ctx }/static/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/static/css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/css/backend_common.css" />
</head>

<body>
	<div class="title">
		<h2>编辑系统参数基本信息</h2>
	</div>
	<input type="hidden" id="mess" value="${message }" />
	<form class="classA mui-input-group" action="${ctx }/backend/argument/sys_arg_value/edit" method="post" name="form" id="form">
		<input type="hidden" name="id" value="${sysArgValueVO.id }" id="sysArgValueId" />

		<table cellpadding="8" cellspacing="1">
			<tr>
				<td class="field"><span class="txt-imp">*</span>参数值代码</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="code" id="code" type="text" value="${sysArgValueVO.code }"> <span id="check_code_exist" class="Prompt"></span>
					</div></td>
				<td class="field"><span class="txt-imp">*</span>参数名称</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="name" id="name" type="text" value="${sysArgValueVO.name }"> <span id="check_name_exist" class="Prompt"></span>
					</div></td>
			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>参数值</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="value" id="value" type="text" value="${sysArgValueVO.value }">
					</div></td>
				<td class="field">排列顺序</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" name="orderNum" id="orderNum" value="${sysArgValueVO.orderNum }">
					</div></td>
			</tr>

			<td class="field">是否可修改</td>
			<td class="text select_td">
			<div class="mui-input-row">
					<select name="isUpdate" id="isUpdate">
						<c:forEach items="${isUpdateList }" var="entity">
							<option value="${entity.value }" <c:if test="${sysArgValueVO.isUpdate.value eq entity.value }">selected="selected"</c:if>>${entity.description}</option>
						</c:forEach>
					</select>
				<div class="triangle-down"></div>
				</div>
			</td>
			<td class="field">描述</td>
			<td class="text"><input name="description" id="description" type="text" value="${sysArgValueVO.description }" /></td>

		</table>
		<div class="mui-button-row">
			<input type="button" class="input_button" value="保存" id="editBtn"> <input type="button" class="input_button" onClick="javascript:history.back(-1);"
				value="返回">
		</div>
	</form>
</body>
<script type="text/javascript" src="${ctx}/static/xtbg/js/common.js"></script>

<script type="text/javascript">
	$(function() {
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

		$("#editBtn").click(function() {
			try {
				$(this).validate.isNull($('#code').val(), '参数值代码不能为空');
				$(this).validate.isNull($('#name').val(), '参数值名称不能为空');
				$(this).validate.isNull($('#value').val(), '参数值值不能为空');
				/*  $(this).validate.isNull($('#orderNum').val(),'排序不能为空');
				 var reg = /^[\d]{1,}$/;
				if(!reg.test($("#orderNum").val())){
					throw ("排列顺序请输入数字");
				}  */
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

		//ajax验证类型code不能相同
		$("#code").bind('blur', function(e) {
			var a = $(this).val();
			$.ajax({
				url : '${ctx}/backend/argument/sys_arg_value/checkCode',
				data : {
					'codeOrName' : encodeURI(encodeURI(a)),
					type : 1,
					sysArgValueId : $("#sysArgValueId").val()
				},
				success : function(data) {
					var isBlank = data.isBlank;
					var isExist = data.isExist;
					if (!isBlank && isExist) {
						$(".check_code_class").css('color', 'red');
						$("#check_code_exist").html("参数值代码已存在");
						$("#account").val('');
					} else if (isBlank) {
						$(".check_code_class").css('color', 'red');
						$("#check_code_exist").html("请输入参数值代码");
					} else {
						$(".check_code_class").css('color', 'green');
						$("#check_code_exist").html("参数值代码可以使用");
					}
				}
			});
		});

		$("#name").bind('blur', function(e) {
			var a = $(this).val();
			$.ajax({
				url : '${ctx}/backend/argument/sys_arg_value/checkCode',
				data : {
					'codeOrName' : encodeURI(encodeURI(a)),
					type : 2,
					sysArgValueId : $("#sysArgValueId").val()
				},
				success : function(data) {
					var isBlank = data.isBlank;
					var isExist = data.isExist;
					if (!isBlank && isExist) {
						$(".check_name_class").css('color', 'red');
						$("#check_name_exist").html("参数名称已存在");
						$("#name").val('');
					} else if (isBlank) {
						$(".check_name_class").css('color', 'red');
						$("#check_name_exist").html("请输入参数名称");
					} else {
						$(".check_name_class").css('color', 'green');
						$("#check_name_exist").html("参数名称可以使用");
					}
				}
			});
		});

	});
</script>
</html>
