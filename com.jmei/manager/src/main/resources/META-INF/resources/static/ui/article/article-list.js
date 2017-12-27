/*!
 * index
 */
var jmei = jmei || {};

jmei.article = (typeof(jmei.article) == 'object' ? jmei.article : function(){
	this.grid = null;
	this.toolbar = null;
	this.pageUrl = jmei.webpath + '/spring/cms/article/page';
});
jmei.article.prototype = {
	initScriptAction: function(){
		var url = this.pageUrl;
		this.grid = $('#gridtable').jqGrid({ 
			url: url,
			datatype: 'json',
			mtype: 'POST',
			ajaxGridOptions: {
				contentType: 'application/json',
				beforeSend: function(){
					prepareAjax(this);
				}
			},
			colNames:['名称', '分类', '是否展示', '最后修改日期', 'ID'],
			colModel:[
				{name:'articleTitle',index:'articleTitle', width:55},
				{name:'categoryName',index:'categoryName', width:55},
				{name:'isShow',index:'isShow', width:90},
				{name:'updateDate',index:'updateDate', width:100},
				{name:'articleId',index:'articleId', width:0, hidden:true}
			],
			prmNames: {page:'pageIndex', rows:'pageSize', search:'query'},
			gridComplete: function(){},
			loadComplete: function(xhr){},
			postData: {},
			jsonReader: {
				root: 'beanList', 
				page: 'currentPage', 
				total: 'pages',
				records: 'rows', 
				repeatitems: false
			},
			rowNum:10,
			rowList:[10, 20, 30],
			pager: '#gridpage',
			viewrecords: true,
			pgtext: '<table><tr><td>{0}</td><td>共{1}页</td></tr></table>',
			loadui: 'block',
			caption:'行业分类...',
			autowidth: true,
			shrinkToFit: true,
			rownumbers: true
		});
		this.resizeGridDemosion();
		this.initToolbar();
	},
	resizeGridDemosion: function(){
		$('#gridtable').setGridHeight(($('div[data-role="page-content"]').height() * 0.9 - ($('#gridpage').height() + $('#toolbar').height())));
		$('#gridtable').setGridWidth($('div[data-role="page-content"]').outerWidth() * 0.99);
	},
	initToolbar: function(){
		var grid = this.grid;
		var barOptions = {style: 'ext', 
				buttons: [
					{id: 'bar_add', iconCls: 'add', text: '新增', 
						click: function(){
							jmei.open('/spring/cms/article/edit', {opt:'add'});
						}},
					{id: 'bar_modify', iconCls: 'modify', text: '编辑', 
						click: function(){
							var rowno = grid.jqGrid('getGridParam','selrow');
							if(rowno){
								var rowmodel = grid.jqGrid('getRowData',rowno);
								
								jmei.open('/spring/cms/article/edit', {opt:'mod', oid:rowmodel.articleId});
							}else{
								layer.alert('请先选择要编辑的记录！', {icon: 6});
							}
						}},
					{id: 'bar_delete', iconCls: 'delete', text: '删除', 
						click: function(){
							var rowno = grid.jqGrid('getGridParam','selrow');
							if(rowno){
								var rowmodel = grid.jqGrid('getRowData',rowno);
								
								$.ajax({
									type: 'GET',
									url: jmei.webpath + '/spring/cms/article/delete',
									data: {opt:'mod', ids:rowmodel.articleId},
									success: function(rs){
										jmei.open('/spring/cms/article/list');
									},
									dataType: 'json',
									contentType: 'application/json',
								});
							}else{
								layer.alert('请先选择要删除的记录！', {icon: 6});
							}
						}},
					{id: 'bar_search', iconCls: 'search', text: '查询', 
						click: function(pr){
							jmei.open('/spring/cms/article/list', {});
						}}
					]};
		this.toolbar = $('#toolbar').toolbar(barOptions);
	}
};
if('object' == (typeof module) && 'object' == (typeof module.exports)){
	module.exports = jmei;
}else{
	window.jmei = jmei;
}
$(function(){
	var s = new jmei.article();
	s.initScriptAction();
	$(window).resize(function(){
		s.resizeGridDemosion();
	});
});
