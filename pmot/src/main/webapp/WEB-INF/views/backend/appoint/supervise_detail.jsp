<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
	<title>事后监管统计</title>
	<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
	<style type="text/css">
		#startDate,#endDate{
			background: url(../../static/backend/js/My97DatePicker/skin/datePicker.gif) right center no-repeat rgb(255, 255, 255)}
	</style>
</head>
<body>
	<div class="title">
		<h2>统计列表</h2>
	</div>
	<div class="main">
		<div class="context">	
			<input type="hidden" id="errormess" value='${errorMessage }' /> 
			<form  action="${ctx }/appoint/supervise/detail" method="post" name="form" id="form">
				<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" />
				<div class="query">
					<div class="query-conditions ue-clear">
						<div class="conditions staff ue-clear">
							<label style="width:auto;">开始日期：</label>
							<input type="text" class="Wdate" id="startDate" name="startDate" 
							value="<fmt:formatDate value="${bookInfoSupervise.startDate}" pattern="yyyy-MM-dd" />" 
							onclick="WdatePicker({isShowClear:true,readOnly:true,maxDate:'%y-%M-{%d-1}'})"/>
						</div>
						<div class="conditions staff ue-clear">
							<label style="width:auto;">结束日期：</label>
							<input type="text" class="Wdate" id="endDate" name="endDate" 
							value="<fmt:formatDate value="${bookInfoSupervise.endDate}" pattern="yyyy-MM-dd" />" 
							onclick="WdatePicker({isShowClear:true,readOnly:true,maxDate:'%y-%M-{%d-1}'})"/>
						</div>
						<%-- <div class="conditions staff ue-clear">
							<label style="width:auto;">号牌号码：</label>
							<input type="text" name="platNumber" value="${bookInfoSupervise.platNumber}" />
						</div> --%>
						<div class="conditions name ue-clear">
							<div class="mui-input-row">
								<label style="width:auto;">检测站：</label>
								<select name="stationId" class="q_select">
									<option value="">请选择...</option>
									<c:forEach items="${stationList}" var="station">
										<c:if test="${bookInfoSupervise.stationId eq station.id}">
											<option value="${station.id}" selected="selected">${station.name}</option>
										</c:if>
										<c:if test="${bookInfoSupervise.stationId ne station.id}">
											<option value="${station.id}">${station.name}</option>
										</c:if>
									</c:forEach>
								</select>
							</div> 
						</div>
						<div class="conditions name ue-clear no_border">
							<input type="submit" value="查询" class="input_button">
						</div>
					</div>
				</div>

				<div class="table_box">
					<table id="liebiaocolor">
						<thead>
							<tr>
								<th class="length2">序号</th>
								<th class="length3">流水号</th>
								<th class="length3">号牌号码</th>
								<th class="length3">号牌种类</th>
								<th class="length3">车架号</th>
								<th class="length3">预约号</th>
								<th class="length3">预约/登陆日期</th> 
								<th class="length3">预约状态</th>
								<th class="length3">办理状态</th>
								<th class="length3">业务状态</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pageView.list }" var="entity" varStatus="status">
								<tr>
									<td class="length3">${status.count }</td>
									<td class="length3">${empty entity.lsh ? "无": entity.lsh}</td>
									<td class="length3">${entity.platNumber}</td>
									<td class="length3">${entity.carTypeName}</td>
									<td class="length3">${entity.frameNumber}</td>
									<td class="length3">${empty entity.bookNumber ? "无":entity.bookNumber}</td>
									<td class="length3">${entity.bookOrLoginDate}</td> 
									<td class="length3">${empty entity.bookState ? "未预约":entity.bookState.description}</td>
									<td class="length3">${empty entity.lsh ? "未办理":"已办理"}</td>
									<td class="length3">${entity.isException>0?"异常记录":"正常"}</td>
								</tr>
							</c:forEach>
						</tbody>

					</table>
				</div>
			</form>
			<%@ include file="/WEB-INF/views/backend/common/pagination.jsp"%>
		</div>
	</div>

	<!-- 图表展示 -->
	<div id="container" style="height:4000px;"></div>
	<script type="text/javascript" src="${ctx}/static/backend/js/My97DatePicker/WdatePicker.js"></script>
	<script>
		$(function(){
			$("td:contains('异常记录')").parent("tr").css("background","#DB3E3E");
			$("td:contains('未办理')").parent("tr").css("background","#BBB");
		});
    </script>
</body>
</html>
