<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>失约配置</title>
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/mui.min.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-reg.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
<link href="${ctx }/static/backend/css/password_strength_style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/backend/css/backend_common.css" />
<style type="text/css">
	.short-triangle-down {
	    width: 0;
	    height: 0;
	    border-left: 5px solid transparent;
	    border-right: 5px solid transparent;
	    border-top: 10px solid #666;
	    position: absolute;
	    top: 12px;
	    right: 88%;
	    pointer-events: none;
	
	}
	#platNumber1{
		width:52px;
		padding-left: 17px;
		font-size: 13px;
		top: -4px;
		position: relative;
	}
</style>

</head>

<body>
	<div class="title">
		<h2>失约配置</h2>
	</div>
	<input type="hidden" id="mess" value="${message }" />
		
	<form class="classA mui-input-group" action="${ctx }/backend/blackListConfig/edit" method="post" name="form" id="form">
		<input type="hidden" value="${blackListConfig.id }" name="id"/>
		<table cellpadding="8" cellspacing="1">
			<tr>
				<td class="field"><span class="txt-imp">*</span>记录类型</td>
				<td class="text select_td">
					<div class="mui-input-row">
						<select name="recordType" id="recordType">
							<c:forEach items="${ recordTypes}" var="recordType">
								<option value="${recordType }" <c:if test="${recordType == blackListConfig.recordType}">selected="selected"</c:if>>${recordType.description}</option>
							</c:forEach>
						</select>
						<div class="triangle-down"></div>
					</div>
				</td>
				<td class="field no_trailer" ><span class="txt-imp">*</span>操作类型：</td>
				<td class="text select_td no_trailer">
					<div class="mui-input-row">
						<select name="operationType" id="operationType">
							<c:forEach items="${ bookOperations}" var="operationType">
								<option value="${operationType }" <c:if test="${operationType == blackListConfig.operationType}">selected="selected"</c:if>>${operationType.description}</option>
							</c:forEach>
						</select>
						<div class="triangle-down"></div>
					</div>
				</td>
			</tr>
			
			<tr>
				<td class="field">有效天数</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="effectDays" id="effectDays" value="${blackListConfig.effectDays }" type="text"> 
					</div>
				</td>
			
				<td class="field"><span class="txt-imp">*</span>限制次数</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="limitCount" id="limitCount"   value="${blackListConfig.limitCount }" type="text"> 
					</div>
				</td>
			</tr>
		</table>
 
		<div class="mui-button-row">
			<button type="submit" id="addBtn" class="mui-btn mui-btn-green">保存</button>&nbsp;&nbsp;
			<button type="button" class="mui-btn" style="background: #E6F4FF;"  onClick="javascript:history.back(-1);">返回</button>
		</div>
		
	</form>
</body>
<script type="text/javascript" src="${ctx }/static/backend/js/jquery-1.10.2.js"></script>	
<script type="text/javascript" src="${ctx }/static/backend/js/validator.js"></script>
<script type='text/javascript' src='${ctx }/static/backend/js/zDialog/zDrag.js'></script>
<script type='text/javascript' src='${ctx }/static/backend/js/zDialog/zDialog.js?{ctx:"${ctx}"}'></script>
<script>
$(function(){
	var mess = $.trim($("#mess").val());
	if( mess != ""){
			Dialog.alert(mess);
	}
});
$("#addBtn").click(function(){
		try {
				$(this).validate.isNull($('#limitCount').val(),'限制次数为必填');
				var reg = /^[\d]{1,}$/;
				if(!reg.test($("#limitCount").val())){
					throw ("限制次数请输入数字");
				} 
				//提交表单
				$(this).validate.submin_form();
			} catch (e) {
					Dialog.alert(e);
			}
});
</script>
</html>
