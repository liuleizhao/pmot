<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${appName }-车辆类型列表</title>
<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
</head>
<body>
	<%@include file="../common/message.jsp" %>
	<div class="title">
		<h2>车辆类型列表</h2>
	</div>
	<div class="main">
		<div class="context">
			<form  action="${ctx }/backend/argument/carType/list" method="get" name="form" id="form">
				<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" />
				<div class="query">
					<div class="query-conditions ue-clear">
						<div class="conditions staff ue-clear i_div">
							<label>车辆类型代码：</label> <input name="code" value="${carType.code }" type="text" />
						</div>
						<div class="conditions staff ue-clear i_div">
							<label>车辆类型名称：</label> <input name="name" value="${carType.name }" type="text" />
						</div>
						<div class="conditions name ue-clear">
							<input id="queryBtn" type="button" value="查询" class="input_button" /> 
							<input onclick="javascript:window.location.href='${ctx }/backend/argument/carType/add'" type="button" value="新增" class="input_button" />
						</div>
					</div>
				</div>

				<div class="table_box">
					<table id="liebiaocolor">
						<thead>
							<tr>
								<th class="length3">车辆类型代码</th>
								<th class="length3">车辆类型名称</th>
								<th class="length3">排列顺序</th>
								<th class="length3">描述</th>
								<th class="length3">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pageView.list }" var="carType" varStatus="status">
								<c:choose>
									<c:when test="${status.index %2 == 1 }">
										<tr id="${carType.id }">
									</c:when>
									<c:otherwise>
										<tr id="${carType.id }">
									</c:otherwise>
								</c:choose>
								<td class="length3">${carType.code }</td>
								<td class="length3">${carType.name }</td>
								<td class="length3">${carType.orderNum }</td>
								<td class="length3">${carType.description }</td>
								<td class="length3">
								<a class="mui-btn-blue" href="${ctx }/backend/argument/carType/edit?carTypeId=${carType.id}">编辑</a>&nbsp; &nbsp; 
								<a class="mui-btn-blue"href="javascript:deleteCarType('${carType.id }');">删除</a></td>
								</tr>
							</c:forEach>
						</tbody>

					</table>
				</div>
			</form>
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
	function deleteCarType(carTypeId) {
		opeanWarningDialog("您确定要删除该号牌类型吗？",function(){doDel(carTypeId);},true);
	}

	function doDel(carTypeId) {
		$.ajax({
			type : 'GET',
			url : '${ctx }/backend/argument/carType/delete',
			data : {
				'carTypeId' : carTypeId
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








