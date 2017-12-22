/*!
 * index
 */
var jmei = jmei || {};

jmei.cases = (typeof(jmei.cases) == 'object' ? jmei.cases : function(){
	this.opts = {
		see: function(){
			console.log('opts-->see');
		}
	};
	this.grid = null;
	this.toolbar = null;
	this.pageUrl = jmei.webpath + '/spring/cms/case/page';
});
jmei.cases.prototype = {
	see: function(){
		console.log('see');
	},
	lookup: function(){
		console.log('loop...');
	},
	initScriptAction: function(){
		this.toolbar = $('#toolbar').toolbar(this.toolbarOpts);
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
			colNames:['名称', '是否展示', '最后修改日期'],
			colModel:[
				{name:'caseName',index:'caseName', width:55},
				{name:'isShow',index:'isShow', width:90},
				{name:'updateDate',index:'updateDate', width:100}
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
	},
	resizeGridDemosion: function(){
		$('#gridtable').setGridHeight(($('div[data-role="page-content"]').height() * 0.9 - ($('#gridpage').height() + $('#toolbar').height())));
		$('#gridtable').setGridWidth($('div[data-role="page-content"]').outerWidth() * 0.99);
	},
	toolbarOpts: {style: 'ext', 
		buttons: [
		{id: "bar_add", iconCls: "add", text: "新增", click: function(pr){console.log("新增");}},
		{id: "bar_modify", iconCls: "modify", text: "编辑", click: function(pr){console.log("编辑");}},
		{id: "bar_delete", iconCls: "delete", text: "删除", click: function(pr){console.log("删除");}},
		{id: "bar_search", iconCls: "search", text: "查询", click: function(pr){console.log("查询");}}]}
};
if('object' == (typeof module) && 'object' == (typeof module.exports)){
	module.exports = jmei;
}else{
	window.jmei = jmei;
}
$(function(){
	var s = new jmei.cases();
	s.initScriptAction();
	$(window).resize(function(){
		s.resizeGridDemosion();
	});
});
