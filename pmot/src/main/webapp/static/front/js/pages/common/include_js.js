var jsfileversion="1.0";  //用于消除ie缓存
var scriptOptions={};
var source=[];
var jsCtx = "";//上下文路径
//parse arguments
var parseJsArgument = function() {
	var scripts = document.getElementsByTagName("script");
	for(var i=0; i<scripts.length; i++) {
		var src = scripts[i].src;
		if(src && src.match(/include_js\.js/)) {
			if(src.indexOf('?') != -1) {
				var strStr = src.split('?')[1];
				var paramArr = strStr.split("&");
				for(var i=0; i<paramArr.length; i++){
					if(paramArr[i].indexOf("=") != -1){
						var tmp = paramArr[i].split("=");
						scriptOptions[tmp[0]] = tmp[1];
					}else{
						scriptOptions[paramArr[i]] = 1;
					}
				}
				
//				var srcOptions = src.split('?')[1].replace(new RegExp("%22","gm"),"\"").replace(new RegExp("%27","gm"),"\'");
//				try {
//					scriptOptions = eval("("+srcOptions+")");
//				} catch (err) {
//					if (typeof(JSON) == 'object' && JSON.parse) {
//        				scriptOptions = JSON.parse(srcOptions);
//					}
//				}
			}
		}
	}
}

parseJsArgument();
jsCtx = scriptOptions.ctx || ""; //赋值上下文路径

//引入常用脚本
source[source.length]="/js/plugins/jquery/jquery-1.9.1.min.js";
source[source.length]="/js/pages/common/common.js";
source[source.length] = "/js/plugins/validate/jquery.jsvalidate.js";
source[source.length]="/js/pages/common/validate_tool.js";  //验证工具类
source[source.length]="/js/plugins/ajax/ajaxcall.js";  //ajax统一请求工具类
source[source.length]="/js/plugins/ajax/jquery.form.js";  //ajax表单提交工具类
//source[source.length]="/js/plugins/ajax/jquery.async.js";  //ajax异步调用工具类
source[source.length]="/js/plugins/urlQuery/urlQuery.js";  //js文件内获取url参数工具类
source[source.length]="/js/plugins/json/jquery.json-2.2.min.js";  //json工具类
//function define here
var write = function(src, encode) {
    document.write('<script type="text/javascript" src="'+jsCtx+src+'" charset="'+(encode||"UTF-8")+'"></script>');
}

//消除ie缓存
if (typeof scriptOptions.jsfileversion != 'undefined' && scriptOptions.jsfileversion != jsfileversion) {
	document.write('<meta http-equiv="pragma" content="no-cache">');
	document.write('<meta http-equiv="cache-control" content="no-cache">');
	document.write('<meta http-equiv="expires" content="0">');
}

//引入提示消息框脚本
if (typeof scriptOptions.webdialog != 'undefined') {
	source[source.length]="/js/plugins/webdialog/webDialog.js";
}

if (typeof scriptOptions.artDialog != 'undefined') {
	source[source.length]="/js/plugins/artDialog/jquery.artDialog.min.js";
	source[source.length]="/js/plugins/artDialog/artDialog.iframeTools.min.js";
}

//引入thichbox弹出页面脚本
if (typeof scriptOptions.thickbox != 'undefined') {
	source[source.length]="/js/plugins/thickbox/thickbox.js";
}

//引入列表页面公共操作脚本
if (typeof scriptOptions.pageList != 'undefined') {
	source[source.length]="/js/pages/common/public_pages_list.js";
}

//引入zTree树形结构脚本
if (typeof scriptOptions.zTree != 'undefined') {
	source[source.length]="/js/plugins/zTree/jquery.ztree-2.6.min.js";
	
	source[source.length]="/js/pages/myZtree/base/lottery.base.min.js";
	source[source.length]="/js/pages/myZtree/base/zTree.controller.js";
}

//引入cookie工具类
if (typeof scriptOptions.cookie != 'undefined') {
	source[source.length]="/js/plugins/cookie/jquery.cookie.js";
}

//引入首页工具类
if (typeof scriptOptions.home != 'undefined') {
	source[source.length]="/js/pages/home/YlMarquee.js";
	source[source.length]="/js/pages/home/silder.js";
	source[source.length]="/js/pages/home/jquery.soChange.js";
	source[source.length]="/js/pages/home/checkMessage.js";
}

//在需要日历的页面引入插件
if (typeof scriptOptions.calendar != 'undefined') {
	source[source.length]="/js/plugins/My97DatePicker/WdatePicker.js";
}

//在预录入页面引入预录入JS
if (typeof scriptOptions.record != 'undefined') {
	source[source.length]="/js/pages/pilot_record.js";
}

//添加遮罩插件
if (typeof scriptOptions.blockUI != 'undefined') {
	source[source.length]="/js/plugins/blockUI/jquery.blockUI.min.js";
}


//写入脚本
for (var index = 0; index < source.length; index++) {
	this.write(source[index]);	
}
