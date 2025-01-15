package com.kh.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CookieController {
	
	@GetMapping("create")
	public String create(HttpServletResponse response) {
		
		// cookie는 name과 value가 필수!
		Cookie cookie = new Cookie("cookie","candy");
		// name과 value는 무조건!!! 문자열만 가능(아스키코드만가능 한글안됨)
		Cookie saveId = new Cookie("saveId","user01");
		
		// 만료시간 설정하기-> 초단위 (만료기간 하루로 설정하고 싶은 경우 초단위로 계산하면 (1 * 60* 60 *24)
		cookie.setMaxAge(1 * 60* 60 *24);
		
		// 쿠키는 객체를 생성한 뒤 응답정보에 첨부해야 발급
		response.addCookie(cookie);
		response.addCookie(saveId);
		
		return "cookie";
	}
	
	
	@GetMapping("delete")
	public String delete(HttpServletResponse response) {
		
		// 쿠키는 따로 삭제명령이 없음 그럼 어떻게 삭제를 구현하나? 
		// => 만료시간을 0초로 설정해서 다시 발급하면 됨 
		
		Cookie cookie = new Cookie("cookie", "delete");
		Cookie saveId = new Cookie("saveId", "sdfsdf");
		cookie.setMaxAge(0);
		saveId.setMaxAge(0);
		
		response.addCookie(cookie);
		response.addCookie(saveId);
		return "cookie";
	}
	
	@GetMapping("join")
	public String join() {
		return "join";
	}
	
	@GetMapping("ad")
	public String ad() {
		return "ad";
	}
	
	@GetMapping("k")
	public String k() {
		return "Khong";
	}

}
