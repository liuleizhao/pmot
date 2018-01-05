<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${appName }-证件类型列表</title>
<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
</head>
<body>
	<%@include file="../common/message.jsp" %>
	<div class="title">
		<h2>证件类型列表</h2>
	</div>
	<div class="main">
		<div class="context">
			<form  action="${ctx }/backend/argument/idType/list" method="get" name="form" id="form">
				<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" />
				<div class="query">
					<div class="query-conditions ue-clear">
						<div class="conditions staff ue-clear i_div">
							<label>证件类型代码：</label> <input name="code" value="${idType.code }" type="text" />
						</div>
						<div class="conditions staff ue-clear i_div">
							<label>证件类型名称：</label> <input name="name" value="${idType.name }" type="text" />
						</div>
						<div class="conditions name ue-clear">
							<input id="queryBtn" type="button" value="查询" class="input_button" /> 
							<input onclick="javascript:window.location.href='${ctx }/backend/argument/idType/add'" type="button" value="新增" class="input_button" />
						</div>
					</div>
				</div>

				<div class="table_box">
					<table id="liebiaocolor">
						<thead>
							<tr>
								<th class="length3">证件类型代码</th>
								<th class="length3">证件类型名称</th>
								<th class="length3">描述</th>
								<th class="length3">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pageView.list }" var="idType" varStatus="status">
								<c:choose>
									<c:when test="${status.index %2 == 1 }">
										<tr id="${idType.id }">
									</c:when>
									<c:otherwise>
										<tr id="${idType.id }">
									</c:otherwise>
								</c:choose>
								<td class="length3">${idType.code }</td>
								<td class="length3">${idType.name }</td>
								<td class="length3">${idType.description }</td>
								<td class="length3">
								<a class="mui-btn-blue" href="${ctx }/backend/argument/idType/edit?idTypeId=${idType.id}">编辑</a>&nbsp; &nbsp; 
								<a class="mui-btn-blue"href="javascript:del('${idType.id }');">删除</a></td>
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
	<!-- 将common.js放置在最后 -->
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
	function del(idTypeId) {
		opeanWarningDialog("您确定要删除该证件类型吗？",function(){doDel(idTypeId);},true);
	}

	function doDel(idTypeId) {
		$.ajax({
			type : 'GET',
			url : '${ctx }/backend/argument/idType/delete',
			data : {
				'idTypeId' : idTypeId
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








