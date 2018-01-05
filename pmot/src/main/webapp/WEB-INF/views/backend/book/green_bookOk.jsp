<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>检测站绿色通道预约</title>
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/mui.min.css" />
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-reg.css" />
<style type="text/css">
	h3{
		text-align: center;
	    font-size: 30px;
	    background: url(/pmot/static/backend/images/ok.png) no-repeat 2px;
	    height: 64px;
	    line-height: 64px;
	    margin-bottom: 30px;
	}
	#info{
		padding-top:15px;
		width:500px;
		margin:0 auto;
		margin-top:30px; 
		font-size:14px;
	}
	#info div{
		margin-top:15px;
	}
	#info span{
		width: 87px;
   		display: inline-block;
	}
	#info input{
		border: 0px;
	}
</style>
</head>

<body>
	<div class="title">
		<h2>绿色通道预约成功</h2>
	</div>
	
	<div id="info">
		<h3>恭喜您，预约成功！</h3>
<%-- 		<div><span>号牌号码：</span>${bookInfo.platNumber}</div>
		<div><span>验证码：</span>${bookInfo.verifyCode}</div>
		<div><span>预约号码：</span>${bookInfo.bookNumber}</div>
		<div><span>申办业务：</span>机动车年审预约</div>
		<div><span>预约日期：</span>${bookInfo.bookDate}</div> --%>
		<c:if test="${not empty bookInfo.platNumber}">
			<div><span>号牌号码：</span><input value="${bookInfo.platNumber}"></div>
		</c:if>
		
		<div><span>验证码：</span><input value="${bookInfo.verifyCode}"></div>
		<div><span>预约号码：</span><input value="${bookInfo.bookNumber}"></div>
		<div><span>申办业务：</span><input value="机动车年审预约"></div>
		<div><span>预约日期：</span><input value="${bookInfo.bookDate}"></div>
	</div>
	
</body>

</html>
