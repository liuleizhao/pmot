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
	.title{height: 33px;border: 1px solid #c1d3de;width: 100%;background: url("/pmot/static/backend/xtbg/images/righttitlebig.png");}
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
															<td colspan="2">${items.key } --- 导入失败
																${fn:length(items.value)} 条 <a name="${items.key }"
																class="showErrMsg" style="float: right;">点击查看</a></td>
														</tr>
													</thead>
													<tr hidden>
														<td hidden id="${items.key }">
															<table class="err_table" align="center">
																<tbody class="err_body">
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
															</table></td>
													</tr>
												</c:if>
											</c:forEach>
										</table>
									</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</td>
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
					$("#message").html("");
				}
			}
		})
	</script>
</body>
</html>

