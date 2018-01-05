<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<div class="pagination ue-clear"></div>
	<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/common.js"></script>
	<script type="text/javascript" src="${ctx}/static/backend/xtbg/js/jquery.pagination.js"></script>
	
<script type="text/javascript">
	//到指定的分页页面
	function topage(currentPage){
		var form = document.forms[0];
		form.currentPage.value=currentPage;
		form.submit();
	}
	
	$('.pagination').pagination( ${pageView.total }, {
			callback: function(page) {
				topage(page+1);
			},
			current_page : ${pageView.pageNum}-1,
			items_per_page : ${pageView.pageSize },
			display_msg: true,
			setPageNo: false
		});
</script>