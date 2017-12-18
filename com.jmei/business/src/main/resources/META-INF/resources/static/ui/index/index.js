/*!
 * index
 */
var jmei = jmei || {};

jmei.index = (typeof(jmei.index) == 'object' ? jmei.index : function(){
	this.opts = {
		see: function(){
			console.log('opts-->see');
		}
	};
});
jmei.index.prototype = {
	see: function(){
		console.log('see');
	},
	lookup: function(){
		console.log('loop...');
	},
	initScriptAction: function(){
		$('div.box').hover(function(e){
			$(this).find('div.gdtip').show();
		}, function(e){
			$(this).find('div.gdtip').hide();
		});
	}
};
if('object' == (typeof module) && 'object' == (typeof module.exports)){
	module.exports = jmei;
}else{
	window.jmei = jmei;
}
$(function(){
	
	var menu = new jmei.menuindex();
	
	menu.navi({el:'div[class="menu"]', index:0});
	
	menu.menu([
	  {container:'dl[data-role="casecategory"]', ele:'dd'}, 
	  {container:'ul[data-role="casecategory"]', ele:'li'}]);
	
	$('#nav').slide({
		type: 'menu',// 效果类型，针对菜单/导航而引入的参数（默认slide）
		titCell: '.nLi', //鼠标触发对象
		targetCell: '.sub', //titCell里面包含的要显示/消失的对象
		effect: 'slideDown', //targetCell下拉效果
		delayTime: 300, //效果时间
		triggerTime: 0, //鼠标延迟触发时间（默认150）
		returnDefault: true //鼠标移走后返回默认状态，例如默认频道是“预告片”，鼠标移走后会返回“预告片”（默认false）
	});
	
	var s = new jmei.index();
	s.opts.see();
	s.see();
	s.initScriptAction();
	
	$('.slideBox').slide({mainCell:'.bd ul', autoPlay:true});
});
