$(function() {
	each_checkbox()
	/*监听推荐方案  */
	$("#recommendedPlan").children().children().click(function() {
			/*选中交强险  */
			$("#tci_content").show();
			$("#risk_FORCEPREMIUM").val("FORCEPREMIUM:交强险:Y")
			$("input[name='tci_radio']").first().prop("checked", true);
			
			/*选中对应商业险  */
			$("#vci_content").show();
			var plan = $(this).parent().children().val();
			cache_risk();
			if ("basic" == plan) {
				$("#risk_A").val("A:车辆损失险:Y");
				$("#risk_B").val("B:商业第三者责任险:1000000");
				$("#risk_G1").val("G1:全车盗抢险:Y");
				//$("#risk_D3").val("D3:车上人员责任险(司机):50000");
				
				choose_by_risk("risk_A");
				choose_by_risk("risk_B");
				choose_by_risk("risk_G1");
				//choose_by_risk("risk_D3");
				
				$("#risk_MA").prop("checked", "checked");
				$("#risk_MA").val("Y");
				$("#risk_MB").prop("checked", "checked");
				$("#risk_MB").val("Y");
				//$("#risk_MD3").prop("checked", "checked");
				//$("#risk_MD3").val("Y");
				$("#risk_MG1").prop("checked", "checked");
				$("#risk_MG1").val("Y")
			} else {
				if ("effective" == plan) {
					$("#risk_A").val("A:车辆损失险:Y");
					$("#risk_B").val("B:商业第三者责任险:1000000");
					$("#risk_G1").val("G1:全车盗抢险:Y");
					$("#risk_D3").val("D3:车上人员责任险(司机):50000");
					$("#risk_D4").val("D4:车上人员责任险(乘客):50000");
					
					choose_by_risk("risk_A");
					choose_by_risk("risk_B");
					choose_by_risk("risk_G1");
					choose_by_risk("risk_D3");
					choose_by_risk("risk_D4");
					
					$("#risk_MA").prop("checked", "checked");
					$("#risk_MA").val("Y");
					$("#risk_MB").prop("checked", "checked");
					$("#risk_MB").val("Y");
					$("#risk_MG1").prop("checked", "checked");
					$("#risk_MG1").val("Y");
					$("#risk_MD3").prop("checked", "checked");
					$("#risk_MD3").val("Y");
					$("#risk_MD4").prop("checked", "checked");
					$("#risk_MD4").val("Y")
				} else {
					if ("comprehensive" == plan) {
						$("#risk_A").val("A:车辆损失险:Y");
						$("#risk_B").val("B:商业第三者责任险:1000000");
						$("#risk_G1").val("G1:全车盗抢险:Y");
						$("#risk_D3").val("D3:车上人员责任险(司机):50000");
						$("#risk_D4").val("D4:车上人员责任险(乘客):50000");
						$("#risk_F").val("F:玻璃单独破碎险:1");
						
						choose_by_risk("risk_A");
						choose_by_risk("risk_B");
						choose_by_risk("risk_G1");
						choose_by_risk("risk_D3");
						choose_by_risk("risk_D4");
						choose_by_risk("risk_F");
						
						$("#risk_MA").prop("checked", "checked");
						$("#risk_MA").val("Y");
						$("#risk_MB").prop("checked", "checked");
						$("#risk_MB").val("Y");
						$("#risk_MG1").prop("checked", "checked");
						$("#risk_MG1").val("Y");
						$("#risk_MD3").prop("checked", "checked");
						$("#risk_MD3").val("Y");
						$("#risk_MD4").prop("checked", "checked");
						$("#risk_MD4").val("Y")
					}
				}
			}
			each_checkbox();
			$("input[name='vci_radio']").first().prop("checked", true);
		});
		/* 监听险种选择，有变化，修改套餐为自定义 */
		$(".riskList select,.riskList input[type='checkbox']").change(function() {
			$("#recommendedPlan").find("[value='custom']").prop("checked", true);
		});
		/* 对应商业险险种当没有选中时 ,去掉对应勾选不计免赔 */
		$(".riskList select").change(function() {
			if ($(this).val() == "") {
				$(this).parent().parent().next().children().prop("checked", false);
			}
		});

		/* 点击checkbox时，改变val */
		$("input[type='checkbox'][name^='risk_']").click(function() {
			if (!$(this).parent().parent().find("input[name='insure_risk']").prop("checked")) {/* 当没有投保时，不能选不计免赔 */
				return false;
			}
			if ($(this).is(':checked')) {
				$(this).val("Y");
			} else {
				$(this).val("");
			}
		});

		/* 同时“监听车辆损失险”和“机动车损失险无法找到第三方特约险”
			1.不投risk_A，不投risk_Z3，
			2.投risk_Z3，risk_A投 */
		$("#risk_A,#risk_Z3").change(function() {
			if ($(this).attr("id") == "risk_A" && $(this).val() == "") {
				$("#risk_Z3").val("");
			}
			if ($(this).attr("id") == "risk_Z3" && $(this).val() != "") {
				$("#risk_A").val("A:车辆损失险:Y");
			}
		});

		/* 控制radio的点击扩大到对应选项的字 */
		$(".choose_box").children().children().click(function() {
			if ($(this).parent().attr("class") != "insurer") {
				$(this).parent().children().prop("checked", true);
			}
		})

		/*交强险和商业险投保单选框，控制险种详情显示  */
		$("[name='tci_radio'],[name='vci_radio']").parent().click(function() {
			var content = $(this).parent().attr("content");
			/* 获取对应险种radio判断第一个是否选中 */
			var radios = $("[name='" + $(this).children().attr('name') + "']").first();
			if (radios.prop("checked")) {
				$("#" + content).show();
				if(radios.prop("name")=="tci_radio"){
					$("#risk_FORCEPREMIUM").val("FORCEPREMIUM:交强险:Y")
				}
			} else {
				if(radios.prop("name")=="tci_radio"){
					$("#risk_FORCEPREMIUM").val("")
					$("#tciStartDate").val("");
				}else{
					cache_risk();
					each_checkbox();
				}
				$("#" + content).hide();
			}
		})
		/*商业险全选功能，投保选中及单选上色*/
		$("input[name='insure_risk'],.riskList input[name='insure_risk']").click(function() {
			if ($(this).attr("id") == "check_all") {
				$("input[name='insure_risk']").prop("checked", $(this).prop("checked"));
			}
			each_checkbox();
		})
		
		$("#vciStartDate,#tciStartDate").blur(function(){
			validate_value($(this).prop("id"));
		})
		
		//获取报价
		$("#sure").click(function() {
			
			/*先判断是否有投保交强险及商业险  */
			if($("input[name='tci_radio']:checked").val() == "N" && $("input[name='vci_radio']:checked").val() == "N"){
				art.dialog('请投保交强险或者商业险', function() {});
				return;
			}
			
			/*判断如果投保交强险，是否选择投保时间  */
			if($("input[name='tci_radio']:checked").val() == "Y"){
				if(!validate_value("tciStartDate")){
			  		return;
			  	}
			}
			/*判断如果投保商业险，是否选择投保时间及险种  */
			if($("input[name='vci_radio']:checked").val() == "Y"){
			  	if(!validate_value("vciStartDate")){
			  		return;
			  	}
				var _riskList = $(".riskList input[name='insure_risk']:checked");
				if(_riskList.length==0){
					art.dialog('请至少选择一个险种', function() {});
					return;
				}
			}

			//获取保险公司id 数组
			var _insurerIds = $("[name='insurerIds']");
			//记录下选择的保险公司的个数 
			insureLength = $("input[name=insurerIds]:checked").length;
			if (insureLength <= 0) {
				$("#insurerPrompt").text("请选择保险公司！");
				return;
			}
			for ( var i = 0, j = _insurerIds.length; i < j; i++) {
				if (_insurerIds[i].checked) {
					$("#insurer_id").val(_insurerIds[i].value);
					getRisk(_insurerIds[i].value);
				}
			}

			$("#form").hide();
			$("#risk_switch").text("展开");

			$("#riskForm").show();//显示报价表头
			//清空之前报价信息
			$("#success").html("");
			$("#error").html("");
			$("#loading").html("");
			$("#loading").append('<tr><td colspan="9"><div class="loading"></div></td></tr>');

		});
		//获取报价信息的方法
		function getRisk(insurerId) {
			$.ajax({url : ctx+'/backend/insurance/indicativePrice',
						data : $('#form').serialize(),
						type : 'POST',
						success : function(data) {
							insureLength = insureLength - 1; //响应一次减1
							if (insureLength == 0) {
								$("#loading").html("");
							}
							var title = "";
							var content = "";
							if (!data.status) {//返回异常信息提示
								
								var errorMessage = data.message;
								
								if (data.url != undefined) {
									$("#error").append('<tr><td colspan = "12">' + data.carInsurance.insurerInfo.name + " : " + errorMessage + '<a id="CPIC" style="float: right;line-height: 22px;" class="mui-btn-blue">投保</a></td></tr>');
									var jq = top.jQuery;
									$("#error #CPIC").click(function() {
										/* jq("#iframe")[0].contentWindow.addPanel(data.carInsurance.insurerInfo.name+"投保",data.url) */
										window.open(data.url, '_blank', 'width=800,height=600,top=100px,left=0px')
									})
								} else {
									if (errorMessage != null && errorMessage.length > 100) {
										$("#error").append('<tr><td colspan = "12">' + data.carInsurance.insurerInfo.name+" : " + errorMessage.substring(0, 100) + '\.\.\.\.\.\.<a name="showError" id="CPIC" style="float: right;line-height: 22px;" class="mui-btn-blue">展开</a></br><div hidden>--'+errorMessage+'</div></td></tr>');
										$("[name= 'showError']").off("click");/*清除展开按钮的点击，避免绑定多次  */
										$("[name='showError']").click(function() {
											$(this).next().next().toggle(200);
											if ($(this).text() == "展开") {
												$(this).text("收起");
											} else {
												$(this).text("展开");
											}
										})
									}else{
										$("#error").append('<tr><td colspan = "12">' + data.carInsurance.insurerInfo.name +" : " + errorMessage.substring(0, 100) + '</td></tr>');
									}
									
								}
								return false;
							}

							$("#riskForm_th").show();
							var carInsurance = data.carInsurance;
							title = '<tr>' + 
									'<td class="length1">' + carInsurance.insurerInfo.name + '</td>' + 
									'<td class="length1">' + carInsurance.tciPremium + '</td>' + 
									'<td class="length1">' + carInsurance.suggestedCompulPrice + '</td>' + /*交强险优惠价*/
									'<td class="length1">'+ carInsurance.compulPriceDiscount*100 + '%</td>' + /*交强险折扣率*/
									'<td class="length1">' + carInsurance.vciPremium + '</td>' + 
									'<td class="length2">' + carInsurance.suggestedBussiPrice + '</td>' +  /*商业险优惠价*/
									'<td class="length2">' + carInsurance.bussiPriceDiscount*100 + '%</td>' +  /*商业险折扣率*/
									'<td class="length2">'+ carInsurance.sumTravelTax + '</td>' + 
									'<td class="length1">' + carInsurance.premium + '</td>' + 
									'<td class="length2" style="color:red">' + carInsurance.suggestedPrice + '</td>' + 
									'<td class="length2" style="color:red">'+ carInsurance.discount*100 + '%</td>' +  /*折扣率*/
									'<td class="length1">' + '<a name="showbtn" class="mui-btn-blue">展开</a>' + '<a name="insure" value="'+insurerId+'" class="mui-btn-blue">投保</a>' + '</td>' + 
									'</tr>';
							if (data.carInsurance.kinds != undefined) {
								var kinds = data.carInsurance.kinds;
								content = '<tr style="display: none;"><td colspan = "10"><table><tr class="risk_title"><td class="length1">险种</td><td class="length1">保额（元）</td><td class="length1">保费（元）</td><td class="length2">折扣率</td></tr>';
								for ( var j = 0; j < kinds.length; j++) {
									var riskName = kinds[j].insuranceRisk.riskName;
									var unitAmount = kinds[j].unitAmount;
									var amount = kinds[j].amount;
									var premium = kinds[j].premium;
									
									if(unitAmount!=null){
										if (parseInt(unitAmount) / 10000 >= 1) {
											unitAmount = parseInt(unitAmount) / 10000 + "万";
										}
										if(parseInt(unitAmount) ==0 || parseInt(unitAmount)==1){
											unitAmount = "-";
										}
									}else{
										if(amount!=null){
											unitAmount = parseInt(amount) / 10000 + "万";
										}else{
											unitAmount = "-";
										}
									}
									var discount  = kinds[j].discount;
									if (discount == null) {
										discount = "-";
									}else{
										discount += "%";
									}
										
									content += '<td class="length2" style="text-align: left;padding-left:20px">' + riskName + '</td>' + '<td class="length1">' + unitAmount + '</td>' + '<td class="length1 risk_premium">' + premium + '</td><td class="length2 risk_premium">' + discount + '</td></tr>';
									if (j == kinds.length - 1) {
										content += '<td></td><td></td><td></td></tr>';			
									}
								}

								if (data.carInsurance.notSendkinds != undefined) {
									var kinds = data.carInsurance.notSendkinds;
									for ( var j = 0; j < kinds.length; j++) {
										var riskName = kinds[j].insuranceRisk.riskName;
										var unitAmount = kinds[j].unitAmount;
										if (parseInt(unitAmount) / 10000 >= 1) {
											unitAmount = parseInt(unitAmount) / 10000 + "万";
										}
										var premium = kinds[j].premium;
										if (unitAmount == null || unitAmount == 0) {
											unitAmount = "不可投";
										}
										content += '<tr>' + '<td>' + riskName + '</td>' + '<td class="length1">' + unitAmount + '</td>' + '<td class="length1">' + premium + '</td>' + '</tr>';
									}

								}

								content += '</table></td></tr>';

							}

							$("#success").append(title + content);
							$("[name= 'showbtn']").off("click");/*清除展开按钮的点击，避免绑定多次  */
							$("[name= 'showbtn']").on("click", function() {
								$(this).parent().parent().next().toggle(200);
								if ($(this).text() == "展开") {
									$(this).text("收起");
								} else {
									$(this).text("展开");
								}
							});
							$("[name= 'insure']").off("click");/*清除展开按钮的点击，避免绑定多次  */
							$("[name= 'insure']").on("click", function() {
								$("#insurerId").val($(this).attr("value"));
								$("body").append("<div id='mask' class='mask'></div><div class='bgblack'><div class='sure_loading'></div></div>");
								showOverlay();
								document.forms[1].submit(); // 提交表单
							});
							return true;
						}
					});
		}
		
		
		/* 显示遮罩层 */
		function showOverlay() {
		    $("#mask").height(pageHeight());
		    $("#mask").width(pageWidth());

		    // fadeTo第一个参数为速度，第二个为透明度
		    // 多重方式控制透明度，保证兼容性，但也带来修改麻烦的问题
		    $("#mask").fadeTo(200, 0.5);
		}


		/* 当前页面高度 */
		function pageHeight() {
		    return document.body.scrollHeight;
		}

		/* 当前页面宽度 */
		function pageWidth() {
		    return document.body.scrollWidth;
		}
		
		setInterval(function() {
		}, 5000)
		
		$("#risk_switch").click(function() {
			$("#form").toggle(100);
			if ($(this).text() == "展开") {
				$(this).text("收起");
			} else {
				$(this).text("展开");
			}
		});
		
		
		
		
		/*遍历 投保checkbox*/
		function each_checkbox(){
			var all_choose = true;
				/*判断是否选择车辆损失险。来决定是否显示相关险种*/
				if(!$(".riskList input[value='A']").prop("checked")){
					$(".riskList input[value='_A']").prop("checked",false);
					$(".riskList input[value='_A']").parent().parent().hide();
				}else{
					$(".riskList input[value='_A']").parent().parent().show()
				}
				$(".riskList input[name='insure_risk']").each(function() {
					if (!$(this).prop("checked")) {
						$("#check_all").prop("checked", false);
						all_choose = false;
						$(this).parent().parent().find("*").css("background-color","#DDD")
						$(this).parent().parent().find("input[type='checkbox'][name^='risk_']").prop("checked",false);
						$(this).parent().parent().find("input[type='checkbox'][name^='risk_']").val("");
						$(this).parent().parent().find("select").prop("disabled",true);
					}else{
						$(this).parent().parent().find("*").css("background-color","")
						$(this).parent().parent().find("input[type='checkbox'][name^='risk_']").prop("checked",true);
						$(this).parent().parent().find("input[type='checkbox'][name^='risk_']").val("Y");
						$(this).parent().parent().find("select").prop("disabled",false);
					}
				});
				$("#check_all").prop("checked", all_choose);
		}
		function choose_by_risk(risk){
			$("#"+risk).parent().parent().parent().children().children().prop("checked", true);
		}
		/*取消险种选择*/
		function cache_risk(){
			$(".riskList select option").attr("selected", false);
			$(".riskList input[type='checkbox']").prop("checked", false);
			$(".riskList input[type='checkbox'][name='risk_*']").val("");
			$("#vciStartDate").val("");
		}
		/*验证时间*/
		function validate_value(id){
				if($("#"+id).val() == ""||$("#"+id).val() == null){
					$("#"+id).next().text("请选择起保日期！");
					$("#"+id).focus();
					return false;
				}else{
					$("#"+id).next().text("");
					return true;
				}
		}
	});