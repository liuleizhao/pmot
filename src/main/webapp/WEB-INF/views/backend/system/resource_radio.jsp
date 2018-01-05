<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>通过资源授权</title>
<script type="text/javascript" src="${ctx}/static/backend/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${ctx}/static/backend/js/validator.js"></script>

<link rel="stylesheet" src="${ctx }/static/backend/css/ztree/ztree_common.css" type="text/css"/>
<link rel="stylesheet" href="${ctx}/static/backend/css/ztree/zTreeStyle/zTreeStyle.css"	type="text/css">

<script type="text/javascript" src="${ctx}/static/backend/js/zTree/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${ctx}/static/backend/js/zTree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/static/backend/js/zTree/jquery.ztree.excheck-3.5.js"></script>
<script type='text/javascript' src='${ctx}/static/backend/js/blockUI/jquery.blockUI.min.js'></script>	
 
		
<style type="text/css">
        	a{font-size:12px;}
        </style>
        <SCRIPT type="text/javascript">
		var setting = {	
						view: {
								selectedMulti: false //设置是否允许同时选中多个节点。
							  },
						check: {
							enable: true,
							chkStyle: "radio",
							radioType: "all"
						},
						callback: {
							//beforeCheck: beforeCheck,
							onCheck: onCheck
						}
						
			          };
   
		//var zNodes;
		var zNodes="";
		$(document).ready(function(){
				$.blockUI({
					overlayCSS : {
						cursor : 'default'
					},
					css: { 
			            border: 'none', 
			            padding: '15px', 
			            backgroundColor: '#ffffff', 
			            '-webkit-border-radius': '20px', 
			            '-moz-border-radius': '20px', 
			            cursor: 'default'
		        	},
		        	message:'<span style="font-size:12px;font-family: "宋体",Tahoma,Arial,helvetica,sans-serif;"><img src="${ctx}/static/backend/images/front/busy.gif" />正在加载资源，请稍候...</span>'  
		     	});
			
			$.post("${ctx }/backend/system/resource/getResource", { "treeType": 2 },
			   function(data){
			   $.unblockUI();
			  zNodes =  eval(data.resources);
			  $.fn.zTree.init($("#resource_list"), setting,zNodes);
			  }, "json");
		});
		
		function onCheck(e, treeId, treeNode) {
			$("#treeNodeId").val(treeNode.id);
			$("#treeNodeName").val(treeNode.name);
		}	
		
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
			$("#expandBtn").bind("click", {type:"expand"}, expandNode);
			$("#toggleBtn").bind("click", {type:"toggle"}, expandNode);
			$("#expandAllBtn").bind("click", {type:"expandAll"}, expandNode);
			$("#collapseAllBtn").bind("click", {type:"collapseAll"}, expandNode);
		});
		
	</SCRIPT>
  </head>
  
  <body>
  		<input type="hidden" value="" id="treeNodeId"/>
  		<input type="hidden" value="" id="treeNodeName"/>
  		<table width="100%" border="0">
		
		
		<!-- <div class="nr"><input type="hidden" id="mess" value="${message }" /></div> -->
		<table width="99%" border="0" cellspacing="0" cellpadding="0"
			align="center">
			<tr>
				<td style="text-align: left;">
					&nbsp;&nbsp;
					<a href="javascript:void(0);" class="cRed" id="expandAllBtn">展开全部节点</a>&nbsp;&nbsp;
					<a href="javascript:void(0);" class="cRed" id="collapseAllBtn">折叠全部节点</a>&nbsp;&nbsp;
					<a href="javascript:void(0);" class="cRed" id="expandBtn">展开选中节点</a>&nbsp;&nbsp;
					<a href="javascript:void(0);" class="cRed" id="toggleBtn">折叠选中节点</a>
				</td>
			</tr>
		</table>
		<div class="content_wrap">
			<div class="zTreeBackground">
				<ul id="resource_list" class="ztree">
					<li>
						<span style="font-size:14px;font-family: "宋体",Tahoma,Arial,helvetica,sans-serif;">
							<img src="${ctx}/static/backend/images/front/busy.gif" />
								正在加载资源树，请稍候...
						</span>
					</li>
				</ul>
			</div>
		</div>
	</table>	
  </body>
</html>
