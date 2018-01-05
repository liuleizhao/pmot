<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>深圳市机动车年审检测预约系统-编辑资源</title>
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/mui.min.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-reg.css" />
<link rel="stylesheet" href="${ctx}/static/backend/css/backend_common.css" />

<script type="text/javascript" src="${ctx }/static/backend/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${ctx }/static/backend/js/validator.js"></script>
<link href="${ctx }/static/backend/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/static/backend/css/style.css" rel="stylesheet" type="text/css" />

<link href="${ctx }/static/backend/css/ztree/style_reset.css" rel="stylesheet" type="text/css" />
<script type='text/javascript' src='${ctx }/static/backend/js/zDialog/zDrag.js'></script>
<script type='text/javascript' src='${ctx }/static/backend/js/zDialog/zDialog.js?{ctx:"${ctx}"}'></script>
<script type="text/javascript">
	$(function() {
		$("#description").html('${resource.description }');

		$(".resourceType").bind(
				"click",
				function() {
					var resourceType = $('input:radio[name="inputResourceType"]:checked').val();
					$("#resType").attr("value", resourceType);
				});

		$(".methodType").bind(
				"click",
				function() {
					var methodType = $('input:radio[name="methodType"]:checked').val();
					$("#methType").attr("value", methodType);
				});
	});
</script>
</head>
<body>
	<div class="title">
		<h2>添加资源</h2>
	</div>
	<input type="hidden" id="message" value="${message }" />
	<form class="classA mui-input-group" action="${ctx }/backend/system/resource/edit" method="post" name="form" id="resourceAddForm">
		<input type="hidden" id="id" name="id" value="${resource.id }" /> 
		<input type="hidden" id="resType" name="resType" value="${resource.resourceType.value}" />
		<input type="hidden" id="methType" name="methType" value="${resource.methodType.value}" />
		<table cellpadding="8" cellspacing="1">
			<tr>
				<td class="field">父资源</td>
				<td class="text">
					<div class="mui-input-row">
						<input id="jq_zTree_name" name="jq_zTree_name" value="${parentName }" disabled="disabled" type="text" /> <input id="jq_zTree_id" name="parentId"
							type="hidden" value="${resource.parentId }" />
				</td>
				</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>排列顺序</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="orderNum" id="orderNum" type="text" value="${resource.orderNum }" />
					</div>
				</td>

			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>资源名称</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="name" id="name" type="text" value="${resource.name }" />
					</div></td>
				<td class="field">资源路径</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="path" id="path" type="text" value="${resource.path }" />
					</div></td>
			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>资源类型</td>
				<td class="text">
					<div class="radio_css">
						<c:forEach items="${resourceTypes }" var="resourceType" varStatus="s">
							<label>
								<div>
									<input id="resourceType${resourceType.index }" name="inputResourceType" type="radio" value="${resourceType.value }"
										<c:if test="${resource.resourceType.value eq resourceType.value }">checked="checked"</c:if> class="resourceType" />
								</div> <span for="resourceType${resourceType.index }">${resourceType.description }</span> </label>
						</c:forEach>
					</div></td>
				<td class="field"><span class="txt-imp">*</span>提交方式</td>
				<td class="text">
					<div class="radio_css">
						<c:forEach items="${methodTypes }" var="methodType" varStatus="s">
							<label>
								<div>
									<input id="methodType${methodType.id }" name="methodType" type="radio" value="${methodType.value }"
										<c:if test="${resource.methodType.value eq methodType.value }">checked="checked"</c:if> class="methodType" />
								</div> <span for="methodType${methodType.id }">${methodType.description }</span> </label>
						</c:forEach>
					</div></td>
			</tr>
			<tr>
				<td class="field">图标:</td>
				<td class="text" colspan="3">
					<div class="mui-input-row">
						<input name="icon" id="icon" type="text" value="${resource.icon }"/>
					</div>
				</td>
			</tr>
			<tr>
				<td class="field">资源描述:</td>
				<td class="text" colspan="3">
					<div class="mui-input-row">
						<input name="description" id="description" type="text" value="${resource.description }"/>
					</div>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>