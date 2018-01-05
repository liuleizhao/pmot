<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>深圳市机动车年审检测预约系统-广告列表</title>
<%@ include file="/static/common/meta.jsp"%>
<link href="${ctx }/static/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/static/css/style.css" rel="stylesheet" type="text/css" />

</head>
<body>
<div class="position">位置：首页 > 广告管理 > 广告列表</div>
	<div class="list-page">
		<div class="box">
			<div class="box-title">
				<h4>广告基本信息管理</h4>
				<div class="tools">
					<a href="javascript:;" class="arrow up" id="search_slide"></a>
				</div>
			</div>
			<div class="box-body">
				<form action="${ctx }/backend/system/adviertisement/list" method="get"
					name="form" id="form">
					<input type="hidden" id="arrow" name="arrow" value="${arrow}"/>
					<input type="hidden" id="mess" value='${message }' />
					<input type="hidden" id="errormess" value='${errorMessage }' /> 
					<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" />
					<table width="100%" border="0" class="yuyue" align="center">
						<tr class="control-tr high">
							<td class="col-sm-1 control-label">广告品牌:</td>
							<td class="col-sm-2"><input class="form-control" name="account" value="${user.account }" type="text" />
							</td>
							
							<td class="col-sm-1 control-label">签约人:</td>
							<td class="col-sm-2"><input class="form-control" name="account" value="${user.account }" type="text" />
							</td>
							
							<td class="col-sm-1 control-label">生效日期:</td>
							<td class="col-sm-2"><input class="form-control" name="account" value="${user.account }" type="text" />
							</td>
							
							
							<td class="col-sm-3 btn-td">
								<button class="button blue" id="queryBtn" type="button">查询</button>
								&nbsp;&nbsp;
								<button class="button orange" id="queryBtn" type="button"
									onclick="javascript:window.location.href='${ctx }/backend/system/adviertisement/add'">新增</button>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<table width="100%" border="0" class="liebiao" id="liebiaocolor">
			<tr class="evencolor">
				<th width="13%" align="center" nowrap="nowrap">广告品牌</th>
				<th width="13%" align="center" nowrap="nowrap">生效日期</th>
				<th width="13%" align="center" nowrap="nowrap">截止日期</th>
				<th width="13%" align="center" nowrap="nowrap">签约人</th>
				<th width="13%" align="center" nowrap="nowrap">合同编号</th>
				<th width="13%" align="center" nowrap="nowrap">创建人</th>
				<th width="13%" align="center" nowrap="nowrap">创建日期</th>
			</tr>
			<c:forEach items="${pageView.list }" var="entity">
				<tr>
				<td align="center">${entity.brand }</td>
				<td align="center"><fmt:formatDate value="${entity.effectivedate }"
							pattern="yyyy-MM-dd" /></td>
				<td align="center"><fmt:formatDate value="${entity.expirationdate }"
							pattern="yyyy-MM-dd" /></td>
				<td align="center">${entity.signUser.name }</td>
				<td align="center">${entity.contractno }</td>
				<td align="center">${entity.creatorUser.name }</td>
				<td align="center"><fmt:formatDate value="${entity.createDate }"
							pattern="yyyy-MM-dd" /></td>
				</tr>
			</c:forEach>
		</table>
		<%@ include file="/WEB-INF/views/backend/common/pagination.jsp"%>
	</div>

	<script type="text/javascript">
		$(function() {
			$("#search_slide").click(function(){
				if($(this).hasClass("down")){
					$(this).parent().parent().parent().find(".box-body").show();
					$(this).removeClass("down");
					$(this).addClass("up");
					$("#arrow").val("up");
				}else{
					$(this).parent().parent().parent().find(".box-body").hide();
					$(this).removeClass("up");
					$(this).addClass("down");
					$("#arrow").val("down");
				}
			});
			
			
			if("down" == "${arrow}"){
				$("#search_slide").trigger("click");
			}
			
		});
	
	</script>
</body>
</html>