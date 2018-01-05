<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta charset="utf-8">
		<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
		<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
		<title>${appName }-黑名单列表</title>
	</head>

	<body>
		<div class="title">
			<h2>黑名单列表</h2></div>
		<div class="main">	
		<div class="context" >
		<input type="hidden" id="mess" value='${message }' />
		<form action="${ctx }/backend/blackListConfig/list" method="get" name="form" id="form">
			<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" />
			<div class="query">
				<div class="query-conditions ue-clear">
					<div class="conditions staff ue-clear i_div"">
						<label>记录类型：</label>
						<select name="recordType" class="q_select">
								<option value="">请选择...</option>
								<c:forEach items="${recordTypes }" var="recordType">
									<option value="${recordType.value }"  
									<c:if test="${busBlackListConfig.recordType eq recordType.value }">selected="selected"</c:if>>${recordType.description}</option>
								</c:forEach>
						</select>
					</div>
					<div class="conditions staff ue-clear i_div">
						<label>操作类型：</label>
						<select name="operationType" class="q_select">
								<option value="">请选择...</option>
								<c:forEach items="${ bookOperations}" var="operationType">
									<option value="${operationType }"
									<c:if test="${busBlackListConfig.operationType eq operationType.value }">selected="selected"</c:if>
									>${operationType.description}</option>
							</c:forEach>
						</select>
					</div>
					<div class="conditions name ue-clear">
						<input id="queryBtn" type="submit" value="查询" class="input_button" /> 
						<input onclick="javascript:window.location.href='${ctx }/backend/blackListConfig/addUI'" type="button" value="新增" class="input_button" />
					</div>
				</div>
			</div>
		</form>
		<div class="table_box">
			<table>
				<thead>
					<tr>
						<th class="length3">记录类型</th>
						<th class="length3">操作类型</th>
						<th class="length3">生效天数</th>
						<th class="length3">限制次数</th>
						<th class="length3">操作</th> 
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${pageView.list }" var="entity" varStatus="status">
						<td class="length3">${entity.recordType.description}</td>
						<td class="length3">${entity.operationType.description}</td>
						<td class="length3">${entity.effectDays}</td> 
						<td class="length3">${entity.limitCount}</td> 
						<td class="length3">
							<a class="mui-btn-blue" href="${ctx }/backend/blackListConfig/editUI?configId=${entity.id}">编辑</a>
							<a class="mui-btn-blue" href="javascript:deleteDialog('${entity.id}')">删除</a>
						</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<%@ include file="/WEB-INF/views/backend/common/pagination.jsp"%>
		</div>
		</div>
	</body>
	<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
	<script type='text/javascript' src='${ctx}/static/backend/js/zDialog/zDrag.js'></script>
	
	<!--artDialog-->
	<script type="text/javascript" src="${ctx }/static/backend/js/validator.js"></script>
	<script type="text/javascript" src="${ctx }/static/backend/js/artDialog/jquery.artDialog.min.js"></script>
	<script type="text/javascript" src="${ctx }/static/backend/js/artDialog/artDialog.iframeTools.min.js"></script>
	<script type="text/javascript">
	$("tbody").find("tr:odd").css("backgroundColor", "#eff6fa");

	$(function() {
		var mess = $("#errormess").val();
		if (mess != null && $.trim(mess) != "") {
			$.dialog({
				title : '3秒后自动关闭',
				time : 3,
				content : mess,
				icon : 'succeed',
				yesFn : true
			});
		}
	});
	
	function deleteDialog(id){
			art.dialog({
			title : "温馨提示",
			content : '您确定删除该配置？',
			icon : 'warning',
			yesFn : function() {
				deleteRecord(id);
				return true;
			},
			noFn : true
		});
	}
	
	function deleteRecord(id){
		$.ajax({
				type : 'GET',
				url : '${ctx }/backend/blackListConfig/delete',
				data : {
					'configId' : id
				},
				success : function(data) {
					if ($.trim(data.errorMessage) == "") {
						$.dialog({
							title : '3秒后自动关闭',
							time : 3,
							content : "操作成功",
							icon : 'succeed',
							yesFn :  function() {
								document.forms[0].submit();
							}
							});
						setTimeout(function() {
							document.forms[0].submit();
						}, 3000);
					} else {
						$.dialog({
							title : '3秒后自动关闭',
							time : 3,
							content : "操作失败，请重试",
							icon : 'error',
							yesFn : true
						});
					}
				}
			});
	}
</script>
</html>
