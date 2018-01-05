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
	    	$(this).validate.isNull($("#verifycode").val(),"请输入验证码!");
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
});
