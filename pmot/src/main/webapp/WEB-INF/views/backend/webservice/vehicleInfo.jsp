<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>接口账户列表</title>
<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
</head>
<body>
	<%@include file="../common/message.jsp" %>
	<div class="title">
		<h2>监管接口测试</h2>
	</div>
	<div class="main">
		<div class="context">
			<form  action="${ctx }/backend/webservice/test/getVehicleInfo" method="post" name="form" id="form">
				<div class="query">
					<div class="query-conditions ue-clear">
						<div class="conditions staff ue-clear i_div">
							<label>号牌号码：</label>
							<input id="platNumber" name="platNumber"  type="text" value="${platNumber}"/>
						</div>
						<div class="conditions staff ue-clear i_div">
							<label>号牌种类：</label>
							<input id="carTypeCode" name="carTypeCode"  type="text" value="${carTypeCode}"/>
						</div>
						<div class="conditions staff ue-clear i_div">
							<label>车架号后4位：</label>
							<input id="frameNum" name="frameNum"  type="text" value="${frameNum}"/>
						</div>
						<div class="conditions staff ue-clear i_div">
							<label>检测站编号：</label>
							<input id="stationCode" name="stationCode"  type="text" value="${stationCode}"/>
						</div>
						<div class="conditions name ue-clear">
							<input id="queryBtn" type="submit" value="查询" class="input_button" /> 
						</div>
					</div>
				</div>
			</form>

			<div id="response" class="table_box" style="border:1px solid red;height:400px;">
				请求时间:${requestDate}
				请求XML:${requestXML}
				响应XML:${responseXML}
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
<script type="text/javascript">
	/* $("#queryBtn").click(function(){
		var platNumber = $("#platNumber").val();
		var carTypeCode = $("#carTypeCode").val(); frameNum = $("#frameNum").val();
		$.post("${ctx}/backend/webservice/test/getVehicleInfo"
			,{
				"platNumber":platNumber,
				"carTypeCode":carTypeCode,
				"frameNum":frameNum
			},
			function(data){
				if(!data){
					data = "服务器端没有数据返回";
				}
				$("#response").html(data);
		});
	}); */
	
</script>
</html>
