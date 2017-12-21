/**
 * java script extends
 * @Auth L@copyright
 * @Date 2017-11-01 16:23:21
 */

/**
 * 日期格式化扩展
 */
Date.prototype.Format = function(fmt){ 
	var o = {
		"M+": this.getMonth() + 1,
		"d+": this.getDate(),
		"h+": this.getHours(),
		"m+": this.getMinutes(),
		"s+": this.getSeconds(),
		"q+": Math.floor((this.getMonth() + 3) / 3),
		"S": this.getMilliseconds()
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
	if (new RegExp("(" + k + ")").test(fmt))
		fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};

/**
 * JS提交参数处理
 */
var prepareAjax = function(ajax){
	var strs= new Array();
	if(ajax.type == 'GET'){
		strs = ajax.url.split('?');
		ajax.url = strs[0];
		if(strs.length > 1){
			strs = strs[1].split('&');
			var md = {};
			for(var index=0; index<strs.length;index++){
				md[strs[index].split('=')[0]] = decodeURIComponent(strs[index].split('=')[1]);
			}
			var query = '';
			var first = true;
			for(var key in md){
				if(first){
					first = false;
					query += key + '=' + md[key];
				}else{
					query += '&' + key + '=' + md[key];
				}
			}
			ajax.url = ajax.url + '?' + query;
		}
	}
	if(ajax.data){
		if(ajax.contentType == 'application/json'){
			strs = ajax.data.split('&');
			var md = {};
			var key = '';
			for(var index=0; index<strs.length;index++){
				key = decodeURIComponent(strs[index].split('=')[0]);
				if(key.indexOf('[') != -1){
					md[key.split('[')[0]] = md[key.split('[')[0]] || {};
					md[key.split('[')[0]][key.split('[')[1].replace(']','')] = decodeURIComponent(strs[index].split('=')[1]);
				}else{
					md[strs[index].split('=')[0]] = decodeURIComponent(strs[index].split('=')[1]);
				}
			}
			
			ajax.data = JSON.stringify(md);
		}
	}
};

/**
 * jQuery 插件编程
 * @param $
 */
(function($){
	/**
	 * 浏览器参数获取函数
	 */
	$.getUrlParam = function(url, key){
		var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
		var r = url.substr(1).match(reg);
		if (r != null){
			return unescape(r[2]);
		} else{
			return null;
		}
	};
	/**
	 * 解析日期字符串函数
	 */
	$.parseDate = function(datestring){
		var tmp = datestring.split(' '),
		date = '',
		time = '';
		if(tmp.length < 1){
			return null;
		}
		
		if(tmp.length == 1){
			date = tmp[0];
			if(date.indexOf('-') != -1){
				var dates = date.split('-');
				return new Date(parseInt(dates[0]), parseInt(dates[1]) - 1, parseInt(dates[2]));
			}else{
				return new Date(parseInt(date.substring(0, 4)), parseInt(date.substring(4, 6)) - 1, parseInt(date.substring(6)));
			}
		}
		if(tmp.length > 1){
			date = tmp[0];
			time = tmp[1];
			var dates = date.split('-');
			var times = time.split(':');
			return new Date(parseInt(dates[0]), parseInt(dates[1]) - 1, parseInt(dates[2]), parseInt(times[0]), parseInt(times[1]), (times.length > 2 ? parseInt(times[2]) : 0));
		}
	};
})(jQuery);