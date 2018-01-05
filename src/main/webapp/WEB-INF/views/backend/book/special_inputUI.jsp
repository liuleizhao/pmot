<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ page import="java.util.Date"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${appName }-特殊通道预约</title>
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/mui.min.css" />
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-reg.css" />
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
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
		<h2>特殊通道预约-填写信息</h2>
	</div>
	<input type="hidden" id="mess" value="${errorMessage }" />
	<form class="classA mui-input-group" action="${ctx }/backend/book/specialBook" method="post" name="form" id="form">
	<input type="hidden" name="bookChannel" value="STATION" />
	<input type="hidden" name="bookInfos" id="bookInfos" value=""/>
	<input type="hidden" name="compApplyFromId" value="${compApplyFrom.id}">
	<input >
		<table cellpadding="8" cellspacing="1" id="batchTable">
			<tr>
				<td class="field"><span class="txt-imp">*</span>企业名称</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="bookerName" id="jq_name" type="text" value="${compApplyFrom.companyName }" readonly="readonly"/> 
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>证件名称</td>
				<td class="text select_td">
					<div class="mui-input-row">
							<select id="jq_id_type_list" name="idTypeId"  value="${bookInfo.idTypeId }">
								<option value="40288282463ceca50146462942d3055c">组织机构代码证书</option>
								<option value="4028823f51d79d4d0151f1ebb1dc361e" selected="selected">统一社会信用代码</option>
							</select>
							<div class="triangle-down"></div>
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>证件号</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" name="idNumber" id="idNumber" value="${compApplyFrom.enterpriseCrediteCode }"  
						maxlength="30" onkeyup="toUpperCase(this)" readonly="readonly"/>
					</div>
				</td>
			</tr>

			<tr>
				
				<td class="field"><span class="txt-imp">*</span>手机号码</td> 
				<td class="text">
					<div class="mui-input-row">
						<input name="mobile" id="mobile" type="text"  value="${bookInfo.mobile }"  maxlength="11"/>
					</div>
				</td>
				<!-- <td class="field"><span class="txt-imp">*</span>车辆类型</td>
				<td class="text select_td">
					<div class="mui-input-row">
						<select id="vehicleType" name="vehicleType" class="q_select">
							<option value="1">	小型汽车</option>
							<option value="2">	大型汽车</option>
							<option value="3">	挂车	</option>
						</select>
						<div class="triangle-down"></div>
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>驱动类型</td>
				<td class="text select_td">
					<div class="mui-input-row">
						<select  id="driverType" name="driverType" class="q_select">
							<option value="0">	两驱（含非全时四驱）</option>
							<option value="1">	全时四驱</option>
						</select>
						<div class="triangle-down"></div>
					</div>
				</td> -->
			</tr>
