<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<link href="${ctpath}/assert/ui/index/index.css" rel="stylesheet"/>
		<script src="${ctpath}/assert/plugins/jquery/jquery-1.12.4.js" type="text/javascript"></script>
		<script src="${ctpath}/assert/plugins/slide/jquery.superslide.2.1.1.js" type="text/javascript"></script>
		<script src="${ctpath}/assert/js/menuindex.js" type="text/javascript"></script>
		<script src="${ctpath}/assert/ui/index/index.js" type="text/javascript"></script>
	</head>
	<body>
		<div class="header">
			<div class="logo"></div>
			<div class="menu"></div>
		</div>
		
		<div class="clear"></div>
		
		<div class="wigets">
			<div class="banners">
				<div id="slideBox" class="slideBox">
					<div class="hd">
						<ul>
						<c:if test="${loopImages != null || not empty loopImages}">
							<c:forEach var="loop" items="${loopImages}" varStatus="stu">
							<li>${stu.index + 1}</li>
							</c:forEach>
						</c:if>
						</ul>
					</div>
					<div class="bd">
						<ul>
						<c:if test="${loopImages != null || not empty loopImages}">
							<c:forEach var="loop" items="${loopImages}" varStatus="stu">
							<li><a href="#" target="_blank"><img src="${ctpath}/${loop.imageUrl}"/></a></li>
							</c:forEach>
						</c:if>
						</ul>
					</div>
					<!-- 下面是前/后按钮代码，如果不需要删除即可 -->
					<a class="prev" href="javascript:void(0)"></a>
					<a class="next" href="javascript:void(0)"></a>
				</div>
			</div>
			<div class="boxhall">
				<c:choose>
					<c:when test="${cases == null || empty cases}">暂无案例信息！</c:when>
					<c:otherwise>
						<c:forEach var="case" items="${cases}" varStatus="stu">
						<div class="box">
							<div class="ctx">
							<img class="backgd" src="${ctpath}/${case.caseOptionImage}"/>
							<div class="gdtip">${case.shortDesc}</div>
							</div>
						</div>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		
		<div class="clear"></div>
		
		<div class="footer">
			Copyright@stone.com
		</div>
	</body>
</html>