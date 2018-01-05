<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/static/common/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

	<head>
		<meta charset="utf-8">
		<%@ include file="/static/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/static/xtbg/css/base.css" />
		<link rel="stylesheet" href="${ctx}/static/xtbg/css/info-mgt.css" />
		
		<link href="${ctx }/static/css/ztree/style_reset.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" src="${ctx }/static/css/ztree/ztree_common.css" type="text/css"/>		
		<link rel="stylesheet" href="${ctx}/static/css/ztree/zTreeStyle/zTreeStyle.css"	type="text/css">		
		
		<script type="text/javascript" src="${ctx}/static/js/zTree/jquery-1.4.4.min.js"></script>
		<script type="text/javascript"	src="${ctx}/static/js/zTree/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/zTree/jquery.ztree.excheck-3.5.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/zTree/jquery.ztree.exedit-3.5.js"></script>
		
		<!-- zDialog -->
		<script type='text/javascript' src='${ctx}/static/js/zDialog/zDrag.js'></script>	
		<script type='text/javascript' src='${ctx}/static/js/zDialog/zDialog.js?{ctx:"${ctx}"}'></script>	
		
		<!--artDialog-->
		<script type="text/javascript" src="${ctx }/static/js/validator.js"></script>
		<script type="text/javascript"	src="${ctx}/static/js/artDialog/jquery.artDialog.min.js"></script>
		<script type="text/javascript"	src="${ctx}/static/js/artDialog/artDialog.iframeTools.min.js"></script>
		<link rel="stylesheet" href="${ctx}/static/js/artDialog/skins/chrome.css"	type="text/css"></link>
		<title>用户操作日志列表</title>
	</head>

	<body>
		<div class="title">
			<h2>用户操作日志列表</h2></div>
		<div class="main">	
		<div class="context" >
		<form action="${ctx}/backend/system/user/user_log_list" method="get" name="form" id="form">
				<input type="hidden" id="mess" value='${message }' />
				<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" />
				
				<div class="query">
					<div class="query-conditions ue-clear">
						<div class="conditions staff ue-clear">
							<label>账号名称：</label>
							 <input name="account" value="${user.account}" type="text" /> 
						</div>
					<	<div class="conditions staff ue-clear i_div">
							<label>类型：</label> 
							<select name="type" id="type" class="q_select">
								<option value="">请选择...</option>
								<c:forEach items="${UserLogTypeList }" var="entity">
									<c:choose>
										<c:when test="${UserLog.type eq entity.value }">
											<option value="${entity.value }" selected="selected">${entity.description}</option>
										</c:when>
										<c:otherwise>
											<option value="${entity.value }">${entity.description}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
						       </select>
						</div>  
						<div class="conditions time ue-clear i_div">
								<label class="criteria_title">操作时间：</label>
								<div class="time-select">
									<input id="beginDate" type="text" name="beginDate" value="<fmt:formatDate value="${beginDate }" pattern="yyyy-MM-dd"/>" placeholder="开始时间" />
									<i class="icon"></i>
								</div>
								<span class="line">-</span>
								<div class="time-select">
									<input id="endDate" type="text" name="endDate" value="<fmt:formatDate value="${endDate }" pattern="yyyy-MM-dd"/>" placeholder="结束时间" />
									<i class="icon"></i>
								</div>
						</div>
						<div class="conditions name ue-clear no_border" style="margin-left: 40px">
							<input id="queryBtn" type="submit" value="查询" class="input_button" /> 
							<input  type="reset" class="input_button" value="重置"/>
						</div>
					</div>
				</div>
			
		</form>
		<div class="table_box">
			<table>
				<thead>
					<tr>
						<th class="length2">用户账号</th>
						<th class="length2">用户姓名</th>
						<th class="length2">IP</th>
						<th class="length2">类型</th>
						<th class="length2">描述</th>
						<th class="length2">时间</th>
					</tr>
				</thead>
				<tbody>
				
					<c:forEach items="${pageView.list }" var="user_log" >
					<tr>
						<td class="length2">${user_log.user.account }</td>
						<td class="length2">${user_log.user.name }</td>
						<td class="length2">${user_log.user.lastLoginIp}</td>
						<td class="length2">${user_log.type.description } </td>
						<td class="length2">${user_log.description } </td>
						<td class="length2"><fmt:formatDate value="${user_log.actionDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>  
					</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
		<%@ include file="/WEB-INF/views/backend/common/pagination.jsp"%>
		</div>
		</div>
	</body>
	<script type="text/javascript" src="${ctx}/static/xtbg/js/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/static/xtbg/js/common.js"></script>
	<script type="text/javascript">
		$("tbody").find("tr:odd").css("backgroundColor", "#eff6fa");

		showRemind('input[type=text], textarea', 'placeholder');
		$(function(){
			var start = {
				  elem: '#beginDate',
				  format: 'YYYY-MM-DD',
				  min: '1970-01-01 00:00:01', //设定最小日期为当前日期
				  max: '2020-12-31', //最大日期
				  istime: true,
				  istoday: false,
				  choose: function(datas){
				     end.min = datas; //开始日选好后，重置结束日的最小日期
				     end.start = datas //将结束日的初始值设定为开始日
				  }
				};
				var end = {
				  elem: '#endDate',
				  format: 'YYYY-MM-DD',
				  min: '1970-01-01 00:00:01',
				  max: '2020-12-31',
				  istime: true,
				  istoday: false,
				  choose: function(datas){
				    start.max = datas; //结束日选好后，重置开始日的最大日期
				  }
				};
				laydate(start);
				laydate(end);
		})
	</script>

</html>
