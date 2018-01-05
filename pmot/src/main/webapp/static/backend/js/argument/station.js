var count = 0;// 附件行数
var sum = 0; // 统计行数
$(function() {

	// 绑定表单提交事件
	$("#submitBtn").bind(
			'click',
			function() {

				try {
					$(this).validate.isNull($('#code').val(), '代码不能为空');

					$(this).validate.isNull($('#name').val(), '名称不能为空');

					$(this).validate.isNull($('#pointx').val(), 'X坐标不能为空');

					$(this).validate.isNull($('#pointy').val(), 'Y坐标不能为空');

					// $(this).validate.isNull($('#org').val(), '所属分所不能为空');

					$(this).validate.isNull($('#phone').val(), '办公电话不能为空');

					$(this).validate.isContact($('#phone').val(),
							'办公电话号码格式有误，请重新输入');

					$(this).validate.isNull($('#lane').val(), '车道数不能为空');

					$(this).validate.isNumber($('#lane').val(), '车道数应为数字');

					$(this).validate.isNull($('#address').val(), '地址不能为空');

					$(this).validate.isNull($('#bookingAmount').val(),
							'可预约人数不能为空');

					$(this).validate.isNumber($('#bookingAmount').val(),
							'可预约人数应为数字');

					$(this).validate.isNull($('#shortName').val(), '简称不能为空');

					if ($('#mobile').val() != null
							&& $.trim($('#mobile').val()) != "") {
						$(this).validate.isContact($('#mobile').val(),
								'移动电话号码格式有误，请重新输入');
					}

					if ($('#email').val() != null
							&& $.trim($('#email').val()) != "") {
						$(this).validate.isMail($('#email').val(),
								'邮箱格式格式有误，请重新输入');
					}

					// if($('#complaintsPhone').val()!= null &&
					// $.trim($('#complaintsPhone').val()) !=""){
					// $(this).validate.isContact($('#complaintsPhone').val(),
					// '请输入正确的投诉电话');
					// }

					if ($('#parkingAmount').val() != null
							&& $.trim($('#parkingAmount').val()) != "") {
						$(this).validate.isNumber($('#parkingAmount').val(),
								'请输入正确的停车位数量');
					}

					$(this).validate.isNull($('#status').val(), '请选择检测站状态');

					$(this).validate.strLength($('#shortDesc').val(), 0, 255,
							'简介太长了，请保持在255个字符之内');

					// 提交表单
					$(this).validate.submin_form();
				} catch (e) {
					$.dialog({
						title : '3秒后自动关闭',
						time : 3,
						content : e,
						icon : 'error',
						yesFn : true
					});
				}
				return false;
			});

});

// 添加附件行
function addRow() {
	if (sum < 10) {
		if (count == 0) {
			var newRow = "<table width='100%' border='0' style='border-color: #CCC;' id='fileTable'>";
			newRow += "<tr id='row"
					+ count
					+ "'> <td width='40%'> <input type='file' name='arrayImgs' style='width:280px'/>&nbsp;&nbsp;&nbsp;&nbsp;";
			newRow += "<input type='button' name='delFile' onclick='delRow("
					+ count
					+ ");' value='删除附件' style='width:80px'/></td></tr></table>";
			$("#fileRows").append(newRow);
			count++;
			sum++;
		} else {
			var newRow = "<tr id='row"
					+ count
					+ "'><td width='40%'><input type='file' name='arrayImgs' style='width:280px'/>&nbsp;&nbsp;&nbsp;&nbsp;";
			newRow += "<input type='button' name='delFile' onclick='delRow("
					+ count + ");' value='删除附件' style='width:80px'/></td></tr>";
			$("#fileTable").append(newRow);
			count++;
			sum++;
		}
	} else {
		$.dialog({
			title : '3秒后自动关闭',
			time : 3,
			content : '请不要一次性上传超过十张附件图片',
			icon : 'error',
			yesFn : true
		});
	}
}

// 删除附件行
function delRow(i) {
	$("#row" + i).remove();
	sum--;
}