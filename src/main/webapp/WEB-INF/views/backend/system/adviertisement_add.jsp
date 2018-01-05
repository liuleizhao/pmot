<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>深圳市机动车年审检测预约系统-新增广告</title>
<%@ include file="/static/common/meta.jsp"%>
<link href="${ctx }/static/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/static/css/style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
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
	
	
	
	
	
		$("#addBtn").click(
				function() {
					try {
					
						$(this).validate.isNull($('#brand').val(),'广告品牌不能为空');
						$(this).validate.isNull($('#effectivedate').val(),'生效日期不能为空');
						$(this).validate.isNull($('#expirationdate').val(),'结束日期不能为空');
						$(this).validate.isNull($('#signatory').val(),'签约人不能为空');
						$(this).validate.isNull($('#contractno').val(),'合同编号不能为空');
						//提交表单
						$(this).validate.submin_form();
		
					} catch (e) {
						$.dialog({
							title : '3秒后自动关闭',
							time : 3,
							content : e,
							icon : 'error',
							yesFn : true
						});
					}
				});
				
		//ajax验证类型code不能相同
		/* $("#name").bind('blur', function(e) {
			var a = $(this).val();
			$.ajax({
				url : '${ctx}/backend/system/product_info/checkProduct',
				data : {
					'name' : a
				},
				success : function(data) {
					var isNotBlank = data.isNotBlank;
					var isExist = data.isExist;
					if (isNotBlank && isExist) {
						$(".check_code_class").css('color', 'red');
						$("#check_code_exist").html("该广告名称已存在");
					} else if (!isNotBlank) {
						$(".check_code_class").css('color', 'red');
						$("#check_code_exist").html("请输入广告名称");
					} else {
						$(".check_code_class").css('color', 'green');
						$("#check_code_exist").html("广告名称可以使用");

					}
				}
			});
		});		
			 */	
	
	var start = {
				  elem: '#startDate',
				  format: 'YYYY-MM-DD',
				  min: '1970-01-01 00:00:01', //设定最小日期为当前日期
				  max: '2020-12-31', //最大日期
				  istime: true,
				  istoday: false,
				  choose: function(datas){
				     end.min = datas; //开始日选好后，重置结束日的最小日期
				     end.start = datas //将结束日的初始值设定为开始日
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
	
	})


	
</script>

</head>
<body>
<div class="position">位置：首页 > 广告管理 > 新增广告</div>
		<div class="list-page">
			<div class="box">
				
				<div class="box-body-edit">
					<input type="hidden" id="mess" value="${message }" />
					<form action="${ctx }/backend/system/adviertisement/add" method="post" name="form" id="form">
					
					<div class="box-title">
					<h4>添加广告基本信息</h4>
					</div>
						<table width="100%" border="0" class="yuyue-edit" align="center">
						
						
								<tr>
								<td class="col-sm-1 control-label"><label class="requiredInput">*</label>所属产品:</td>
								<td class="col-sm-2">
								
									<input type="hidden" name="productId" id="productId" value="${productId}"/>
									<input class="form-control" name="productName" id="productName" type="text" value="${productName}" readonly="readonly" />
									<%-- <select class="form-control" name="productId" id="productId">
										<option value="">请选择</option>
										<c:forEach items="${productlist}" var="product">
											<option value="${product.id }">${product.name}</option>
										</c:forEach>
									</select> --%>
								</td>
								<td class="col-sm-1 control-label"><label class="requiredInput">*</label>签约人:</td>
								<td class="col-sm-2">
								<select class="form-control" name="signatory" id="signatory">
										<option value="">请选择</option>
										<c:forEach items="${userList}" var="user">
											<option value="${user.id }">${user.name}</option>
										</c:forEach>
									</select>
								
								</td>
								<td class="col-sm-1 control-label"><label class="requiredInput">*</label>合同编号:</td>
								<td class="col-sm-2"><input class="form-control" name="contractno" id="contractno" type="text" /></td>
								<td class="col-sm-3"></td>
							</tr>
							<tr>
								<td class="col-sm-1 control-label"><label class="requiredInput">*</label>广告品牌:</td>
								<td class="col-sm-2"><input class="form-control" name="brand" id="brand" type="text" /></td>
								<td class="col-sm-1 control-label"><label class="requiredInput">*</label>生效日期:</td>
								<td class="col-sm-2"><input class="form-control" name="effectivedate" id="effectivedate" type="text"/></td>
								<td class="col-sm-1 control-label"><label class="requiredInput">*</label>结束日期:</td>
								<td class="col-sm-2"><input class="form-control" name="expirationdate" id="expirationdate" type="text"/></td>
								<td class="col-sm-3"></td>
							</tr>
						
							
						</table>
						
						<div class="box-title">
							<h4>科目金额配置</h4>
						</div>
						<table width="100%" border="0" class="yuyue-edit" align="center">
							<c:forEach items="${pdpjRelationList}" var="pdpjRelation">
								<tr>
								<td class="col-sm-1 control-label"><label class="requiredInput">*</label>${pdpjRelation.projectInfo.name}</td>
								<td class="col-sm-2">
								
								<input type="hidden" name="projectId" value="${pdpjRelation.projectInfo.id}">
								<input class="form-control" name="price" type="text" value="${pdpjRelation.projectInfo.price}" />
								</td>
								<td class="col-sm-1 control-label"><label class="requiredInput">*</label>金额类型</td>
								<td class="col-sm-1">
								
								<input type="hidden" name="pricetype" value="${pdpjRelation.projectInfo.pricetype.id}" />
								
								<input class="form-control" name="pricetypedesc" type="text" value="${pdpjRelation.projectInfo.pricetype.description}" readonly = "readonly" />
								</td>
								<td class="col-sm-1">
								<td class="col-sm-1">
								<td class="col-sm-2">
								<td class="col-sm-3">
								</tr>
							</c:forEach>
						</table>
				<div align="center">
						<button class="button blue" id="addBtn" type="button">新增</button>&nbsp;&nbsp;&nbsp;&nbsp;
						<button class="button orange" onClick="javascript:history.back(-1);" type="button"><i class="fa fa-search"></i> 返回</button>
				</div>			
					</form>
				</div>
			</div>
		</div>
</body>
</html>