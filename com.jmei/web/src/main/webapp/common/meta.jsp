<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<meta charset="utf-8">
<meta name="format-detection" content="telephone=no">
<meta content="IE=edge" http-equiv="X-UA-Compatible">
<link rel="shortcut icon" href="favicon.ico" />
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
<title><%=(request.getParameter("cms_page_title") == null ? "" : request.getParameter("cms_page_title")) %></title>
<link href="${ctpath}/static/css/global.css" rel="stylesheet"/>
<link href="${ctpath}/static/lib/css/icon.css" rel="stylesheet" />

<link href="${ctpath}/static/jquery/grid/css/ui.jqgrid.css" rel="stylesheet" />
<link href="${ctpath}/static/jquery/ui/jquery-ui.theme.min.css" rel="stylesheet" />
<link href="${ctpath}/static/jquery/ui/jquery-ui.structure.min.css" rel="stylesheet" />
<link href="${ctpath}/static/jquery/ui/jquery-ui.min.css" rel="stylesheet" />

<script src="${ctpath}/static/jquery/jquery-1.12.4.js" charset="utf-8" type="text/javascript"></script>
<script src="${ctpath}/static/lib/tools.js" charset="utf-8" type="text/javascript"></script>
<script src="${ctpath}/static/slide/jquery.superslide.2.1.1.js" charset="utf-8" type="text/javascript"></script>

<script src="${ctpath}/static/jquery/ui/jquery.form.min.js" charset="utf-8" type="text/javascript"></script>
<script src="${ctpath}/static/jquery/form/jquery-ui.min.js" charset="utf-8" type="text/javascript"></script>
<script src="${ctpath}/static/jquery/toolbar/jq-toolbar.js" charset="utf-8" type="text/javascript"></script>
<script src="${ctpath}/static/jquery/grid/js/i18n/grid.locale-cn.js" charset="utf-8" type="text/javascript"></script>
<script src="${ctpath}/static/jquery/grid/js/jquery.jqGrid.src.js" charset="utf-8" type="text/javascript"></script>

<!--link href="${ctpath}/static/kindeditor/themes/default/default.css" rel="stylesheet" />
<link href="${ctpath}/static/kindeditor/plugins/code/prettify.css" rel="stylesheet" />
<script src="${ctpath}/static/kindeditor/kindeditor-all.js" charset="utf-8" type="text/javascript"></script>
<script src="${ctpath}/static/kindeditor/lang/zh-CN.js" charset="utf-8" type="text/javascript"></script>
<script src="${ctpath}/static/kindeditor/plugins/code/prettify.js" charset="utf-8" type="text/javascript"></script-->