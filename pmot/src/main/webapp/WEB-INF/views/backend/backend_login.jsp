<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${appName }-后台用户登录</title>
<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
<link href="${ctx }/static/backend/css/reset_login.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/static/backend/css/style_login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	$(function(){
		var message = $("#errorMessage").val();
		if(message !=null && $.trim(message)!=''){
			$.dialog({
	    		title: '3秒后自动关闭',
	    	    time: 3,
	    	    content: message,
	    	    icon: 'error',
	    	    yesFn: true
	    	});
		}
		
		$('#loginBtn').bind('click',function(){
			try{
		    	$(this).validate.isNull($('#account').val(),"请输入用户名!");
		    	$(this).validate.isNull($('#password').val(),"请输入用户密码!");
		    	document.forms[0].submit();
		    }catch(e){
		    	$.dialog({
		    		title: '3秒后自动关闭',
		    	    time: 3,
		    	    content: e,
		    	    icon: 'error',
		    	    yesFn: true
		    	});
		    }
		    return false;
		});
		
		$("#password").keydown(function(e){
			if(e.which == 13){
				var account = $("#account").val();
				if(!account){
					$(".accountMsg").show();
					$("#account").focus();
					return;
				}
				
				var password = $("#password").val();
				if(!password){
					$(".passwordMsg").show();
					return;
				}
				document.forms[0].submit();
			}
		});
		
		$("#account").bind("keydown click",function(){
			$(".accountMsg").hide();
		});
		$("#password").bind("keydown click",function(e){
			if(e.which != 13){
				$(".passwordMsg").hide();
			}
		});
	});

	if (window.parent.length > 0) {
		if("${errorMessage }"!=""){
			window.parent.location = "${ctx}/backend/login?errorMessage=${errorMessage }";
		}else{
			window.parent.location = "${ctx}/backend/login";
		}
	}
</script>
</head>
<body>
<!--头部-->
<div id="top">
<p><img src="${ctx}/static/backend/images/jiantou.png" /> 欢迎登陆${appName }</p>
</div>
<!--头部 END-->
<!--登录界面-->
<div id="content">
	<%-- 错误信息区域 --%>
	<input type="hidden" id="errorMessage" value="${errorMessage }"/>
 <div class="title"><img src="${ctx }/static/backend/images/title.png" alt="${appName }" style="width: 100%;"/></div>
 <div class="denglu">
 <form action="${ctx}/backend/login" method="post" name="loginForm">
  <input class="username" name="account"  id="account" type="text" value="${ account}" />
  <span class="accountMsg">请您输入账号后再登陆</span>
  <input class="password" name="password" id="password" type="password" value="" />
  <span class="passwordMsg">请您输入密码后再登陆</span>
  <input type="button" class="subinput" name="loginBtn" id="loginBtn" src="${ctx }/static/backend/images/login.gif" alt="登陆">
 </form>
 </div>
</div>
<!--登录界面 END-->
<!--底部-->
<div id="foot">
<p>版权所有: 深圳市公安局交通警察局</p>
</div>
<!--底部 END-->
</body>
</html>