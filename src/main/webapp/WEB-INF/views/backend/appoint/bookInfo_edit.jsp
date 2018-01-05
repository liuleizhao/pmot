<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>${appName }-修改预约信息</title>
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
		<h2>修改预约信息</h2>
	</div>
	<form class="classA mui-input-group" action="${ctx }/backend/appoint/bookInfo/edit" method="post" name="form" id="form">
		<input name="id" value="${ bookInfo.id}" type="hidden">
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
						<input type="checkbox" name="newflag" id="newflag" style="position: relative;" />
						<span style="margin-left: 5px;">是否新车</span>
					</div>
				</td>
			</tr>
			<tr>
				<td class="field">车架号</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" id="frameNumber" name="frameNumber" value="${bookInfo.frameNumber}" maxlength="50"> 
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
		</table>
		<div class="mui-button-row">
			<button type="button" id="editBtn" class="mui-btn mui-btn-green">保存</button>&nbsp;&nbsp;
			<button type="button" class="mui-btn" style="background: #E6F4FF;"  onClick="javascript:history.back(-1);">返回</button>
		</div>
	</form>
</body>
	<script type="text/javascript" src="${ctx }/static/backend/js/jquery-1.10.2.js"></script>	
	<script type="text/javascript" src="${ctx }/static/backend/js/validator.js"></script>
	<script type='text/javascript' src='${ctx }/static/backend/js/zDialog/zDrag.js'></script>
	<script type='text/javascript' src='${ctx }/static/backend/js/zDialog/zDialog.js?{ctx:"${ctx}"}'></script>
<script>
    // 转大写
function toUpperCase(obj) {
	obj.value = $.trim(obj.value.toUpperCase());
};
 
	jQuery(document).ready(function() {	
    		var mess = $("#mess").val();
			if (mess != null && $.trim(mess) != "") {
				Dialog.alert(mess);
			}
			
			// 新车标记
			var newFlag = '${bookInfo.newflag}';
			if(newFlag ==1){
				$("#newflag").val(1);
				$("#newflag").attr("checked","true");
			}else
			{
				$("#newflag").val(0);
			}
			$("#newflag").attr("disabled","disabled");
			
		$("#editBtn").click(function() {
			try {
				$(this).validate.isNull($("#frameNumber").val(), '请输入车架号');
				var frameNumberLength = $("#frameNumber").val().length;
				var newflag = $("input[id='newflag']").is(':checked');
				if(newflag){
					if(frameNumberLength <= 4){
					 	throw ("请输入完整的车架号！");
					}if(frameNumberLength > 24){
						throw ("车架号不能超过24位！");
					}
				}else
				{
					if(frameNumberLength != 4){
					 	throw ("旧车请请输入车架号后4位！");
					}
				}
				$(this).validate.submin_form();
			} catch (e) {
				Dialog.alert(e);
			} 
		}); 
	});
</script>
</html>