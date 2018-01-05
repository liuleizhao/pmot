<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta charset="utf-8">
		<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
		<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
		<title>${appName }-接口信息管理</title>
	</head>
	<body>
		<%@include file="../common/message.jsp" %>
		<div class="title">
			<h2>接口列表</h2></div>
		<div class="main">	
		<div class="context" >
		<form action="${ctx }/backend/webservice/interfaceInfo/list" method="get" name="form" id="form">
			<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" />
			<div class="query">
				<div class="query-conditions ue-clear">
					<div class="conditions staff ue-clear">
						<label>接口编号：</label>
						<input name="jkid" value="${interfaceInfo.jkid }" type="text" />
					</div>
					<div class="conditions staff ue-clear">
						<label>接口名称：</label>
						<input name="name" value="${interfaceInfo.name }" type="text" />
					</div>
					<div class="conditions staff ue-clear">
						<label>类&nbsp;&nbsp;名：</label>
						<input name="className" value="${interfaceInfo.className }" type="text" />
					</div>
					<div class="conditions staff ue-clear">
						<label>方法名称：</label>
						<input name="methodName" value="${interfaceInfo.methodName }" type="text" />
					</div>
					<div class="conditions name ue-clear no_border">
						<input type="submit" value="查询" class="input_button">
						<input type="button" value="新增" onclick="javascript:window.location.href='${ctx }/backend/webservice/interfaceInfo/add'" class="input_button">
					</div>
				</div>
			</div>
		</form>
		<div class="table_box">
			<table>
				<thead>
					<tr>
						<th class="length3">接口编号</th>
						<th class="length3">接口名称</th>
						<th class="length3">类名</th>
						<th class="length3">方法名称</th>
						<th class="length3">是否保存日志</th>
						<th class="length3">描述信息</th>
						<th class="length4">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${pageView.list }" var="interfaceInfo" varStatus="status">
					<tr>
						<td class="length3">${interfaceInfo.jkid }</td>
						<td class="length3">${interfaceInfo.name }</td>
						<td class="length3">${interfaceInfo.className }</td>
						<td class="length3">${interfaceInfo.methodName }</td>
						<td class="length3">
							 ${interfaceInfo.recordable.description }
						</td>
						<td class="length3">${interfaceInfo.description }</td>
						<td class="length4">
							<a class="mui-btn-blue" href="${ctx }/backend/webservice/interfaceInfo/edit?interfaceInfoId=${interfaceInfo.id}">编辑</a>
							<a class="mui-btn-blue" href="${ctx }/backend/webservice/interParamRelation/list?interfaceInfoId=${interfaceInfo.id}">配置参数</a>
							<a class="mui-btn-blue"href="javascript:del('${interfaceInfo.id }');">删除</a></td>
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
	function del(interfaceInfoId) {
		opeanWarningDialog("您确定要删除该接口信息吗？",function(){doDel(interfaceInfoId);},true);
	}

	function doDel(interfaceInfoId) {
		$.ajax({
			type : 'GET',
			url : "${ctx }/backend/webservice/interfaceInfo/delete",
			data : {
				'interfaceInfoId' : interfaceInfoId
			},
			success : function(data) {
				if (data) {
					openSuccessDialog("删除成功",true,false,{time:3});
					setTimeout(function() {
						document.forms[0].submit();
					}, 1000);
				} else {
					openErrorDialog("删除失败，请重试",true,false);
				}
			}
		});
	}
	</script>
</html>
