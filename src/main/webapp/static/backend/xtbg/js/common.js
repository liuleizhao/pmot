$(document).ready(function(){
	// 解决placeholder兼容性问题
	showRemind("input[type=text], textarea", "placeholder");
	// 去除虚线框（会影响效率）
	$("a").on('focus',function(){$(this).blur();});
	// 消息提示弹窗
	var message = $("#message").val();
	if (message) {
		openSuccessDialog(message,true,false,{time:3,title:"3秒后自动关闭"});
	}
	var errorMessage = $("#errorMessage").val();
	if(errorMessage){
		openErrorDialog(errorMessage,true,false,{time:3,title:"3秒后自动关闭"});
	}
	
	$('.file input[type=file]').change(function(e) {
        $(this).siblings('.text').text($(this).val());
    });
	
	if(!-[1,]){
		$('input[type=radio]').bind('click',function(){
			var name = $(this).attr('name');
			$('input[type=radio]["name="'+ name +']').removeClass('checked');
			if($(this).prop('checked')){
				$(this).addClass('checked');
			}
		});
	}
	if(!!!$('.opt-panel').size() &&　!!!$('.system-switch').size()){
		$(document).click(function(e) {
			$(top.window.document).find('.opt-panel').hide().end().find('.system-switch').hide();
			$(top.window.document).find('.more-info').removeClass('active').end().find('.logo-icon').removeClass('active');
		});
	}
	
	if(!!!$('.more-bab-list').size()){
		$(document).click(function(e) {
			$(top.window.document).find('iframe').contents().find('.more-bab-list').hide();
			$(top.window.document).find('iframe').contents().find('.tab-more').removeClass('active');
		});	
	}
});


function hideElement(currentElement, targetElement, fn) {
	if (!$.isArray(targetElement)) {
		targetElement = [ targetElement ];
	}
	$(document).on("click.hideElement", function(e) {
		var len = 0, $target = $(e.target);
		for (var i = 0, length = targetElement.length; i < length; i++) {
			$.each(targetElement[i], function(j, n) {
				if ($target.is($(n)) || $.contains($(n)[0], $target[0])) {
					len++;
				}
			});
		}
		if ($.contains(currentElement[0], $target[0])) {
			len = 1;
		}
		if (len == 0) {
			currentElement.hide();
			fn && fn(currentElement, targetElement);
		}
	});
};


/*
 *  用来给不支持HTML5 placeholder属性的浏览器增加此功能。
 *  @param element {String or Object} 需要添加placeholder提示的输入框选择器或者输入框jquery对象。
 *  @param defualtCss {String} 提示默认的样式class。
 */

function showRemind(element,defualtCss){
	if(-[1,]){
		return false;
	}

	$(element).each(function(el, i){
		var placeholder = $(this).attr('placeholder');
		if(placeholder){
			$(this).addClass(defualtCss).val(placeholder);
			$(this).focus(function(e){
				if($(this).attr('placeholder') === $(this).val()){
					$(this).val('').removeClass(defualtCss);
				}
			});

			$(this).blur(function(e){
				if($(this).val() === ""){
					$(this).addClass(defualtCss).val($(this).attr('placeholder'));
				}
			});
		}
	});
}

function resize(resizeHandle){
	var d = document.documentElement;
	var timer;//避免resize触发多次,影响性能
	var width = d.clientWidth, height = d.clientHeight;
	$(top.window).on('resize',function(e){
		if((width != d.clientWidth || height != d.clientHeight)){
			width = d.clientWidth, height = d.clientHeight;
			if(timer){clearTimeout(timer);}
			timer = setTimeout(function(){
				resizeHandle();
			},10);	
		}
	});
}

/**
 * 提示弹窗
 * @param title			弹窗标题
 * @param content		提示内容
 * @param icon			图标样式
 * @param yesFunction	确定按钮回调函数（类型为Function和Boolean-为false时不显示按钮）
 * @param noFunction	取消按钮回调函数（类型为Function和Boolean-为false时不显示按钮）
 * @param otherOption	其它选项（类型为Object）
 * @returns
 */
function openDialog(title,content,icon,yesFunction,noFunction,otherOption){
	var obj = {};
	var yesFn = yesFunction ? (yesFunction instanceof Function ? yesFunction : true) : false;
	var noFn = noFunction ? (noFunction instanceof Function ? noFunction : true) : false;
	if(title){
		obj.title = title;
	}
	var baseOption = {
		content : content,
		icon : icon,
		yesFn : yesFn,
		noFn : noFn
	};
	if(otherOption instanceof Object){
		$.extend(obj,otherOption);
	}
	$.extend(baseOption,obj);
	art.dialog(baseOption);
}
function opeanWarningDialog(content,yesFunction,noFunction,otherOption){
	openDialog("温馨提示",content,"warning",yesFunction,noFunction,otherOption);
}
function openSuccessDialog(content,yesFunction,noFunction,otherOption){
	openDialog("",content,"succeed",yesFunction,noFunction,otherOption);
}
function openErrorDialog(content,yesFunction,noFunction,otherOption){
	openDialog("",content,"error",yesFunction,noFunction,otherOption);
}


$(document).click(function(){
	$(".select-list").hide();
})

//时间格式转换
Date.prototype.format = function(fmt) {
	var o = {
		"M+" : this.getMonth()+1, //月份
		"d+" : this.getDate(), //日
		"h+" : this.getHours(), //小时
		"m+" : this.getMinutes(), //分
		"s+" : this.getSeconds(), //秒
		"q+" : Math.floor((this.getMonth()+3)/3), //季度
		"S" : this.getMilliseconds() //毫秒
	};
	if(/(y+)/.test(fmt))
		fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
	for(var k in o)
		if(new RegExp("("+ k +")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
	return fmt;
}