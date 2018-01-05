<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>参数添加</title>
	<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/mui.min.css" />
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-reg.css" />
	<link rel="stylesheet" href="${ctx}/static/backend/css/backend_common.css" />
</head>
	<style>
		.mui-input-group .mui-input-row:after{position: absolute;right: 10px;bottom: 5px;left: 15px;height: 0px;width: 50%;}
		.mui-input-group input, .mui-input-group textarea{margin-bottom: 0px;background-color: transparent;box-shadow: none;border-width: 1px;border-style: initial;border-color: initial;border-image: initial;border-radius: 0px;}
		.select_td select{margin: 5px 10px;height: 30px;padding: 0;padding-left: 17px;font-size: 13px;width: 90%;}
		.triangle-down{right: 10%;top:15px;}
		input[type="color"], input[type="date"], input[type="datetime-local"], input[type="datetime"], input[type="email"], input[type="month"], input[type="number"], input[type="password"], input[type="search"], input[type="tel"], input[type="text"], input[type="time"], input[type="url"], input[type="week"], select{width: 80%;border: 1px solid #CCC!important;margin: 5px 10px;height: 30px;}
	</style>
<body>
	<div class="title">
		<h2>参数添加</h2>
	</div>
	<form class="classA mui-input-group z_99" action="${ctx }/backend/data/conf/add" method="POST" name="form" id="editFrom">
		<table cellpadding="8" cellspacing="1">
			<tr>
				<td class="field"><span class="txt-imp">*</span>表名</td>
				<td class="text select_td">
					<div class="mui-input-row">
						<select name="tableName" id="tableName">
							<c:forEach items="${tableList }" var="entity" varStatus="status">
								<option value="${entity }"<c:if test="${status.index == 0 }">selected="selected"</c:if>>${entity }</option>
							</c:forEach>
						</select>
						<div class="triangle-down"></div>
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>状态</td>
				<td class="text select_td">
				<div class="mui-input-row">
						<select name="state" id="state">
							<c:forEach items="${stateList }" var="entity" varStatus="status">
								<option value="${entity }"
								<c:if test="${status.index == 0 }">selected="selected"
								</c:if>>${entity.description }</option>
							</c:forEach>
						</select>
					<div class="triangle-down"></div>
				</div>
				</td>
			</tr>
			
			<tr>
				<td class="field"><span class="txt-imp">*</span>导出字段</td>
				<td class="text" colspan="3" style="height: 80px;">
					<div class="mui-input-row" style="height: 80px;">
						<textarea id="field" name="field" readonly="readonly" placeholder="不选择字段则导出全部信息..." style="width: 80%;border: 1px solid #CCC;margin: 8px 10px;"></textarea>
						<button type="button" id="choose_field" style="margin-top: 20px;">选择字段</button>
					</div>
				</td>
			</tr>
			<!-- <tr>
				<td class="field"><span class="txt-imp">*</span>条件sql</td>
				<td class="text" colspan="3">
					<div class="mui-input-row" style="height: 80px;">
						<textarea id="condition" readonly="readonly" name="condition" style="width: 80%;border: 1px solid #CCC;margin: 8px 10px;"></textarea>
						<button type="button" id="chose_condition" style="margin-top: 20px;">选择字段</button>
					</div>
				</td>
			</tr> -->
			<tr>
				<td class="field">描述</td>
				<td class="text" colspan="3">
					<div class="mui-input-row" style="height: 80px;">
						<textarea id="description" name="description" placeholder="简单描述一下..." style="width: 80%;border: 1px solid #CCC;margin: 8px 10px;"></textarea>
					</div>
				</td>
			</tr>
		</table>
		<div class="mui-button-row">
			<button type="submit" class="mui-btn mui-btn-green">保存</button>&nbsp;&nbsp;
			<button type="button" class="mui-btn" style="background: #E6F4FF;"  onclick="javascript:history.back(-1);">返回</button>
		</div>
	</form>
	<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
	<script type="text/javascript" src="${ctx }/static/backend/js/artDialog/jquery.artDialog.min.js"></script>
	<script type="text/javascript">
		$(function() {
			init();
			$("#choose_field").click(function() {
				var field_arr = $("#field").val().split(",");
				var content = "<table>";
				var _ID = 0;
				for ( var i = 0; i < colList.length; i++) {
					if((i+_ID)%2==0){content += "<tr>"}
					var checkbox = "";
					if(colList[i] == "ID"){
						_ID = -1;
						continue;
					}else{
						if($.inArray(colList[i], field_arr) != -1){
							checkbox = "<input name='"+colList[i]+"' class='c_checkbox' type='checkbox' checked='checked' style='vertical-align:middle;'/>"
						}else{
							checkbox = "<input name='"+colList[i]+"' class='c_checkbox' type='checkbox' style='vertical-align:middle;'/>"
						}
					}
					content += "<td style='min-width: 150px;'>"+checkbox+colList[i]+"</td>"
					if((i+_ID)%2==1){content += "</tr>"}
				}
				content += "</table>";
				$.dialog({
						id:"c_dialog",
						title : $("#tableName").val(),
						Maxheight: "400",
						lock: true,
						background: '#000', // 背景色
    					opacity: 0.6,	// 透明度
						content :content,
						yesFn : function(){
							var c_checkbox = $(".c_checkbox:checked");
							var field_str = "";
							for ( var i = 0; i < c_checkbox.length; i++) {
								field_str += c_checkbox[i].name;
								if(i < c_checkbox.length-1){
									field_str+=","
								}
							}
							if(field_str != ""){
								field_str = "ID,"+field_str;
							}
							$("#field").val(field_str);
							return true;
						}
					});
				})
	
			//获取字段列表
			$("#tableName").bind("change", function(e) {
				$("#field").val("")
				var a = $(this).val();
				if(!(a=$.trim(a))){
					return false;
				}
				$.post("${ctx}/backend/data/conf/getCol",{"tableName" : a},function(result){
					if(result){
						colList = result;
					}
				});
			});
			
		});
			function init(){
				colList = new Array(); //全局变量 字段数组
				<c:forEach items="${colList }" var="entity">
					colList.push("${entity }");
				</c:forEach>
				
				if("${message }"!=""){
					$.dialog({
						id:"message",
						title : "信息",
						Maxheight: "400",
						lock: true,
						background: '#000', // 背景色
    					opacity: 0.6,	// 透明度
						content :"${message }",
						yesFn : true
					});
				}
			}
	</script>
</body>
</html>