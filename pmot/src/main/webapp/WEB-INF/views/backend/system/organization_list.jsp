<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>车辆年审核保系统-资源列表</title>
<%-- <%@ include file="/static/common/meta.jsp"%> --%>

<script type="text/javascript" src="${ctx }/static/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${ctx }/static/js/validator.js"></script>

<link rel="stylesheet" href="${ctx}/static/xtbg/css/base.css" />
<link rel="stylesheet" href="${ctx}/static/xtbg/css/info-mgt.css" />

<link href="${ctx }/static/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/static/css/style.css" rel="stylesheet" type="text/css" />
	
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
 <style type="text/css">
     	a{font-size:12px;}

.ztree li span.button.add {text-align:center;margin-left:6px; margin-right: 6px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}


.ztree li span.button.edit {text-align:center;margin-left:6px; margin-right: 6px;}
.ztree li span.button.remove {text-align:center;margin-left:6px; margin-right: 6px;}

     </style>

  <SCRIPT type="text/javascript">
		var setting = {	
						 view: {
								addHoverDom: addHoverDom,
								removeHoverDom: removeHoverDom,
								selectedMulti: false //设置是否允许同时选中多个节点。
							  },
						edit: {
							enable: true,
							showRemoveBtn: false,
							showRenameBtn: true,
							showRemoveBtn: true,
							renameTitle: "修改",
							removeTitle:"删除"
						},
						callback: {
							beforeEditName: beforeEditName,
							beforeRemove: beforeRemove
							
						}
						
			          };
   
		//var zNodes;
		var zNodes="";
		$(document).ready(function(){
			$.post("${ctx }/backend/system/organization/getOrganizations",
			function(data){
				zNodes =  eval(data.organizations);
			  	$.fn.zTree.init($("#organization_list"), setting,zNodes);
			}, "json");
		});
		
		//var newCount = 1;
		//添加按钮
		function addHoverDom(treeId, treeNode) {
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) {
				//控制添加一个增加按钮
				return;
			};
			
			var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
				+ "' title='新增' onfocus='this.blur();'></span>";
			sObj.after(addStr);
			var btn = $("#addBtn_"+treeNode.tId);
			if (btn) {
				btn.bind("click", function(){
					//添加子组织
					location.href = "${ctx }/backend/system/organization/add?organizationId="+treeNode.id;
				});
			}
		};
		
		function removeHoverDom(treeId, treeNode) {
			$("#addBtn_"+treeNode.tId).unbind().remove();
		};
		function selectAll() {
			var zTree = $.fn.zTree.getZTreeObj("organization_list");
			zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
		}
		
		//编辑节点
		function beforeEditName(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("organization_list");//获取 zTree 对象
			zTree.selectNode(treeNode);//选中指定节点
			location.href = "${ctx }/backend/system/organization/edit?organizationId="+treeNode.id;
			return false;
		}
		
		function beforeRemove(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("organization_list");
			zTree.selectNode(treeNode);
			Dialog.confirm("警告：您确认要删除【"+treeNode.name+"】吗？",
				function(){
					var r = removeResource(zTree,treeNode);
					//zTree.removeNode(treeNode);
					}
			);
			return false;
		}
	
		function removeResource(zTree,treeNode){
			$.post("${ctx}/backend/system/organization/remove", { "organizationId": treeNode.id },
						   function(data){
						    if(data==true){
						    zTree.removeNode(treeNode);
						    		$.dialog({
											title : '3秒后自动关闭',
											time : 3,
											content : "删除【"+treeNode.name+"】成功",
											icon : 'succeed',
											yesFn : true
										});
										
						    }else {
								$.dialog({
											title : '3秒后自动关闭',
											time : 3,
											content : "删除【"+treeNode.name+"】失败，请重新操作",
											icon : 'succeed',
											yesFn : true
										});
							}
						     
						   }, "json");
		}
		
		/*---------------------------------------------------*/
		//mark 用于表示是针对该节点做修改还是增加子节点  0 修改  1增加
		function openEditDialog(id,mark,zTree,treeNode){
				var diag = new Dialog();
				if("0"==mark){
					diag.Title = "修改组织";
				}else{
					diag.Title = "新增组织";
				}
				
				diag.Width = 700;
				diag.Height = 600;
				diag.Drag=false;
			    diag.URL = "${ctx}/backend/system/organization/edit?organizationId="+id+"&mark="+mark;  //controller
			    
			    
				diag.OKEvent = function(){
					//父资源ID
					var parentId = diag.innerFrame.contentWindow.document.getElementById('jq_zTree_id').value;
					//资源名称
					var name = diag.innerFrame.contentWindow.document.getElementById('name').value;
					//社会信用代码
					var socialCreditCard = diag.innerFrame.contentWindow.document.getElementById('socialCreditCard').value;
					//组织类型
					var resourceType = diag.innerFrame.contentWindow.document.getElementById('resType').value;
					//排列顺序                                                      													
					var orderNum = diag.innerFrame.contentWindow.document.getElementById('orderNum').value;
					//资源描述
					var description = diag.innerFrame.contentWindow.document.getElementById('description').value;
					//资源ID
					var id = diag.innerFrame.contentWindow.document.getElementById('id').value;
					
					try{
						if(""==name){
							throw ("组织名称不能为空");
							}
						var reg = /^[\d]{1,}$/;
						
						if("" == resourceType){
							throw ("组织类型不能为空");
						}
						if(!reg.test(orderNum)){
							throw ("输入正确的排列顺序");
						}
					}catch (e) {
						Dialog.alert(e, null, 200, 50);
						return false;
					}
					$.ajax(
						{		
							url:"${ctx}/backend/system/organization/edit",
							type:"post",
						    data: {
						    		id:id,
						    		parentId:parentId,
						    		name:name,
						    		orgType:resourceType,
						    		orderNum:orderNum,
						    		description:description,
									socialCreditCard:socialCreditCard
						    	  },
						   	dataType:"json",
						   	success:function(data){
						   		if(data!=null){
						   			//Dialog.alert(data.msg,null,200,50);
							   		if("0"==mark){
							   		//alert(1);
										treeNode.name=name;
										zTree.updateNode(treeNode);
									}else{
										var newNode = {id:data.newNodeId,name:data.newNodeName};
										zTree.addNodes(treeNode, newNode);
									}
						   			$.dialog({
											title : '3秒后自动关闭',
											time : 3,
											content : data.msg,
											icon : 'succeed',
											yesFn : true
										});
						   		}
						   		
						   	}
						}
					);
					diag.close();
					};
					/* diag.OKEvent= false; */
					diag.show();
			}
		function expandNode(e) {
			var zTree = $.fn.zTree.getZTreeObj("organization_list"),
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
		<div class="title">
			<h2>机构列表</h2></div>
		<div class="main">	
		<div class="context" >
			<div class="box-body">
					<table width="100%" border="0" class="yuyue" align="center" style="border: 0px">
						<tr style="border: 0px">
							<td class="col-sm-6" colspan="3">
								&nbsp;&nbsp;&nbsp;&nbsp;
									<a href="javascript:void(0);" class="cRed" id="expandAllBtn">展开全部节点</a>&nbsp;&nbsp;
									<a href="javascript:void(0);" class="cRed" id="collapseAllBtn">折叠全部节点</a>&nbsp;&nbsp;
									<a href="javascript:void(0);" class="cRed" id="expandBtn">展开选中节点</a>&nbsp;&nbsp;
									<a href="javascript:void(0);" class="cRed" id="toggleBtn">折叠选中节点</a>&nbsp;&nbsp;
									<a href="${ctx}/backend/system/organization/add" class="cRed" id="addUIBtn">添加机构</a>
								<div class="content_wrap">
									<div class="zTreeBackground">
										<ul id="organization_list" class="ztree">
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
					</table>
				</form>
			</div>
		</div>
		</table>
	</div>
	</div>
	</div>
 
</body>
</html>