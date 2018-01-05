<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${appName }-预约详情</title>
	<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/mui.min.css" />
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-reg.css" />
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
	<link rel="stylesheet" href="${ctx}/static/backend/css/backend_common.css" />
</head>
<style>
.testresult span {
	padding: 0px 10px 0px 0px;
}
</style>

<body>
	<div class="title">
		<h2>预约详情</h2>
	</div>
	<form class="classA mui-input-group" method="post" name="form" id="form">
		<table cellpadding="8" cellspacing="1">
			<tr>
				<td class="field"><span class="txt-imp">*</span>预约号码</td>
					<td class="text">
						<div class="mui-input-row">
							<input name="account" id="account" type="text" value="${bookInfo.bookNumber }" readonly="readonly"/>
						</div>
				</td>
				<c:if test="${ backend_user_session.userType != 'STATION' || (fn:startsWith(bookInfo.bookNumber,'G') || fn:startsWith(bookInfo.bookNumber,'D'))}">
					<td class="field"><span class="txt-imp">*</span>预约验证码</td>
					<td class="text select_td" >
						<div class="mui-input-row">
						<input type="text" maxlength="30" value="${bookInfo.verifyCode  }" readonly="readonly"/>
						</div>
					</td>
				</c:if>
			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>预约人</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="name" id="name" type="text" value="${bookInfo.bookerName }" readonly="readonly"/>
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>手机号</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="mobile" id="mobile" type="text" value="${bookInfo.mobile }" readonly="readonly"/> 
					</div>
				</td>
			</tr> 
			
			
			 <tr>
				<td class="field"><span class="txt-imp">*</span>证件类型</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" maxlength="30" value="${bookInfo.idTypeName }" readonly="readonly"/>
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>证件号</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" value="${bookInfo.idNumber}" readonly="readonly"/> 
					</div>
				</td>
			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>预约状态</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" maxlength="30" value="${bookInfo.bookState.description }" readonly="readonly">
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>预约渠道</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" maxlength="30" value="${bookInfo.bookChannel.description }" readonly="readonly">
					</div>
				</td>
			</tr>
			
			<tr>
				<td class="field"><span class="txt-imp">*</span>号牌种类</td>
				<td class="text">
					<div class="mui-input-row">
						 <input type="text" maxlength="30" value="${bookInfo.carTypeName }" readonly="readonly">
					</div>
				</td>
				<td class="field">号牌号码</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" maxlength="30" value="${bookInfo.platNumber }" readonly="readonly">
					</div>
				</td>
			</tr>
			<tr>
				<td class="field">车架号</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" value="${bookInfo.frameNumber}" maxlength="50" readonly="readonly"> 
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>车辆性质</td>
				<td class="text select_td">
					<div class="mui-input-row">
						 <input type="text" maxlength="30" value="${bookInfo.vehicleCharacter.description }" readonly="readonly"/>
					</div>
				</td>
			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>预约时间</td>
				<td class="text select_td" >
					<div class="mui-input-row">
					 <input type="text" maxlength="30" value="${bookInfo.bookDate  } ${bookInfo.bookTime}" readonly="readonly"/> 
					</div>
				</td>
				<td class="field">预约检测站</td>
				<td class="text select_td" >
					<div class="mui-input-row">
						 <input type="text" value="${bookInfo.stationName}" maxlength="50" readonly="readonly"/> 
					</div>
				</td>
			</tr>  
			<tr>
				<td class="field"><span class="txt-imp">*</span>检测状态</td>
				<td class="text select_td" >
					<div class="mui-input-row">
					<input type="text" maxlength="30" value="${bookInfo.checkState.description  }" readonly="readonly"/>
					</div>
				</td>
				<td class="field">检测时间</td>
				<td class="text select_td" >
					<div class="mui-input-row">
						 <input type="text" value="<fmt:formatDate value="${bookInfo.checkTime}" pattern="yyyy-MM-dd HH:mm:ss" />" readonly="readonly"/> 
					</div>
				</td>
			</tr>  
			<tr>
				<td class="field"><span class="txt-imp">*</span>实际检测站名称</td>
				<td class="text select_td" >
					<div class="mui-input-row">
					<input type="text" maxlength="30" value="${bookInfo.checkStation  }" readonly="readonly"/>
					</div>
				</td>
				<td class="field">检测序列号</td>
				<td class="text select_td" >
					<div class="mui-input-row">
						 <input type="text" value="${bookInfo.checkSerialNumber}" maxlength="50" readonly="readonly"/>
					</div>
				</td>
			</tr> 
		</table>
		<div class="mui-button-row">
			<button type="button" class="mui-btn" style="background: #E6F4FF;"  onClick="javascript:history.back(-1);">返回</button>
		</div>
	</form>
</body>
<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
</html>