<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>检测站绿色通道预约</title>
<style type="text/css">
	.title{height: 33px;border: 1px solid #c1d3de;width: 100%;background: url("/pmot/static/backend/xtbg/images/righttitlebig.png");}
	.title h2{margin-left: 7px;padding-left: 22px;font-weight: bold;font-size: 14px;color: #000000;float: left;padding-top: 0px;margin-top: 6px;background: url("/mot/static/backend/xtbg/images/titleico.png")no-repeat left center;}
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
		padding: 10px 50px 10px 50px;
	}
</style>
</head>

<body>
	<div class="title">
		<h2>绿色通道预约成功</h2>
	</div>
	
	<div id="info">
		<h3>恭喜您，预约成功！</h3>
		<div><span>号牌号码：</span>${bookInfo.platNumber}</div>
		<div><span>验证码：</span>${bookInfo.verifyCode}</div>
		<div><span>预约号码：</span>${bookInfo.bookNumber}</div>
		<div><span>申办业务：</span>机动车年审预约</div>
		<div><span>预约日期：</span>${bookInfo.bookDate}</div> 		
	</div>
	
</body>

</html>
