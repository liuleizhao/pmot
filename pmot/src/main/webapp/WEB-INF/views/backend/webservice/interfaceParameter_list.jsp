<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta charset="utf-8">
		<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
		<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
		<title>${appName }-接口参数信息管理</title>
	</head>
	<body>
		<%@include file="../common/message.jsp" %>
		<div class="title">
			<h2>接口参数列表</h2></div>
		<div class="main">	
		<div class="context" >
		<form action="${ctx }/backend/webservice/interfaceParameter/list" method="get" name="form" id="form">
			<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" />
			<div class="query">
				<div class="query-conditions ue-clear">
					<div class="conditions staff ue-clear">
						<label>参数名称：</label>
						<input name="name" value="${interfaceParameter.name }" type="text" />
					</div>
					<div class="conditions name ue-clear no_border">
						<input type="submit" value="查询" class="input_button">
						<input type="button" value="新增" onclick="javascript:window.location.href='${ctx }/backend/webservice/interfaceParameter/add'" class="input_button">
					</div>
				</div>
			</div>
		</form>
		<div class="table_box">
			<table>
				<thead>
					<tr>
						<th class="length3">参数名称</th>
						<th class="length3">参数类型</th>
						<th class="length3">描述信息</th>
						<th class="length4">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${pageView.list }" var="interfaceParameter" varStatus="status">
					<tr>
						<td class="length3">${interfaceParameter.name }</td>
						<td class="length3">${interfaceParameter.type }</td>
						<td class="length3">${interfaceParameter.description }</td>
						<td class="length4">
						<a class="mui-btn-blue" href="${ctx }/backend/webservice/interfaceParameter/edit?interfaceParameterId=${interfaceParameter.id}">编辑</a>
						<a class="mui-btn-blue" href="javascript:;" onclick="del('${interfaceParameter.id}')">删除</a>
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
	<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
	<script type="text/javascript">
		$("tbody").find("tr:odd").css("backgroundColor", "#eff6fa");
		$(function() {
			$("#queryBtn").click(function() {
				//new 2015-12-02 设置当前页为1，避免第2页以后，通过输入条件查询造成的显示不出数据的问题
				$("#currentPage").attr("value", "1");
				document.forms[0].submit();
			});
		});
		function del(parameterId) {
			opeanWarningDialog("您确定要删除该参数吗？",function(){doDel(parameterId);},true);
		}
	
		function doDel(parameterId) {
			$.ajax({
				type : "GET",
				url : "${ctx }/backend/webservice/interfaceParameter/delete",
				data : {
					"interfaceParameterId" : parameterId
				},
				success : function(data) {
					if (data) {
						openSuccessDialog("删除成功",true,false,{time:3});
						setTimeout(function() {
							document.forms[0].submit();
						}, 3000);
					} else {
						openErrorDialog("删除失败，请重试",true,false);
					}
				}
			});
		}
	</script>
</html>
