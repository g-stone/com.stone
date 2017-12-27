/*!
 * index
 */
var jmei = jmei || {};

jmei.cases = (typeof(jmei.cases) == 'object' ? jmei.cases : function(){
	this.editUrl = jmei.webpath + '/spring/cms/article/category/edited';
	this.listUrl = '/spring/cms/article/category/list';
});
jmei.cases.prototype = {
	initScriptAction: function(){
		if(curObj){
			$.each($('[data-role="edit"]'), function(index, src){
				if($(src).attr('name') in curObj){
					if($(src)[0].tagName == 'INPUT' || $(src)[0].tagName == 'SELECT'){
						$(src).val(curObj[$(src).attr('name')]);
					}
				}
			});
		}
		
		var editedUrl = this.editUrl;
		var listedUrl = this.listUrl;
		
		$('button[name="submit"]').bind('click', function(){
			$.each($('[data-role="edit"]'), function(index, src){
				if($(src)[0].tagName == 'INPUT' || $(src)[0].tagName == 'SELECT'){
					curObj[$(src).attr('name')] = $(src).val();
				}
			});
			
			$.ajax({
				type: 'POST',
				url: editedUrl,
				data: JSON.stringify(curObj),
				success: function(rs){
					jmei.open(listedUrl);
				},
				dataType: 'json',
				contentType: 'application/json',
			});
		});
	}
};
if('object' == (typeof module) && 'object' == (typeof module.exports)){
	module.exports = jmei;
}else{
	window.jmei = jmei;
}
$(function(){
	var s = new jmei.cases();
	s.initScriptAction();
});
