/*!
 * menu index
 */
var jmei = jmei || {};

jmei.menuindex = (typeof(jmei.menuindex) == 'object' ? jmei.menuindex : function(){
	this.opts = {
		see: function(){
			console.log('opts-->see');
		}
	};
	this.navigation = {el:'ul', 
			attrs: [{key:'id', vals:['nav']}, {key:'class', vals:['nav','clearfix']}], 
			navis: [
			    {el:'li', aid:'', uri:'/spring/index', name:'首页', attrs:[{key:'class', vals:['nLi']}]},
			    {el:'li', aid:'a071e8e9d37911e792de00ffcff25f0f', uri:'/spring/brief?vi=1&id=', name:'简介', attrs:[{key:'class', vals:['nLi']}]},
			    {el:'li', aid:'', uri:'/spring/case/list', name:'案例', 
			        sub:{el:'ul', attrs:[{key:'class', vals:['sub']}, {key:'data-role', vals:['casecategory']}]}, 
			        attrs:[{key:'class', vals:['nLi']}]},
			    {el:'li', aid:'a435535ed37911e792de00ffcff25f0f', uri:'/spring/service?vi=3&id=', name:'服务', attrs:[{key:'class', vals:['nLi']}]},
			    {el:'li', aid:'9959627cd37911e792de00ffcff25f0f', uri:'/spring/trends?vi=4&id=', name:'动态', attrs:[{key:'class', vals:['nLi']}]},
			    {el:'li', aid:'f9a32a52d37911e792de00ffcff25f0f', uri:'/spring/recruit?vi=5&id=', name:'招聘', attrs:[{key:'class', vals:['nLi']}]},
			    {el:'li', aid:'f91e6461d37911e792de00ffcff25f0f', uri:'/spring/contact?vi=6&id=', name:'联系我们', attrs:[{key:'class', vals:['nLi']}]}
			]};
});
jmei.menuindex.prototype = {
	see: function(){
		console.log('see');
	},
	menu: function(ele){
		$.ajax({
			url: jmei.webpath + '/spring/case/category',
			dataType: 'json',
			success: function(data){
				if(data.code == 1){
					data.data.forEach(function(val, index, self){
						ele.forEach(function(el, ex, ed){
							$(el.container).append($('<' + el.ele + '><a href="' + jmei.webpath + '/spring/case/list?id=' + val.caseCategoryId + '" data-role="caseCategoryLoading" data-case-category="' + val.caseCategoryId + '">' + val.categoryName + '</a></' + el.ele + '>'))
						});
					});
				}
			}
		});
	},
	navi: function(opts){
		var container = $(opts.el);
		var currentIndex = opts.index || 0;
		
		var htmlCtx = '<' + this.navigation.el;
		this.navigation.attrs.forEach(function(nv, nx, nas){
			htmlCtx += ' ' + nv.key + '="';
			
			nv.vals.forEach(function(av, ax, aas){
				htmlCtx += (ax == 0 ? '' : ' ') + av;
			});
			
			htmlCtx += '"';
		});
		htmlCtx += '>';
		
		this.navigation.navis.forEach(function(nv, nx, nas){
			htmlCtx += '<' + nv.el;
			
			nv.attrs.forEach(function(av, ax, aas){
				htmlCtx += ' ' + av.key + '="';
				
				av.vals.forEach(function(vv, vx, vas){
					htmlCtx += (vx == 0 ? '' : ' ') + vv;
				});
				
				htmlCtx += (nx == currentIndex ? (av.key == 'class' ? ' on' : '') : '');
				
				htmlCtx += '"';
			});
			htmlCtx += '>';
			
			htmlCtx += '<h3><a href="' + jmei.webpath + nv.uri + nv.aid + '" target="_blank">' + nv.name + '</a></h3>';
			
			if('sub' in nv){
				htmlCtx += '<' + nv.sub.el;
				
				nv.sub.attrs.forEach(function(av, ax, aas){
					htmlCtx += ' ' + av.key + '="';
					
					av.vals.forEach(function(vv, vx, vas){
						htmlCtx += (vx == 0 ? '' : ' ') + vv;
					});
					
					htmlCtx += '"';
				});
				
				htmlCtx += '></' + nv.sub.el + '>';
			}
			
			htmlCtx += '</' + nv.el + '>';
		});
		
		htmlCtx += '</' + this.navigation.el + '>';
		
		container.append($(htmlCtx));
	}
};
if('object' == (typeof module) && 'object' == (typeof module.exports)){
	module.exports = jmei;
}else{
	window.jmei = jmei;
}
