<%@ page language="java" pageEncoding="UTF-8"%>
<div class="position">
	<a id="userRole" href="${ctx }/backend/system/user/userRoleAuthorize?userId=${user.id }">角色授权</a> 
	<%-- <a id="userResource" href="${ctx }/backend/system/user/userResourceAuthorize?userId=${user.id }">资源授权</a>  --%>
<%--  |	<a id="userRoleTemp" href="${ctx }/backend/system/user/userRoleTempAuthorize?userId=${user.id }">临时角色授权</a> | 
	<a id="userResourceTemp" href="${ctx }/backend/system/user/userResourceTempAuthorize?userId=${user.id }">临时资源授权</a> | 
	<a id="userRoleTempList" href="${ctx }/backend/system/userRoleTemp/list?userId=${user.id }">临时角色授权管理</a> | 
	<a id="userResourceTempList" href="${ctx }/backend/system/userResourceTemp/list?userId=${user.id }">临时资源授权管理</a>  --%>
</div>