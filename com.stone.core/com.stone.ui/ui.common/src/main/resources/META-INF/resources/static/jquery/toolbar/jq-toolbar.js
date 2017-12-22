/**
 * @author huangjingwen
 * 按钮工具栏
 * @version 1.0
    buttons=[ {
	        id: '04', //按钮id
	        iconCls: '',//图标样式
	        cmd: 'test',//对应的权限标识
	        params:{pr1:123},//额外的参数
	        statu: false,//是否可用
	        text: '按钮',//文本
	        click: function (pr) {//点击事件，如果存在click，则字符串形式的handler不可用
	        },
	        handler:'字符串形式的事件', //配合methodsObject使用
	        methodsObject:'window对象下的事件集合名词'
    }]
 */
(function ($) {
    var defaultOpts = {
        params:{},//用于集成到tree datagrid时 行按钮的数据参数
        align: 'left',//对齐方式，默认是left 、center、right
        style:'normal',// normal / min / plain
        buttons: [], //{} 
        operated: function () { }//任意按钮事件均触发的事件，优先自身注册的时候然后才调用通用事件,this= a标签
    };
    function _addButton(target,buttons,isInit) {
        var opts = target.data("opts");
        $.each(buttons, function (i, btnOpt) {
            if (!isInit)
                opts.buttons.push(btnOpt);   
            var statu = typeof btnOpt.statu === 'undefined' ? true : btnOpt.statu;
            var $a_btn ,a_cls="button";
            if (opts.style === "min")
                a_cls = "min_button";
            else if (opts.style === "plain")
                a_cls = "plain_button";
            $a_btn = $("<a cmd='" + btnOpt.cmd + "' id='" + btnOpt.id + "' statu='" + statu + "' class='" + a_cls + "'></a>").appendTo(target);
            if (opts.style !== 'plain')
                $a_btn.append("<span>" + btnOpt.text + "</span>");
            else
                $a_btn.html(btnOpt.text);

            if ($.isFunction(btnOpt.click)) {
                $a_btn.click(function (e) {
                    if (e && e.stopPropagation)
                        e.stopPropagation();
                    else
                        window.event.cancelBubble = true;
                    if ($(this).attr('statu') === 'true') {
                        if ($.isFunction(btnOpt.click))
                            btnOpt.click($.extend({}, { id: btnOpt.id, cmd: btnOpt.cmd }, $.isPlainObject(btnOpt.params)) ? btnOpt.params : {});
                        opts.operated.call($(this));
                    }
                });
            } else {
                if (window[btnOpt.methodsObject] != undefined) {
                    if (typeof window[btnOpt.methodsObject][btnOpt.handler] === 'function') {
                        $a_btn.bind("click", function (e) {
                            if (e && e.stopPropagation)
                                e.stopPropagation();
                            else
                                window.event.cancelBubble = true;
                            var _pr = $.isPlainObject(btnOpt.params) ? btnOpt.params : {};
                            _pr = $.extend({}, _pr, opts.params);
                            window[btnOpt.methodsObject][btnOpt.handler].call($a_btn, $.extend({}, { id: btnOpt.id, cmd: btnOpt.cmd }, _pr));
                            opts.operated.call($(this));
                        });
                    }
                }
            }
            if (!statu) {
                $a_btn.addClass("buttonDisabled");
            }
            if (btnOpt.iconCls && btnOpt.iconCls != ''&&opts.style!=='plain') {
                var $ioc = $("<div class='buttonIcon " + btnOpt.iconCls + " '></div>").appendTo($a_btn);
                if (opts.style === "min")
                    $ioc.css("top","2px");
                $a_btn.children("span").css("padding-left","16px");
            }
        });
    }
    var methods = {
        /**
        *初始化工具栏
        ***/
        init: function () {
            var options = arguments.length == 0 ? {} : arguments[0];
            return this.each(function () {
                var opts = $.extend({}, defaultOpts, options);
                var target = $(this);
                if (opts.style === 'normal')
                    $(this).addClass("buttonWrap");
                target.data("opts", opts);
                _addButton(target, opts.buttons, true);
                target.append("<div style='clear:both'/>");
            });
        },
        /**
        *添加按钮（可以批量添加）
        *args buttons=[]//按钮的json配置
        ***/
        addButtons: function () {
            var buttons = arguments.length == 0 ? [] : arguments[0];
            return this.each(function () {
                var target = $(this);
                var opts = target.data("opts");
                _addButton(target, buttons, false);
                target.children("div").remove().appendTo(target);
            });
        },
        /***
        *删除按钮（可以批量删除）
        *args btnIds=[] //按钮的id数组
        ***/
        delButtons: function () {
            var btnIds = arguments.length == 0 ? [] : arguments[0];
            return this.each(function () {
                var target = $(this);
                var opts = target.data("opts");              
                var newButtons = [];             
                $.each(opts.buttons, function (j,btnOpt) {
                    var isDel = false;
                    $.each(btnIds, function (i, id) {                        
                        if (btnOpt.id === id) {
                            isDel = true;
                            return false;
                        }
                    });
                    if (isDel) {                     
                        target.children("#"+btnOpt.id).remove();
                    } else {
                        newButtons.push(btnOpt);
                    }
                });
                opts.buttons = newButtons;               
            });
        },
        /**
        *禁用按钮（可以批量禁用）
        *args  btnIds=[] //按钮的id数组
        ***/
        disableButtons: function () {
            var btnIds = arguments.length == 0 ? [] : arguments[0];
            return this.each(function () {
                var target = $(this);
                if (btnIds.length == 0) {
                    target.children("a").attr("statu", "false").addClass("buttonDisabled");
                } else {
                    $.each(btnIds, function (i, id) {                       
                        target.children('#' + id).attr("statu", "false").addClass("buttonDisabled");
                    });
                }
            });
        },
        /**
         *启用按钮（可以批量启用）
         *args  btnIds=[] //按钮的id数组
         ***/
        enableButtons: function () {
            var btnIds = arguments.length == 0 ? [] : arguments[0];
            return this.each(function () {
                var target = $(this);
                if (btnIds.length == 0) {
                    target.children('a').attr("statu", "true").removeClass('buttonDisabled');
                } else {
                    $.each(btnIds, function (i, id) {
                        target.children('#' + id).attr("statu", "true").removeClass('buttonDisabled');
                    });
                }
            });
        }
    };
    $.fn.toolbar = function () {
        var method = arguments[0];
        if (methods[method]) {
            method = methods[method];
            arguments = Array.prototype.slice.call(arguments, 1);
        } else if (typeof (method) == 'object' || !method) {
            method = methods.init;
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.toolbar');
            return this;
        }
        return method.apply(this, arguments);
    };
})(jQuery);