<!-- 			<tr>
				<td class="field"><span class="txt-imp">*</span>车辆性质</td>
				<td class="text select_td">
					<div class="mui-input-row">
						<select id="vehicleCharacter" name="vehicleCharacter" class="q_select">
							<option value="XXZK">小型载客汽车</option>
							<option value="ZZZH">小型载货汽车(含专项作业车)</option>
							<option value="WHPZSC">危化品运输车</option>
							<option value="XXXC">校车</option>
						</select>
						<div class="triangle-down"></div>
					</div>
				</td>
				<td class="field no_trailer" ><span class="txt-imp">*</span>燃油类型</td>
				<td class="text select_td no_trailer">
					<div class="mui-input-row">
						<select  id="fuelType" name="fuelType" class="q_select">
							<option value="1">	汽油、混合动力、纯电</option>
							<option value="2">	柴油、柴电混合（总质量小于等于3500KG）</option>
							<option value="3">	柴油、柴电混合（总质量大于3500KG）	</option>
						</select>
						<div class="triangle-down"></div>
					</div>
				</td>
				<td colspan="2"></td>
			</tr> -->
			<tr align="center">
				<td colspan="2" >批量填写车辆信息</td>
				<td colspan="4">
				<button type="button" class="mui-btn"  onClick="addLine();" style="background: yellow;">添加一行</button>
				</td>
			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>号牌号码</td>
				<td class="text">
					<div class="mui-input-row">
						<select id="platNumber1" name="platNumber1">
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
						<input type="text" name="platNumber2" id="platNumber2" maxlength="6" onkeyup="toUpperCase(this)"/>
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>号牌种类</td>
				<td class="text select_td">
					<div class="mui-input-row">
						<select id="carTypeId" name="carTypeId" class="q_select">
							<option value="312AED23657445C194540C794DBDBDB9">小型汽车（蓝底白字）</option>
							<option value="B0FCD21FCB3A45448C48134C690D1E05">使馆汽车（黑底白字、红使字 ）</option>
							<option value="B4394B3F2F3B4E78911713C3D54D4196">领馆汽车（黑底白字、红领字）</option>
							<option value="D7FAFC5A68004845864C42345B58D7BC">教练汽车（黄底黑字黑框线）</option>
							<option value="763FF1EEE4BB4C3995B402E8A7D2C550">小型新能源汽车（渐变绿底黑字）</option>
							<option value="5A747782F9FA4F0BB4B882D7771A975D">境外汽车（黑底白/红字）</option>
							
							<option value="5AA667F13C2143F0A41C6940E74B127E">大型汽车（黄底黑字）</option>
							<option value="0D7E3ABB86774FD1927EE05CF82FDA4B">低速车（黄底黑字黑框线）</option>
							<option value="B4394B3F2F3B4E78911713C3D54D4196">领馆汽车（黑底白字、红领字）</option>
							<option value="D7FAFC5A68004845864C42345B58D7BC">教练汽车（黄底黑字黑框线）</option>
							<option value="0EBEC3DB9EAA40A7B97DDD547FF58F51">大型新能源汽车（黄绿双拼色底黑字）</option>
							<option value="5A747782F9FA4F0BB4B882D7771A975D">境外汽车（黑底白/红字）</option>
							<option value="31CCBA351E0A4B7AA1BAFBDE2AA93161">警用汽车（警O牌）</option>
							
							<option value="0AAA03BC4AE74531BF1FE45A03C38577">挂车</option>
							
						</select>
						<div class="triangle-down"></div>
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>预约时间</td>
				<td>
					
					 <input id="bookDate" type="text" name="bookDate" 
					 onclick="WdatePicker({minDate:'${startDate}',dateFmt:'yyyy-MM-dd',maxDate:'${endDate}'});" placeholder="预约日期"  /> 
				</td>
			</tr>
		</table>
		<div class="mui-button-row">
		 <!-- <input id="bookDate" type="text" name="bookDate" placeholder="预约日期"  />  -->
			<button type="button" id="addBtn" class="mui-btn mui-btn-green">保存</button>&nbsp;&nbsp;
			<button type="button" class="mui-btn" style="background: #E6F4FF;"  onClick="javascript:history.back(-1);">返回</button>
		</div>
		
	</form>
</body>
	<script type='text/javascript' src='${ctx}/static/front/js/pages/inputJson.js'></script>
	<script type="text/javascript" src="${ctx }/static/backend/js/jquery-1.10.2.js"></script>
	<script type="text/javascript" src="${ctx}/static/backend/js/My97DatePicker/WdatePicker.js"></script>	
	<script type="text/javascript" src="${ctx }/static/backend/js/validator.js"></script>
	<script type='text/javascript' src='${ctx }/static/backend/js/zDialog/zDrag.js'></script>
	<script type='text/javascript' src='${ctx }/static/backend/js/zDialog/zDialog.js?{ctx:"${ctx}"}'></script>
	<%-- <script type="text/javascript" src="${ctx}/static/backend/js/laydate-v1.1/laydate/laydate.js"></script> --%>
