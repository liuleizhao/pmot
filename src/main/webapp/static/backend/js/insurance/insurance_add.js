$(function() {
		
		$("#sure").click(
			function() {
			try {
				$(this).validate.isNull($('#name').val(),'车主姓名不能为空！');
				$(this).validate.isNull($('#mobile').val(),'联系电话不能为空！');
				// 验证电话号码是否合法
				var mobileReg = /^\d{11}$/;
				if(!mobileReg.test($('#mobile').val())){
					 throw("请输入正确的联系电话！");
				}
			   	$(this).validate.isNull($('#idType').val(),'证件类型不能为空！');
			   	var idName = $("#idType").find('option:selected').text(); 
				if(idName == '居民身份证'){ // 如果证件类型是身份证
					$(this).validate.isCardFormat($('#idNumber').val(), '证件号格式不正确！','-99');
				}
				$(this).validate.isNull($('#licenseNo').val(),'请输入号牌号码!');
				$(this).validate.isNull($('#frameNo').val(),'请输入车架号！');
			    $(this).validate.isNull($('#engineNo').val(),'请输入发动机号！');
			    $(this).validate.isNull($('#brandName').val(),'请输入厂牌型号！');
			    $(this).validate.isNull($('#colorCode').val(),'请选择车身颜色！');
			    $(this).validate.isNull($('#licenseColor').val(),'请选择号牌颜色！');
			    /*
			    $(this).validate.isNull($('#enrollDate').val(),'请选择购置日期！');
			    $(this).validate.isNull($('#singeinDate').val(),'请选择登记日期！');
			    $(this).validate.isNull($('#certificateDate').val(),'请选择发证日期！');
			    $(this).validate.isNull($('#vciStartDate').val(),'请选择商业险起保日期！');
			    
 			    $(this).validate.isNull($('#mdfyPurchasePrice').val(),'请输入新车购置价！');
			    $(this).validate.isNull($('#seatCount').val(),'请输入核定载客！');
			    $(this).validate.isNull($('#tonCount').val(),'请输入核定载质量！');
			    $(this).validate.isNull($('#attachNature').val(),'请选择所属性质！');
			    $(this).validate.isNull($('#useCharater').val(),'请选择使用性质！');
			    $(this).validate.isNull($('#carKindCode').val(),'请选择机动车车辆种类！');
			    $(this).validate.isNull($('#carUseType').val(),'请选择车辆用途！');
			    $(this).validate.isNull($('#licenseTypeCode').val(),'请选择号牌种类！');
			    $(this).validate.isNull($('#carKindCodeShow').val(),'请选择车辆种类！');
			    $(this).validate.isNull($('#vehicleCategory').val(),'请选择交管车辆种类！');
			    
 				// 验证投保人与被保人的信息  insuredSameInd
			    $(this).validate.isNull($('#insuredName').val(),'请输入投保人姓名！');
			    $(this).validate.isNull($('#applyMobile').val(),'请输入投保人电话号码！');
			    $(this).validate.isNull($('#identifyType').val(),'请选择保人证件类型！');
			    $(this).validate.isNull($('#identifyNumber').val(),'请输入投保人证件号码！');
			    $(this).validate.isNull($('#address').val(),'请输入投保人地址！');
			    
				if(!$(this).is(':checked')) // 没有选中，那么验证
				{
					$(this).validate.isNull($('#coverName').val(),'请输入被投保人姓名！');
				    $(this).validate.isNull($('#coverMobile').val(),'请输入被投保人电话号码！');
				    $(this).validate.isNull($('#coverIdType').val(),'请选择被保人证件类型！');
				    $(this).validate.isNull($('#coverNumber').val(),'请输入被投保人证件号码！');
				    $(this).validate.isNull($('#coverAddress').val(),'请输入被投保人地址！');
				} */
				
			    document.forms[0].submit(); // 提交表单
			   } catch (e) {
					Dialog.alert(e, null, 200, 50);
				}	
	    });
	    
	    $('#carKindCode').change(function(){
		 	var carKindCode = $(this).val();
			try{
				if(""== carKindCode){
					throw ("请选择机动车种类");
				}
			}catch (e) {
				Dialog.alert(e, null, 200, 50);
				return false;
			}
			if("100" == carKindCode) // 客车
			{
				$("#vehicleCategory option[value^='H']").each(function () {
		             $(this).attr("disabled","disabled");
		        });
		        $("#vehicleCategory option[value^='K']").each(function () {
		             $(this).removeAttr("disabled");
		        });  
			}
			if("200" == carKindCode)
			{
				$("#vehicleCategory option[value^='H']").each(function () {
		             $(this).removeAttr("disabled");
		        }); 
				$("#vehicleCategory option[value^='K']").each(function () {
		             $(this).attr("disabled","disabled");
		        });
			}
		 });
		 
		 $('#vehicleCategory').change(function(){
		  	// 判断是否选中了机动车种类
		  	var carKindCode  = $('#carKindCode').val();
		  	try{
				if(""== carKindCode){
					throw ("请先选择机动车种类");
				}
			}catch (e) {
				Dialog.alert(e, null, 200, 50);
				$(this).val(""); // 如果尚未选择，那么清空当前的选择
				return false;
			}
		 });
		 
		$('#insuredSameInd').click(function(){
			if($(this).is(':checked')){
			
				$(".cover_person").hide();
				/*勾选的时候同步投保人信息到被投保人  */
				$("#coverName").val($("#insuredName").val());
				$("#coverMobile").val($("#applyMobile").val());
		 		$("#coverIdType").val($("#identifyType").val());
				$("#coverNumber").val($("#identifyNumber").val());
				$("#coverAddress").val($("#address").val());
			}else{
			   $(".cover_person").show();
			}
		});
		
				
		var mess = $("#mess").val();
		if (mess != null && $.trim(mess) != "") {
			$.dialog({
				title : '3秒后自动关闭',
				time : 3,
				content : mess,
				icon : 'succeed',
				yesFn : true
			});
		}
		
		$("#frameNo").blur(function(){ // 车架号转大写
			  var frameNo = $(this).val();
			if(""!= frameNo){
			   $("#frameNo").val(frameNo.toUpperCase());
		    }
		});
		
		
		/*监听投保人姓名*/
		$("#insuredName").blur(function(e) {
			if($("#insuredSameInd").is(':checked')){
				$("#coverName").val($(this).val());
			}
		})
		/*监听投保人联系方式*/
		$("#applyMobile").blur(function(e) {
			if($("#insuredSameInd").is(':checked')){
				$("#coverMobile").val( $(this).val());
			}
		});
		
		/*监听投保人证件类型*/
		 $("#identifyType").change(function(){
			 if($("#insuredSameInd").is(':checked')){
		 		$("#coverIdType").val($(this).val());
		 	}
		 });
		 
		/*监听投保人证件号码*/
		$("#identifyNumber").blur(function(e) {
			if($("#insuredSameInd").is(':checked')){/*判断勾选  */
				$("#coverNumber").val($(this).val());/*获取被投保人证件号  */
				if($("#coverIdType").val("01")){/*获取被投保人生日  */
					var coverNumber = $(this).val();
					$("#birthday3").val(getBirthdayFromIdCard(coverNumber));
				}
			}
			if($("#identifyType").val("01")){/*获取投保人生日  */
					var identifyNumber = $(this).val();
					$("#birthday2").val(getBirthdayFromIdCard(identifyNumber));
			}
		});
		
		/*监听投保人地址*/
		$("#address").blur(function(e) {
			if($("#insuredSameInd").is(':checked')){
				$("#coverAddress").val($(this).val());
			}
		});
		
		/*监听车主证件号，获取生日  */
		$("#idNumber").blur(function(e) {
			if($("#idType").val("01")){
					var idNumber = $(this).val();
					$("#birthday1").val(getBirthdayFromIdCard(idNumber));
			}
		});
		
		/*监听被投保人证件号，获取生日  */
		$("#coverNumber").blur(function() {
			if($("#coverIdType").val("01")){
					var coverNumber = $(this).val();
					$("#birthday3").val(getBirthdayFromIdCard(coverNumber));
			}
		});
			
		
		function getBirthdayFromIdCard(idCard) {  
	        var birthday = "";  
	        if(idCard != null && idCard != ""){  
	            if(idCard.length == 15){  
	                birthday = "19"+idCard.substr(6,6);  
	            } else if(idCard.length == 18){  
	                birthday = idCard.substr(6,8);  
	            }  
	            birthday = birthday.replace(/(.{4})(.{2})/,"$1-$2-");  
	        }  
	        return birthday;  
	      } 
	});