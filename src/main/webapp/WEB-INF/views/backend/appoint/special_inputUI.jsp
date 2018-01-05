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
	    right: 77%;
	    pointer-events: none;
	
	}
	.triangle-down{
		right:7%;
	}
	.select_td select.platNumber1{
		width:52px;
		padding-left: 17px;
		font-size: 13px;
		position: relative;
	}
	
	.mui-input-group .mui-input-row:after {
    position: absolute;
    right: 10px;
    bottom: 5px;
    left: 15px;
    height: 1px;
    width: 80%;
}

.select_td select {
    margin: 0;
    padding: 0;
    padding-left: 17px;
    font-size: 13px;
    width: 86%;
}
.deleteButton{
    background: #f17171;
    color: #fff;
    height: 29px;
    width: 65px;
    font-size: 10px;
    position: relative;
    top: 3px;
}
input[type=checkbox]{
	height: 15px;
    position: relative;
    top: 3px;
    width: 16px;
}


</style>

</head>

<body>
	<div class="title">
		<h2>特殊通道预约-填写信息</h2>
	</div>
	<input type="hidden" id="mess" value="${errorMessage }" />
	<form class="classA mui-input-group" action="" method="post" name="form" id="form">
	<input type="hidden" name="bookChannel" value="BACKEND" />
	<input type="hidden" name="bookInfos" id="bookInfos" value=""/>
	<input type="hidden" name="compApplyFormId" value="${compApplyFrom.id}" />
	<input type="hidden" name="idTypeId" value="4028823f51d79d4d0151f1ebb1dc361e" />  <!-- 默认选存这个 -->
	<input >
		<table cellpadding="8" cellspacing="1" id="batchTable">
			<tr>
				<td class="field"><span class="txt-imp">*</span>企业名称</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="bookerName" id="jq_name" type="text" value="${compApplyFrom.companyName }" readonly="readonly"/> 
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>企业社会信用代码</td>
				<td class="text ">
					<div class="mui-input-row">
						<input name="idNumber" id="enterpriseCrediteCode" type="text" value="${compApplyFrom.enterpriseCrediteCode }"  readonly="readonly"/> 
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>组织机构代码</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="otherIdNumber" id="organizationCode" type="text" value="${compApplyFrom.organizationCode }"  readonly="readonly"/> 
					</div>
				</td>
				
				<td class="field"><span class="txt-imp">*</span>手机号码</td> 
				<td class="text">
					<div class="mui-input-row">
						<input name="mobile" id="mobile" type="text"  value="${bookInfo.mobile }"  maxlength="11"/>
					</div>
				</td>
			</tr>

			<tr align="center">
				<td colspan="2" >批量填写车辆信息</td>
				<td colspan="6">
				<button type="button" class="mui-btn"  onClick="addLine();" style="background: #54c1f7;color: #FFF;" />添加一行</button>
				</td>
			</tr>
			
			
			<tr>
			<td class="field"><span class="txt-imp">*</span>号牌种类</td>
				<td class="text select_td">
					<div class="mui-input-row">
						<select id="carTypeId" name="carTypeId" class="q_select">
							<option value="5AA667F13C2143F0A41C6940E74B127E">大型汽车（黄底黑字）</option>
							<option value="312AED23657445C194540C794DBDBDB9">小型汽车（蓝底白字）</option>
							<option value="B4394B3F2F3B4E78911713C3D54D4196">领馆汽车（黑底白字、红领字）</option>
							<option value="A4FA9722C81C408B8A5BB65F8BD9C9B1">外籍汽车（黑底白字）</option>
							<option value="0D7E3ABB86774FD1927EE05CF82FDA4B">低速车（黄底黑字黑框线）</option>
							<option value="0AAA03BC4AE74531BF1FE45A03C38577">挂车</option>
							<option value="D7FAFC5A68004845864C42345B58D7BC">教练汽车（黄底黑字黑框线）</option>
							<option value="31CCBA351E0A4B7AA1BAFBDE2AA93161">警用汽车（警O牌）</option>
							<option value="0EBEC3DB9EAA40A7B97DDD547FF58F51">大型新能源汽车（黄绿双拼色底黑字）</option>
							<option value="763FF1EEE4BB4C3995B402E8A7D2C550">小型新能源汽车（渐变绿底黑字）</option>
						</select>
						<div class="triangle-down" ></div>
					</div>
				</td>
			
				<td class="field"><span class="txt-imp">*</span>号牌号码</td>
				<td class="text select_td">
					<div class="mui-input-row">
						<select class="platNumber1"  name="platNumber1">
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
						<input class="disableStyle" style="padding: 10px 0px; width: 40%;" type="text" name="platNumber2" id="platNumber2" maxlength="7" onkeyup="toUpperCase(this)"/>
						新车<input type="checkbox" value="1" name="newflag" onclick="newFlagCheck(this);"/>
					</div>
				</td>
				
				<td class="field" ><span class="txt-imp">*</span>车架号后四位</td>
				<td>
					<div class="mui-input-row">
						<input placeholder="新车输入完整的车架号" style="width:100%;" name="frameNumber"  type="text"  value="${bookInfo.frameNumber }"  maxlength="24" onkeyup="toUpperCase(this)"/>
					</div>
				</td>
				
				<td class="field"><span class="txt-imp">*</span>预约日期</td>
				<td>
					<div class="mui-input-row">
					 <input type="text" name="bookDate" 
					 onclick="WdatePicker({minDate:'${startDate}',dateFmt:'yyyy-MM-dd',maxDate:'${endDate}'});" placeholder="预约日期"  />
					 <button type="button" class="deleteButton" onClick="removeLine(this);">删除一行</button>
					 </div> 
				</td>
			</tr>
			
		</table>
		<div class="mui-button-row">
		 <!-- <input id="bookDate" type="text" name="bookDate" placeholder="预约日期"  />  -->
			<button type="button" id="addBtn" class="mui-btn mui-btn-green">提交</button>&nbsp;&nbsp;
			<button type="button" class="mui-btn" style="background: #E6F4FF;"  onClick="javascript:history.back(-1);">返回</button>
		</div>
		
	</form>