<script>
	// 转大写
	function toUpperCase(obj) {   
		obj.value = $.trim(obj.value.toUpperCase());
	};
	
	function addLine(){
		var platNumber1s = $("select[name='platNumber1']").length;
		platNumber1s = platNumber1s+1;
		var tr = '<tr><td class="field"><span class="txt-imp">*</span>号牌号码</td><td class="text"><div class="mui-input-row"><select id="platNumber1" name="platNumber1"><option value="粤">粤</option><option value="鄂">鄂</option><option value="豫">豫</option><option value="皖">皖</option><option value="赣">赣</option><option value="冀">冀</option><option value="鲁">鲁</option><option value="浙">浙</option><option value="苏">苏</option><option value="湘">湘</option><option value="闽">闽</option><option value="蒙">蒙</option><option value="京">京</option><option value="辽">辽</option><option value="渝">渝</option><option value="沪">沪</option><option value="陕">陕</option><option value="川">川</option><option value="黑">黑</option><option value="晋">晋</option><option value="桂">桂</option><option value="吉">吉</option><option value="宁">宁</option><option value="贵">贵</option><option value="琼">琼</option><option value="甘">甘</option><option value="青">青</option><option value="津">津</option><option value="云">云</option><option value="藏">藏</option><option value="新">新</option></select><div class="short-triangle-down"></div><input type="text" name="platNumber2" id="platNumber2" maxlength="6" onkeyup="toUpperCase(this)"></div></td><td class="field"><span class="txt-imp">*</span>号牌种类</td><td class="text select_td"><div class="mui-input-row"><select id="carTypeId" name="carTypeId" class="q_select"><option value="312AED23657445C194540C794DBDBDB9">小型汽车（蓝底白字）</option><option value="B0FCD21FCB3A45448C48134C690D1E05">使馆汽车（黑底白字、红使字 ）</option><option value="B4394B3F2F3B4E78911713C3D54D4196">领馆汽车（黑底白字、红领字）</option><option value="D7FAFC5A68004845864C42345B58D7BC">教练汽车（黄底黑字黑框线）</option><option value="763FF1EEE4BB4C3995B402E8A7D2C550">小型新能源汽车（渐变绿底黑字）</option><option value="5A747782F9FA4F0BB4B882D7771A975D">境外汽车（黑底白/红字）</option><option value="5AA667F13C2143F0A41C6940E74B127E">大型汽车（黄底黑字）</option><option value="0D7E3ABB86774FD1927EE05CF82FDA4B">低速车（黄底黑字黑框线）</option><option value="B4394B3F2F3B4E78911713C3D54D4196">领馆汽车（黑底白字、红领字）</option><option value="D7FAFC5A68004845864C42345B58D7BC">教练汽车（黄底黑字黑框线）</option><option value="0EBEC3DB9EAA40A7B97DDD547FF58F51">大型新能源汽车（黄绿双拼色底黑字）</option><option value="5A747782F9FA4F0BB4B882D7771A975D">境外汽车（黑底白/红字）</option><option value="31CCBA351E0A4B7AA1BAFBDE2AA93161">警用汽车（警O牌）</option><option value="0AAA03BC4AE74531BF1FE45A03C38577">挂车</option></select><div class="triangle-down"></div></div></td><td class="field"><span class="txt-imp">*</span>预约时间</td><td><input id="bookDate'+platNumber1s+'" type="text" name="bookDate" onclick="WdatePicker({minDate:&quot;${startDate}&quot;,dateFmt:&quot;yyyy-MM-dd&quot;,maxDate:&quot;${endDate}&quot;})" placeholder="预约日期"></td></tr>';
		$("#batchTable").append(tr);
	}
	
$(function(){
	//时间控件
	//date('bookDate');
});
/* function date(bookDate){
	var start = {
	  elem: '#'+bookDate,
	  format: 'YYYY-MM-DD',
	  min: '${startDate}', //设定最小日期为当前日期 
	  max: '${endDate}', //最大日期
	  istime: true,
	  istoday: false,
	  choose: function(datas){
	  }
	};
	laydate(start);
} */

	jQuery(document).ready(function() {	
    		var mess = $("#mess").val();
			if (mess != null && $.trim(mess) != "") {
				Dialog.alert(mess);
			}
		$("#addBtn").click(function() {
			try {
				$(this).validate.isNull($("#jq_name").val(), '姓名不能为空');
				$(this).validate.isNull($("#jq_id_type_list").val(), '请选择证件类型');
				var idName = $("#jq_id_type_list").find('option:selected').text(); 
				$(this).validate.isNull($("#idNumber").val(), '证件号不能为空');
				if(idName == '居民身份证'){
					$(this).validate.isCardFormat($("#idNumber").val(), '证件号格式不正确','-99');
				}
				$(this).validate.isNull($("#mobile").val(), '请输入手机号');
				$(this).validate.isMobile($("#mobile").val(), '手机号码格式不正确');
				 
				var platNumber1s = $("select[name='platNumber1']");
				var platNumber2s = $("input[name='platNumber2']");
				var carTypeIds = $("select[name='carTypeId']");
				var bookDates = $("input[name='bookDate']");
				var newArray = [];
				// 循环判断
				for(var i = 0;i<platNumber1s.length;i++){
					var num = i+1;
					var suffixPlatNumber = platNumber2s.eq(i).val();
					$(this).validate.isNull(suffixPlatNumber, '第'+num+"行号牌号码不能为空！");
					 
					var carTypeId = carTypeIds.eq(i).val();
					$(this).validate.isNull(carTypeId, '第'+num+"行号牌种类必须！");
					
					var bookDate = bookDates.eq(i).val();
					$(this).validate.isNull(bookDate, '第'+num+"行请选择预约时间！"); 
					
					var o = new Object();
					o.platNumber = platNumber1s.eq(i).val() + suffixPlatNumber;
					o.carTypeId = carTypeId;
					o.bookDate = bookDate;
					newArray.push(o);
				}
				var json = JSON.stringify(newArray);
				$("#bookInfos").val(bookInfos);
				$(this).validate.submin_form();
			} catch (e) {
				Dialog.alert(e);
			} 
		}); 
	});
</script>
</html>
