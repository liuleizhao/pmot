<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${appName}-新增资源</title>
<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/mui.min.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-reg.css" />
<link rel="stylesheet" href="${ctx}/static/backend/css/reset.css">
<link rel="stylesheet" href="${ctx}/static/backend/css/style.css">
<link rel="stylesheet" href="${ctx}/static/backend/css/backend_common.css" />

<script type="text/javascript" src="${ctx}/static/backend/js/zDialog/zDialog.js"></script>
<%-- <script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script> --%>
<script type="text/javascript">
	$(function() {
		$("#addBtn").click(
				function() {
					try {
						/**************************check start****************************/
						$(this).validate.isNull($("#name").val(), '资源名称不能为空');
						$(this).validate.isNull($(
								'input:radio[name="resourceType"]:checked')
								.val(), '请选择资源类型');
						$(this).validate.isNull(
								$('input:radio[name="methodType"]:checked')
										.val(), '请选择提交方式');
						// 如何选择功能类型，提交方式必须选
						/* if($('input:radio[name='inputResourceType']:checked').val() = '3')
						{
							$(this).validate.isNull($('input:radio[name="inputMethodType"]:checked').val(), '请选择提交方式类型');
						} */

						$(this).validate.isNull($("#orderNum").val(), '请输入排列顺序');
						var reg = /^[\d]{1,}$/;
						if (!reg.test($("#orderNum").val())) {
							throw ("排列顺序请输入数字");
						}
						// $(this).validate.submin_form();
						$("#resourceAddForm").submit();
					} catch (e) {
						Dialog.alert(e);
					}
					return false;
				});
	});

	function select() {
		var diag = new Dialog();
		diag.Title = "增加资源";
		diag.Width = 500;
		diag.Height = 600;
		diag.URL = "${ctx}/backend/system/resource/openradio"; //controller
		diag.OKEvent = function() {
			$id('jq_zTree_id').value = diag.innerFrame.contentWindow.document
					.getElementById('treeNodeId').value;
			$id('jq_zTree_name').value = diag.innerFrame.contentWindow.document
					.getElementById('treeNodeName').value;
			diag.close();
		};
		diag.show();
	}
</script>
</head>
<body>

	<div class="title">
		<h2>添加资源</h2>
	</div>
	<input type="hidden" id="message" value="${message }" />
	<form class="classA mui-input-group z_99" action="${ctx }/backend/system/resource/add" method="post" name="form" id="resourceAddForm">
		<table cellpadding="8" cellspacing="1">
			<tr>
				<td class="field">父资源</td>
				<td class="text">
					<div class="mui-input-row">
						<input id="jq_zTree_name" name="jq_zTree_name" value="无" disabled="disabled" type="text"> <span id="check_code_exist" class="Prompt"></span> <a
							style="margin-left: 10px" id="jq_select" class="cRed" href="javascript:void(0);" onclick="select();">选择</a> &nbsp;&nbsp; <a id="jq_parent_null"
							class="cRed" href="javascript:void(0);">无父组织</a> <input id="jq_zTree_id" name="parentId" type="hidden" />
					</div></td>
				<td class="field"><span class="txt-imp">*</span>排列顺序:</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="orderNum" id="orderNum" type="text" />
					</div></td>

			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>资源名称</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="name" id="name" type="text">
					</div>
				</td>
				<td class="field">资源路径:</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="path" id="path" type="text" />
					</div>
				</td>
			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>资源类型</td>
				<td class="text">
						<div class="radio_css">
							<c:forEach items="${resourceTypes }" var="resourceType" varStatus="s">
								<label>
									<div>
										<input id="resourceType${resourceType.index }" name="resourceType" type="radio" value="${resourceType.value }"/>
									</div> 
									<span for="resourceType${resourceType.id }">${resourceType.description }</span> </label>
							</c:forEach>
						</div>
					</td>
				<td class="field"><span class="txt-imp">*</span>提交方式</td>
				<td class="text">
						<div class="radio_css">
							<c:forEach items="${methodTypes }" var="methodType" varStatus="s">
								<label>
									<div>
										<input id="methodType${methodType.id }" name="methodType" type="radio" value="${methodType.value }"/>
									</div> 
									<span for="methodType${methodType.id }">${methodType.description }</span> </label>
							</c:forEach>
						</div>
				</td>
			</tr>
			<tr>
				<td class="field">图标:</td>
				<td class="text" colspan="3">
					<div class="mui-input-row">
						<input name="icon" id="icon" type="text" value="${resource.icon }" />
					</div></td>
			</tr>
			<tr>
				<td class="field">资源描述:</td>
				<td class="text" colspan="3">
					<div class="mui-input-row">
						<input name="description" id="description" type="text" value="${resource.description }"/>
					</div></td>
			</tr>
		</table>
		<div class="mui-button-row">
			<input type="button" class="input_button" value="保存" id="addBtn"> <input type="button" class="input_button" value="返回"
				onClick="javascript:history.back(-1);">
		</div>
	</form>
	</div>
	</div>
	</div>
</body>
</html>