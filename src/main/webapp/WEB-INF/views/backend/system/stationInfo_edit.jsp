<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${appName }--编辑检测站</title>
<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/mui.min.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-reg.css" />、
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
<link href="${ctx }/static/backend/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/static/backend/css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/backend/css/backend_common.css" />
</head>
<body>
	<%@include file="../common/message.jsp" %>
	<div class="title">
		<h2>编辑检测站基本信息</h2>
	</div>
	<form class="classA mui-input-group" action="${ctx }/backend/system/station/edit" method="post" name="form" id="form">
		<input type="hidden" name="id" value="${stationInfoVO.id }" id="stationId"/>
		<table cellpadding="8" cellspacing="1">
			<tr>
				<td class="field"><span class="txt-imp">*</span>检测站代码</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="hidden" id="preCode" value="${stationInfoVO.code }"> 
						<input name="code" id="code" readonly="readonly" type="text" value="${stationInfoVO.code }"> 
						<span style="color:red;marign-left:20px;display:none;">检测站代码已存在</span>
					</div>
				</td>
			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>检测站名称</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="hidden" id="preName" value="${stationInfoVO.name }">
						<input type="text" name="name" id="name" value="${stationInfoVO.name }"> 
						<span style="color:red;marign-left:20px;display:none;">检测站名称已存在</span>
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>检测站简称</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" name="viewName" id="viewName" value="${stationInfoVO.viewName }"/> 
					</div>
				</td>
			</tr>
			
			<tr>
				<td class="field"><span class="txt-imp">*</span>联系地址</td>
				<td class="text" colspan="3">
					<div class="mui-input-row">
						<input type="text" name="address" id="address" value="${stationInfoVO.address }"/>
					</div>
				</td>
			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>手机号码</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" name="mobile" id="mobile" value="${stationInfoVO.mobile }" maxlength="11"/>
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>固定电话</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="phone" id="phone" type="text" value="${stationInfoVO.phone }" >
					</div>
				</td>
			</tr>
			<tr>
				<td class="field">邮箱</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" name="email" id="email" value="${stationInfoVO.email }"/>
					</div>
				</td>
				<td class="field">绿标</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="greenMark" id="greenMark" type="text"  value="${stationInfoVO.greenMark }"/>
					</div>
				</td>
			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>地理位置X</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" name="pointX" id="pointX" value="${stationInfoVO.pointX }"/>
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>地理位置Y</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" name="pointY" id="pointY"  value="${stationInfoVO.pointY }"/>
					</div>
				</td>
			</tr>
			
			<tr>
				<td class="field"><span class="txt-imp">*</span>所在区</td>
				<td class="text select_td">
					<div class="mui-input-row">
						<select name="districtId" id="districtId" class="q_select">
							<option value="">所有</option>
							<c:forEach var="district" items="${districtList}">
                            	<option value="${district.id }" <c:if test="${stationInfoVO.districtId eq district.id}">selected="selected"</c:if>>${district.name }</option>
                           	</c:forEach>
						</select>
						<div class="triangle-down"></div>
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>排列顺序</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="orderNum" id="orderNum" type="text" value="${stationInfoVO.orderNum }"/>
					</div>
				</td> 
			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>接口序列号 </td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" name="serialNumber" id="serialNumber" value="${stationInfoVO.serialNumber }" style="width: 90%"/>
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>检测站状态</td>
				<td class="text select_td">
					<div class="mui-input-row">
						<select name="stationState" id="stationState">
							<c:forEach items="${statusList }" var="entity">
								<option value="${entity.value }" <c:if test="${stationInfoVO.stationState == entity.value }">selected="selected"</c:if>>${entity.description}</option>
							</c:forEach>
						</select>
						<div class="triangle-down"></div>
					</div>
				</td>
			</tr>
			<tr>
				<td class="field">可办理车辆性质</td>
				<td class="text" colspan="3">
					<c:forEach items="${vehicleCharacterList}" var="vehicleCharacter" varStatus="statu">
						<input style="width: 30px;" height="40px" type="checkbox" name="vehicleCharacterIds" value="${vehicleCharacter.id}"
						<c:if test="${fn:contains(stationInfoVO.vehicleCharacters,vehicleCharacter.index)}">checked="checked"</c:if>/>
						<label>${vehicleCharacter.description}</label>
						<c:if test="${statu.count%8==0}">
							<br>
						</c:if>
					</c:forEach>
				</td>
			</tr>
		</table>
		<div class="mui-button-row">
			<button type="button" id="editBtn" class="mui-btn mui-btn-green">编辑</button>&nbsp;&nbsp;
			<button type="button" class="mui-btn" style="background: #E6F4FF;"  onClick="javascript:history.back(-1);">返回</button>
		</div>
		
	</form>
</body>
<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
<script>
	$(function() {
		$("#editBtn").click(function() {
				try {
					$(this).validate.isNull($('#code').val(),'检测站代码不能为空');
					$(this).validate.isNull($('#name').val(),'检测站名称为空');
					$(this).validate.isNull($('#address').val(),'联系地址不能为空');
					$(this).validate.isNull($('#mobile').val(),'电话号码不能为空');
					var mobileReg = /^\d{11}$/;
					if(!mobileReg.test($('#mobile').val())){
						throw ("电话号码名格式不正确！");
					}
					$(this).validate.isNull($('#phone').val(), '固定号码不能为空');
					$(this).validate.isContact($('#phone').val(), '固定号码格式不正确'); // 联系方式
					
					var email = $.trim($('#email').val());
					if(email){
						$(this).validate.isMail(email, '邮箱格式不正确');
					}
					$(this).validate.isNull($('#pointX').val(), '地理理位置X不能为空');
					$(this).validate.isNull($('#pointY').val(), '地理理位置Y不能为空');
					$(this).validate.isNull($('#districtId').val(), '所在区不能为空');
					$(this).validate.isNull($('#orderNum').val(), '排列顺序不能为空');
					$(this).validate.isNull($('#serialNumber').val(),'接口序列号不能为空');
					//提交表单
					if($("#code").next().is(":hidden") && $("#name").next().is(":hidden")){
						$(this).validate.submin_form();
					}
				} catch (e) {
					 openErrorDialog(e,true,false,{time:3,title:"3秒后自动关闭"});
				}
		}); 
		
		$("#code").focus(function(){
			$("#code").next().hide();
		});
		$("#code").bind("blur", function(e) {
			var preCode = $("#preCode").val();
			var a = $(this).val();
			if(!(a=$.trim(a)) || a==preCode){
				return false;
			}
			$.ajax({
				url : "${ctx}/backend/system/station/checkCodeExisted",
				data : {
					"code" : encodeURI(encodeURI(a))
				},
				success : function(data) {
					if(data){
						$("#code").next().show();
					}
				}
			});
		});
		
		$("#name").focus(function(){
			$("#name").next().hide();
		});
		$("#name").bind("blur", function(e) {
			var preCode = $("#preName").val();
			var a = $(this).val();
			if(!(a=$.trim(a)) || a==preCode){
				return false;
			}
			$.ajax({
				url : "${ctx}/backend/system/station/checkNameExisted",
				data : {
					"name" : encodeURI(encodeURI(a))
				},
				success : function(data) {
					if(data){
						$("#name").next().show();
					}
				}
			});
		});
	});
</script>
</html>
