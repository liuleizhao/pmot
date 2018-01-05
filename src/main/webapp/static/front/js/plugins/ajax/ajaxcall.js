/**
 * ajax统一请求工具类
 */
(function($) {  
	jQuery.fn.extend({
		//interval is for million seconds, times=-1 mean never stop
		ajaxRequest: function(interval, times, callback, url, urlParams, timerLabel, ajaxType) {
			$(this).everyTime(interval, timerLabel, function(i) {
				jQuery.extendedAjax.asyncAjax(url, function(responseText){
					callback.call(this, $.formatJSONMessage(responseText));
				}, urlParams, ajaxType);
			}, times);
		},
		//if timerLabel is undefined, then stop all timers started from function 'ajaxRequest()'
		stopAllAjaxRequest: function(callback, timerLabel) {
			jQuery.each(jQuery.timer.global, function(index, item) {
				jQuery.timer.remove(item);
			});
		},
		/*
		 * invoke as: 
			$(this).syncAjaxRequest(function(jsonMessage){
				if (jsonMessage.status == 'success') {
					//do something using jsonMessage.result
				} else {
					alert("获取xx数据失败!" + jsonMessage.msg);
				}
			}, 'url address');*/		
		asyncAjaxRequest: function(callback, url, urlParams, ajaxType) {
			jQuery.extendedAjax.asyncAjax(url, function(responseText) {
				callback.call(this, $.formatJSONMessage(responseText));
			}, urlParams, ajaxType);
		},
		syncAjaxRequest: function(callback, url, urlParams, ajaxType) {
			jQuery.extendedAjax.syncAjax(url, function(responseText) {
				callback.call(this, $.formatJSONMessage(responseText));
			}, urlParams, ajaxType);
		},
		//ajax request from different domain
		getJSONFromOtherDomain: function( callback, url, data ) {
			return $.getJSON(url + "?callback=?", data, callback);
		}
	});
	
	jQuery.extend({
		//格式化json格式的交互消息
		formatJSONMessage: function(jsonData) {
			//json格式消息与响应的JSONMessage对象保持一致, status: 成功-success, 失败-fail
			var jsonMessage = {status:"success",code:'0',msg:'',result:{}};
			try {
				var result = eval("(" + jsonData + ")");
				//对不规范响应内容进行处理
				if (result != undefined && result.status == undefined) {
					jsonMessage.stauts = "success";
					jsonMessage.code = "0";
					jsonMessage.msg = "";
					jsonMessage.result = result;
				}  else if (result != undefined) {
					jsonMessage = result;
				}
				
				if(result.code == 9){
					$.dialog.open('/login.action', {title: '用户登录', width: 860, height: 500});
					jsonMessage = {status:"fail",code:'0',msg:'请登录!',result:{}};
				} else if(result.code == 8){
					$.dialog({icon: 'face-sad', content: result.msg});
					jsonMessage = {status:"fail",code:'0',msg:'你没有权限!',result:{}};
				}
			} catch(err) {
				try {
					if (typeof(JSON) == 'object' && JSON.parse) {
        				var result = JSON.parse(jsonData);
    					//对不规范响应内容进行处理
						if (result != undefined && result.status == undefined) {
							jsonMessage.stauts = "success";
							jsonMessage.code = "0";
							jsonMessage.msg = "";
							jsonMessage.result = result;
						}  else if (result != undefined) {
							jsonMessage = result;
						}
					} else {
						jsonMessage = {status:"fail",code:'0',msg:'响应的内容语法错误!',result:{}};
					}
				} catch(err) {
					jsonMessage = {status:"fail",code:'0',msg:err.description||'',result:{}};
				}
			}
			return jsonMessage || {status:"fail",code:'0',msg:'',result:{}};
		},
		extendedAjax: {
			asyncAjax: function(url, callback, data, type) {
				$.ajax({
				    url: (jsCtx || '')+url,
				    data: data,
				    cache : false, 
				    async : true,
				    timeout: 20000, //default20000毫秒
				    type : type || "POST",
				    dataType : 'json/xml/html',
				    success : callback,
				    error: function(data) {
//						alert('由于网络原因,以致未能成功处理异步ajax请求!');
				    }
				});		
			},
			syncAjax: function(url, callback, data, type) {
				$.ajax({
				    url: (jsCtx || '')+url,
				    data: data,
				    cache : false, 
				    async : false,
				    timeout: 20000, //default20000毫秒
				    type : type || "POST",
				    dataType : 'json/xml/html',
				    success : callback,
				    error: function(data) {
//						alert('由于网络原因,以致未能成功处理同步ajax请求!');
				    }
				});		
			}
		}
	});
})(jQuery);