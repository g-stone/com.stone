<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="format-detection" content="telephone=no">
		<meta content="IE=edge" http-equiv="X-UA-Compatible">
		<link rel="shortcut icon" href="favicon.ico" />
		<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
		<title>佳美文印</title>
		
		<link href="${ctpath}/assert/css/super.slide.css" rel="stylesheet"/>
		<link href="${ctpath}/assert/css/global.css" rel="stylesheet"/>
		<link href="${ctpath}/assert/css/header.css" rel="stylesheet"/>
		<link href="${ctpath}/assert/ui/detail/detail.css" rel="stylesheet"/>
		<script src="${ctpath}/assert/plugins/jquery/jquery-1.12.4.js" type="text/javascript"></script>
		<script src="${ctpath}/assert/plugins/slide/jquery.superslide.2.1.1.js" type="text/javascript"></script>
		<script src="${ctpath}/assert/js/menuindex.js" type="text/javascript"></script>
		<script src="${ctpath}/assert/ui/detail/detail.js" type="text/javascript"></script>
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
			<div class="detail">
				<p class="title">${case.caseName}</p>
				<div class="line"></div>
				<div class="contexts">
					<p>${case.shortDesc}</p>
					<c:if test="${caseImages != null || not empty caseImages}">
						<c:forEach var="image" items="${caseImages}" varStatus="stu">
						<img src="${ctpath}/${image.imageUrl}">
						</c:forEach>
					</c:if>
					<p>${case.caseDesc}</p>
				</div>
			</div>
		</div>
		
		<div class="clear"></div>
		
		<div class="footer">
			Copyright@stone.com
		</div>
	</body>
</html>