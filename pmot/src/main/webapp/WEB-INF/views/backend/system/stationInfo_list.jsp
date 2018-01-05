<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${appName }-检测站列表</title>
<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
</head>
<body>
	<%@include file="../common/message.jsp" %>
	<div class="title">
		<h2>检测站列表</h2>
	</div>
	<div class="main">
		<div class="context">
			<form action="${ctx }/backend/system/station/list" method="get" name="form" id="form">
					<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" /> 
					<div class="query">
					<div class="query-conditions ue-clear">
						<div class="conditions staff ue-clear i_div">
							<label>检测站代码：</label> 
							<input name="code" value="${station.code }" type="text" />
						</div>
						<div class="conditions staff ue-clear i_div">
							<label>检测站名称：</label> 
							<input name="name" value="${station.name }" type="text" />
						</div>
						<div class="conditions name ue-clear no_border">
							<input id="queryBtn" type="submit" value="查询" class="input_button" /> 
							<input  type="button" class="input_button" value="新增"  onclick="javascript:window.location.href='${ctx }/backend/system/station/add'"/>
						</div>
					</div>
				</div>
			</form>
				<div class="table_box">
					<table>
						<thead>
							<tr>
								<th class="length3">检测站代码</th>
								<th class="length3">检测站名称</th>
								<th class="length3">联系人电话</th> 
								<th class="length3">固定电话</th>
								<th class="length3">邮箱</th>
								<th class="length4">检测站地址</th>
								<th class="length3">状态</th>
								<th class="length3">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pageView.list }" var="entity" varStatus="status">
								<c:choose>
									<c:when test="${status.index %2 == 1 }">
										<tr class="evencolor" id="${entity.id }">
									</c:when>
									<c:otherwise>
										<tr class="oddcolor" id="${entity.id }">
									</c:otherwise>
								</c:choose>
									<td class="length3">${entity.code }</td>
									<td class="length3">${entity.name }<tdh>
									<td class="length3">${entity.mobile }</td>
									<td class="length3">${entity.phone }</td>
									<td class="length3">${entity.email }</td>
									<td class="length4">${entity.address }</td>
									<td class="length3"">${entity.stationState.description }</td>
									<td class="length3">
										<a class="mui-btn-blue" href="${ctx }/backend/system/station/edit?stationId=${entity.id}">编辑</a>
										<a class="mui-btn-blue"href="javascript:del('${entity.id }');">删除</a></td>
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
	function del(stationId) {
		opeanWarningDialog("您确定要删除该检测站吗？",function(){doDel(stationId);},true);
	}

	function doDel(stationId) {
		$.ajax({
			type : 'GET',
			url : '${ctx }/backend/system/station/delete',
			data : {
				'stationId' : stationId
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