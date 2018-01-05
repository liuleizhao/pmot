<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${appName}-角色列表</title>
<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
</head>
<body>
	<%@include file="/WEB-INF/views/backend/common/message.jsp" %>
	<div class="title">
		<h2>角色列表</h2>
	</div>
	<div class="main">
		<div class="context">
			<form action="${ctx }/backend/system/role/list" method="get" name="form" id="form">
				<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" />
				<div class="query">
					<div class="query-conditions ue-clear">
						<div class="conditions staff ue-clear i_div">
							<label>角色名称：</label> <input name="name" value="${roleName }" type="text" />
						</div>

						<div class="conditions name ue-clear no_border">
							<input id="queryBtn" type="submit" value="查询" class="input_button"> 
							<input type="button" value="新增" class="input_button"
								onclick="javascript:window.location.href='${ctx }/backend/system/role/add'">
						</div>
					</div>
				</div>
			</form>
			<div class="table_box">
				<table>
					<thead>
						<tr>
							<th class="length2">角色名称</th>
							<th class="length3">角色描述</th>
							<th class="length3">角色授权数</th>
							<th class="length2">创建者</th>
							<th class="length3">创建时间</th>
							<th class="length4">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageView.list }" var="role" varStatus="status">
							<c:choose>
								<c:when test="${status.index %2 == 1 }">
									<tr class="length2" id="${role.id }">
								</c:when>
								<c:otherwise>
									<tr class="length2" id="${role.id }">
								</c:otherwise>
							</c:choose>
							<td class="length3">${role.name }</td>
							<td class="length2">${role.description }</td>

							<td class="length2">${role.count }</td>

			                <td class="length2">${role.creator }</td>
							<td class="length3"><fmt:formatDate value="${role.createdDate }" pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td class="length4">
								<a class="mui-btn-blue" href="${ctx }/backend/system/role/edit?roleId=${role.id}">编辑</a> 
								&nbsp; &nbsp; 
								<a class="mui-btn-blue" href="${ctx }/backend/system/role/views?roleId=${role.id}">查看</a> 
								&nbsp; &nbsp; 
								<a class="mui-btn-blue"href="javascript:deleteRole('${role.id }','${role.name }');">删除</a>
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
<!-- 将common.js放置在最后 -->
<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
<script type="text/javascript">
	$(function() {
		$("#queryBtn").click(function() {
			document.forms[0].submit();
		});

	});

	function deleteRole(roleId, roleName) {
		if ("系统管理员" == roleName) {
			$.dialog({
				title : '3秒后自动关闭',
				time : 3,
				content : '系统管理员角色不能删除',
				icon : 'warning',
				yesFn : true
			});
		} else {
			art.dialog({
				title : "温馨提示",
				content : "您确定要删除角色【"+roleName+"】吗？",
				icon : 'warning',
				yesFn : function() {
					removeRole(roleId, roleName);
					return true;
				},
				noFn : true
			//为true等价于function(){}
			});
		}
	}

	function removeRole(roleId, roleName) {
		$.ajax({
			type : 'GET',
			url : '${ctx }/backend/system/role/delete',
			data : {
				'roleId' : roleId
			},
			success : function(data) {
				if (data) {
					openSuccessDialog("删除成功",true,false,{time:3});
					setTimeout(function() {
						document.forms[0].submit();
					}, 2000);

				} else {
					openErrorDialog("删除失败，请重试",true,false);
				}
			}
		});
	}
	
	$(function() {
			$("#search_slide").click(
					function() {
						if ($(this).hasClass("down")) {
							$(this).parent().parent().parent()
									.find(".box-body").show();
							$(this).removeClass("down");
							$(this).addClass("up");
							$("#arrow").val("up");
						} else {
							$(this).parent().parent().parent()
									.find(".box-body").hide();
							$(this).removeClass("up");
							$(this).addClass("down");
							$("#arrow").val("down");
						}
					});

			if ("down" == "${arrow}") {
				$("#search_slide").trigger("click");
			}

		});
		$(".select-title").on("click", function() {
			$(".select-list").hide();
			$(this).siblings($(".select-list")).show();
			return false;
		})
		$(".select-list").on("click", "li", function() {
			var txt = $(this).text();
			$(this).parent($(".select-list")).siblings($(".select-title")).find("span").text(txt);
		})

		$("tbody").find("tr:odd").css("backgroundColor", "#eff6fa");

		showRemind('input[type=text], textarea', 'placeholder');
</script>
</html>