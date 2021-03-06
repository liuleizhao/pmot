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

	<form class="classA mui-input-group" action="${ctx }/backend/appoint/compApplyAdd" method="post" name="form" id="form" style="border:0px;">
		<table cellpadding="8" cellspacing="1" style="border:0px;">
			<tr>
				<td class="field"><span class="txt-imp">*</span>审批意见</td>
				<td class="text select_td" >
					<div class="mui-input-row">
						<select name="approveRemart" id="approveRemart">
							<c:forEach var="approveRemart" items="${approveRemarts}"> 
	                              <option value="${approveRemart.value }" >${approveRemart.description }</option>
	                        </c:forEach>
						</select>
						<div class="triangle-down"></div>
					</div>
				</td>
			</tr>
			
			<tr>
				<td class="field"><span class="txt-imp">*</span>备注</td>
				<td class="text" colspan="3">
					<textarea rows="5" cols="30" name="approveDiscription" id="approveDiscription"  value="${compApplyFrom.approveDiscription }"></textarea>
				</td>
			</tr>
		</table>


				
	</form>
</body>
<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
<script type="text/javascript">
	$(function(){
		 var start = {
				  elem: '#startDate',
				  format: 'YYYY-MM-DD',
				  min: '1970-01-01 00:00:01', //设定最小日期为当前日期
				  max: '2020-12-31', //最大日期
				  istime: true,
				  istoday: false,
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
					$(this).validate.isNumber($("#applyCount").val(), "申请数量只能是数字");
					
					$(this).validate.isNull($("#enterpriseCrediteCode").val(),"企业社会信用代码");
					$(this).validate.isNull($("#organizationCode").val(), "组织机构代码");
					$(this).validate.isNull($("#startDate").val(), "起始日期");
					$(this).validate.isNull($("#endDate").val(), "终止日期");
					$(this).validate.isNull($("#discription").val(), "终止日期"); 
					//提交表单
					$(this).validate.submin_form();
				} catch (e) {
					openErrorDialog(e,true,false,{time:3,title:"3秒后自动关闭"});
				}
		});	
				
	});
	
</script>
</html>