<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${appName }-检测站列表</title>
<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
</head>
<body>
	<%@include file="../common/message.jsp" %>
	<div class="title">
		<h2>检测站列表</h2>
	</div>
	<div class="main">
		<div class="context">
			<form action="${ctx }/backend/system/station/appointCountUI" method="get" name="form" id="form">
					<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" /> 
					<input type="hidden" name="stationInfos" id="stationInfos" value=""/>
					<div class="query">
					<div class="query-conditions ue-clear">
						<div class="conditions staff ue-clear i_div">
							<label>检测站代码：</label> 
							<input name="code" value="${station.code }" type="text" />
						</div>
						<div class="conditions staff ue-clear i_div">
							<label>检测站名称：</label> 
							<input name="name" value="${station.name }" type="text" />
						</div>
						<div class="conditions name ue-clear no_border">
							<input id="queryBtn" type="submit" value="查询" class="input_button" /> 
						</div>
						<div class="conditions name ue-clear no_border">
							<input id="updateBtn" type="button" value="更新" class="input_button" /> 
						</div>
					</div>
				</div>
			</form>
				<div class="table_box">
					<table>
						<thead>
							<tr>
								<th class="length3"><input type="checkbox" id="all" onchange="swapCheck();"/><label>全选</label></th>
								<th class="length3">检测站代码</th>
								<th class="length3">检测站名称</th>
								<th class="length3">联系人电话</th> 
								<th class="length3">固定电话</th>
								<th class="length4">检测站地址</th>
								<th class="length3">最大预约量</th>
								<th class="length3">状态</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pageView.list }" var="entity" varStatus="status">
								<c:choose>
									<c:when test="${status.index %2 == 1 }">
										<tr class="evencolor" id="${entity.id }">
									</c:when>
									<c:otherwise>
										<tr class="oddcolor" id="${entity.id }">
									</c:otherwise>
								</c:choose>
									<td class="length3">
										<input style="width: 30px;" height="60px;" type="checkbox" name="stationIds" value="${entity.id}"/>
									</td>
									<td class="length3">${entity.code }</td>
									<td class="length3">${entity.name }<tdh>
									<td class="length3">${entity.mobile }</td>
									<td class="length3">${entity.phone }</td>
									<td class="length4">${entity.address }</td>
									<td class="length3">
										<input type="text" value="${entity.maxNumber }" name="maxNumbers" style="height: 18px;"/>
									</td>
									<td class="length3"">${entity.stationState.description }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			<%@ include file="/WEB-INF/views/backend/common/pagination.jsp"%>
		</div>
	</div>
</body>
	<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
	<script type="text/javascript">
	$("tbody").find("tr:odd").css("backgroundColor", "#eff6fa");
	var isCheckAll = false;  
       function swapCheck() {  
           if (isCheckAll) {  
               $("input[type='checkbox'][name='stationIds']").each(function() {  
                   this.checked = false;  
               });  
               isCheckAll = false;  
           } else {  
               $("input[type='checkbox'][name='stationIds']").each(function() {  
                   this.checked = true;  
               });  
               isCheckAll = true;  
           }  
       }  
      $("#updateBtn").click(function() {
				try {
					
				    var stationLength = $("input[type='checkbox'][name='stationIds']:checked").length;
					if(stationLength <= 0){
						throw ("请选择需要设置的监测站！");
					}
					var stations = $("input[type='checkbox'][name='stationIds']");
					var maxNumbers = $("input[name='maxNumbers']");
					var newArray = [];
					// 循环判断
					for(var i = 0;i<stations.length;i++){
						if(stations.eq(i).is(":checked")){
							var o = new Object();
							o.id = stations.eq(i).val();
 							o.maxNumber = maxNumbers.eq(i).val();
							newArray.push(o);
						}
					}
					var json = JSON.stringify(newArray);
					//alert(1111+"json:" + json);
					$("#stationInfos").val(json);
					$("#form").attr("action", "${ctx}/backend/system/station/editAppointCount");
					$("#form").attr("method", "post");
					//alert(json);
					//提交表单
					$("#form").submit();
				} catch (e) {
					openErrorDialog(e,true,false,{time:3,title:"3秒后自动关闭"});
					return false;
				}
			});
	$(function() {
		$("#queryBtn").click(function() {
			//new 2015-12-02 设置当前页为1，避免第2页以后，通过输入条件查询造成的显示不出数据的问题
			$("#currentPage").attr("value", "1");
			document.forms[0].submit();
		});
	});
</script>
</html>