(function($) {
	/*
	 * js校验框架插件,e.g.
	$(this).jsvalidate.load({
		rules: {
			account: {
				required:true, 
				min:10,
				max:100
			}
		},
		messages: {
			account: {
				required: '账号不可为空!',
				range: '值的范围应为{0}到{1}'
			}
		},
		//先找ruleName+'_check',如该方法不存在则找options.check方法,都不存在则执行自带的check方法
		account_check: function(actValue) {
			alert('execute account_check:' + actValue);
		},
		check: function(actValue, ruleName) {
			alert('execute options.check');
		},
		error: function(actValue, ruleName, checkResult) {
			alert($.jsvalidator.formatMessage(checkResult, -1));
		},
		success: function(actValue, ruleName, checkResult) {
			alert('success');
		},
		checkMethods: {
			required: function(actValue, expect) {
				alert('自定义的required method');
				return actValue!=undefined;
			}
		}
	});
	$(this).jsvalidate.valid('account', 1);  //调用例子
	*/
	$.extend($.fn,{
		jsvalidate: {
			options: {},
			/**
			 * 初始化校验参数
			 */
			load: function(options) {
				// check if a validator for this form was already created
				if ($(this).data('jsvalidator')) {
					return $(this).data('jsvalidator');
				}
				$(this).data('jsvalidator', this);
				this.options = $.extend(true, {}, $.jsvalidator.defaults, options);
				for (var method in (options.checkMethods||{})) {
					this.addMethod(method, options.checkMethods[method]);
				}
				return this;
			},
			/**
			 * 开始执行校验
			 * ruleName为rules里面的rule的名称
			 * actValue为用来校验的真正的值
			 * execError, true-校验不通过时执行处理, false-不执行
			 * execSuccess, true-校验通过时执行处理, false-不执行
			 */
			valid: function(ruleName, actValue, execError, execSuccess) {
				execError = (typeof execError != 'boolean')?true:execError;
				execSuccess = (typeof execSuccess != 'boolean')?true:execSuccess;
				var thisOptions = $(this).jsvalidate.options;
				var rule = thisOptions.rules[ruleName];
				if (typeof rule == 'undefined') return false;
				//合并范围校验的check
				if (rule.min && rule.max) {
					rule.range = [rule.min, rule.max];
					delete rule.min;
					delete rule.max;
				}
				if (rule.minlength && rule.maxlength) {
					rule.rangelength = [rule.minlength, rule.maxlength];
					delete rule.minlength;
					delete rule.maxlength;
				}	
				var checkClass = thisOptions[ruleName+'_check'] || thisOptions.check || this.check;
				var checkResult = checkClass(actValue, ruleName);
				if (checkResult.ok && execSuccess) {
					thisOptions.success.call(this, actValue, ruleName, checkResult);
				} else if (!checkResult.ok && execError) {
					thisOptions.error.call(this, actValue, ruleName, checkResult);
				}
				return checkResult.ok;
			},
			//默认的对每个rule的check方法,返回{ok:[true|false], msg: '', errorList:errorList:[{'checkMethod':'required','message':'','params':[1,2,3]}]}
			check: function(actValue, ruleName) {
				var checkResult = {ok:false,msg:'',errorList:[]};
				var thisOptions = $(this).jsvalidate.options;
				var rule = thisOptions.rules[ruleName];
				if (typeof rule == 'undefined') {
					checkResult.ok = false;
					checkResult.msg = "还没有定义该校验规则:"+ruleName;
					return checkResult;
				}
				for(checkMethod in rule) {
					//如果待校验的选项可以为空，并且确实没有输入值，则不用对其他规则做验证
					if(!(rule["required"] || false)){
						if($.trim(actValue) == ""){
							break;
						}
					}
					//只要有一个check没通过则该rule校验不通过
					if (!thisOptions.methods[checkMethod].call(this, actValue, rule[checkMethod])) {
						checkResult.ok = false;
						checkResult.msg = "还没有定义该校验的规则:"+ruleName;  //errorList长度<1时才使用checkResult.msg的值
						var message = (thisOptions.messages[ruleName])?thisOptions.messages[ruleName][checkMethod]||'':'';
						if (message.length > 0) {
							checkResult.errorList.push({'checkMethod':checkMethod,'message':message, 'params':$.makeArray(actValue, $.makeArray(rule[checkMethod]))});  //params value e.g. [expectValue1, expectValue2, actValue]
						}
						return checkResult;
					}
				}
				//校验成功
				checkResult.ok = true;
				checkResult.msg = '';
				checkResult.errorList = [];
				return checkResult;
			},
			//给methods增加新的校验方法
			addMethod: function(name, method, message) {
				this.options.methods[name] = method;
				this.options.messages[name] = message != undefined ? message : this.options.messages[name];
			}
		}
	});
	
	$.jsvalidator = {
		defaults: {
				/**
				 * e.g. rules: {
				 * 	'rule name': {
				 * 		'checks': {required: true, min: 2, max: 10}, 
				 * 		error: function(){}, 
				 * 		success: function(){},
				 * 		method: {}
				 * 	}
				 * }
				 */
				rules: {},
				messages: {},
				//校验失败时默认执行的方法
				error: function(actValue, ruleName, checkResult) {
					//alert($.jsvalidator.formatMessage(checkResult, -1));  //打印所有的messages
					$.dialog({
						title: '3秒后自动关闭',
					    time: 3,
					    content: $.jsvalidator.formatMessage(checkResult, -1),
					    icon: 'error',
					    yesFn: true
					});
				},
				//校验成功时默认执行的方法
				success: function(actValue, ruleName, checkResult) {
				},
				errorList: [],
				methods: {
					required: function(actValue, expect) {
						return expect?($.trim(actValue).length > 0):true;
					},
					minlength: function(actValue, expect) {
						if (expect==undefined) return true;
						return (actValue||'').length >= expect;
					},
					maxlength: function(actValue, expect) {
						if (expect==undefined) return true;
						return (actValue||'').length<=expect;
					},
					rangelength: function(actValue, expect) {
						if (expect==undefined || expect.length < 2) return true;
						return (actValue||'').length>=expect[0] && (actValue||'').length<=expect[1];
					},
					min: function(actValue, expect) {
						if (expect==undefined) return true;
						return (actValue||'')>=expect;
					},
					max: function(actValue, expect) {
						if (expect==undefined) return true;
						return (actValue||'')<=expect;
					},
					range: function(actValue, expect) {
						if (expect==undefined || expect.length < 2) return true;
						return (actValue||'')>=expect[0] && (actValue||'')<=expect[1];
					},
					email: function(actValue, expect) {
						return expect?/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i.test(actValue):true;
					},
					url: function(actValue, expect) {
						return expect?/^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i.test(actValue):true;
					},
					date: function(actValue, expect) {
						return expect?!/Invalid|NaN/.test(new Date(actValue)):true;
					},
					dateISO: function(actValue, expect) {
						return expect?/^\d{4}[\/-]\d{1,2}[\/-]\d{1,2}$/.test(actValue):true;
					},
					number: function(actValue, expect) {
						return expect?/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(actValue):true;
					},
					digits: function(actValue, expect) {
						return expect?/^\d+$/.test(actValue):true;
					},
					equalTo: function(actValue, target) {
						var _targetVal = $('#' + target).val();
						return actValue == $.trim(_targetVal);
					},
					isMobile : function(mobile, msg) {
						var mobile_reg = /^(?:13\d|14\d|15\d|18\d)-?\d{5}(\d{3}|\*{3})$/;
						return mobile_reg.test(jQuery.trim(mobile));
					}
				}
		},	
		/**
		 * invoke as: $.jsvalidator.formatMessage(checkResult, 0);
		 * checkResult, e.g. {ok:false,msg:'',errorList:[{'checkMethod':'required','message':'','params':[1,2,3]}]}
		 * index小于0将返回全部消息 
		 */
		formatMessage: function(checkResult, index) {
			checkResult = $.extend({ok:false,msg:'',errorList:[]}, checkResult);
			index = index||0;
			if (index>=checkResult.errorList.length) {
				index = checkResult.errorList.length - 1;
			} else if (index < 0) {  //index小于0将返回全部消息
				var messages = '';
				for (var i = 0; i < checkResult.errorList.length; i++) {
					messages += (checkResult.errorList.length<1)?checkResult.msg||'':$.jsvalidator.format(checkResult.errorList[i].message, checkResult.errorList[i].params)+" "; //有多行应在行尾加"<br/>"或"\n"
				}
				return messages;
			}
			return (checkResult.errorList.length<1)?checkResult.msg||'':$.jsvalidator.format(checkResult.errorList[index].message, checkResult.errorList[index].params);
		},
		//e.g. $.jsvalidator.format('ss{0}dd{1}ff', 1, 2)或$.jsvalidator.format('ss{0}dd{1}ff', [1,2])
		format: function(source, params) {
			if ( arguments.length == 1 ) 
				return function() {
					var args = $.makeArray(arguments);
					args.unshift(source);
					return $.jsvalidator.format.apply( this, args );
				};
			if ( arguments.length > 2 && params.constructor != Array  ) {
				params = $.makeArray(arguments).slice(1);
			}
			if ( params.constructor != Array ) {
				params = [ params ];
			}
			$.each(params, function(i, n) {
				source = source.replace(new RegExp("\\{" + i + "\\}", "g"), n);
			});
			return source;
		}
	};
})(jQuery);