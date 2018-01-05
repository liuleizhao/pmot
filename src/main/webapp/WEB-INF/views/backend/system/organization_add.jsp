<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>深圳市机动车年审检测预约系统-添加机构</title>

<!--llz add  -->
<%@ include file="/static/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/xtbg/css/mui.min.css" />
<link rel="stylesheet" href="${ctx}/static/xtbg/css/base.css" />
<link rel="stylesheet" href="${ctx}/static/xtbg/css/info-reg.css" />
<link href="${ctx }/static/css/password_strength_style.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/static/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/static/css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/css/backend_common.css" />


<link rel="stylesheet" href="${ctx}/static/css/ztree/zTreeStyle/zTreeStyle.css" type="text/css">

<script type="text/javascript" src="${ctx}/static/js/zTree/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/zTree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctx}/static/js/zTree/jquery.ztree.excheck-3.5.js"></script>

<script type='text/javascript' src='${ctx}/static/js/zDialog/zDialog.js?{ctx:"${ctx}"}'></script>

<!--artDialog-->
<script type="text/javascript" src="${ctx }/static/js/validator.js"></script>
<script type="text/javascript" src="${ctx }/static/js/artDialog/jquery.artDialog.min.js"></script>
<script type="text/javascript" src="${ctx }/static/js/artDialog/artDialog.iframeTools.min.js"></script>


