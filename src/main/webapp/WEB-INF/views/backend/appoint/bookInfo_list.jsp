<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ page import="java.util.Date"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>${appName }-预约列表</title>
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

	<div class="title">
		<h2>预约列表</h2>
	</div>
	<div class="main">
		<div class="context">
			<input type="hidden" id="errormess" value='${errorMessage }' /> 
			<form  action="${ctx }/backend/appoint/bookInfo/list" method="get" name="form" id="form">
				<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" />
				<div class="query">
					<div class="query-conditions ue-clear">
						<c:if test="${backend_user_session.userType != 'STATION'}">
							<div class="conditions staff ue-clear i_div">
								<label>检测站名称：</label> 
								<input type="text" name="stationName" value="${bookInfo.stationName }">
							</div>
						</c:if>
						<div class="conditions staff ue-clear i_div">
							<label>预约号：</label> 
							<input type="text" name="bookNumber" value="${bookInfo.bookNumber }">
						</div>
						<div class="conditions staff ue-clear i_div">
							<label>证件号：</label> 
							<input type="text" name="idNumber" value="${bookInfo.idNumber }">
						</div>
						<div class="conditions staff ue-clear i_div">
							<label>号牌号码：</label> 
							<input type="text" name="platNumber" value="${bookInfo.platNumber }">
						</div>
						<div class="conditions staff ue-clear i_div">
							<label>车架号：</label> 
							<input type="text" name="frameNumber" value="${bookInfo.frameNumber }">
						</div>
						<div class="conditions staff ue-clear i_div">
							<label>预约状态：</label> 
							<select name="bookState" id="bookState" class="q_select">
								<option value="">所有</option>
								<c:forEach var="bookState" items="${bookStateList}"> 
                                  		<option value="${bookState.value }" 
                                  		<c:if test="${bookInfo.bookState eq bookState.value}">selected="selected"</c:if>>${bookState.description }</option>
                                 </c:forEach>
							</select>
						</div>
						
						<div class="conditions staff ue-clear i_div">
							<label>预约渠道：</label> 
							<select name="bookChannel" id="bookChannel" class="q_select">
								<option value="">所有</option>
								<c:forEach var="channel" items="${channelList}"> 
                                  		<option value="${channel.value }" 
                                  		<c:if test="${bookInfo.bookChannel eq channel.value}">selected="selected"</c:if>>${channel.description }</option>
                                 </c:forEach>
							</select>
						</div>
						
						
						<div class="conditions time ue-clear i_div" style="width: 370px;">
								<label class="criteria_title">预约起止日期：</label>
								<div class="time-select">
									<input id="startDate" type="text" name="startDate" value="${startDate }" placeholder="开始时间" />
									<i class="icon"></i>
								</div>
								<span class="line">-</span>
								<div class="time-select">
									<input id="endDate" type="text" name="endDate" value="${endDate }" placeholder="结束时间" />
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
				 	<fmt:formatDate value="<%=new Date()%>" pattern="yyyy-MM-dd" type="date"/>  
				</c:set>
				<div class="table_box">
					<table id="liebiaocolor">
						<thead>
							<tr>
							    <th class="length3">预约号</th>
								<th class="length3">预约人</th>
								<th class="length3">手机号码</th>
								<c:if test="${backend_user_session.userType != 'STATION'}">
									<th class="length3">检测站</th>
								</c:if>
								<th class="length3">证件类型</th>
								<th class="length3">证件号</th>
								<th class="length3">号牌种类</th>
								<th class="length3">号牌号码</th>
								<th class="length3">车架号</th>
								<th class="length3">预约日期</th>
								<th class="length3">预约时间</th>
								<th class="length3">预约状态</th>
								<th class="length3">检测状态</th>
								<th class="length3">预约渠道</th>
								<th class="length3">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pageView.list }" var="entity" varStatus="status">
								<th class="length3">${entity.bookNumber}</th>
								<td class="length3">${entity.bookerName}</td>
								<td class="length3">${entity.mobile}</td>
								<c:if test="${backend_user_session.userType != 'STATION'}">
									<td class="length3">${entity.stationName}</td>
								</c:if>
								<td class="length3">${entity.idTypeName}</td>
								<td class="length3">${entity.idNumber}</td>
								<td class="length3">${entity.carTypeName}</td>
								<td class="length3">${entity.platNumber}</td>
								<td class="length3">${entity.frameNumber}</td>
								<td class="length3">${entity.bookDate}</td>
								<td class="length3">${entity.bookTime}</td>
								<td class="length3">${entity.bookState.description}</td>
								<td class="length3">${entity.checkState.description}</td>
								<td class="length3">${entity.bookChannel.description}</td>
								
								<td class="length3">
									<a class="mui-btn-blue" href="${ctx }/backend/appoint/bookInfo/view?id=${entity.id}">查看详情</a>&nbsp; &nbsp;
								 	<!-- 作废 -->
								 	<c:if test="${backend_user_session.userType == 'STATION'}">
									 	<c:if test="${entity.bookState.index eq 1 && entity.bookDate == nowDate }"><!--  -->
												<input type="button" value="作 废" class="anni01" style="width:60px;height:25px" 
												onClick="javascript:returnBookNumber('${entity.bookNumber}');"/>&nbsp; &nbsp;
										</c:if>
										<c:if test="${entity.bookState.index eq 1 || entity.bookState.index eq 2 }">
											 <a class="mui-btn-blue" href="${ctx }/backend/appoint/bookInfo/editUI?id=${entity.id}">修改车辆信息</a>
										</c:if>
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
		//时间空间
		var start = {
		  elem: '#startDate',
		  format: 'YYYY-MM-DD',
		  min: '1970-01-01 00:00:01', //设定最小日期为当前日期
		  max: '2020-12-31', //最大日期
		  istime: true,
		  istoday: false,
		  choose: function(datas){
		     end.min = datas; //开始日选好后，重置结束日的最小日期
		     end.start = datas; //将结束日的初始值设定为开始日
		  }
		};
		var end = {
		  elem: '#endDate',
		  format: 'YYYY-MM-DD',
		  min: '1970-01-01 00:00:01',
		  max: '2020-12-31',
		  istime: true,
		  istoday: false,
		  choose: function(datas){
		    start.max = datas; //结束日选好后，重置开始日的最大日期
		  }
		};
		laydate(start);
		laydate(end);
	
		$("#queryBtn").click(function() {
			//new 2015-12-02 设置当前页为1，避免第2页以后，通过输入条件查询造成的显示不出数据的问题
			$("#currentPage").attr("value", "1");
			document.forms[0].submit();
		});
		
	});
	
 	function returnBookNumber(bookNumber)
	{
		var diag = new Dialog();
		diag.Title = "填写预约验证码",
		diag.Width = 400;
		diag.Height = 100;
		diag.Drag=false;
		diag.URL = "${ctx }/backend/appoint/bookInfo/returnNumUI";
		diag.OKEvent = function(){
			var verifyCode = diag.innerFrame.contentWindow.document.getElementById('verifyCode').value;
			try{
				if(""==verifyCode){
					throw ("请输入预约验证码");
				}
			}catch (e) {
				Dialog.alert(e, null, 200, 50);
				return false;
			}
			window.location.href = "${ctx }/backend/appoint/bookInfo/returnNum?verifyCode="+verifyCode+"&bookNumber="+bookNumber;
			diag.close();
		};
		diag.show();
	}
</script>
</html>
