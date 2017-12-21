/*!
 * index
 */
var jmei = jmei || {};

jmei.gallery = (typeof(jmei.gallery) == 'object' ? jmei.gallery : function(){
	this.opts = {
		see: function(){
			console.log('opts-->see');
		}
	};
	this.grid = null;
	this.pageUrl = jmei.webpath + '/spring/cms/article/category/page';
});
jmei.gallery.prototype = {
	see: function(){
		console.log('see');
	},
	lookup: function(){
		console.log('loop...');
	},
	initScriptAction: function(){
		var url = this.pageUrl;
		this.grid = $("#gridtable").jqGrid({ 
			url: url,
			datatype: 'json',
			mtype: 'POST',
			ajaxGridOptions: {
				contentType: 'application/json',
				beforeSend: function(){
					prepareAjax(this);
				}
			},
			colNames:['Inv No','Date', 'Client', 'Amount','Tax','Total','Notes'],
			colModel:[
				{name:'id',index:'id', width:55},
				{name:'invdate',index:'invdate', width:90},
				{name:'name',index:'name asc, invdate', width:100},
				{name:'amount',index:'amount', width:80, align:"right"},
				{name:'tax',index:'tax', width:80, align:"right"},
				{name:'total',index:'total', width:80,align:"right"},
				{name:'note',index:'note', width:150, sortable:false}
			],
			prmNames: {page:'pageIndex', rows:'pageSize', search:'query'},
			gridComplete: function(){},
			loadComplete: function(xhr){},
			postData: {},
			jsonReader: {
				root: 'beanList', 
				page: 'pages', 
				total: 'rows',
				records: 'size', 
				repeatitems: false
			},
			rowNum:10,
			rowList:[10, 20, 30],
			pager: '#gridpage',
			viewrecords: true,
			sortorder: 'desc',
			caption:'Dynamic hide/show columns',
			autowidth: true,
			shrinkToFit: true
		});
	}
};
if('object' == (typeof module) && 'object' == (typeof module.exports)){
	module.exports = jmei;
}else{
	window.jmei = jmei;
}
$(function(){
	var s = new jmei.gallery();
	s.opts.see();
	s.see();
	s.initScriptAction();
});
