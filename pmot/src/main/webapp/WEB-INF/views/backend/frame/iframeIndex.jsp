<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>首页</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/backend/xtbg/css/base.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/static/backend/xtbg/css/jquery.dialog.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/static/backend/xtbg/css/index.css" />
	</head>

	<body>
		<div id="container">
			<div id="hd">
				<div class="hd-wrap ue-clear">
					<div class="top-light"></div>
					<h1 class="logo"></h1>
					<div class="toolbar ue-clear">
						<a href="javascript:;" date-src="${ctx }/backend/frame/iframeIndex" class="home-btn">首页</a>
						<a href="javascript:;" class="updatePwd-btn" id="updatePwd">修改密码</a>
						<a href="javascript:;" class="quit-btn" id="exits">退出</a>
					</div>
				</div>
			</div>
			<div class="welcome_bg">
				<div class="welcome_ico">欢迎您：${user.name }</div>
			</div>
			<div id="bd">
				<div class="wrap ue-clear">
					<div class="sidebar">
						<h2 class="sidebar-header"><p>功能导航</p></h2>
							<!-- 动态leftMenuStart -->
							<c:if test="${not empty parentResources}">  
								<ul class="nav">
			   					<c:forEach var="resource" items="${parentResources }">
				   						<li class="${resource.icon }">
											<div class="nav-header">
												<a href="javascript:;" class="ue-clear"><span>${resource.name}</span><i class="icon"></i></a>
											</div>
											<!-- 获取二级节点 -->
											<c:if test="${not empty childResources}"> 
												<c:set value="${resource.id }" var="rId"></c:set>
												<ul class="subnav">
													 <c:forEach var="twoResource" items="${childResources[rId]}">
														<li><a href="javascript:;" date-src="${ctx }${twoResource.path }">${twoResource.name }</a></li>  
													 </c:forEach>
												</ul>
											</c:if>
										</li>
			   					</c:forEach>
			   					</ul>
			    			</c:if>  							
					</div>

					<div class="content">
						<iframe src="${ctx }/backend/frame/home" id="iframe" width="100%" height="100%" frameborder="0"></iframe>
					</div>
				</div>
			</div>
			<div id="ft" class="ue-clear" style="position: fixed;bottom: -25px;z-index: 999;height:50px;width: 100%;">
				<div class="ft-left">
					<span>深圳市公安局交通警察局</span>
					<!-- <em>Office&nbsp;System</em> -->
				</div>
				<!-- <div class="ft-right">
					<span>Automation</span>
					<em>V1.0&nbsp;2017</em>
				</div> -->
			</div>
		</div>

	</body>
	
	<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
	<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/core.js"></script>
	
	<script type='text/javascript' src='${ctx}/static/backend/js/zDialog/zDrag.js'></script>	
	<script type='text/javascript' src='${ctx}/static/backend/js/zDialog/zDialog.js?{ctx:"${ctx}"}'></script>	
	<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/jquery.dialog.js"></script>
	
	<script type="text/javascript" src="${ctx }/static/backend/js/artDialog/jquery.artDialog.min.js"></script>
	<script type="text/javascript" src="${ctx }/static/backend/js/artDialog/artDialog.iframeTools.min.js"></script>
	<link rel="stylesheet" href="${ctx}/static/backend/js/artDialog/skins/chrome.css"type="text/css"></link>
	
	<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/index.js"></script>
	<script type="text/javascript">
	$('#exits').click(function(e) {
		Dialog.confirm("确认退出系统吗？",
		function(){
			window.location.href = "${ctx}/backend/loginout";
		  }
		);
	});
	$('#updatePwd').click(function(e) {
		$.dialog.open('${ctx}/backend/enter',{title: '修改密码',  width: 600, height: 350});
		// window.location.href = "${ctx}/backend/login/enter";
	});
	</script>
</html>