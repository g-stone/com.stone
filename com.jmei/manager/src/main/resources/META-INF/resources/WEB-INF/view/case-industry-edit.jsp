<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="/common/meta.jsp">
			<jsp:param value="案例行业" name="cms_page_title"/>
		</jsp:include>
		<link href="${ctpath}/static/ui/industry/industry-edit.css" rel="stylesheet"/>
		<script src="${ctpath}/static/ui/industry/industry-edit.js" type="text/javascript"></script>
		<script>
			var curObj = ${jsonObj};
		</script>
	</head>
	<body>
		<div class="editor" data-role="container">
			<h2>编辑----添加----修改</h2>
			<table class="editpanel" style="overflow-y:scroll;">
				<tr>
					<td>分类名：</td>
					<td><input data-role="edit" name="categoryName" type="text"/></td>
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
					<td colspan="2"><button name="submit" class="btn">提交</button></td>
				</tr>
			</table>
		</div>
	</body>
</html>