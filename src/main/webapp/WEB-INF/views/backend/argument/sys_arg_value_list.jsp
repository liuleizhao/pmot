<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${appName }-系统参数列表</title>
<link rel="stylesheet" href="${ctx}/static/xtbg/css/base.css" />
<link rel="stylesheet" href="${ctx}/static/xtbg/css/info-mgt.css" />
</head>

<body>

	<div class="title">
		<h2>系统参数列表</h2>
	</div>
	<div class="main">
		<div class="context">
			<form action="${ctx }/backend/argument/sys_arg_value/list" method="get" name="form" id="form">
				<input type="hidden" id="arrow" name="arrow" value="{arrow}" /> <input type="hidden" id="mess" value='${message }' /> 
				<input type="hidden" id="errormess" value='${errorMessage }' /> <input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" />
					<div class="query">
					<div class="query-conditions ue-clear">
						<div class="conditions staff ue-clear">
							<label>参数代码：</label> <input style="width:151px" name="code" value="${sysArgValue.code }"  type="text" />
						</div>
						<div class="conditions staff ue-clear i_div">
							<label>参数名称：</label> <input name="name" value="${sysArgValue.name }" type="text" />
						</div>
						<div class="conditions name ue-clear">
							<input id="queryBtn" type="button" value="查询" class="input_button" /> 
							<input onclick="javascript:window.location.href='${ctx }/backend/argument/sys_arg_value/add'" type="button" value="新增" class="input_button" />
						</div>
					</div>
				</div>

				<div class="table_box">
					<table id="liebiaocolor">
						<thead>
							<tr>
								<th class="length2">参数代码</th>
								<th class="length2">参数名称</th>
								<th class="length2">参数值</th>
								<th class="length3">是否可修改</th>
								<th class="length1">排序</th>
								<th class="length4">描述</th>
								<th class="length2">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pageView.list }" var="sysArgValue" varStatus="status">
				<c:choose>
					<c:when test="${status.index %2 == 1 }">
						<tr id="${sysArgValue.id }">
					</c:when>
					<c:otherwise>
						<tr id="${sysArgValue.id }">
					</c:otherwise>
				</c:choose>
				<td class="length2">${sysArgValue.code }</td>
				<td class="length2">${sysArgValue.name }</td>
				<td class="length2">${sysArgValue.value }</td>
				<td class="length3">${sysArgValue.isUpdate.description }</td>
				<td class="length1">${sysArgValue.orderNum }</td>
				<td class="length4">${sysArgValue.description }</td>
				<c:choose>
					<c:when test="${sysArgValue.isUpdate.id == 1 }">
						<td class="length2"><a class="mui-btn-blue" href="${ctx }/backend/argument/sys_arg_value/edit?sysArgValueId=${sysArgValue.id}">编辑</a></td>
					</c:when>
					<c:otherwise>
						<td class="length2"><a class="mui-btn-blue" href="${ctx }/backend/argument/sys_arg_value/edit?sysArgValueId=${sysArgValue.id}">查看</a></td>
					</c:otherwise>
				</c:choose>
				</tr>
			</c:forEach>
						</tbody>

					</table>
				</div>
			</form>
			<%@ include file="/WEB-INF/views/backend/common/pagination.jsp"%>
		</div>
	</div>
</body>
<script type="text/javascript" src="${ctx}/static/xtbg/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/xtbg/js/common.js"></script>

<script type="text/javascript">
	$(".select-title").on("click", function() {
		$(".select-list").hide();
		$(this).siblings($(".select-list")).show();
		return false;
	})
	$(".select-list").on(
			"click",
			"li",
			function() {
				var txt = $(this).text();
				$(this).parent($(".select-list")).siblings($(".select-title"))
						.find("span").text(txt);
			})

	$("tbody").find("tr:odd").css("backgroundColor", "#eff6fa");

	showRemind('input[type=text], textarea', 'placeholder');

	$(function() {
		var mess = $("#mess").val();
		if (mess != null && $.trim(mess) != "") {
			$.dialog({
				title : '3秒后自动关闭',
				time : 3,
				content : mess,
				icon : 'succeed',
				yesFn : true
			});
		}

		var errormess = $("#errormess").val();
		if (errormess != null && $.trim(errormess) != "") {
			$.dialog({
				title : '3秒后自动关闭',
				time : 3,
				content : errormess,
				icon : 'error',
				yesFn : true
			});
		}
		$("#queryBtn").click(function() {
			//new 2015-12-02 设置当前页为1，避免第2页以后，通过输入条件查询造成的显示不出数据的问题
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
</script>
</html>


