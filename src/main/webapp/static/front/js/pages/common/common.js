
// 页面跳转
function gotoPage(cpath,curl){
	window.location.href = cpath + '/qc/' + curl;
}

/** 
 * 检查浏览器(0--IE6以下;1--IE6及以上;2--Netscape、火狐)
 */
function returnBrowserCheckValue() {
	var app = navigator.appName;
	var verStr = navigator.appVersion; 
	//火狐浏览器 
	if (app.indexOf("Netscape") != -1) {
		return 2;
	} else {
		if (app.indexOf("Microsoft") != -1) {
			if (verStr.indexOf("MSIE 3.0") != -1 || verStr.indexOf("MSIE 4.0") != -1 || verStr.indexOf("MSIE 5.0") != -1 || verStr.indexOf("MSIE 5.1") != -1) {
				return 0;
			} else {
				return 1;
			}
		}
	}
}

/** 
 * 去掉字串两边的空格
 */
function trim(str) {
	return lTrim(rTrim(str));
}

/** 
 * 去掉字串左边的空格 
 */
function lTrim(str) { 
	//如果字串左边第一个字符为空格
	if (str.charAt(0) == " ") {
		str = str.slice(1); //将空格从字串中去掉 
		str = lTrim(str); //递归调用  
	}
	return str;
}

/** 
 * 去掉字串左边的空格 
 */
function rTrim(str) { 
	var iLength=str.length; 
	//如果字串右边第一个字符为空格
	 if (str.charAt(iLength - 1) == " ") {
		str = str.slice(0, iLength - 1); //将空格从字串中去
		str = rTrim(str); //递归调用
	}
	return str;
}


/**
 * 系统脚本公用变量设置
 */
var PUBLIC_PROMT_LIVE_SECONDE=3;//提示框存活时间
var PUBLIC_EXCEPTION="系统访问出现异常，请稍后重试";//提示框存活时间

/** 
 * 公共函数定义
 */
( function($) {
	$.book = $.book || {};//定义命名空间
	$.book.common={
			
		/** 
		 * 检查浏览器(0--IE6以下;1--IE6及以上;2--Netscape、火狐)
		 */	
		"returnBrowserCheckValue": function(){
			var app = navigator.appName;
			var verStr = navigator.appVersion; 
			//火狐浏览器 
			if (app.indexOf("Netscape") != -1) {
				return 2;
			} else {
				if (app.indexOf("Microsoft") != -1) {
					if (verStr.indexOf("MSIE 3.0") != -1 || verStr.indexOf("MSIE 4.0") != -1 || verStr.indexOf("MSIE 5.0") != -1 || verStr.indexOf("MSIE 5.1") != -1) {
						return 0;
					} else {
						return 1;
					}
				}
			}
		},
		
		/** 
		 * 检查字符串是否为空
		 */
	    "isBlank": function(str){
			if(null==str || ""==$.book.common.trim(str)){
				return true;
			}
			return false;
		},
	
		/** 
		 * 去掉字串两边的空格
		 */
	    "trim": function(str){
			return $.book.common.lTrim($.book.common.rTrim(str));
		},
		
		/** 
		 * 去掉字串左边的空格
		 */
	    "lTrim": function(str){
			//如果字串左边第一个字符为空格
			if (str.charAt(0) == " ") {
				str = str.slice(1); //将空格从字串中去掉 
				str = $.book.common.lTrim(str); //递归调用  
			}
			return str;
		},
		
		/** 
		 * 去掉字串右边的空格
		 */
	    "rTrim": function(str){
			var iLength=str.length; 
			//如果字串右边第一个字符为空格
			 if (str.charAt(iLength - 1) == " ") {
				str = str.slice(0, iLength - 1); //将空格从字串中去
				str = $.book.common.rTrim(str); //递归调用
			}
			return str;
		},
		
		/**
		 * 提示信息框(前提条件：提示信息非空)
		 */
		"artDialog": function(msg,icon){
			$.dialog({
					title: PUBLIC_PROMT_LIVE_SECONDE+"秒后自动关闭",
				    time: PUBLIC_PROMT_LIVE_SECONDE,
				    content: msg==null || msg=="" || (msg+"").indexOf("Error")!=-1?PUBLIC_EXCEPTION:msg,
				    icon: icon,
				    yesFn: true
				});
		}
	}
})(jQuery);


