<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${appName }-批量新增默认预约日期维护</title>
<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/mui.min.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-reg.css" />
<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
<link rel="stylesheet" href="${ctx}/static/backend/css/backend_common.css" />
</head>
<body>
	<%@include file="../common/message.jsp" %>
	<div class="title">
		<h2>批量新增默认预约日期维护</h2>
	</div>
	<c:forEach var="existDetail" items="${existDetails }">
		<input value="${existDetail.interfaceId}" class="existInterId" type="hidden"/>
	</c:forEach>
	<form class="classA mui-input-group" action="${ctx }/backend/webservice/interfaceControlGeneral/editDetail" method="post" name="form" id="form">
	<input value="${generalId}" type="hidden" name="generalId"/>
	<input value="0" type="hidden" name="isUpdate" id="isUpdate"/>
		<table cellpadding="8" cellspacing="1">
			<tr>
				<td class="field"><span class="txt-imp">*</span>接口
				<br>
				<input type="checkbox" id="all" onchange="swapCheck();" style="cursor:pointer;"/><label>全选</label>
				</td>
				<td class="text" colspan="3">
					<c:forEach items="${allInterfaces}" var="inter" varStatus="statu">
						<input style="width: 30px;" height="60px;" type="checkbox" name="interIds" value="${inter.id}"/>
						<label>${inter.jkid}:&nbsp;${inter.name}</label>
						<c:if test="${statu.count % 4 == 0}">
							<br>
						</c:if>
					</c:forEach>
				</td>
			</tr>
			
		</table>
		
		<div class="mui-button-row">
			<button type="button" id="addBtn" class="mui-btn mui-btn-green">添加</button>&nbsp;&nbsp;
			<button type="button" class="mui-btn" style="background: #E6F4FF;"  onClick="javascript:history.back(-1);">返回</button>
		</div>
	</form>
</body>
<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
		
<script type="text/javascript">
	var isCheckAll = false;  
        function swapCheck() {  
            if (isCheckAll) {  
                $("input[type='checkbox'][name='interIds']").each(function() {  
                    this.checked = false;  
                });  
                isCheckAll = false;  
            } else {  
                $("input[type='checkbox'][name='interIds']").each(function() {  
                    this.checked = true;  
                });  
                isCheckAll = true;  
            }  
        }  
        
	$(function() {
		var existInterIds = $(".existInterId");
		if(typeof(existInterIds) != 'undefined' && existInterIds.length >0){
			//标记为更新
			$("#isUpdate").val("1");
			for(var i = 0;i < existInterIds.length;i++){
				$("input[type='checkbox'][name='interIds']").each(function() {  
                   if(this.value ==  existInterIds.eq(i).val()){
                   		this.checked = true;  
                   }  
               	}); 
			}
		 
		}
		$("#addBtn").click(function() {
				try {
					var interIdLength = $("input[type='checkbox'][name='interIds']:checked").length;
					if(interIdLength <= 0){
						throw ("请至少选择一个接口！");
					}
					//提交表单
					$(this).validate.submin_form();
				} catch (e) {
					openErrorDialog(e,true,false,{time:3,title:"3秒后自动关闭"});
				}
			});
	});
</script>
</html>
