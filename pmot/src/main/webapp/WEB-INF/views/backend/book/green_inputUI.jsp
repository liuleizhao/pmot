<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${appName }-绿色通道预约</title>
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
		<h2>检测站绿色通道预约-填写信息</h2>
	</div>
	<input type="hidden" id="mess" value="${errorMessage }" />
	<input id="vehicleTypeValue" type="hidden" value="${bookInfo.vehicleType }" />
	<input value="${bookInfo.idTypeId }" type="hidden" id="idTypeIdValue" />
	<input value="${bookInfo.carTypeId }" type="hidden" id="carTypeIdValue" />
	<input value="${bookInfo.vehicleCharacter }" type="hidden" id="vehicleCharacterValue" />
	<input value="${fn:substring(bookInfo.platNumber,0,1)}" type="hidden" id="platNumber1Value" />
	
	<form class="classA mui-input-group" action="${ctx }/backend/book/greenBook" method="post" name="form" id="form">
	<input type="hidden" name="platNumber" id="platNumber"/>
	<input type="hidden" name="bookChannel" value="STATION" /> 	
	<input >
		<table cellpadding="8" cellspacing="1">
			<tr>
				<td class="field"><span class="txt-imp">*</span>姓名</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="bookerName" id="jq_name" type="text" value="${bookInfo.bookerName }" /> 
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>证件名称</td>
				<td class="text select_td">
					<div class="mui-input-row">
							<input value="${bookInfo.idTypeId }" type="hidden" id="idTypeIdValue">
							<select id="jq_id_type_list" name="idTypeId"  value="${bookInfo.idTypeId }">
								<option value="e4e48584399473d20139947f125e2b2c">居民身份证</option>
								<option value="40288282463ceca50146462942d3055c">组织机构代码证书</option>
								<option value="4028823f51d79d4d0151f1ebb1dc361e">统一社会信用代码</option>
								<option value="e4e48584399b293601399b60996b55e6">境外人员身份证明</option>
							</select>
							<div class="triangle-down"></div>
					</div>
				</td>
			</tr>

			<tr>
				<td class="field"><span class="txt-imp">*</span>证件号</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" name="idNumber" id="idNumber" value="${bookInfo.idNumber }"  maxlength="30" onkeyup="toUpperCase(this)"/>
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>手机号码</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="mobile" id="mobile" type="text"  value="${bookInfo.mobile }"  maxlength="11"/>
					</div>
				</td>
			</tr>
			
			<tr>
				<td class="field"><span class="txt-imp">*</span>号牌号码</td>
				<td class="text">
					<div class="mui-input-row">
						<select id="platNumber1">
							<option value="粤">粤</option>
							<option value="鄂">鄂</option>
                         	<option value="豫">豫</option>
                         	<option value="皖">皖</option>
                         	<option value="赣">赣</option>
                         	<option value="冀">冀</option>
                         	<option value="鲁">鲁</option>
                         	<option value="浙">浙</option>
                         	<option value="苏">苏</option>
                         	<option value="湘">湘</option>
                         	<option value="闽">闽</option>
                         	<option value="蒙">蒙</option>
                         	<option value="京">京</option>
                         	<option value="辽">辽</option>
                         	<option value="渝">渝</option>
                         	<option value="沪">沪</option>
                         	<option value="陕">陕</option>
                         	<option value="川">川</option>
                         	<option value="黑">黑</option>
                         	<option value="晋">晋</option>
                         	<option value="桂">桂</option>
                         	<option value="吉">吉</option>
                         	<option value="宁">宁</option>
                         	<option value="贵">贵</option>
                         	<option value="琼">琼</option>
                         	<option value="甘">甘</option>
                         	<option value="青">青</option>
                         	<option value="津">津</option>
                         	<option value="云">云</option>
                         	<option value="藏">藏</option>
                         	<option value="新">新</option>
						</select>
						<div class="short-triangle-down"></div>
						<input type="text" name="platNumber2" id="platNumber2" value="<c:if test="${empty bookInfo.platNumber }">B</c:if>${fn:substring(bookInfo.platNumber,1,fn:length(bookInfo.platNumber))}" maxlength="7" onkeyup="toUpperCase(this)"/>
						<input type="checkbox" name="newflag" id="newflag" value="1"  style="position: relative;" />
						<span style="margin-left: 5px;">是否新车</span>
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span><span id="spanFrameNumber">车架号后4位</span></td>
				<td class="text">
					<div class="mui-input-row">
						<input name="frameNumber" id="frameNumber" type="text"  value="${bookInfo.frameNumber }"  maxlength="24" onkeyup="toUpperCase(this)"/>
					</div>
				</td>
			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>号牌种类</td>
				<td class="text select_td">
					<div class="mui-input-row">
						<select  id="carTypeId" name="carTypeId" class="q_select">
							<option value="">请选择</option>
							<option value="312AED23657445C194540C794DBDBDB9">小型汽车（蓝底白字）</option>
							<option value="5AA667F13C2143F0A41C6940E74B127E">大型汽车（黄底黑字）</option>
							<option value="B4394B3F2F3B4E78911713C3D54D4196">领馆汽车（黑底白字、红领字）</option>
							<option value="A4FA9722C81C408B8A5BB65F8BD9C9B1">外籍汽车（黑底白字）</option>
							<option value="0D7E3ABB86774FD1927EE05CF82FDA4B">低速车（黄底黑字黑框线）</option>
							<option value="0AAA03BC4AE74531BF1FE45A03C38577">挂车</option>
							<option value="D7FAFC5A68004845864C42345B58D7BC">教练汽车（黄底黑字黑框线）</option>
							<option value="31CCBA351E0A4B7AA1BAFBDE2AA93161">警用汽车</option>
							<option value="0EBEC3DB9EAA40A7B97DDD547FF58F51">大型新能源汽车（黄绿双拼色底黑字）</option>
							<option value="763FF1EEE4BB4C3995B402E8A7D2C550">小型新能源汽车（渐变绿底黑字）</option>
						</select>
						
						<div class="triangle-down"></div>
					</div>
				</td>
				<td colspan="2"></td>
			</tr>
		</table>
 
		<div class="mui-button-row">
			<button type="button" id="addBtn" class="mui-btn mui-btn-green">保存</button>&nbsp;&nbsp;
			<button type="button" class="mui-btn" style="background: #E6F4FF;"  onClick="javascript:history.back(-1);">返回</button>
		</div>
		
	</form>
