/*!
 * index
 */
var jmei = jmei || {};

jmei.cases = (typeof(jmei.cases) == 'object' ? jmei.cases : function(){
	this.editUrl = jmei.webpath + '/spring/cms/case/edited';
	this.listUrl = '/spring/cms/case/list';
	this.kindeditors = {};
});
jmei.cases.prototype = {
	initScriptAction: function(){
		if(curObj){
			$.each($('[data-role="edit"]'), function(index, src){
				if($(src).attr('name') in curObj){
					if($(src)[0].tagName == 'INPUT' || $(src)[0].tagName == 'SELECT' || $(src)[0].tagName == 'TEXTAREA'){
						$(src).val(curObj[$(src).attr('name')]);
					}
					if($(src)[0].tagName == 'IMG'){
						$(src).attr('src', curObj[$(src).attr('name')]);
					}
				}
			});
		}
		
		var editors = {};
		$.each($('textarea[data-role="edit"][data-type="kindeditor"]'), function(index, src){
			var editor = KindEditor.create(src, {
				cssPath : jmei.webpath + '/static/kindeditor/plugins/code/prettify.css',
				uploadJson : jmei.webpath + '/spring/kindeditor/upload',
				fileManagerJson : jmei.webpath + '/spring/kindeditor/manager',
				allowFileManager : true,
				allowImageUpload : true,
				resizeType: 1,
				allowPreviewEmoticons:false,
				items : [
					'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
					'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
					'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
					'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
					'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
					'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
					'anchor', 'link', 'unlink', '|', 'about'
				]});
			editors[$(src).attr('name')] = editor;
		});
		
		var editedUrl = this.editUrl;
		var listedUrl = this.listUrl;
		this.kindeditors = editors;
		
		$('button[name="submit"]').bind('click', function(){
			$.each($('[data-role="edit"]'), function(index, src){
				if($(src)[0].tagName == 'INPUT' || $(src)[0].tagName == 'SELECT' || $(src)[0].tagName == 'TEXTAREA'){
					if($(src).attr('name') in editors){
						editors[$(src).attr('name')].sync();
					}
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
