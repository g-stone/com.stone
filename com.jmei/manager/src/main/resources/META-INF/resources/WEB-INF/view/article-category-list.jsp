<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="/common/meta.jsp">
			<jsp:param value="文章栏目管理" name="cms_page_title"/>
		</jsp:include>
		<link href="${ctpath}/static/ui/article/article-category-list.css" rel="stylesheet"/>
		<script src="${ctpath}/static/ui/article/article-category-list.js" type="text/javascript"></script>
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