<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>接口账户列表</title>
<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
</head>
<body>
	<%@include file="../common/message.jsp" %>
	<div class="title">
		<h2>接口账户列表</h2>
	</div>
	<div class="main">
		<div class="context">
			<form  action="${ctx }/backend/webservice/interfaceControlGeneral/list" method="get" name="form" id="form">
				<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" />
				<div class="query">
					<div class="query-conditions ue-clear">
						<div class="conditions staff ue-clear i_div">
							<label>检测站名称：</label>
							<input name="stationName"  type="text" value="${interfaceControlGeneral.stationName}"/>
						</div>
						<div class="conditions staff ue-clear i_div">
							<label>IP地址：</label>
							<input name="ips"  type="text" value="${interfaceControlGeneral.ips}"/>
						</div>
						<div class="conditions name ue-clear">
							<input id="queryBtn" type="submit" value="查询" class="input_button" /> 
							<input onclick="javascript:window.location.href='${ctx }/backend/webservice/interfaceControlGeneral/add'" type="button" value="新增" class="input_button" />
						</div>
					</div>
				</div>

				<div class="table_box">
					<table id="liebiaocolor">
						<thead>
						<tr>
							<th class="length3">检测站名称</th>
							<th class="length3">检测站序列号</th>
							<th class="length3">IP地址</th>
							<th class="length3">是否开启</th>
							<th class="length3">操作</th>
						</tr>
						</thead>
						<tbody>
							<c:forEach items="${pageView.list }" var="entity" varStatus="status">
							<tr id="${entity.id}">
								<td class="length3">${entity.stationName }</td>
								<td class="length3">${entity.serialNumber }</td>
								<td class="length3">${entity.ips }</td>
								<td class="length3">${entity.state.description }</td>
								<td class="length3">
									<a class="mui-btn-blue" href="${ctx }/backend/webservice/interfaceControlGeneral/editUI?generalId=${entity.id}">编辑</a>
									<c:if test="${entity.state.index == 1 }">
										<a class="mui-btn-blue" href="javascript:isUpdateState('${entity.id}');">停用</a>
									</c:if>
									<c:if test="${entity.state.index == 0 }">
										<a class="mui-btn-blue" href="javascript:isUpdateState('${entity.id}');">启用</a>
									</c:if>
									<a class="mui-btn-blue" href="${ctx }/backend/webservice/interfaceControlGeneral/editDetailUI?generalId=${entity.id}">接口配置</a>
								</td>
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
	function isUpdateState(id) {
		opeanWarningDialog("您确定更改该账户的使用状态？",function(){updateState(id);},true);
	}
	function updateState(id){
		$.ajax({
				type : 'GET',
				url : '${ctx }/backend/webservice/interfaceControlGeneral/updateState',
				data : {
					'generalId' : id
				},
				success : function(data) {
					if(data){
						openSuccessDialog("更新成功",true,false,{time:3});
						setTimeout(function() {
							document.forms[0].submit();
						}, 3000);
					}else{
						openErrorDialog("更新失败，请重试",true,false);
					}
				}
			});
	}
</script>
</html>
