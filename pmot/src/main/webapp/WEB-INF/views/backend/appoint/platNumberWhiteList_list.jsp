<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${appName}-车辆信息白名单列表</title>
<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
</head>
<body>
	<%@include file="/WEB-INF/views/backend/common/message.jsp" %>
	<div class="title">
		<h2>车辆信息白名单列表</h2>
	</div>
	<div class="main">
		<div class="context">
			<form action="${ctx }/backend/appoint/platNumberWhiteList/list" method="get" name="form" id="form">
				<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" />
				<div class="query">
					<div class="query-conditions ue-clear">
						<div class="conditions staff ue-clear i_div">
							<label>号牌号码：</label> 
							<input type="text" name="platNumber" value="${bookInfo.platNumber }">
						</div>
						<div class="conditions staff ue-clear i_div">
							<label>车架号：</label> 
							<input type="text" name="frameNumber" value="${bookInfo.frameNumber }">
						</div>
						<div class="conditions name ue-clear no_border">
							<input id="queryBtn" type="submit" value="查询" class="input_button"> 
							<input type="button" value="新增" class="input_button"
								onclick="javascript:window.location.href='${ctx }/backend/appoint/platNumberWhiteList/add'">
						</div>
					</div>
				</div>
			</form>
			<div class="table_box">
				<table>
					<thead>
						<tr>
							<th class="length2">号牌种类</th>
							<th class="length3">车牌号</th>
							<th class="length3">车架号</th>
							<th class="length3">创建时间</th>
							<th class="length4">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageView.list }" var="entity" varStatus="status">
							<%-- <c:choose>
								<c:when test="${status.index %2 == 1 }">
									<tr class="length2" id="${entity.id }">
								</c:when>
								<c:otherwise>
									<tr class="length2" id="${entity.id }">
								</c:otherwise>
							</c:choose> --%>
							<td class="length3">${entity.carTypeName }</td>
							<td class="length3">
								<c:if test="${ entity.newFlag eq '1' }">新车</c:if>
								${entity.platNumber }
							</td>
							<td class="length2">${entity.frameNumber }</td>
							<td class="length3"><fmt:formatDate value="${entity.createDate }" pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td class="length4">
								<a class="mui-btn-blue" href="${ctx }/backend/appoint/platNumberWhiteList/edit?id=${entity.id}">编辑</a>
								<%-- 
								&nbsp; &nbsp; 
								 <a class="mui-btn-blue" href="${ctx }/backend/system/role/views?roleId=${role.id}">查看</a>  --%>
								<%--
								&nbsp; &nbsp; 
								 <a class="mui-btn-blue"href="javascript:deleteRole('${role.id }','${role.name }');">删除</a> --%>
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