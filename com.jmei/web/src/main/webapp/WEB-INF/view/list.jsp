<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="format-detection" content="telephone=no">
		<meta content="IE=edge" http-equiv="X-UA-Compatible">
		<link rel="shortcut icon" href="${ctpath}/favicon.ico" />
		<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
		<title>佳美文印</title>
		
		<link href="${ctpath}/assert/css/super.slide.css" rel="stylesheet"/>
		<link href="${ctpath}/assert/css/global.css" rel="stylesheet"/>
		<link href="${ctpath}/assert/css/header.css" rel="stylesheet"/>
		<link href="${ctpath}/assert/ui/list/list.css" rel="stylesheet"/>
		<script src="${ctpath}/assert/plugins/jquery/jquery-1.12.4.js" type="text/javascript"></script>
		<script src="${ctpath}/assert/plugins/slide/jquery.superslide.2.1.1.js" type="text/javascript"></script>
		<script src="${ctpath}/assert/js/menuindex.js" type="text/javascript"></script>
		<script src="${ctpath}/assert/ui/list/list.js" type="text/javascript"></script>
	</head>
	<body>
		<div class="header">
			<div class="logo"></div>
			<div class="menu"></div>
		</div>
		
		<div class="clear"></div>
		
		<div class="wigets">
			<div class="menuwrap">
				<div class="toolbars">
					<dl data-role="casecategory">
						<dt>目录索引</dt>
					</dl>
				</div>
			</div>
			<div class="list">
				<div class="wrapgoods">
				<c:choose>
					<c:when test="${cases == null || empty cases}">暂无案例信息！</c:when>
					<c:otherwise>
						<c:forEach var="case" items="${cases}" varStatus="stu">
						<div class="goods">
							<a href="${ctpath}/case/detail?caseId=${case.caseId}">
								<img src="${ctpath}/${case.caseOptionImage}">
							</a>
						</div>
						</c:forEach>
					</c:otherwise>
				</c:choose>
				</div>
			</div>
		</div>
		
		<div class="clear"></div>
		
		<div class="footer">
			Copyright@stone.com
		</div>
	</body>
</html>