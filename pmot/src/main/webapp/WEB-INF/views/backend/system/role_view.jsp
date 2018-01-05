<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>${appName}-修改角色</title>
		<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/mui.min.css" />
		<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
		<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-reg.css" />
		<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
		<link rel="stylesheet" href="${ctx}/static/backend/css/backend_common.css" />
		<link href="${ctx }/static/backend/css/ztree/style_reset.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" src="${ctx }/static/backend/css/ztree/ztree_common.css" type="text/css"/>		
		<link rel="stylesheet" href="${ctx}/static/backend/css/ztree/zTreeStyle/zTreeStyle.css"	type="text/css">
		<title>角色信息</title>
	</head>

	<body>
		<div class="title">
			<h2>角色信息</h2></div>
		<div class="main">	
		<div class="context" > 
		<form class="classA mui-input-group" action="${ctx }/backend/system/role/views" method="get" name="form" id="form">
		<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" />
		<table cellpadding="8" cellspacing="1">
			<tr>
				<td class="field">角色名称</td>
				<td class="text">
					<div >
					    <input   type="hidden" name="roleId" id="roleId"  value="${role.id }" >
						<input disabled="disabled" name="name" id="name" type="text" value="${role.name }" > <span id="check_name_exist" class="Prompt"></span>
					</div>
				</td>
				<td class="field">排列顺序</td>
				<td class="text">
					<div >
						<input disabled="disabled" name="orderNum" id="orderNum" type="text" value="${role.orderNum }">
					</div></td>
			</tr>
		
				<tr >
				<td class="field">角色描述</td>
				<td class="text">
					<div >
						<input disabled="disabled" name="description" id="description" type="text" value="${role.description }">
					</div></td>
					
			</tr>
			</table>
		</form>
			
		<h2 style="margin: 10px">角色用户基本信息:</h2>
		<div class="table_box">
			<table>
				<thead>
					<tr>
						<th class="length2">账户名称</th>
						<th class="length2">姓名</th>
						<th class="length2">所属组织</th>
						<c:if test="${fn:contains(role.name,'财务')==true}">
							<th class="length2">操作</th>
						</c:if>
					</tr>
				</thead>
				<tbody id="search">
					<c:forEach items="${pageView.list}" var="user" varStatus="status">
						<tr>
							<td class="length2">${user.account }</td>
							<td class="length2">${user.name }</td>
							<td class="length2">${user.orgName }</td>
							<c:if test="${fn:contains(role.name,'财务')==true}">
								<td class="length2"><a href="javascript:;" onclick="configRelation('${user.id}','${user.orgId}')">配置</a></td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<%@ include file="/WEB-INF/views/backend/common/pagination.jsp"%>
		</div>
		</div> 
		<div><button type="button" class="mui-btn" style="background: #E6F4FF;width: 242px; margin-left: 500px;"  onClick="javascript:history.back(-1);">返回</button></div>
	</body>
	<script type="text/javascript" src="${ctx }/static/js/jquery-1.10.2.js"></script>
	<script type="text/javascript" src="${ctx}/static/xtbg/js/common.js"></script>
	<script type="text/javascript">
		$(".select-title").on("click", function() {
			$(".select-list").hide();
			$(this).siblings($(".select-list")).show();
			return false;
		})
		$(".select-list").on("click", "li", function() {
			var txt = $(this).text();
			$(this).parent($(".select-list")).siblings($(".select-title")).find("span").text(txt);
		})

		 $("#search").find("tr:odd").css("backgroundColor", "#eff6fa"); 

		showRemind('input[type=text], textarea', 'placeholder');
		function configRelation(id,orgId){
			var dialog = Dialog.open({
				URL:"${ctx}/backend/finance/balance/financeUserOrgRelation?id="+id+"&orgId="+orgId,
				OKEvent:function(){
					var treeObj = dialog.innerFrame.contentWindow.$.fn.zTree.getZTreeObj("resource_list");
					var nodes = treeObj.getCheckedNodes();
					var orgIds = "";
					for(var i=0; i<nodes.length; i++){
						orgIds += nodes[i].id;
						if(i < nodes.length-1) orgIds += ",";
					}
					console.log(orgIds);
					$.ajax({
						url:"${ctx}/backend/finance/balance/addFinanceUserOrgRelation",
						type:"POST",
						data:{
							"id":id,//user的id
							"orgId":orgId,//user的orgId
							"orgIds":orgIds//子组织id集合
						},
						success:function(result){
							if(result){
								dialog.close();
								Dialog.alert("提示：配置成功");
							}
						}
					});
				}
			});
		}
	</script>

</html>