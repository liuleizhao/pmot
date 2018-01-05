<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta charset="utf-8">
		<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
		<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
		<title>${appName }-黑名单详情查询</title>
	</head>

	<body>
		<div class="title">
			<h2>黑名单详情查询</h2></div>
		<div class="main">	
		<div class="context" >
		<input type="hidden" id="mess" value='${message }' />
		<div class="table_box">
			<div class="query">
					 <input type="button" value="返回" class="input_button" onClick="javascript:history.back(-1);" style="float: right;margin-left: 30px;"/>
			</div>
			<table id="liebiaocolor">
				<thead>
					<tr>
						<th class="length3">姓名</th>
						<th class="length3">手机号</th>
						<th class="length3">号牌号码</th>
						<th class="length3">预约号</th>
						<th class="length3">车辆性质</th> 
						<th class="length3">预约时间</th> 
						<th class="length3">预约状态</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${bookInfoList }" var="entity" varStatus="status">
						<td class="length3">${entity.bookerName }</td>
						<td class="length3">${entity.mobile}</td>
						<td class="length3">${entity.platNumber}</td>
						<td class="length3">${entity.bookNumber}</td>
						<td class="length3">${entity.vehicleCharacter.description}</td> 
						<td class="length3">${entity.bookDate}&nbsp;${entity.bookTime}</td> 
						<td class="length3">${entity.bookState.description}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<%-- <%@ include file="/WEB-INF/views/backend/common/pagination.jsp"%> --%>
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
