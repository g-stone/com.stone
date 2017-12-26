package com.stone.data.controller;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import com.stone.data.service.ICommonService;
import com.stone.tools.model.ResultObject;

public class BasicController {
	
	protected <T> void editPreOption(Model model, HttpServletRequest req, Class<T> obj){
		String opt = req.getParameter("opt");
		
		if(StringUtils.hasText(opt)){
			opt = opt.trim().toLowerCase();
			List<String> opts = new ArrayList<String>();
			if(opts.contains(opt)){
				if("add".equals(opt)){
					model.addAttribute("opt", "add");
				}
				if("mod".equals(opt)){
					model.addAttribute("opt", "mod");
					
					String id = req.getParameter("oid");
					T m = commonService.get(obj, id);
					model.addAttribute("obj", m);
				}
			}else{
				model.addAttribute("opt", "add");
			}
		} else {
			model.addAttribute("opt", "add");
		}
	}
	
	protected <T> ResultObject delPreOption(HttpServletRequest req, Class<T> obj){
		ResultObject result = new ResultObject();
		result.setCode(ResultObject.SUCCESS);
		result.setMsg("删除成功！");
		String ids = req.getParameter("ids");
		if(StringUtils.hasText(ids)){
			String[] arrayIds = ids.split(",");
			for(String id:arrayIds){
				if(StringUtils.hasLength(id)){
					commonService.delete(obj, id);
				}
			}
		}
		
		return result;
	}
	
	private static final ThreadLocal<SoftReference<Map<Class<?>, Logger>>> 
	THREADLOCAL_LOGGER = new ThreadLocal<SoftReference<Map<Class<?>, Logger>>>() {
		@Override
		protected SoftReference<Map<Class<?>, Logger>> initialValue() {
			return new SoftReference<Map<Class<?>, Logger>>(
					new HashMap<Class<?>, Logger>());
		}
	};
	private ICommonService commonService;
	private static List<String> opts;
	static{
		opts = new ArrayList<String>();
		opts.add("add");
		opts.add("mod");
	}
	
	protected Logger get(){
		final SoftReference<Map<Class<?>, Logger>> ref = THREADLOCAL_LOGGER.get();
		Map<Class<?>, Logger> logs = ref.get();
		
		if(logs == null){
			logs = new HashMap<Class<?>, Logger>();
			THREADLOCAL_LOGGER.set(new SoftReference<Map<Class<?>, Logger>>(logs));
		}
		
		Logger logger = logs.get(getClass());
		if(logger == null){
			logger = Logger.getLogger(getClass());
			logs.put(getClass(), logger);
		}
		
		return logger;
	}
	protected ICommonService getCommonService() {
		return commonService;
	}
	@Resource(name = "commonService")
	protected void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}
}
