<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>深圳市机动车年审检测预约系统-用户授权</title>
<%@ include file="/static/common/meta.jsp"%>
<link href="${ctx }/static/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/static/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/static/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${ctx}/static/js/validator.js"></script>
<link href="${ctx }/static/css/ztree/style_reset.css" rel="stylesheet" type="text/css" />
 
<link rel="stylesheet" src="${ctx }/static/css/ztree/ztree_common.css" type="text/css"/>
<link rel="stylesheet" href="${ctx}/static/css/ztree/zTreeStyle/zTreeStyle.css"	type="text/css">	
	
<script type="text/javascript" src="${ctx}/static/js/zTree/jquery-1.4.4.min.js"></script>
<script type="text/javascript"	src="${ctx}/static/js/zTree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/static/js/zTree/jquery.ztree.excheck-3.5.js"></script>
<!--artDialog-->
<script type="text/javascript" src="${ctx}/static/js/validator.js"></script>
<script type="text/javascript"	src="${ctx}/static/js/artDialog/jquery.artDialog.min.js"></script>
<script type="text/javascript"	src="${ctx}/static/js/artDialog/artDialog.iframeTools.min.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/static/js/artDialog/skins/chrome.css"	></link>

		
<script type="text/javascript">
	var setting = {	
				view: {
						selectedMulti: false //设置是否允许同时选中多个节点。
					  },
				check: {
					enable: true,
					chkStyle: "checkbox",
					chkboxType: { "Y": "ps", "N": "s" }
				},
				callback: {
				}
	          };
	//var zNodes;
	var zNodes="";
	function expandNode(e) {
		var zTree = $.fn.zTree.getZTreeObj("resource_list"),
		type = e.data.type,
		nodes = zTree.getSelectedNodes();
		if (type.indexOf("All")<0 && nodes.length == 0) {
			artDialog( {
						title : '3秒后自动关闭',
						time : 3,
						content : "请选择一个父节点",
						icon : 'error',
						yesFn : true
					});
		}
		if (type == "expandAll") {
			zTree.expandAll(true);
		} else if (type == "collapseAll") {
			zTree.expandAll(false);
		} else {
			var callbackFlag = $("#callbackTrigger").attr("checked");
			for (var i=0, l=nodes.length; i<l; i++) {
				zTree.setting.view.fontCss = {};
				if (type == "expand") {
					zTree.expandNode(nodes[i], true, null, null, callbackFlag);
				} else if (type == "collapse") {
					zTree.expandNode(nodes[i], false, null, null, callbackFlag);
				} else if (type == "toggle") {
					zTree.expandNode(nodes[i], null, null, null, callbackFlag);
				} else if (type == "expandSon") {
					zTree.expandNode(nodes[i], true, true, null, callbackFlag);
				} else if (type == "collapseSon") {
					zTree.expandNode(nodes[i], false, true, null, callbackFlag);
				}
			}
		}
	}
	
			
		$(function(){
			$("#userResource").css({"color":"red"});
			var userId = $("#userId").val();
			$.post("${ctx }/backend/system/resource/getResource", { "treeType": 2,"userId":userId },
			   function(data){
			  zNodes =  eval(data.resources);
			  $.fn.zTree.init($("#resource_list"), setting,zNodes);
		  	}, "json");
		  	
			$("#expandBtn").bind("click", {type:"expand"}, expandNode);
			$("#toggleBtn").bind("click", {type:"toggle"}, expandNode);
			$("#expandAllBtn").bind("click", {type:"expandAll"}, expandNode);
			$("#collapseAllBtn").bind("click", {type:"collapseAll"}, expandNode);
			var message = $("#message").val();
				if("" != message){
					$.dialog({
								title : '3秒后自动关闭',
								time : 3,
								content : message,
								icon : 'succeed',
								yesFn : true
							});
				}
				
				$("#addBtn").click(function() {
					try {
						/**************************check start****************************/
						var zTree = $.fn.zTree.getZTreeObj("resource_list");
						var nodes = zTree.getCheckedNodes(true);
						 var ids = '';//选中节点ids
	                    //遍历选中的节点，为s赋值
	                    for(var i=0; i<nodes.length; i++){
	                        if (ids != '') {
	                       	 ids += ',';
	                        }
	                        ids += nodes[i].id;
	                    }
	                    if(ids.length<=0){
	                   	 throw ("请选择资源");
	                    }
						$("#resourceIds").attr("value",ids);
						$(this).validate.submin_form();
					} catch (e) {
						alert(e);
						$.dialog({
								title : '3秒后自动关闭',
								time : 3,
								content : e,
								icon : 'error',
								yesFn : true
							});
					}
				return false;	
			});
		});
		</script>
</head>
<body>
<BODY>
		<div class="position">位置：首页 > 权限管理 > 用户授权</div>
		<div class="list-page">
			<div class="box">
				<div class="box-title">
					<h4>用户授权</h4>
				</div>
				<div class="box-body-edit">
					<%@ include file="/WEB-INF/views/backend/common/authorize_top.jsp"%>
					
					<form  action="${ctx}/backend/system/user/userResourceAuthorize" method="post" name="form" id="form">
						<input type="hidden" name="resourceIds"  id="resourceIds" value="${resourceIds}">
						<input type="hidden" id="message" value="${message }" />
						<input type="hidden" name="userId" id="userId"  value="${user.id }">
						<table width="100%" border="0" class="yuyue-edit" align="center">
							<tr height="40">
								<td class="col-sm-1 control-label"><label class="requiredInput">*</label>账户名:</td>
								<td class="col-sm-3">
									<input class="form-control" name="account" id="account" type="text" disabled="disabled" value="${user.account }"/>
								</td>
								<td class="col-sm-1 control-label">姓名:</td>
								<td class="col-sm-3"><input class="form-control" name="name" id="name" type="text" disabled="disabled" value="${user.name }"/></td>
								<td colspan="2"></td>
							</tr>
							<tr>
								<td class="col-sm-1 control-label"><label class="requiredInput">*</label>选择资源:</td>
								<td class="col-sm-4" colspan="2">
											&nbsp;&nbsp;
											<a href="javascript:void(0);" class="cRed" id="expandAllBtn">展开全部节点</a>&nbsp;&nbsp;
											<a href="javascript:void(0);" class="cRed" id="collapseAllBtn">折叠全部节点</a>&nbsp;&nbsp;
											<a href="javascript:void(0);" class="cRed" id="expandBtn">展开选中节点</a>&nbsp;&nbsp;
											<a href="javascript:void(0);" class="cRed" id="toggleBtn">折叠选中节点</a>
											<div class="content_wrap">
											<div class="zTreeBackground">
												<ul id="resource_list" class="ztree">
													<li>
														<span style="font-size:14px;font-family: "宋体",Tahoma,Arial,helvetica,sans-serif;">
															<img src="${ctx}/static/images/front/busy.gif" />
																正在加载资源树，请稍候...
														</span>
													</li>
												</ul>
											</div>
											</div>
								</td>
							</tr>
				 
							<tr>
								<td class="col-sm-12 control-label" colspan="4">
									<button class="button blue" id="addBtn" type="button">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
									<button class="button orange" onClick="javascript:history.back(-1);" type="button"><i class="fa fa-search"></i> 返回</button>
								</td>
								<td colspan="2"></td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
</body>
</html>