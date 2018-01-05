<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>首页</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${ctx}/static/backend/easyui/jquery.min.js"></script>
<script src="${ctx}/static/backend/easyui/jquery.easyui.min.js"></script>

<link href="${ctx}/static/backend/xtbg/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/backend/xtbg/css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/backend/xtbg/css/default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/backend/xtbg/css/style.css" rel="stylesheet" type="text/css" />
<!-- END bootstrap css -->
<link href="${ctx}/static/backend/xtbg/css/fullcalendar.css" rel="stylesheet" />

<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/home.css" />
<!--bootstrap js -->
<script src="${ctx}/static/backend/xtbg/js/jquery.blockui.min.js" type="text/javascript"></script>
<script src="${ctx}/static/backend/xtbg/js/app.js"></script>
<!--end bootstrap js  -->
<!-- zDialog -->
<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
<link href="${ctx}/static/backend/easyui/easyui.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<div id="tt" class="easyui-tabs" style="width:100%;height:100%">
		<div title="首页">
			<div class="centre">
				<img alt="" style="" src="${ctx}/static/backend/xtbg/images/homebg.png">
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		// initiate layout and plugins
		App.init();
	});
	//显示公告详情页面
	function openDetail(noticeId) {
		var maxWidth = document.body.offsetWidth;
		var diag = new Dialog();
		diag.Title = "公告详情";
		diag.Width = maxWidth;
		diag.Height = 650;
		diag.URL = "${ctx}/backend/pubNotice/pubNotice/noticeDetail?noticeId="
				+ noticeId;
		diag.show();
	}

	$(".title-list ul").on(
			"click",
			"li",
			function() {
				var aIndex = $(this).index();
				$(this).addClass("current").siblings().removeClass("current");
				$(".matter-content").removeClass("current").eq(aIndex)
						.addClass("current");
			});

	$(".duty").find("tbody").find("tr:even").css("backgroundColor", "#eff6fa");
	//刷新
	$('#tt').tabs({
	tools:[{
			iconCls:'tabs-refresh',
			handler:function(){
				var tab = $('#tt').tabs('getSelected');  // get selected panel
				$('#tt').tabs('update', {
					tab: tab,
					options: {
						title: tab.title,
					}
				});
			}
		}]
	});
	

	var index = 0;
	function addPanel(title, url) {

		var Tab = $('#tt').tabs('getTab', title);
		if (Tab != null) {
			//如果存在，选中刷新
			$('#tt').tabs('select', title);
			
			var tab = $('#tt').tabs('getSelected');  // get selected panel
				$('#tt').tabs('update', {
					tab: tab,
					options: {
						title: tab.title,
					}
				});
			return;
		}
		index++;
		$('#tt').tabs(
				'add',
				{
					title : title,
					content : '<iframe scrolling="auto" frameborder="0"  src="'
							+ url
							+ '" style="width:100%;height:100%;"></iframe>',
					closable : true,
					/* tools:[{
						iconCls:'tabs-refresh',
						handler:function(){
							$('#tt').tabs('update', {
								tab: tab,
								options: {
									title: 'New Title',
									href: 'get_content.php'  // the new content URL
								}
							});
						}
				    }] */
				});
	}
	function removePanel() {
		var tab = $('#tt').tabs('getSelected');
		if (tab) {
			var index = $('#tt').tabs('getTabIndex', tab);
			$('#tt').tabs('close', index);
		}
	}
</script>
</html>