package com.kh.od.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OpenDataController {

	@GetMapping("air.do")
	public String airPage() {
		return "air/air";
	}
	
	@GetMapping("horse.do")
	public String horsePage() {
		return "horse/horse";
	}
	
	@GetMapping("kin.do")
	public String kinPage() {
		return "naver/kin";
	}
	
	@GetMapping("kakao.do")
	public String mapPage() {
		return "kakao/map";
	}
	
	@GetMapping("busan.do")
	public String busanPage() {
		return "busan/busan";
	}
	
	@GetMapping("parking.do")
	public String parkingPage() {
		return "parking/parking";
	}
	

	
}
