<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="/common/meta.jsp">
			<jsp:param value="文章栏目管理" name="cms_page_title"/>
		</jsp:include>
		<link href="${ctpath}/static/ui/article/article-edit.css" rel="stylesheet"/>
		<script src="${ctpath}/static/ui/article/article-edit.js" type="text/javascript"></script>
		<link href="${ctpath}/static/kindeditor/themes/default/default.css" rel="stylesheet" />
		<link href="${ctpath}/static/kindeditor/plugins/code/prettify.css" rel="stylesheet" />
		<script src="${ctpath}/static/kindeditor/kindeditor-all.js" charset="utf-8" type="text/javascript"></script>
		<script src="${ctpath}/static/kindeditor/lang/zh-CN.js" charset="utf-8" type="text/javascript"></script>
		<script src="${ctpath}/static/kindeditor/plugins/code/prettify.js" charset="utf-8" type="text/javascript"></script>
		<script>
			var curObj = ${jsonObj};
		</script>
	</head>
	<body>
		<div class="editor" data-role="container">
			<h2>编辑----添加----修改</h2>
			<table class="editpanel">
				<tr>
					<td>标题：</td>
					<td><input data-role="edit" name="articleTitle" type="text"/></td>
				</tr>
				<tr>
					<td>分类：</td>
					<td>
						<select data-role="edit" name="articleCategoryId">
						<c:forEach var="category" items="${categories}" varStatus="stu">
						<option value="${category.articleCategoryId}">${category.categoryName}</option>
						</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>是否展示：</td>
					<td>
						<select data-role="edit" name="isShow">
							<option value="0">不展示</option>
							<option value="1">展示</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>排序：</td>
					<td><input data-role="edit" name="sortNo" type="text"/></td>
				</tr>
				<tr>
					<td>内容：</td>
					<td>
						<textarea data-role="edit" data-type="kindeditor" name="contents"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<button name="submit" class="btn">提交</button>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>