<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ page import="java.util.Date"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>${appName }-大客户预约列表</title>
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
</head>
<style>
	.aui_focus tr{
	 border: 0px;
	}
	.aui_focus tr td{
	 border: 0px;
	}
	.title {
    height: 33px;
    padding-top: 0px;
	}
	
	.query-conditions .i_div {
    width: 280px;
}
	
</style>

<body>

	<div class="title">
		<h2>大客户预约列表</h2>
	</div>
	<div class="main">
		<div class="context">
			<input type="hidden" id="errormess" value='${errorMessage }' /> 
			<form  action="${ctx }/backend/appoint/comApllyBookList" method="get" name="form" id="form">
				<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" />
				<input type="hidden" id="compApplyFromId" name="compApplyFromId" value="${compApplyFromId }"/>
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
						<div class="conditions time ue-clear i_div" >
						<label class="criteria_title">预约日期：</label>
							<div class="time-select" >
								<input style="width:140px;" id="appointDate" type="text" name="bookDate" value="${bookInfo.bookDate }" placeholder="预约日期" />
								<i class="icon"></i>
							</div>
						</div>
						<div class="conditions name ue-clear">
							<input id="queryBtn" type="button" value="查询" class="input_button" /> 
						</div>
					</div>
				</div>
				<!-- 设置当前时间，用于下面比较，可以共用 -->
				<c:set var="nowDate"> 
				 	<fmt:formatDate value="<%=new Date()%>" pattern="yyyy-MM-dd " type="date"/>  
				</c:set>
				<div class="table_box">
					<table id="liebiaocolor">
						<thead>
							<tr>
								<th class="length3">企业名称</th>
								<th class="length3">联系电话</th>
							    <th class="length3">预约号</th>
							    <th class="length3">验证码</th>
								
								
								<th class="length3">号牌种类</th>
								<th class="length3">号牌号码</th>
								<th class="length3">车架号</th>
								<th class="length3">预约日期</th>
								<th class="length3">预约状态</th>
								<th class="length3">检测状态</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pageView.list }" var="entity" varStatus="status">
								<td class="length3">${entity.bookerName}</td>
								<td class="length3">${entity.mobile}</td>
								<td class="length3">${entity.bookNumber}</td>
								<td class="length3">${entity.verifyCode}</td>
								<td class="length3">${entity.carTypeName}</td>
								<td class="length3">${entity.platNumber}</td>
								<td class="length3">${entity.frameNumber}</td>
								<td class="length3">${entity.bookDate}</td>
								<td class="length3">${entity.bookState.description}</td>
								<td class="length3">${entity.checkState.description}</td>
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
	<script type="text/javascript" src="${ctx }/static/backend/js/jquery-1.10.2.js"></script>	
	<script type="text/javascript" src="${ctx }/static/backend/js/validator.js"></script>
	<script type='text/javascript' src='${ctx }/static/backend/js/zDialog/zDrag.js'></script>
	<script type='text/javascript' src='${ctx }/static/backend/js/zDialog/zDialog.js?{ctx:"${ctx}"}'></script>
	<script type="text/javascript" src="${ctx}/static/backend/js/laydate-v1.1/laydate/laydate.js"></script>
		
<script type="text/javascript">
$("tbody").find("tr:odd").css("backgroundColor", "#eff6fa");
	$(function() {
		var mess = $("#errormess").val();
		if (mess != null && $.trim(mess) != "") {
			Dialog.alert(mess);
		}
				//时间控件
	var start = {
				  elem: '#appointDate',
				  format: 'YYYY-MM-DD',
				  min: '1970-01-01 00:00:01', //设定最小日期为当前日期
				  max: '2020-12-31', //最大日期
				  istime: true,
				  istoday: false,
				  choose: function(datas){
				  }
				};
				laydate(start);
	
		$("#queryBtn").click(function() {
			//new 2015-12-02 设置当前页为1，避免第2页以后，通过输入条件查询造成的显示不出数据的问题
			$("#currentPage").attr("value", "1");
			document.forms[0].submit();
		});
		
	});
	
</script>
</html>
