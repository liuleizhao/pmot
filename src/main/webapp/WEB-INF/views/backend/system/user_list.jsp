<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta charset="utf-8">
		<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
		<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
		<title>${appName }-用户基本信息管理</title>
	</head>

	<body>
		<%@include file="/WEB-INF/views/backend/common/message.jsp" %>
		<div class="title">
			<h2>用户列表</h2></div>
		<div class="main">	
		<div class="context" >
		<form action="${ctx }/backend/system/user/list" method="get" name="form" id="form">
				<input type="hidden" id="mess" value='${message }' />
				<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" />
		<div class="query">
			<div class="query-conditions ue-clear">
				<div class="conditions staff ue-clear">
					<label>账号名称：</label>
					<input name="account" value="${user.account }" type="text" />
				</div>
				<div class="conditions staff ue-clear">
					<label>姓&nbsp;&nbsp;名：</label>
					<input name="name" value="${user.name }" type="text" />
				</div>
				<div class="conditions name ue-clear">
				<div class="mui-input-row">
					<label>用户类型：</label>
						<select name="userType" id="userType" class="q_select">
								<option value="">请选择...</option>
								<c:forEach items="${userTypeList }" var="entity">
									<option value="${entity.value }"  
									<c:if test="${user.userType eq entity.value }">selected="selected"</c:if>>${entity.description}</option>
								</c:forEach>
						</select>
					</div> 
				</div>
				<div class="conditions name ue-clear no_border">
					<input type="submit" value="查询" class="input_button">
					<input type="button" value="新增" onclick="javascript:window.location.href='${ctx }/backend/system/user/add'" class="input_button">
				</div>
			</div>
		</div>
	</form>
		<div class="table_box">
			<table>
				<thead>
					<tr>
						<th class="length2">账户名称</th>
						<th class="length2">姓名</th>
						<th class="length2">性别</th>
						<th class="length2">移动电话</th>
						<th class="length2">所属检测站</th>
						<th class="length2">用户类型</th>
						<th class="length2">email</th> 
						<th class="length2">职位</th>
						<th class="length2">登陆次数</th>
						<th class="length2">用户状态</th>
						<th class="length4">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${pageView.list }" var="user" varStatus="status">
					<tr>
						<td class="length2">${user.account }</td>
						<td class="length2">${user.name }</td>
						<td class="length2"><c:choose>
								<c:when test="${user.sex == 1 }">
										 男
									</c:when>
								<c:otherwise>
										 女
									</c:otherwise>
							</c:choose>
						</td>
						<td class="length2">${user.mobile }</td>
						<td class="length2">${user.stationName }</td>
						<td class="length2">${user.userType.description }</td>
						<td class="length2">${user.email }</td>
					    <td class="length2">${user.post }</td>
						<td class="length2">${user.loginCount }</td>
						<td class="length2">${user.status.description }</td>
						<td class="length4">
						<a class="mui-btn-blue" href="${ctx }/backend/system/user/edit?userId=${user.id}">编辑</a>
						&nbsp; &nbsp; 
						<a class="mui-btn-blue" href="${ctx }/backend/system/user/userRoleAuthorize?userId=${user.id}">授权</a>
						&nbsp; &nbsp; 
						<a class="mui-btn-blue" href="javascript:deleteUser('${user.id}','${user.name}');">删除</a>
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
	<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
	<script type='text/javascript' src='${ctx}/static/backend/js/zDialog/zDrag.js'></script>
	<!--artDialog-->
	<script type="text/javascript" src="${ctx }/static/backend/js/validator.js"></script>
	<script type="text/javascript" src="${ctx }/static/backend/js/artDialog/jquery.artDialog.min.js"></script>
	<script type="text/javascript" src="${ctx }/static/backend/js/artDialog/artDialog.iframeTools.min.js"></script>
	<!-- 将common.js放置在最后 -->
	<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
	<script type="text/javascript">
		$("tbody").find("tr:odd").css("backgroundColor", "#eff6fa");

		showRemind('input[type=text], textarea', 'placeholder');
		
		function deleteUser(id,name){
			art.dialog({
				title : "温馨提示",
				content : "您确定要删除用户【"+name+"】吗？",
				icon : 'warning',
				yesFn : function() {
					location.href = "${ctx}/backend/system/user/delete?userId="+id;
					return true;
				},
				noFn : true
			});
		}
	</script>

</html>
