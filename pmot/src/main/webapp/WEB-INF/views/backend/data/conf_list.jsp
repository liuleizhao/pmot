<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>导出列表</title>
	<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
	<link rel="stylesheet" href="${ctx}/static/backend/css/backend_common.css" />
</head>
<body>
	<div class="title">
		<h2>导出列表</h2>
	</div>
	<div class="main">
		<div class="context">
			<form  action="${ctx }/backend/data/conf/list" method="get" name="form" id="form">
				<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}" />
				<div class="query">
					<div class="query-conditions ue-clear">
						<div class="conditions staff ue-clear i_div">
							<label>表名：</label> <input name="tableName" value="${tableName }" type="text" />
						</div>
						<div class="conditions staff ue-clear i_div">
							<label>状态：</label> 
							<select name="state" id="state" class="q_select">
								<option value="">所有</option>
								<c:forEach items="${stateList }" var="entity">
									<option value="${entity }"<c:if test="${entity eq state}">selected="selected"</c:if>>${entity.description }</option>
								</c:forEach>
							</select>
						</div>
						<div class="conditions name ue-clear">
							<input id="queryBtn" type="button" value="查询" class="input_button" /> 
							<input onclick="javascript:window.location.href='${ctx }/backend/data/conf/add'" type="button" value="新增" class="input_button" />
						</div>
					</div>
				</div>
			</form>
			<div class="table_box">
				<table id="liebiaocolor">
					<thead>
						<tr>
							<th class="length3">表名</th>
							<th class="length3">状态</th>
							<th class="length3">创建时间</th>
							<th class="length3">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageView.list }" var="item" varStatus="status">
							<tr id="${item.id }">
								<td class="length3">${item.tableName }</td>
								<td class="length3">${item.state.description }</td>
								<td class="length3"><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td class="length3">
									<a class="mui-btn-blue" href="${ctx}/backend/data/conf/edit?id=${item.id}">编辑</a>&nbsp; &nbsp; 
									<a class="mui-btn-blue" href="javascript:del('${item.id }')">删除</a> 
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<%@ include file="/WEB-INF/views/backend/common/pagination.jsp"%>
		</div>
	</div>
	<!-- 将common.js放置在最后 -->
	<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
	<script type="text/javascript" src="${ctx }/static/backend/js/artDialog/jquery.artDialog.min.js"></script>
	<script type="text/javascript">
		$("tbody").find("tr:odd").css("backgroundColor", "#eff6fa");
		$(function() {
			init()
			$("#queryBtn").click(function() {
				$("#currentPage").attr("value", "1");
				document.forms[0].submit();
			});
		});
		
		function init(){
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
		
		function del(id){
				$.dialog({
					id:"del",
					title : "删除！",
					Maxheight: "400",
					lock: true,
					background: '#000', // 背景色
   					opacity: 0.6,	// 透明度
					content :"是否确认删除该记录！",
					yesFn : function(){
						create_form(id);
					}
				});
			}
		function create_form(id){
			var form1 = document.createElement("form");  
		    //form1.id = "form1";  
		   	//form1.name = "form1";  
		    document.body.appendChild(form1); // 添加到 body 中  
		    var input = document.createElement("input");  // 创建一个输入  
		    // 设置相应参数  
		    input.type = "text";  
		    input.name = "id";
		    input.value = id;  
		    form1.appendChild(input);  // 将该输入框插入到 form 中  
		    form1.method = "POST"; // form 的提交方式   
		    form1.action = "${ctx}/backend/data/conf/del"; // form 提交路径  
		    form1.submit();   // 对该 form 执行提交  
		    document.body.removeChild(form1);  // 删除该 form  
		}	
	</script>
</body>
</html>