</body>
	<script type="text/javascript" src="${ctx }/static/backend/js/jquery-1.10.2.js"></script>
	<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
	<script type="text/javascript" src="${ctx}/static/backend/js/My97DatePicker/WdatePicker.js"></script>	
	<script type="text/javascript" src="${ctx }/static/backend/js/validator.js"></script>
	<script type='text/javascript' src='${ctx }/static/backend/js/zDialog/zDrag.js'></script>
	<script type='text/javascript' src='${ctx }/static/backend/js/zDialog/zDialog.js?{ctx:"${ctx}"}'></script>
<script>
	// 转大写
	function toUpperCase(obj) {   
		obj.value = $.trim(obj.value.toUpperCase());
	};
	
	function addLine(){
		var tr = '<tr><td class="field"><span class="txt-imp">*</span>号牌种类<td class="text select_td"><div class="mui-input-row"><select id="carTypeId" name="carTypeId" class="q_select"><option value="312AED23657445C194540C794DBDBDB9">小型汽车（蓝底白字）<option value="B0FCD21FCB3A45448C48134C690D1E05">使馆汽车（黑底白字、红使字 ）<option value="B4394B3F2F3B4E78911713C3D54D4196">领馆汽车（黑底白字、红领字）<option value="D7FAFC5A68004845864C42345B58D7BC">教练汽车（黄底黑字黑框线）<option value="763FF1EEE4BB4C3995B402E8A7D2C550">小型新能源汽车（渐变绿底黑字）<option value="5A747782F9FA4F0BB4B882D7771A975D">境外汽车（黑底白/红字）<option value="5AA667F13C2143F0A41C6940E74B127E">大型汽车（黄底黑字）<option value="0D7E3ABB86774FD1927EE05CF82FDA4B">低速车（黄底黑字黑框线）<option value="B4394B3F2F3B4E78911713C3D54D4196">领馆汽车（黑底白字、红领字）<option value="D7FAFC5A68004845864C42345B58D7BC">教练汽车（黄底黑字黑框线）<option value="0EBEC3DB9EAA40A7B97DDD547FF58F51">大型新能源汽车（黄绿双拼色底黑字）<option value="5A747782F9FA4F0BB4B882D7771A975D">境外汽车（黑底白/红字）<option value="31CCBA351E0A4B7AA1BAFBDE2AA93161">警用汽车（警O牌）<option value="0AAA03BC4AE74531BF1FE45A03C38577">挂车</select><div class="triangle-down"></div></div><td class="field"><span class="txt-imp">*</span>号牌号码<td class="text select_td"><div class="mui-input-row"><select class="platNumber1" name="platNumber1"><option value="粤">粤<option value="鄂">鄂<option value="豫">豫<option value="皖">皖<option value="赣">赣<option value="冀">冀<option value="鲁">鲁<option value="浙">浙<option value="苏">苏<option value="湘">湘<option value="闽">闽<option value="蒙">蒙<option value="京">京<option value="辽">辽<option value="渝">渝<option value="沪">沪<option value="陕">陕<option value="川">川<option value="黑">黑<option value="晋">晋<option value="桂">桂<option value="吉">吉<option value="宁">宁<option value="贵">贵<option value="琼">琼<option value="甘">甘<option value="青">青<option value="津">津<option value="云">云<option value="藏">藏<option value="新">新</select><div class="short-triangle-down"></div><input class="disableStyle" style="padding:10px 0;width:40%" type="text" name="platNumber2" id="platNumber2" maxlength="7" onkeyup="toUpperCase(this)"> 新车<input type="checkbox" value="1" name="newflag" onclick="newFlagCheck(this)"></div><td class="field"><span class="txt-imp">*</span>车架号后四位<td><div class="mui-input-row"><input placeholder="新车输入完整的车架号" style="width:100%" name="frameNumber" type="text" value="${bookInfo.frameNumber }" maxlength="24" onkeyup="toUpperCase(this)"></div><td class="field"><span class="txt-imp">*</span>预约日期<td><div class="mui-input-row"><input type="text" name="bookDate" onclick="WdatePicker({minDate:&quot;${startDate}&quot;,dateFmt:&quot;yyyy-MM-dd&quot;,maxDate:&quot;${endDate}&quot;})" placeholder="预约日期"> <button type="button" class="deleteButton" onclick="removeLine(this)">删除一行</button></div>';
		$("#batchTable").append(tr);
	}
	

	jQuery(document).ready(function() {	
    		var mess = $("#mess").val();
			if (mess != null && $.trim(mess) != "") {
				Dialog.alert(mess);
			}
		$("#addBtn").click(function() {
			try {
				$(this).validate.isNull($("#jq_name").val(), '姓名不能为空');
				//$(this).validate.isNull($("#jq_id_type_list").val(), '请选择证件类型');
			//	var idName = $("#jq_id_type_list").find('option:selected').text(); 
			//	$(this).validate.isNull($("#idNumber").val(), '证件号不能为空');
			//	if(idName == '居民身份证'){
			//		$(this).validate.isCardFormat($("#idNumber").val(), '证件号格式不正确','-99');
			//	}
				$(this).validate.isNull($("#mobile").val(), '请输入手机号');
				$(this).validate.isMobile($("#mobile").val(), '手机号码格式不正确');
				
				var carTypeIds = $("select[name='carTypeId']"); //号牌种类
				var newflags =  $("input[name='newflag']");  //新车标记
				var platNumber1s = $("select[name='platNumber1']");
				var platNumber2s = $("input[name='platNumber2']");  //车牌号
				var frameNumbers = $("input[name='frameNumber']");  //车架号
				var bookDates = $("input[name='bookDate']");
				var newArray = [];
				// 循环判断
				for(var i = 0;i<platNumber1s.length;i++){
					var num = i+1;
					var o = new Object();
					var carTypeId = carTypeIds.eq(i).val();
					$(this).validate.isNull(carTypeId, '第'+num+"行号牌种类必须！");
					var frameNumber = frameNumbers.eq(i).val();
					if(newflags.eq(i).is(':checked') ){
						o.platNumber = "";   //新车车牌号为空
  						if(frameNumber.length <= 4){
							throw("第"+num+"行新车请输入完整的车架号"); 
						}
						o.newflag = "1";  //新车为1
					}else{
						var suffixPlatNumber = platNumber2s.eq(i).val();
						$(this).validate.isNull(suffixPlatNumber, '第'+num+"行号牌号码不能为空！");
						if(suffixPlatNumber.length<4 || suffixPlatNumber.length >7){
							throw('第'+num+"行"+" 车牌号输入有误");
						}
						if(frameNumber.length != 4){
							throw('第'+num+"行"+" 旧车填写车架号的后四位");
						}
						
						o.platNumber = platNumber1s.eq(i).val() + suffixPlatNumber;  //车牌号4-7位
						
						o.newflag = "0";  //新车为1
					}
					
					var bookDate = bookDates.eq(i).val();  //预约时间
					$(this).validate.isNull(bookDate, '第'+num+"行请选择预约时间！"); 
					o.carTypeId = carTypeId;    //号牌种类
					o.frameNumber = frameNumber;  //车架号
					o.bookDate = bookDate;  //预约时间
					newArray.push(o);
				}
				var json = JSON.stringify(newArray);
				
				console.log(json);
				$("#bookInfos").val(json);
				
				//用ajax 提交
				$.ajax({
					type:"POST",
					url:"${ctx }/backend/appoint/specialBook",
					data:$('#form').serialize(),
					success:function(data){
					if($.trim(data.errorMessage) != ""){
						if(typeof(data.existBookInfos) != "undefined"){
							var existBookInfos = data.existBookInfos;
							var msg = "已预约车辆预约失败 ：";
							for(var i = 0;i<existBookInfos.length;i++){
								msg += "<br>【"+existBookInfos[i].platNumber+"】"+"时间"+existBookInfos[i].bookDate+" "+"地点： "+ existBookInfos[i].stationName;
								
							}
							Dialog.alert(msg);
							
						}else if($.trim(data.errorMessage) != ""){
							Dialog.alert(data.errorMessage);
						}
						
					 }else{
					 	//列表页面
					 	Dialog.alert("添加信息成功",function(){
					 		window.location.href = "${ctx }/backend/appoint/compApplylist";
					 	});
					 	
					 }
					},
					error:function(data){
						Dialog.alert("服务器忙");
					}
				});

			//	$(this).validate.submin_form();
			} catch (e) {
				Dialog.alert(e);
			} 
		}); 
		
	});
	
	function newFlagCheck(v){
		if(v.checked){
			$(v).prev().val("");
			$(v).prev().attr("disabled","disabled");
			$(v).prev().css("background","#e0e0e0");
			$(v).prev().css("height","25px");
			
			
		}else{
			$(v).prev().removeAttr("disabled");
			$(v).prev().removeClass("disableStyle");
			$(v).prev().css("background","#fff");
		}
	}
	
	function removeLine(v){
		$(v).parent().parent().parent().remove();
	}
	


</script>
</html>
