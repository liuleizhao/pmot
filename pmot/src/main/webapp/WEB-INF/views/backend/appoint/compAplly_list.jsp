<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ page import="java.util.Date"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>${appName }-大客户预约申请列表</title>
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
</style>

<body>
	<c:set var="nowDate"> 
	 <%=new Date().getTime() %>
	</c:set>
	<div class="title">
		<h2>大客户预约申请列表</h2>
	</div>
	<div class="main">
		<div class="context">
			<input type="hidden" id="errormess" value='${errorMessage }' /> 
			<form  action="${ctx }/backend/appoint/compApplylist" method="get" name="form" id="form">
				<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" />
				<div class="query">
					<div class="query-conditions ue-clear">
						
						<div class="conditions staff ue-clear i_div">
							<label>企业名称：</label> 
							<input type="text" name="companyName" value="${companyName }">
						</div>
						<div class="conditions time ue-clear i_div" >
						<label class="criteria_title">预约日期：</label>
							<div class="time-select" >
								<input style="width:140px;" id="appointDate" type="text" name="appointDate" value="<fmt:formatDate value="${appointDate }" pattern="yyyy-MM-dd"/>" placeholder="预约日期" />
								<i class="icon"></i>
							</div>
						</div>
				
						<div class="conditions name ue-clear">
							<input id="queryBtn" type="submit" value="查询" class="input_button" /> 
							<input id="queryBtn" type="button" value="新增" onclick="javascript:window.location.href='${ctx }/backend/appoint/compApplyAdd'" class="input_button" /> 
						</div>
					</div>
				</div>
				<!-- 设置当前时间，用于下面比较，可以共用 -->
				<div class="table_box">
					<table id="liebiaocolor">
						<thead>
							<tr>
								<th class="length3">企业名称</th>
								<th class="length3">申请数量</th>
								<th class="length3">企业社会信用代码</th>
								<th class="length3">组织机构代码</th>
								<th class="length3">申请预约时间</th>
								<th class="length3">申请时间</th>
								<th class="length3">审批结果</th>
								<th class="length3">审批人</th>
								<th class="length3">审批时间</th>
								<th class="length3">已预约数量</th>
								<th class="length3">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pageView.list }" var="entity" varStatus="status">

								<td class="length3">${entity.companyName}</td>
								<td class="length3">${entity.applyCount}</td>
								<td class="length3">${entity.enterpriseCrediteCode}</td>
								<td class="length3">${entity.organizationCode}</td>
								<td class="length3"><fmt:formatDate value="${entity.startDate}" pattern="yyyy年MM月dd" />-<fmt:formatDate value="${entity.endDate}" pattern="yyyy年MM月dd"/></td>
								<td class="length3"><fmt:formatDate value="${entity.createDate}" pattern="yyyy-MM-dd hh:mm:ss" /></td>
								<td class="length3">${entity.approveRemart.description}</td>
								<td class="length3">${entity.approverName}</td>
								<td class="length3"><fmt:formatDate value="${entity.approveDate}" pattern="yyyy-MM-dd hh:mm:ss" /></td>
								<td class="length3">${entity.addedNum}</td>
								<td class="length3">
								<a class="mui-btn-blue" href="${ctx }/backend/appoint/viewDetail?compApplyId=${entity.id}">查看详情</a>&nbsp; &nbsp;  	
								<c:if test="${ entity.approveRemart == 'AGREE' }">
									<c:if test="${nowDate lt entity.endDate.time}">
										<c:if test="${empty entity.addedNum || entity.addedNum < entity.applyCount}">
											<a class="mui-btn-blue" href="${ctx }/backend/appoint/specialBookUI?compApplyFromId=${entity.id}">添加预约信息</a>&nbsp; &nbsp; 	
										</c:if>
										
									</c:if>
									
									<%-- <c:if test="${!empty entity.addedNum }">
										<a class="mui-btn-blue" href="${ctx }/backend/appoint/specialBookList?compApplyFromId=${entity.id}">查看车辆</a>&nbsp; &nbsp; 	
									</c:if> --%>
								</c:if>
									
								</td> 
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
		
	});
	
</script>
</html>
