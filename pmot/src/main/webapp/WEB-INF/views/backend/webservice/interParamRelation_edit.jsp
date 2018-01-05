<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta charset="utf-8">
		<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
		<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
		<title>${appName }-接口信息管理</title>
	</head>
	<style type="text/css">
		.inputText{
			height: 22px;
		}
	</style>
	<body>
		<div class="title">
			<h2>${interfaceInfo.name }：接口参数列表</h2>
		</div>
		<div class="main">	
		<div class="context" >
		<input type="hidden" id="mess" value='${message }' />
		<form action="${ctx }/backend/webservice/interParamRelation/editParameter" method="post" name="form" id="form">
			<input type="hidden" id="interfaceInfoId" name="interfaceInfoId" value="${interfaceInfo.id }" />
			<input type="hidden" name="params" id="params" value=""/>
			<input type="button" value="更新" class="input_button" id="update_button" 
				style="float: right;margin-right: 50px;margin-bottom:10px;">
			<div class="table_box">
				<table>
					<thead>
						<tr>
							<th class="length2">
								全选
								<input id="jq_checkbox_all" type="checkbox" value="-1" name="checkbox_all" onclick="swapCheck();">
							</th>
							<th class="length3">参数名称</th>
							<th class="length3">参数类型</th>
							<th class="length2">非空验证</th>
							<th class="length2">排列顺序</th>
							<th class="length4">vo非空说明</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${interfaceParameters }" var="parameter" varStatus="status">
						<tr>
							<c:set value="false" var="paramIschecked"></c:set>
							<c:set value="0" var="isNullIsCheckbox"></c:set>
							
							<c:set value="" var="orderIndexInput"></c:set>
							<c:set value="" var="requiredAttrsInput"></c:set>
							<c:forEach items="${paramRelations }" var="paramRelation">
								<c:if test="${parameter.id == paramRelation.parameterId}">
									<c:set value="true" var="paramIschecked"></c:set>
								 	<c:set value="${paramRelation.notNull }" var="isNullIsCheckbox"></c:set>
									<c:set value="${paramRelation.orderIndex }" var="orderIndexInput"></c:set>
									<c:set value="${paramRelation.requiredAttrs }" var="requiredAttrsInput"></c:set>
								</c:if>
							</c:forEach>
							
							<td class="length2">
								<input type="checkbox" value="${parameter.id }" name="paramRelations[${status.index }].parameterId"  
								<c:if test="${paramIschecked == true}">
									 checked="checked"
								</c:if> />
							</td> 
							<td class="length3">${parameter.name }</td>
							<td class="length3">${parameter.type }</td>
							<td class="length2"><input type="checkbox" name="paramRelations[${status.index }].isNull"  value="${isNullIsCheckbox }"
								<c:if test="${isNullIsCheckbox == '1'}">
									 checked="checked"
								</c:if> />
							 </td>
							<td class="length2"><input type="text" name="paramRelations[${status.index }].orderIndex" 
								class="inputText" value="${orderIndexInput }" style="width: 50%"></td>
							<td class="length4"><input type="text" name="paramRelatiosn[${status.index }].requiredAttrs" 
								class="inputText" value="${requiredAttrsInput }" style="width: 100%"></td>
						</c:forEach>
					</tbody>
				</table>
			</form>
		</div>
	</div>
	</body>
	<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
	<script type='text/javascript' src='${ctx}/static/backend/js/zDialog/zDrag.js'></script>
	
	<!--artDialog-->
	<script type="text/javascript" src="${ctx }/static/backend/js/validator.js"></script>
	<script type="text/javascript" src="${ctx }/static/backend/js/artDialog/jquery.artDialog.min.js"></script>
	<script type="text/javascript" src="${ctx }/static/backend/js/artDialog/artDialog.iframeTools.min.js"></script>
	<script type="text/javascript">
	    //初始化列表
    	/* initTable();
		function initTable(){
			var paramers = $("input[type='checkbox'][name$='parameterId']");
			var orderIndexs = $("input[name$='orderIndex']");
			var isNulls = $("input[type='checkbox'][name$='isNull']");
			var requiredAttrs = $("input[name$='requiredAttrs']");
			// 已经绑定的关系表
			var paramRelations = '${paramRelations}';
			console.log("paramRelations:"+paramRelations);
			if(paramRelations && paramRelations.length){
				for(var i = 0;i<paramers.length;i++){
					for(var j = 0;j<paramRelations.length;j++){
						if(paramRelations[j].parameterId == paramers.eq(i).val()){
							paramers.eq(i).attr("checked",true);
							orderIndexs.eq(i).val(paramRelations[j].orderIndex);
							if(paramRelations[j].notNull == "1"){
								isNulls.eq(i).attr("checked",true);
							}
							requiredAttrs.eq(i).val(paramRelations[j].requiredAttrs);
						}
					}
				}
			}
		} */
		var isCheckAll = false;  
        function swapCheck() {  
            if (isCheckAll) {  
                $("input[type='checkbox'][name$='parameterId']").each(function() {  
                    this.checked = false;  
                });  
                isCheckAll = false;  
            } else {  
                $("input[type='checkbox'][name$='parameterId']").each(function() {  
                    this.checked = true;  
                });  
                isCheckAll = true;  
            }  
        } 
       
		$(function() {
			var mess = $("#mess").val();
			if (mess != null && $.trim(mess) != "") {
				$.dialog({
					title : '3秒后自动关闭',
					time : 3,
					content : mess,
					icon : 'succeed',
					yesFn : true
				});
			}
			
		  $("#update_button").click(function() {
				try {
				    var paramLength = $("input[type='checkbox'][name$='parameterId']:checked").length;
					if(paramLength <= 0){
						throw ("请选择需要添加的参数！");
					}
					var paramers = $("input[type='checkbox'][name$='parameterId']");
					var orderIndexs = $("input[name$='orderIndex']");
					var isNulls = $("input[type='checkbox'][name$='isNull']");
					var requiredAttrs = $("input[name$='requiredAttrs']");
					var interfaceInfoId = $("#interfaceInfoId").val();
			 
					var newArray = [];
					// 循环判断
					for(var i = 0;i<paramers.length;i++){
						if(paramers.eq(i).is(":checked")){
							var o = new Object();
							o.parameterId = paramers.eq(i).val();
							o.orderIndex = orderIndexs.eq(i).val();
							if(isNulls.eq(i).is(":checked")){
								o.notNull = 1;
							}else{
								o.notNull = 0;
							}
							o.interfaceInfoId=interfaceInfoId;
							o.requiredAttrs=requiredAttrs.eq(i).val();
							newArray.push(o);
						}
					}
					var json = JSON.stringify(newArray);
					$("#params").val(json);
					//console.log(json);
					//提交表单
					$(this).validate.submin_form();
					/* [{"parameterId":"402881f95d408424015d40849bb90002","orderIndex":"1","isNull":1,"interfaceInfoId":"5D49170B139B56FBE0536B01A8C0D6B6","requiredAttrs":"name,idTypeId,idNumber,platNumber,frameNumber,vehicleType,useCharater,carTypeId,driveType,fuelType,mobile,vehicleCharacter"},
					{"parameterId":"402881f95d408424015d40849d420035","orderIndex":"","isNull":"0","interfaceInfoId":"5D49170B139B56FBE0536B01A8C0D6B6","requiredAttrs":""},{"parameterId":"402881f95d408424015d40849d420036","orderIndex":"","isNull":"0","interfaceInfoId":"5D49170B139B56FBE0536B01A8C0D6B6","requiredAttrs":""}]
					 */
				} catch (e) {
					$.dialog({
						title : '3秒后自动关闭',
						time : 3,
						content : e,
						icon : 'error',
						yesFn : true
					});
					return false;
				}
			});
        
		});
	</script>
</html>


