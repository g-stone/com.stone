<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="/common/meta.jsp">
			<jsp:param value="佳美文印CMS" name="cms_page_title"/>
		</jsp:include>
		<link href="${ctpath}/static/ui/gallery/gallery.css" rel="stylesheet"/>
		<script src="${ctpath}/static/ui/gallery/gallery.js" type="text/javascript"></script>
		<script>
			$(function(){
				$('.sideMenu').slide({
					titCell: 'h3', 
					targetCell: 'ul', 
					effect: 'slideDown', 
					delayTime: 300, 
					trigger: 'click', 
					triggerTime: 150, 
					defaultPlay: true, 
					returnDefault: false,
					easing: 'swing'
				});
			});
		</script>
	</head>
	<body>
		<div style="width:99%;min-height:100%;display:block;overflow:hidden;margin:0 auto;">
			<div style="display:block;min-height:18%;margin:0 auto;background-color:2b88ff;"></div>
			<div style="width:21%;float:left;min-height:82%;">
				<div class="sideMenu">
					<h3><em></em>案例管理</h3>
					<ul>
						<li data-para="{'url':'${ctpath}/spring/cms/case/category/list','index':0}">案例分类</li>
						<li data-para="{'url':'${ctpath}/spring/cms/case/industry/list','index':0}">案例行业</li>
						<li data-para="{'url':'${ctpath}/spring/cms/case/list','index':0}">案例列表</li>
					</ul>
					<h3><em></em>文章管理</h3>
					<ul>
						<li data-para="{'url':'${ctpath}/spring/cms/article/category/list','index':0}">文章分类</li>
						<li data-para="{'url':'${ctpath}/spring/cms/article/list','index':0}">文章列表</li>
					</ul>
					<h3><em></em>系统设置</h3>
					<ul>
						<li data-para="{'url':'${ctpath}/spring/cms/case/category/list',index:0}">首页轮播图片</li>
						<li data-para="{'url':'${ctpath}/spring/cms/case/category/list',index:0}">豆瓣读书频道</li>
						<li data-para="{'url':'${ctpath}/spring/cms/case/category/list',index:0}">天猫首页品牌切换</li>
						<li data-para="{'url':'${ctpath}/spring/cms/case/category/list',index:0}">格瓦拉首页活动进行中</li>
						<li data-para="{'url':'${ctpath}/spring/cms/case/category/list',index:0}">腾讯博客图片滚动</li>
					</ul>
				</div>
			</div>
			<div style="width:79%;float:right;">
				<div style="height:73px;display:block;min-height:82%;" data-role="page-content"></div>
			</div>
		</div>
	</body>
</html>