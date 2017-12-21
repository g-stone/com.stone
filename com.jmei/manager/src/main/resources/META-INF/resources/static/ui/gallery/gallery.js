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
});
jmei.gallery.prototype = {
	see: function(){
		console.log('see');
	},
	lookup: function(){
		console.log('loop...');
	},
	initScriptAction: function(){
		$('li[data-para]')
			.css({'cursor':'pointer'})
			.bind('click', function(){
				var para = eval('(' + $(this).attr('data-para') + ')');
				$('div[data-role="page-content"]').load(para.url);
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
