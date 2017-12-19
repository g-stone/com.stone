package com.jmei.manager.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 管理工作台控制器
 * @author dba
 */
@RequestMapping("/spring/cms")
@RestController("cmsGalleryController")
public class CmsGalleryController {
	@RequestMapping(value = "/gallery")
	public ModelAndView gallery(Model model){
		ModelAndView view = new ModelAndView();
		
		view.setViewName("gallerys");
		return view;
	}
}
