package com.jmei.manager.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/spring/cms")
@RestController("galleryController")
public class GalleryController {
	@RequestMapping(value = "/gallery")
	public ModelAndView site(Model model){
		ModelAndView view = new ModelAndView();
		
		view.setViewName("gallerys");
		return view;
	}
}
