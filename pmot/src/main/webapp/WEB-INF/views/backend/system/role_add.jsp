<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${appName}-新增角色</title>
<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/mui.min.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-reg.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
<link rel="stylesheet" href="${ctx}/static/backend/css/backend_common.css" />
<link href="${ctx }/static/backend/css/ztree/style_reset.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" src="${ctx }/static/backend/css/ztree/ztree_common.css" type="text/css"/>		
<link rel="stylesheet" href="${ctx}/static/backend/css/ztree/zTreeStyle/zTreeStyle.css"	type="text/css">

<script type="text/javascript" src="${ctx}/static/backend/js/zTree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/static/backend/js/zTree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
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
			$.post("${ctx }/backend/system/resource/getResource", { "treeType": 2 },
				   function(data){
					  zNodes =  eval(data.resources);
					  $.fn.zTree.init($("#resource_list"), setting,zNodes);
			  }, "json");
			$("#expandBtn").bind("click", {type:"expand"}, expandNode);
			$("#toggleBtn").bind("click", {type:"toggle"}, expandNode);
			$("#expandAllBtn").bind("click", {type:"expandAll"}, expandNode);
			$("#collapseAllBtn").bind("click", {type:"collapseAll"}, expandNode);
			var message = $("#errorMessage").val();
			if("" != message ){
				$.dialog({
							title : '3秒后自动关闭',
							time : 3,
							content : message,
							icon : 'succeed',
							yesFn : true
						});
			}
			
			//ajax验证类型name不能相同
			$("#name").bind('blur',function(e){
		   		var a = $(this).val();
		   	  $.ajax({
				    url:'${ctx}/backend/system/role/checkRole',
				    data:{'name':encodeURI(encodeURI(a))},
				    success:function(data){
		                var isBlank = data.isBlank;
		                var isExist = data.isExist;
							if(!isBlank && isExist){
								$(".check_name_class").css('color','red');
								$("#check_name_exist").html("角色名称已存在");
								$("#name").val('');
							} else if(isBlank){
								$(".check_name_class").css('color','red');
								$("#check_name_exist").html("请输入角色名称");
							} else {
								$(".check_name_class").css('color','green');
								$("#check_name_exist").html("角色名称可以使用");
							}
						}
				  });
				}
			);
			$("#addBtn").click(function() {
				try {
					/**************************check start****************************/
					$(this).validate.isNull($("#name").val(), '角色名称不能为空');
					var zTree = $.fn.zTree.getZTreeObj("resource_list");
					var nodes = zTree.getCheckedNodes(true);
					var ids = '';//选中节点ids
                    for(var i=0; i<nodes.length; i++){
                        if (ids != '') {
                       	 ids += ',';
                        }
                        ids += nodes[i].id;
                    }
					$("#ids").attr("value",ids);
					$(this).validate.submin_form();
				} catch (e) {
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
		<div class="title">
		<h2>新增角色</h2>
	</div>
	<input type="hidden" id="errorMessage" value="${errorMessage }" />
	<form class="classA mui-input-group z_99" action="${ctx }/backend/system/role/add" method="post" name="form" id="editFrom">
		<input type="hidden" name="ids"  id="ids">


		<table cellpadding="8" cellspacing="1">
			<tr>
				<td class="field"><span class="txt-imp">*</span>角色名称</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="name" id="name" type="text" value="${role.name }" > <span id="check_name_exist" class="Prompt"></span>
					</div>
				</td>
				<td class="field">排列顺序</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="orderNum" id="orderNum" type="text" value="${role.orderNum }">
					</div></td>
			</tr>
			<tr>
				<td class="field">角色描述</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="description" id="description" type="text" value="${role.description }">
					</div></td>
			</tr>
			<tr>
				<td class="field">资源列表</td>
				<td class="text" colspan="3">&nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:void(0);" class="cRed" id="expandAllBtn">展开全部节点</a>&nbsp;&nbsp; <a
					href="javascript:void(0);" class="cRed" id="collapseAllBtn">折叠全部节点</a>&nbsp;&nbsp; <a href="javascript:void(0);" class="cRed" id="expandBtn">展开选中节点</a>&nbsp;&nbsp;
					<a href="javascript:void(0);" class="cRed" id="toggleBtn">折叠选中节点</a>
					<div class="content_wrap">
						<div class="zTreeBackground">
							<ul id="resource_list" class="ztree">
								<li><span style="font-size:14px;font-family: "宋体",Tahoma,Arial,helvetica,sans-serif;"> <img
										src="${ctx}/static/backend/images/front/busy.gif" /> 正在加载资源树，请稍候... </span></li>
							</ul>
						</div>
					</div>
				</td>
			</tr>
		</table>
		<div class="mui-button-row">
			<input type="button" class="input_button" value="保存" id="addBtn"> <input type="button" class="input_button" value="返回"
				onClick="javascript:history.back(-1);">
		</div>
	</form>
</body>
</html>