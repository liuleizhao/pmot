<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${appName }大客户申请</title>
<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/mui.min.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-reg.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
<link href="${ctx }/static/backend/css/password_strength_style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/backend/css/backend_common.css" />
<script type="text/javascript" src="${ctx}/static/backend/js/laydate-v1.1/laydate/laydate.js"></script>

</head>
<style>
.testresult span {
	padding: 0px 10px 0px 0px;
}
</style>

<body>
	<div class="title">
		<h2>大客户申请预约量</h2>
	</div>
	<form class="classA mui-input-group" action="${ctx }/backend/appoint/compApplyAdd" method="post" name="form" id="form">
		<table cellpadding="8" cellspacing="1">
			<tr>
				<td class="field"><span class="txt-imp">*</span>企业名称</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="companyName" id="companyName" type="text" value="${compApplyFrom.companyName }" onkeyup="toUpperCase(this)"/> <span id="companyName_info" class="Prompt"></span>
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>申请数量</td>
				<td class="text">
					<div class="mui-input-row">
						<input type="text" name="applyCount" id="applyCount" value="${compApplyFrom.applyCount }"> <span id="applyCount_info" class="Prompt"></span>
					</div>
				</td>
			</tr>

			<tr>
				<td class="field"><span class="txt-imp">*</span>企业社会信用代码</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="enterpriseCrediteCode" id="enterpriseCrediteCode" type="text" value="${compApplyFrom.enterpriseCrediteCode }" onkeyup="toUpperCase(this)"/> <span id="compApplyFrom_info" class="Prompt"></span>
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>组织机构代码</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="organizationCode" id="organizationCode" type="text" value="${compApplyFrom.organizationCode }" onkeyup="toUpperCase(this)"/> <span id="organizationCode_info" class="Prompt"></span>
					</div>
				</td>
			</tr>
			<tr>
				<td class="field"><span class="txt-imp">*</span>起始日期</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="startDate" id="startDate" type="text" value="<fmt:formatDate value="${compApplyFrom.startDate }" pattern="yyyy-MM-dd" />"/> <span id="startDate_info" class="Prompt"></span>
					</div>
				</td>
				<td class="field"><span class="txt-imp">*</span>终止日期</td>
				<td class="text">
					<div class="mui-input-row">
						<input name="endDate" id="endDate" type="text" value="<fmt:formatDate value="${compApplyFrom.endDate }" pattern="yyyy-MM-dd" />"/> <span id="endDate_info" class="Prompt"></span>
					</div>
				</td>
			</tr>
			
			<tr>
				<td class="field"><span class="txt-imp">*</span>备注</td>
				<td class="text" colspan="3">
					<textarea rows="5" cols="50" name="discription" id="discription">${compApplyFrom.discription }</textarea>
				</td>
			</tr>
		</table>

		<div class="mui-button-row">
			<button type="button" id="addBtn" class="mui-btn mui-btn-green">申请</button>&nbsp;&nbsp;
			<button type="button" class="mui-btn" style="background: #E6F4FF;"  onClick="javascript:history.back(-1);">返回</button>
		</div>
	</form>
	
	
</body>
<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
<script type="text/javascript">
	$(function(){
		 var start = {
				  elem: '#startDate',
				  format: 'YYYY-MM-DD',
				  min: laydate.now(+7), //设定最小日期为当前日期
				  max: '2020-12-31', //最大日期
				  istime: true,
				  istoday: true,
				  choose: function(datas){
				     end.min = datas; //开始日选好后，重置结束日的最小日期
				     end.start = datas; //将结束日的初始值设定为开始日
				  }
				};
				var end = {
				  elem: '#endDate',
				  format: 'YYYY-MM-DD',
				  min: '1970-01-01 00:00:01',
				  max: '2020-12-31',
				  istime: true,
				  istoday: false,
				  choose: function(datas){
				    start.max = datas; //结束日选好后，重置开始日的最大日期
				  }
				};
				laydate(start);
				laydate(end); 
				
		$("#addBtn").click(
			function() {
				try {
					
					$(this).validate.isNull($("#companyName").val(),"企业名称不能为空");
					$(this).validate.isNull($("#applyCount").val(), "申请数量");
					if($("#applyCount").val() > 300){
						throw("一次申请数量不能超过300");
					}
					$(this).validate.isNumber($("#applyCount").val(), "申请数量只能是数字");
					
					$(this).validate.isNull($("#enterpriseCrediteCode").val(),"企业社会信用代码不能为空");
					$(this).validate.isNull($("#organizationCode").val(), "组织机构代码不能为空");
					
					$(this).validate.isNull($("#startDate").val(), "起始日期不能为空");
					$(this).validate.isNull($("#endDate").val(), "终止日期不能为空");
					var nowDate = new Date();
					var startDate = new Date(Date.parse($("#startDate").val()));
					if(startDate-nowDate < 1000*24*60*60*6){
						throw("必须提前一周提出申请");
					}
					
					$(this).validate.isNull($("#discription").val(), "备注不能为空"); 
					//提交表单
					$(this).validate.submin_form();
				} catch (e) {
					openErrorDialog(e,true,false,{time:3,title:"3秒后自动关闭"});
				}
		});	
				
	});
		// 转大写
	function toUpperCase(obj) {   
		obj.value = $.trim(obj.value.toUpperCase());
	};
	
</script>
</html>