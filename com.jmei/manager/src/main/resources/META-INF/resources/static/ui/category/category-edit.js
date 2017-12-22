/*!
 * index
 */
var jmei = jmei || {};

jmei.category = (typeof(jmei.category) == 'object' ? jmei.category : function(){
	this.editUrl = jmei.webpath + '/spring/cms/case/category/page';
});
jmei.category.prototype = {
	initScriptAction: function(){
		
	}
};
if('object' == (typeof module) && 'object' == (typeof module.exports)){
	module.exports = jmei;
}else{
	window.jmei = jmei;
}
$(function(){
	var s = new jmei.category();
	s.initScriptAction();
});
