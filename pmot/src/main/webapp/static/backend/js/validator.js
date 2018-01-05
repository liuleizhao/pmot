/**
 * validator
 */
(function($) {
	$.fn.extend({
			validate : {
				//验证字符串是否非空
				isNull : function(str, msg){
					if(str == null || $.trim(str) == ''){
						throw (msg);
					}
				},
				//计算字符串长度
				strLength : function(str, minLen, maxLen, msg) {
					var sum = 0;
					for ( var i = 0, len = str.length; i < len; i++) {
						if ((0 <= str.charCodeAt(i)) && (str
								.charCodeAt(i) <= 255)) {
							sum++;
						} else {
							sum += 2;
						}
					}
					if (sum < minLen || sum > maxLen) {
						throw (msg);
					}
				},
				//验证是否有特需字符
				checkSpechar:function(input,msg){
					var SPECIAL_STR = "#~!@%^&*();'\"?><[]{}\\|,:/=-+—“”‘.`$ 　"; 
					for(i=0;i<input.length;i++){
						if (SPECIAL_STR.indexOf(input.charAt(i)) !=-1){
							throw(msg);
						}
					}
				},
				//验证手机
				isMobile : function(mobile, msg) {
					var mobile_reg = /^(?:13\d|14\d|15\d|18\d|17\d)-?\d{5}(\d{3}|\*{3})$/;
					if (!mobile_reg.test(jQuery.trim(mobile))) {
						throw (msg);
					}
				},
				//验证电话
				isTel : function(tel, msg) {
					var tel_reg = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
					if (!tel_reg.test(tel)) {
						throw (msg);
					}
				},
				//验证联系方式，包括电话和手机
				isContact:function(contact,msg){
					var mobile_reg = /^(?:13\d|14\d|15\d|18\d|17\d)-?\d{5}(\d{3}|\*{3})$/;
					var tel_reg = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
					var isMobile = mobile_reg.test(contact);
					var isTel = tel_reg.test(contact);
					if(!isMobile&&!isTel){
                       throw(msg);
					}
				},
				//验证email
				isMail : function(mail, msg) {
					var mail_reg = /^([0-9a-zA-Z]([-.\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\w]*[0-9a-zA-Z]\.)+[a-zA-Z]{2,9})$/;
					if (!mail_reg.test(mail)) {
						throw (msg);
					}
				},
				//比较两个值是否相同
				isSame : function(str1, str2, msg){
					if(str1!=str2){
						throw (msg);
					}
				},
				//身份证号码长度
                isCardlength:function(card,msg){
				   var card_regstr = /^([xX0-9]){15}|([xX0-9]){18}$/; 
				   if(!card_regstr.test(card)){
				      throw(msg);
				   }
				},
				//是否符合身份证格式
				isCardFormat:function(certid,msg,msg2){
				   var reg_15 = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
					var reg_18 = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([\d|x|X]{1})$/;
					var monthPerDays = new Array("31", "28", "31", "30", "31", "30", "31", "31", "30", "31", "30", "31");
					if (!reg_15.test(certid) && !reg_18.test(certid)) {
						throw(msg);
					}
					var year, month, day, yy, mm, dd;
					var birthDate = (certid.length == 15) ? "19" + certid
							.substr(6, 6) : certid.substr(6, 8);
					year = birthDate.substr(0, 4);
					if (birthDate.substr(4, 1) == '0')
						month = birthDate.substr(5, 1);
					else
						month = birthDate.substr(4, 2);
					if (birthDate.substr(6, 1) == '0')
						day = birthDate.substr(7, 1);
					else
						day = birthDate.substr(6, 2);
					dd = parseInt(day);
					mm = parseInt(month);
					yy = parseInt(year);
					if (mm > 12 || mm < 1 || dd > 31 || dd < 1) {
						throw (msg);
					}
					if (year % 100 != 0) {
						if (year % 4 == 0)
							monthPerDays[1] = 29;
					} else {
						if (year % 400 == 0)
							monthPerDays[1] = 29;
					}
					if (monthPerDays[mm - 1] < dd) {
						throw (msg);
					}
					if (certid.length == 18) {
						var arTemp = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
						var num = 0;
						var proof;
						for ( var i = 0; i < 17; i++) {
							num = num + certid.substr(i, 1) * arTemp[i];
						}
						var proofarr = ['1','0','X','9','8','7','6','5','4','3','2'];
						proof = proofarr[num % 11];
						if (certid.substr(17, 1)!= proof) {
							throw(msg);
						}
					}
					var now = new Date();
					var gdate = now.getDate();
					var gmonth = now.getMonth();
					var gyear18 = now.getFullYear()-18;
					mm = mm -1;
					var prevTS18 = new Date(gyear18, gmonth, gdate,0,0,0);
					var ageTs = new Date(yy,mm,dd,0,0,0);
					if(prevTS18 < ageTs){
                       throw(msg2);
					}	
				},
				CharMode : function(iN) {
					if (iN >= 48 && iN <= 57) //数字 
						return 1;
					if (iN >= 65 && iN <= 90) //大写字母 
						return 2;
					if (iN >= 97 && iN <= 122) //小写 
						return 4;
					else
						return 8; //特殊字符 
				},
				bitTotal : function(num) {
					var modes = 0;
					for (i = 0; i < 4; i++) {
						if (num & 1)
							modes++;
						num >>>= 1;
					}
					return modes;
				},
				remote : function(url,parameter,callback) {
					$.ajax({
					url : url,
					method : "post",
					cache : false,
					data:parameter,
					success : function(jsonMessage) {
                         eval(callback+"("+jQuery.trim(jsonMessage)+")"); 
					}
				   });
				},
				//验证是否为符合标准的数字
				isNumber:function(input, msg){
					 var reg = /^[0-9]{1,20}$/;
					 if (!reg.test(input)) {
							throw(msg);
						}
				},
				//验证是否为符合标准的小数
				isDouble:function(input, msg){
					 var reg = /^[0-9]{1,10}\.[0-9]{1,10}$/;
					 if (!reg.test(input)) {
							throw(msg);
						}
				},
				isValidNumber:function(input, msg){
					var integerReg = /^[0-9]{1,20}$/;
					var doubleReg = /^[0-9]{1,10}\.[0-9]{1,10}$/;
					if(!integerReg.test(input) && !doubleReg.test(input)){
						throw(msg);
					}
				},
				isIP:function(ip,msg){
					 var ip_reg = /^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/;
					 if(!ip_reg.test(ip)){
						 throw (msg);
					 }
					 var ip_part =  ip.split('.');
					   $.each(ip_part,function(index,num){
						   if(num.length > 1 && num.charAt(0) === '0'){
				                //大于1位的，开头都不可以是‘0’
				            	 throw (msg);
				            }else if(parseInt(num , 10) > 255){
				                //大于255的不能通过
				            	 throw (msg);
				            }
					   }
				        );
				},
				//提交表单
				submin_form : function(){
					document.forms[0].submit();
				}
			  }
			});
})(jQuery);