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
		<input type="hidden" id="mess" value='${errorMessage }' />
		<form action="${ctx }/backend/argument/blackListRecord/list" method="get" name="form" id="form">
			<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" />
			<div class="query">
				<div class="query-conditions ue-clear">
					<div class="conditions staff ue-clear">
						<label>记录类型：</label>
						<select name="recordType" class="q_select">
								<option value="">请选择...</option>
								<c:forEach items="${recordTypes }" var="recordType">
									<option value="${recordType.value }"  
									<c:if test="${blackListRecord.recordType eq recordType.value }">selected="selected"</c:if>>${recordType.description}</option>
								</c:forEach>
						</select>
					</div>
					<div class="conditions staff ue-clear">
						<label style="text-align: center;"> 值：</label>
						<input name="recordValue" value="${blackListRecord.recordValue }" type="text" />
					</div>
					<div class="conditions staff ue-clear">
						<label>状&nbsp;&nbsp;&nbsp;&nbsp;态：</label>
						<select name="status" class="q_select">
								<option value="">请选择...</option>
								<c:forEach items="${blacklistStates }" var="blacklistState">
									<option value="${blacklistState.value }"  
									<c:if test="${blackListRecord.status eq blacklistState.value }">selected="selected"</c:if>>${blacklistState.description}</option>
								</c:forEach>
						</select>
					</div>
					<div class="conditions name ue-clear no_border">
						<input type="submit" value="查询" class="input_button">
					</div>
				</div>
			</div>
		</form>
		<div class="table_box">
			<table>
				<thead>
					<tr>
				 		<th class="length3">记录类型</th>
						<th class="length3">值</th>
						<th class="length3">状态</th>
						<th class="length3">创建日期</th>
						<th class="length3">结束日期</th> 
						<th class="length3">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${pageView.list }" var="entity" varStatus="status">
						<td class="length3">${entity.recordType.description}</td>
						<td class="length3">${entity.recordValue}</td>
						<th class="length3">${entity.status.description}</th>
						<td class="length3">     
							<fmt:formatDate value="${entity.createDate}" pattern="yyyy-MM-dd hh:mm:ss" type="date"/>
						</td> 
						<td class="length3">
							<fmt:formatDate value="${entity.endDate}" pattern="yyyy-MM-dd hh:mm:ss" type="date"/>
						</td> 
						<td class="length3">
							<a class="mui-btn-blue" href="${ctx }/backend/argument/blackListRecord/viewUI?recordId=${entity.id}">查看详细记录</a>
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
		});
	</script>
</html>
