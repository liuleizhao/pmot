/*
 * jQuery Async 1.0.0
 * 异步调用工具类
 */
 
//ref. <script src='../../js/jquery/async/jquery.async.js' type='text/javascript'></script>
(function($) {
	$.extend({
		async: function() {
	        var callbackQueue = [];  
	        this.result = undefined;  
	        this.state = "running";  
	        this.completed = false;  
	 
	        this.yield = function(result, delayMillSeconds) {  
	            var self = this;  
	            setTimeout(function() {  
	                self.result = result;  
	                self.state = "completed";  
	                self.completed = true;  
	 
	                while (callbackQueue.length > 0) {  
	                    var callback = callbackQueue.shift();  
	                    callback(self.result);  
	                }  
	            }, delayMillSeconds||1);  
	            return this;  
	        };  
	 
	        this.addCallback = function(callback) {  
	            callbackQueue.push(callback);  
	            if (this.completed) {  
	                this.yield(this.result);  
	            }  
	            return this;  
	        };  
		},
		//封装简单的异步调用,该方法返回多实例的异步对象
		asyncInvoke: function(callback, result, delayMillSeconds) { 
			var $async = new $.async();
			$async.addCallback(callback);
			$async.yield(result, delayMillSeconds);
			return $async;
		}
	});
})(jQuery);

//$(document).ready(function() {
//	//demo
//	var $async = new $.async();
//	alert('000000');
//	$async.addCallback(function(){
//		alert('111111');
//	})
//	.addCallback(function(){
//		alert('222222');
//	});
//	$async.yield('hello world');
//	alert('3333333');
//	alert('4444444');
//	alert('5555555');
//});