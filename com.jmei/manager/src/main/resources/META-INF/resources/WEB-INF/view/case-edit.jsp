<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="/common/meta.jsp">
			<jsp:param value="案例编辑" name="cms_page_title"/>
		</jsp:include>
		<link href="${ctpath}/static/ui/case/case-edit.css" rel="stylesheet"/>
		<script src="${ctpath}/static/ui/case/case-edit.js" type="text/javascript"></script>
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
					<td>案例名：</td>
					<td><input data-role="edit" name="caseName" type="text"/></td>
				</tr>
				<tr>
					<td>分类：</td>
					<td>
						<select data-role="edit" name="caseCategoryId">
						<c:forEach var="category" items="${categories}" varStatus="stu">
						<option value="${category.caseCategoryId}">${category.categoryName}</option>
						</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>行业：</td>
					<td>
						<select data-role="edit" name="industryCategoryId">
						<c:forEach var="industry" items="${industries}" varStatus="stu">
						<option value="${industry.industryCategoryId}">${industry.categoryName}</option>
						</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>合作商：</td>
					<td><input data-role="edit" name="employ" type="text"/></td>
				</tr>
				<tr>
					<td>缩略图：</td>
					<td>
						<img data-role="edit" name="caseOptionImage" src="" style="width:64px;height:64px;border:none;cursor:pointer;">
						<input data-role="edit" name="caseOptionImage" type="hidden"/>
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
					<td>摘要：</td>
					<td>
						<textarea data-role="edit" data-type="kindeditor" name="shortDesc"></textarea>
					</td>
				</tr>
				<tr>
					<td>描述：</td>
					<td>
						<textarea data-role="edit" data-type="kindeditor" name="caseDesc"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2"><button name="submit" class="btn">提交</button></td>
				</tr>
			</table>
		</div>
	</body>
</html>