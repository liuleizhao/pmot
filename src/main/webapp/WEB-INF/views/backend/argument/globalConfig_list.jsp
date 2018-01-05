<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${appName }-全局参数列表</title>
<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
</head>
<body>
	<%@include file="../common/message.jsp" %>
	<div class="title">
		<h2>全局参数列表</h2>
	</div>
	<div class="main">
		<div class="context">
			<form  action="${ctx }/backend/argument/globalConfig/list" method="get" name="form" id="form">
				<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" />
				<div class="query">
					<div class="query-conditions ue-clear">
						<div class="conditions staff ue-clear i_div">
							<label>参数Key：</label> <input name="dataKey" value="${globalConfig.dataKey}" type="text" />
						</div>
						<div class="conditions staff ue-clear i_div">
							<label>参数名称：</label> <input name="name" value="${globalConfig.name }" type="text" />
						</div>
						<div class="conditions name ue-clear">
							<input id="queryBtn" type="button" value="查询" class="input_button" /> 
							<input onclick="javascript:window.location.href='${ctx }/backend/argument/globalConfig/add'" type="button" value="新增" class="input_button" />
						</div>
					</div>
				</div>
			</form>
				<div class="table_box">
					<table id="liebiaocolor">
						<thead>
							<tr>
								<th class="length3">参数名称</th>
								<th class="length3">参数Key</th>
								<th class="length3">参数值</th>
								<th class="length3">描述</th>
								<th class="length3">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pageView.list }" var="globalConfig" varStatus="status">
								<tr id="${globalConfig.id }">
									<td class="length3">${globalConfig.name }</td>
									<td class="length3">${globalConfig.dataKey }</td>
									<td class="length3">${globalConfig.dataValue }</td>
									<td class="length3">${globalConfig.description }</td>
									<td class="length3">
										<a class="mui-btn-blue" href="${ctx }/backend/argument/globalConfig/edit?globalConfigId=${globalConfig.id}">编辑</a>&nbsp; &nbsp; 
										<a class="mui-btn-blue"href="javascript:del('${globalConfig.id }');">删除</a>
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
	function del(globalConfigId) {
		opeanWarningDialog("您确定要删除该参数信息吗？",function(){
			doDel(globalConfigId);
		},true);
	}
	function doDel(globalConfigId) {
		$.ajax({
			type : 'GET',
			url : '${ctx }/backend/argument/globalConfig/delete',
			data : {
				'globalConfigId' : globalConfigId
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








