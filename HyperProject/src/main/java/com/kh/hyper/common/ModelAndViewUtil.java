package com.kh.hyper.common;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component // bean으로 등록해서 계속 사용하고 싶은데 그냥 class이므로 component로 설정
public class ModelAndViewUtil {
	
	
	public ModelAndView setViewNameAndData(String viewName, Map<String, Object> modelData) {
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName(viewName);
		if(modelData != null) {
			mv.addAllObjects(modelData);
		}
		
		return mv;
		
	}
	
	
	
	

}
