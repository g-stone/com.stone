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
		/**
			KindEditor.ready(function(K) {
				var editor1 = K.create('textarea[name="content1"]', {
					cssPath : '${ctpath}/static/kindeditor/plugins/code/prettify.css',
					uploadJson : '${ctpath}/spring/kindeditor/upload',
					fileManagerJson : '${ctpath}/spring/kindeditor/manager',
					allowFileManager : true,
					afterCreate : function() {
						var self = this;
						K.ctrl(document, 13, function() {
							self.sync();
							document.forms['example'].submit();
						});
						K.ctrl(self.edit.doc, 13, function() {
							self.sync();
							document.forms['example'].submit();
						});
					}
				});
				prettyPrint();
			});
		*/
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
	<%-- 
		<form action="" id="example" name="example">
			<textarea name="content1" cols="100" rows="8" style="width:100%;height:200px;visibility:hidden;margin:0 auto;">sssss</textarea><br />
			<input type="submit" name="button" value="提交内容" />
		</form>
	--%>
		<div style="width:99%;min-height:100%;display:block;overflow:hidden;margin:0 auto;">
			<div style="display:block;min-height:18%;margin:0 auto;background-color:2b88ff;"></div>
			<div style="width:21%;float:left;min-height:82%;">
				<div class="sideMenu">
					<h3><em></em>书签切换系列</h3>
					<ul>
						<li>淘宝首页右侧公告</li>
						<li>京东首页产品切换</li>
						<li>苏宁易购首页品牌切换</li>
						<li>1号店双重切换</li>
						<li>腾讯健康频道切换</li>
					</ul>
					<h3><em></em>幻灯片/焦点图系列</h3>
					<ul>
						<li>淘宝首页焦点图</li>
						<li>腾讯娱乐频道焦点图</li>
						<li>腾讯电影频道焦点图</li>
						<li>网易游戏频道焦点图</li>
						<li>易迅首页焦点图</li>
					</ul>
					<h3><em></em>带按钮切换</h3>
					<ul>
						<li>淘宝首页今日活动</li>
						<li>豆瓣读书频道</li>
						<li>天猫首页品牌切换</li>
						<li>格瓦拉首页活动进行中</li>
						<li>腾讯博客图片滚动</li>
					</ul>
				</div>
			</div>
			<div style="width:79%;float:right;">
				<div style="height:73px;display:block;min-height:82%;" data-role="page-content"></div>
			</div>
		</div>
	</body>
</html>