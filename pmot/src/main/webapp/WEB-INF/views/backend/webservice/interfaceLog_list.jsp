<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta charset="utf-8">
		<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
		<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
		<title>${appName }-接口日志列表</title>
	</head>

	<body>
		<div class="title">
			<h2>接口日志详情</h2></div>
		<div class="main">	
		<div class="context" >
		<input type="hidden" id="mess" value='${message }' />
		<form action="${ctx }/backend/webservice/interfaceLog/list" method="get" name="form" id="form">
			<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" />
			<div class="query">
				<div class="query-conditions ue-clear">
					<div class="conditions staff ue-clear">
						<label>接口编号：</label>
						<input name="jkid" value="${interfaceLog.jkid }" type="text" />
					</div>
					<div class="conditions staff ue-clear">
						<label>访问IP：</label>
						<input name="ip" value="${interfaceLog.ip }" type="text" />
					</div>
					<div class="conditions staff ue-clear">
						<label>请求内容：</label>
						<input name="requestXml" value="${interfaceLog.requestXml }" type="text" />
					</div>
					<div class="conditions staff ue-clear">
						<label>响应内容：</label>
						<input name="responseXml" value="${interfaceLog.responseXml }" type="text" />
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
						<th class="length3">接口编号</th>
						<th class="length3">ip</th>
						<th class="length3">请求时间</th>
						<th class="length3">请求内容</th>
						<th class="length3">响应时间</th>
						<th class="length3">响应内容</th>
						<th class="length3">时长</th>
						<th class="length3">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${pageView.list }" var="interfaceLog" varStatus="status">
					<tr>
						<td class="length3">${interfaceLog.jkid }</td>
						<td class="length3">${interfaceLog.ip }</td>
						<td class="length3"> <fmt:formatDate value="${interfaceLog.requestDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td class="length3">
							 <c:out value=" ${fn:substring(interfaceLog.requestXml,0,50)}..." escapeXml="true"/>
						 </td> 
						<td class="length3"><fmt:formatDate value="${interfaceLog.responseDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td class="length3">
						 <c:out value=" ${fn:substring(interfaceLog.responseXml,0,50)}..." escapeXml="true"/>
						</td>
						<td class="length3">
						${interfaceLog.runTime }
						</td>
						<td class="length3">
						<a class="mui-btn-blue" href="${ctx }/backend/webservice/interfaceLog/view?id=${interfaceLog.id}">查看详情</a>
						</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<%@ include file="/WEB-INF/views/backend/common/pagination.jsp"%>
		</div>
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
