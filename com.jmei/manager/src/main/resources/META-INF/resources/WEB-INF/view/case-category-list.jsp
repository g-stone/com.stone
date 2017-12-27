<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="/common/meta.jsp">
			<jsp:param value="案例分类" name="cms_page_title"/>
		</jsp:include>
		<link href="${ctpath}/static/ui/category/category-list.css" rel="stylesheet"/>
		<script src="${ctpath}/static/ui/category/category-list.js" type="text/javascript"></script>
		<script>
		
		</script>
	</head>
	<body>
		<div class="wrap" data-role="container">
			<div id="toolbar" class="tbar"></div>
			<table id="gridtable"></table>
			<div id="gridpage"></div>
		</div>
	</body>
</html>