<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${appName }-查看系统参数</title>
<%@ include file="/static/common/meta.jsp"%>
<link href="${ctx }/static/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/static/css/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<BODY>
		<div class="position">位置：首页 > 参数管理 > 查看系统参数</div>
		<div class="list-page">
			<div class="box">
				<div class="box-title">
					<h4>查看系统参数基本信息</h4>
				</div>
				<div class="box-body-edit">
					<input type="hidden" id="mess" value="${message }" />
					<form action="${ctx }/backend/argument/sys_arg_value/edit" method="post" name="form" id="form">
					<input type="hidden" name="id" value="${sysArgValueVO.id }" id="sysArgValueId"/>
						<table width="100%" border="0" class="yuyue-edit" align="center">
							<tr>
								<td class="col-sm-1 control-label"><label class="requiredInput">*</label>参数值代码:</td>
								<td class="col-sm-3">
								<input class="form-control" name="code" id="code" type="text"  value="${sysArgValueVO.code }" readonly="readonly"/>
									<font id="check_code_exist" class="check_code_class"></font>
								</td>
								<td class="col-sm-1 control-label"><label class="requiredInput">*</label>参数名称:</td>
								<td class="col-sm-3"><input class="form-control" name="name" id="name" type="text" value="${sysArgValueVO.name }" readonly="readonly"/>
									<font id="check_name_exist" class="check_name_class"></font>
								</td>
								<td class="col-sm-1 control-label">参数值:</td>
								 <td class="col-sm-3"><input class="form-control" name="value" id="value" type="text" value="${sysArgValueVO.value }" readonly="readonly"/></td>
							</tr>
							<tr>
								<td class="col-sm-1 control-label">是否可修改:</td>
								<td class="col-sm-3">
							 		<select name="isUpdate" id="isUpdate" class="form-control" disabled="disabled">
										<c:forEach items="${isUpdateList }" var="entity">
											<option value="${entity.value }"  <c:if test="${sysArgValueVO.isUpdate.value eq entity.value }">selected="selected"</c:if> >${entity.description}</option>
										</c:forEach>
									</select>
								</td>
								<td class="col-sm-1 control-label"><label class="requiredInput">*</label>排列顺序:</td>
								<td class="col-sm-3">
								<input class="form-control" name="orderNum" id="orderNum" type="text" value="${sysArgValueVO.orderNum }" readonly="readonly"/>
								</td>
								<td class="col-sm-1 control-label">描述:</td>
								<td class="col-sm-3">
								<input class="form-control" name="description" id="description" type="text" value="${sysArgValueVO.description }" readonly="readonly"/>
								</td>
									<td colspan="2"></td>
							</tr>
							<tr>
								<td class="col-sm-12 control-label" colspan="6">
									<button class="button orange" onClick="javascript:history.back(-1);" type="button"><i class="fa fa-search"></i> 返回</button>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
</body>
</html>