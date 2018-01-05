<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>机动车业务网上预约</title>
<link href="${ctx}/static/front/css/input/home.css" rel="stylesheet" type="text/css">
<link href="${ctx}/static/front/js/plugins/artDialog/skins/default.css" rel="stylesheet" type="text/css">
<style type="text/css">
	.reqiredInput{color: #F00;}
	.info_table{width: 800px;border: 0;background: #99BBE8;text-align: left;}
	.info_body tr td{font-size: 14px;background-color: #EEF4FD;}
	.info_body tr td.table{width: 30%;text-align: right;}
	.info_body tr td{height: 40px;padding: 0px 10px 0px 10px;}
	.info_body td input{height: 23px;line-height: 20px;padding-left: 6px;}
	td input[type="button"]{width: 150px;height: 30px;background: #E6F4FF;border: 1px solid #CCC;}
	.info_body td input[type="text"]{height: 20px;}
	.info_body td input[type="checkbox"]{height: 20px;margin-top: 10px;}
	.err_table{float:left;width: 400px;border: 0;}
	.err_body tr td{font-size: 12px;background-color: #EEF4FD;white-space:nowrap;height: 25px;padding: 0px 10px 0px 10px;}
	.label{vertical-align: middle;height: 30px;line-height: 30px;}
	.radio{position: relative;top: 2px;width:15px!important;height:15px!important;border: 0px;background: #eef4fd;}
	input[type="file" i]{align-items: baseline;color: inherit;text-align: start;}
	.title{height: 33px;border: 1px solid #c1d3de;width: 100%;background: url("/mot/static/backend/xtbg/images/righttitlebig.png");}
	.title h2{margin-left: 7px;padding-left: 22px;font-weight: bold;font-size: 14px;color: #000000;float: left;padding-top: 0px;margin-top: 6px;background: url("/mot/static/backend/xtbg/images/titleico.png")no-repeat left center;}
	form{border: 1px solid rgb(193, 211, 222);}
</style>
</head>
<body style="background:#FFF">
	<div class="title">
		<h2>导入导出</h2>
	</div>
	<form action="${ctx}/backend/data/sync/import" method="post" id="form" enctype="multipart/form-data">
		<span id="message" style="display: none">${message}</span>
		<table border="0" align="center" cellpadding="2" cellspacing="0" bgcolor="#FFFFFF" style="margin-top: 20px;">
			<tr>
				<td align="center" valign="middle">
					<table class="info_table" cellspacing="1" cellpadding="5">
						<thead class="info_body">
							<tr>
								<td class="table"><label class="reqiredInput">*</label>同步预约信息：</td>
								<td>
									<label class="label"> <input class="radio" type="radio" name="sync" value="1" checked="checked">导入 </label>
								 	<label class="label"> <input class="radio" type="radio" name="sync" value="2"  >导出 </label>
								</td>
							</tr>
						</thead>
						</table>
						<table class="info_table" cellspacing="1" cellpadding="5">
						<tbody class="info_body" id="export" hidden>
							<tr> 
								<td style="text-align: right;width: 30%"><label class="reqiredInput">*</label>开始时间：
								</td>
								<td colspan="2">
									<input id="d4311" name="startDate" class="Wdate" type="text" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')||\'%y-%M-%d\'}'})" /> 
								</td>
							</tr>
							<tr> 
								<td style="text-align: right;"><label class="reqiredInput">*</label>结束时间：</td>
								<td colspan="2">
									<input id="d4312" name="endDate" class="Wdate" type="text" onclick="WdatePicker({minDate:'#F{$dp.$D(\'d4311\')}',maxDate:'%y-%M-%d'})" />
								</td>
							</tr>
						</tbody>
						<tbody class="info_body" id="import" >
							<tr>
								<td class="table"><label class="reqiredInput">*</label>导入文件（ZIP格式）：</td>
								<td>
								<input type="file" name="file"> 
								</td>
							</tr>
							<c:if test="${errorMap !=null && fn:length(errorMap)>0}">
							<tr>
								<td></td>
								<td id="errorDiv">
										<table class="err_table" align="center">
												<c:forEach items="${errorMap }" var="items">
													<c:if test="${fn:length(items.value)>0}">
															<thead class="err_body">
																<tr>
																	<td colspan="2">${items.key } --- 导入失败  ${fn:length(items.value)} 条
																	 	<a name="${items.key }" class="showErrMsg" style="float: right;">点击查看</a>
																	 </td>
																</tr>
															</thead>
															<tr hidden>
																<td hidden id="${items.key }">
																	<table class="err_table" align="center">
																		<tbody class="err_body" >
																			<tr>
																				<td>ID</td>
																				<td>失败描述</td>
																			</tr>
																			<c:forEach items="${items.value }" var="item">
																				<tr>
																					<td>${item.key }</td>
																					<td>${item.value }</td>
																				</tr>
																			</c:forEach>
																		</tbody>
																	</table>
																</td>							
															</tr>
													</c:if>
												</c:forEach>
												</table>
										
								
								</td>
							</tr>
							</c:if>
						</tbody>
					</table></td>
			</tr>
			<tr>
				<td align="center" valign="middle">
					<table width="50%" style="margin-bottom: 20px;margin-top: 20px;" border="0" align="center" cellpadding="2" cellspacing="0">
						<tr>
							<td align="center" valign="middle"><input type="button" id="nextButton" name="Submit" value="执行" /></td>
						</tr>
					</table></td>
			</tr>
		</table>
	</form>
	<script type='text/javascript' src='${ctx}/static/front/js/pages/common/include_js.js?ctx=${ctx}/static/front&artDialog&calendar&jsfileversion=2.0'></script>
	
	<script type="text/javascript">
		
		$(function() {
		
			initData();
		
			$(".radio").click(function() {
				if ($(this).val() == "1") {
					$("#import").show();
					$("#export").hide();
					$("#form").attr("action", "${ctx}/backend/data/sync/import")
				} else {
					$("#export").show();
					$("#import").hide();
					$("#form").attr("action", "${ctx}/backend/data/sync/export")
				}
			})

			var last_time;
			$("#nextButton").click(function() {
				if (last_time) {
					if (new Date().getTime() - last_time < 5000) {
						$.dialog({
							id : 'time',
							title : '提示',
							time : 3,
							content : "操作频率太快，请在5秒后点击导出",
							yesFn : true
						});
						return;
					}
				}
				last_time = new Date().getTime();

				var radio_val = $('input:radio:checked').val();
				if (radio_val == "1") {
					if (document.all.file.value != "") {

					} else {
						$.dialog({
							id : 'file',
							title : '提示',
							time : 3,
							content : "你没有选择导入文件",
							yesFn : true
						});
						return;
					}
					$("#form").submit();
				} else {
					var start_date = $("#d4311").val();
					var end_date = $("#d4312").val();
					
					var checkbox = $(".tableName");
					
					if (start_date == "" || end_date == "") {
						$.dialog({
							id : 'date',
							title : '提示',
							time : 3,
							content : "你没有选择时间",
							yesFn : true
						});
						return;
					}
					
					$.dialog({
							id : 'wait',
							lock: true,
							background: '#000', // 背景色
    						opacity: 0.6,	// 透明度
							title : '提示',
							content : "正在导出,请等待...",
						});
					
					var url = $("#form").attr("action")
					$.post(url, {
						beginDate : start_date,
						endDate : end_date
					}, function(result) {
						if (result.code == 1) {
							openfile(result.name.trim())
						} else {
							$.dialog({id: 'wait'}).close();
							$.dialog({
								id : 'info',
								title : '提示',
								time : 3,
								content : result.message,
								yesFn : true
							});
						}
					});
				}
			})

			$("td").on("click", ".radio", function() {
				radio_name = $(this).attr("name");
			})
			
			$("#errorDiv").on("click",".showErrMsg", function() {
					$.dialog({
						title : $(this).attr("name"),
						Maxheight: "400",
						lock: true,
						background: '#000', // 背景色
    					opacity: 0.6,	// 透明度
						content :"<div style='max-height: 400px; overflow-y: scroll;'>"+$("#"+ $(this).attr("name")).html()+"</div>",
						yesFn : true
					});
			
			});
			
			function initData(){
				var message = $("#message").html();
				if ($.trim(message)) {
					$.dialog({
						title : '提示',
						content : message,
						yesFn : true
					});
				}
			
				$("#d4311").val(getDate(-3));
				$("#d4312").val(getDate(0))
			}
			
			//有窗口弹出的下载
		function openfile(name) {
			   // 创建一个 form  
		    var form1 = document.createElement("form");  
		    form1.id = "form1";  
		    form1.name = "form1";  
		    // 添加到 body 中  
		    document.body.appendChild(form1);
		    // 创建一个输入  
		    var input = document.createElement("input");  
		    // 设置相应参数  
		    input.type = "text";  
		    input.name = "name";  
		    input.value = name;  
		    // 将该输入框插入到 form 中  
		    form1.appendChild(input);  
		    // form 的提交方式  
		    form1.method = "POST";  
		    // form 提交路径  
		    form1.action = "${ctx}/backend/data/sync/download"; 
		    // 对该 form 执行提交  
		    form1.submit();  
		    // 删除该 form  
		    document.body.removeChild(form1); 
		    $.dialog({id: 'wait'}).close(); 
		}
			function getDate(AddDayCount){
			   var dd = new Date();    
			   dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期    
			   var y = dd.getFullYear();     
			   var m = (dd.getMonth()+1)<10?"0"+(dd.getMonth()+1):(dd.getMonth()+1);//获取当前月份的日期，不足10补0    
			   var d = dd.getDate()<10?"0"+dd.getDate():dd.getDate();//获取当前几号，不足10补0    
			   return y+"-"+m+"-"+d;     
			} 
			
		})
	</script>
</body>
</html>

