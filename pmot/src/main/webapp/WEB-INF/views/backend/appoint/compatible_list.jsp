<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
	<title>预约兼容管理</title>
	<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
</head>
<body>
	<%@include file="../common/message.jsp" %>
	<div class="title">
		<h2>预约兼容列表</h2>
	</div>
	<div class="main">
		<div class="context">
			<form  action="${ctx }/backend/appoint/compatible/list" method="get" name="form" id="form">
				<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" />
				<div class="query">
					<div class="query-conditions ue-clear"> 
						<div class="conditions staff ue-clear i_div">
							<label>检测站：</label>
							<select name="stationId" id="stationId" class="q_select">
								<option value="">请选择...</option>
								<c:forEach items="${stations }" var="entity">
									<option value="${entity.id }"  
									<c:if test="${bookInfoCompatible.stationId eq entity.id }">selected="selected"</c:if>>${entity.name}</option>
								</c:forEach>
							</select>
						</div>
						<div class="conditions name ue-clear">
							<input id="queryBtn" type="button" value="查询" class="input_button" /> 
							<input onclick="javascript:window.location.href='${ctx }/backend/appoint/compatible/add'" type="button" value="新增" class="input_button" />
						</div>
					</div>
				</div>
			</form>
				<div class="table_box">
					<table id="liebiaocolor">
						<thead>
							<tr>
								<th class="length3">检测站</th>
								<th class="length3">兼容时长</th>
								<th class="length3">生效开始日期</th>
								<th class="length3">生效结束日期</th>
								<th class="length3">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pageView.list }" var="compatible" varStatus="status">
								<tr id="${compatible.id }">
									<td class="length3">${compatible.stationName }</td>
									<td class="length3">${compatible.compatibleValue }</td>
									<td class="length3"><fmt:formatDate value="${compatible.startDate }" pattern="yyyy-MM-dd" /></td>
									<td class="length3"><fmt:formatDate value="${compatible.endDate }" pattern="yyyy-MM-dd" /></td>
									<td class="length3">
										<a class="mui-btn-blue" href="${ctx }/backend/appoint/compatible/edit?id=${compatible.id}">编辑</a>&nbsp; &nbsp; 
										<a class="mui-btn-blue"href="javascript:del('${compatible.id }');">删除</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			<%@ include file="/WEB-INF/views/backend/common/pagination.jsp"%>
		</div>
	</div>
	<!-- 将common.js放置在最后 -->
	<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
	<script type="text/javascript">
		$("tbody").find("tr:odd").css("backgroundColor", "#eff6fa");
		$(function() {
			$("#queryBtn").click(function() {
				$("#currentPage").attr("value", "1");
				document.forms[0].submit();
			});
		});
		function del(id) {
			var stationName = $("#"+id+" td").eq(0).text();
			opeanWarningDialog("您确定要删除【"+stationName+"】的兼容配置吗？",function(){
				doDel(id);
			},true);
		}
		function doDel(id) {
			$.ajax({
				type : "GET",
				url : "${ctx }/backend/appoint/compatible/delete",
				data : {
					"id" : id
				},
				success : function(data) {
					if (data) {
						openSuccessDialog("删除成功",true,false,{time:2});
						setTimeout(function() {
							document.forms[0].submit();
						}, 2000);
					} else {
						openErrorDialog("删除失败，请重试",true,false);
					}
				}
			});
		}
	</script>
</body>
</html>
