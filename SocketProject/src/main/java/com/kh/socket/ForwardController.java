package com.kh.socket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForwardController {

		@GetMapping("test-page")
		public String testPage() {
			return "test";
		}
		
		@GetMapping("group")
		public String groupPage() {
			return "group";
		}
}
