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
		<title>佳美文印CMS</title>
		<link href="${ctpath}/static/kindeditor/themes/default/default.css" rel="stylesheet" />
		<link href="${ctpath}/static/kindeditor/plugins/code/prettify.css" rel="stylesheet" />
		<link href="${ctpath}/static/css/global.css" rel="stylesheet"/>
		<link href="${ctpath}/static/ui/index/index.css" rel="stylesheet"/>
		<!--script src="${ctpath}/static/jquery/jquery-1.12.4.js" type="text/javascript"></script-->
		<!--script src="${ctpath}/static/ui/index/index.js" type="text/javascript"></script-->
		<script src="${ctpath}/static/kindeditor/kindeditor-all.js" charset="utf-8" type="text/javascript"></script>
		<script src="${ctpath}/static/kindeditor/lang/zh-CN.js" charset="utf-8" type="text/javascript"></script>
		<script src="${ctpath}/static/kindeditor/plugins/code/prettify.js" charset="utf-8" type="text/javascript"></script>
		<script>
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
		</script>
	</head>
	<body>
		<form action="" id="example" name="example">
			<textarea name="content1" cols="100" rows="8" style="width:700px;height:200px;visibility:hidden;">sssss</textarea><br />
			<input type="submit" name="button" value="提交内容" />
		</form>
	</body>
</html>