<script type="text/javascript">
	var setting = {
		view : {
			selectedMulti : false
		//设置是否允许同时选中多个节点。
		},
		check : {
			enable : true,
			chkStyle : "checkbox",
			chkboxType : {
				"Y" : "ps",
				"N" : "s"
			}
		},
		callback : {}
	};
	//var zNodes;
	var zNodes = "";
	function expandNode(e) {
		var zTree = $.fn.zTree.getZTreeObj("resource_list"), type = e.data.type, nodes = zTree
				.getSelectedNodes();
		if (type.indexOf("All") < 0 && nodes.length == 0) {
			artDialog({
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
			for ( var i = 0, l = nodes.length; i < l; i++) {
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

	$(function() {
		var organizationId = $("#id").val();
		var isAdmin = ${backend_user_session.isAdmin};
		if(isAdmin <= 0 ){
			setting.check.enable = false;
		}
		$.post("${ctx }/backend/system/role/getRoleTree", {
			organizationId : organizationId
		}, function(data) {
			zNodes = eval(data.roles);
			$.fn.zTree.init($("#resource_list"), setting, zNodes);
		}, "json");

		$("#expandBtn").bind("click", {
			type : "expand"
		}, expandNode);
		$("#toggleBtn").bind("click", {
			type : "toggle"
		}, expandNode);
		$("#expandAllBtn").bind("click", {
			type : "expandAll"
		}, expandNode);
		$("#collapseAllBtn").bind("click", {
			type : "collapseAll"
		}, expandNode);
		var message = $("#errormess").val();
		if ("" != message) {
			$.dialog({
				title : '3秒后自动关闭',
				time : 3,
				content : message,
				icon : 'succeed',
				yesFn : true
			});
		}

		$("#addBtn").click(
				function() {
					try {
						/**************************check start****************************/
						$(this).validate.isNull($("#name").val(), '组织名称不能为空');
						$(this).validate.isNull($("#socialCreditCard").val(),'社会信用代码不能为空');
						$(this).validate.isNull($("#orgType").val(), '组织类型不能为空');
						$(this).validate.isNull($("#code").val(), '组织代码不能为空');
						$(this).validate.isNull($("#address").val(), '地址不能为空');
						$(this).validate.isNull($("#contact").val(), '联系人名称不能为空');
						$(this).validate.isNull($("#contactIdNumber").val(), '联系人身份证不能为空');
						$(this).validate.isNull($("#contactMobile").val(), '联系人手机号不能为空');
						$(this).validate.isNull($("#leader").val(), '负责人名称不能为空');
						$(this).validate.isNull($("#leaderIdNumber").val(), '负责人身份证不能为空');
						$(this).validate.isNull($("#leaderMobile").val(), '负责人手机号不能为空');
						$(this).validate.isNull($("#legalPerson").val(), '法人名称不能为空');
						$(this).validate.isNull($("#legalPersonMobile").val(), '法人手机号不能为空');
						$(this).validate.isNull($("#legalPersonIdNumber").val(), '法人身份证不能为空');
						$(this).validate.isNull($("#orderNum").val(), '排列顺序不能为空');
						var zTree = $.fn.zTree.getZTreeObj("resource_list");
						var nodes = zTree.getCheckedNodes(true);
						var ids = '';//选中节点ids
						for ( var i = 0; i < nodes.length; i++) {
							if (ids != '') {
								ids += ',';
							}
							ids += nodes[i].id;
						}
						$("#ids").attr("value", ids);
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

		$(".resourceType").bind(
				"click",
				function() {
					var resourceType = $(
							'input:radio[name="inputResourceType"]:checked')
							.val();
					$("#orgType").val(resourceType);
				});
		var areaId = $("#areaId").val();		
		$("#areaSelect option[value="+areaId+"]").attr("selected", "true");

	});

	function select(id) {
		var diag = new Dialog();
		diag.Title = "选择父组织";
		diag.Width = 500;
		diag.Height = 600;
		diag.URL = "${ctx}/backend/system/organization/selectParentOrg?changeOrgId="+id; //controller
		diag.OKEvent = function() {
			$id('jq_zTree_id').value = diag.innerFrame.contentWindow.document
					.getElementById('treeNodeId').value;
			$id('jq_zTree_name').value = diag.innerFrame.contentWindow.document
					.getElementById('treeNodeName').value;
			diag.close();
		};
		diag.show();
	}
</script>
</head>
<body>
	<div class="title">
		<h2>添加机构</h2>
	</div>
	<form class="classA mui-input-group z_99" action="${ctx }/backend/system/organization/add" method="post" name="form" id="form">
		<input type="hidden" name="id" id="id" value="${organization.id}"> <input type="hidden" name="ids" id="ids"> 
		<input  id="orgAdminId" name='orgAdminId' type="hidden" value="${organization.orgAdminId }">
		<input type="hidden" name="orgType" id="orgType" value="${organization.orgType}">
		<input type="hidden" id="arrow" name="arrow" value="${arrow}" /> 
		<input type="hidden" id="mess" value='${message }' /> 
		<input type="hidden" id="errormess"value='${errorMessage }' /> 
		<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" />
		<input type="hidden" id="areaId" value="${areaId }">
		
		<table cellpadding="8" cellspacing="1">
			<tr>
				<td class="field"><span class="txt-imp">*</span>组织名称</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="name" id="name" type="text" value="${organization.name }"
							<c:if test="${isEdit && backend_user_session.isAdmin <= 0}">readonly="readonly"</c:if>
						> 
						<span id="check_name_exist" class="Prompt"></span>
					</div></td>
				<td class="field"><span class="txt-imp">*</span>社会信用代码</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="socialCreditCard" id="socialCreditCard" type="text" value="${organization.socialCreditCard }"
							<c:if test="${isEdit && backend_user_session.isAdmin<=0}">readonly="readonly"</c:if>
						>
					</div></td>
			</tr>

			<tr>
				<td class="field"><span class="txt-imp">*</span>组织类型</td>
				<td class="text">
					<div class="mui-input-row">
						<div class="control-group">
							<div class="controls">
								<c:forEach items="${organizationTypes }" var="resourceType" varStatus="s">
									<label class="radio o_label">
										<div class="radio" >
											<input id="resourceType${resourceType.index }" name="inputResourceType" type="radio" class="resourceType" value="${resourceType.value }"
												<c:if test="${organization.orgType.value eq resourceType.value }">checked="checked"</c:if> 
												<c:if test="${isEdit && backend_user_session.isAdmin<=0}">disabled="disabled"</c:if>	
											/>
										</div>
										 <span for="resourceType${resourceType.id }">${resourceType.description }</span> 
									</label>
								</c:forEach>
							</div>
						</div>
					</div></td>
				<td class="field"><span class="txt-imp">*</span>组织代码</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="code" id="code" type="text" value="${organization.code }"
							<c:if test="${isEdit && backend_user_session.isAdmin<=0}">readonly="readonly"</c:if>
						>
					</div></td>
			</tr>
			<tr>
				<td class="field">组织描述</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="description" id="description" type="text" value="${organization.description }">
					</div></td>
				<td class="field"><span class="txt-imp">*</span>地址</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="address" id="address" type="text" value="${organization.address }"/>
					</div></td>
			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>联系人名称</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="contact" id="contact" type="text" value="${organization.contact }" /> <span id="check_name_exist" class="Prompt"></span>
					</div></td>
				<td class="field"><span class="txt-imp">*</span>联系人身份证</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="contactIdNumber" id="contactIdNumber" type="text" value="${organization.contactIdNumber }" />
					</div></td>
			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>联系人手机号</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="contactMobile" id="contactMobile" type="text" value="${organization.contactMobile }" />
					</div></td>
				<td class="field"><span class="txt-imp">*</span>负责人名称</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="leader" id="leader" type="text" value="${organization.leader }" /> <span id="check_name_exist" class="Prompt"></span>
					</div></td>
			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>负责人身份证</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="leaderIdNumber" id="leaderIdNumber" type="text" value="${organization.leaderIdNumber }" />
					</div></td>
				<td class="field"><span class="txt-imp">*</span>负责人手机号</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="leaderMobile" id="leaderMobile" type="text" value="${organization.leaderMobile }" />
					</div></td>
			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>法人名称</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="legalPerson" id="legalPerson" type="text" value="${organization.legalPerson }" /> <span id="check_name_exist" class="Prompt"></span>
					</div></td>
				<td class="field"><span class="txt-imp">*</span>法人身份证</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="legalPersonIdNumber" id="legalPersonIdNumber" type="text" value="${organization.legalPersonIdNumber }" />
					</div></td>
			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>法人手机号</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="legalPersonMobile" id="legalPersonMobile" type="text" value="${organization.legalPersonMobile }" />
					</div></td>
				<td class="field"><span class="txt-imp">*</span>排列顺序</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="orderNum" id="orderNum" type="text" value="${organization.orderNum }" />
					</div></td>
			</tr>
			<tr>
				<td class="field">父组织</td>
				<td class="text">
					<div class="mui-input-row">
						<input id="jq_zTree_name" name="parentName" readonly="readonly" type="text" value="${empty parentOrg ?"无父组织":parentOrg.name}" /> 
						<span id="check_code_exist" class="Prompt"></span>
						<!-- 只有超级管理员才有选择父组织的权限 -->
						<c:if test="${backend_user_session.isAdmin>0}">
							<a style="margin-left: 10px" id="jq_select" class="cRed" href="javascript:void(0);" onclick="select('${organization.id}');">选择</a> &nbsp;&nbsp; <a id="jq_parent_null"
								class="cRed" href="javascript:void(0);">无父组织</a> 
							<input id="jq_zTree_id" name="parentId" type="hidden" value="${parentOrg.id }" />
						</c:if>
					</div></td>
				<td class="field">选择区/县</td>
				<td class="text select_td">
					<div class="mui-input-row">
						<select name="areaId" id="areaSelect">
							<c:forEach var="area" items="${areaInfos }">
								<option value=${area.id }>${area.name }</option>
							</c:forEach>
						</select>
						<div class="triangle-down"></div>
					</div>
				</td>
			</tr>

			<tr>
				<td class="field">角色值</td>
							<td class="text" colspan="3">&nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:void(0);" class="cRed" id="expandAllBtn">展开全部节点</a>&nbsp;&nbsp; <a
						href="javascript:void(0);" class="cRed" id="collapseAllBtn">折叠全部节点</a>&nbsp;&nbsp; <a href="javascript:void(0);" class="cRed" id="expandBtn">展开选中节点</a>&nbsp;&nbsp;
						<a href="javascript:void(0);" class="cRed" id="toggleBtn">折叠选中节点</a>
						<div class="content_wrap">
							<div class="zTreeBackground">
								<ul id="resource_list" class="ztree">
									<li><span style="font-size:14px;font-family: "宋体",Tahoma,Arial,helvetica,sans-serif;"> <img src="${ctx}/static/images/front/busy.gif" />
											正在加载资源树，请稍候... </span></li>
								</ul>
							</div>
						</div></td>
			</tr>
		</table>
		<div class="mui-button-row">
			<input type="button" class="input_button" value="保存" id="addBtn"> <input type="button" class="input_button" value="返回"
				onClick="javascript:history.back(-1);">
		</div>
	</form>
</body>
</html>