</body>
	<script type='text/javascript' src='${ctx}/static/front/js/pages/inputJson.js'></script>
	<script type="text/javascript" src="${ctx }/static/backend/js/jquery-1.10.2.js"></script>	
	<script type="text/javascript" src="${ctx }/static/backend/js/validator.js"></script>
	<script type='text/javascript' src='${ctx }/static/backend/js/zDialog/zDrag.js'></script>
	<script type='text/javascript' src='${ctx }/static/backend/js/zDialog/zDialog.js?{ctx:"${ctx}"}'></script>
<script>
    // 转大写
function toUpperCase(obj) {
	obj.value = $.trim(obj.value.toUpperCase());
};

$(function(){

		//回显信息
		var idTypeIdValue = $("#idTypeIdValue").val();
		$("#jq_id_type_list option[value="+idTypeIdValue+"]").attr("selected", "true");
		var vehicleTypeValue = $("#vehicleTypeValue").val();
		if($.trim(vehicleTypeValue) != ""){
			$("#vehicleType option[value="+vehicleTypeValue+"]").attr("selected", "true");
		}
		var carTypeIdValue = $("#carTypeIdValue").val();
		if($.trim(carTypeIdValue) != ""){
			$("#carTypeId option[value="+carTypeIdValue+"]").attr("selected", "true");
		}
		var platNumber1Value = $("#platNumber1Value").val();
		if($.trim(platNumber1Value) != ""){
			$("#platNumber1 option[value="+platNumber1Value+"]").attr("selected", "true");
		}
		//回显结束
});
	jQuery(document).ready(function() {	
    		var mess = $("#mess").val();
			if (mess != null && $.trim(mess) != "") {
				Dialog.alert(mess);
			}
		$("#addBtn").click(function() {
			try {
				$(this).validate.isNull($("#jq_name").val(), '姓名不能为空');
				var txtlength = $("#jq_name").val().length;
			 	if(txtlength < 2) {
			    	throw ("请输入完整的姓名！");
			    }
				$(this).validate.isNull($("#jq_id_type_list").val(), '请选择证件类型');
				var idName = $("#jq_id_type_list").find('option:selected').text(); 
				$(this).validate.isNull($("#idNumber").val(), '证件号不能为空');
				if(idName == '居民身份证'){
					$(this).validate.isCardFormat($("#idNumber").val(), '证件号格式不正确','-99');
				}
				$(this).validate.isNull($("#mobile").val(), '请输入手机号');
				$(this).validate.isMobile($("#mobile").val(), '手机号码格式不正确');
				//$(this).validate.isNull($("#jq_use_charater").val(), '车辆使用性质不能为空');
				var platNumber = $("#platNumber2").val();
				
				var newflag = $("input[id='newflag']").is(':checked');
				if(newflag){
					 $("#platNumber").val("");
				}else{
					var carTypeId = $("#carTypeId").val();
					$(this).validate.isNull(platNumber, '请输入号牌号码');
					var reg_platNumber = new RegExp(/^[0-9a-zA-Z]*$/);  
					if(!reg_platNumber.test($.trim(platNumber))){
					  throw ("号牌号码只能是数字或字母，汉字无需输入！");
					}
				    if(carTypeId != '0AAA03BC4AE74531BF1FE45A03C38577'){
						if(platNumber.length > 8 || platNumber.length < 4){
							 throw ("请输入正确的号牌号码！");
						}
					}else{
						if(platNumber.length > 5){
							 throw ("挂车的号牌号码不能超过5位！");
						}if($("#frameNumber").val().length > 24){
							 throw ("车架号不能超过24位！");
						}
					}
					$("#platNumber").val($("#platNumber1").val()+$("#platNumber2").val());
				}
				
				$(this).validate.isNull($("#frameNumber").val(), '请输入车架号');
				var frameNumberLength = $("#frameNumber").val().length;
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
				$(this).validate.isNull($("#carTypeId").val(), '请选择号牌种类');
				$(this).validate.submin_form();
			} catch (e) {
				Dialog.alert(e);
			} 
		}); 
	});
	
   $("#newflag").click(function(){
		var v =  $("input[id='newflag']").is(':checked');
		if(v){
			$("#platNumber2").val("");
			$("#platNumber2").attr("disabled","disabled");
			$("#platNumber2").css("background","#84636326");
			$("#spanFrameNumber").html("完整车架号");
			$("#frameNumber").attr("placeholder","完整车架号");
		}else{
			$("#platNumber2").removeAttr("disabled");
			$("#platNumber2").removeAttr("style");
			$("#spanFrameNumber").html("车架号后4位");
			$("#frameNumber").attr("placeholder","车架号后4位");
		}
	});
	
</script>
</html>
