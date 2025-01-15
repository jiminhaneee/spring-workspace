package com.kh.hyper.common.interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
public class LoginInterceptor extends HandlerInterceptorAdapter{
	/*
	 * InterCeptor(정확히는 HandlerInterceptor)
	 *
	 * RequestHandler가 호출되기 전, 실행한 후 가로채서 실행할 내용을 작성 가능
	 *
	 * preHandler (전 처리) : 핸들러 호출 전 낚아챔
	 * postHandler (후 처리) : 요청 처리 후 낚아챔
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
			
		// 반환타입이 boolean이라 true 또는 false 반환
		// true : 기존 요청 호출대로 Handler를 정상수행 ==> Controller에 있는 메소드 호출
		// false : Handler호출 안함 ~ 끝 ~
		
		// 공통으로 빼고 싶은 부분 => controller에 왔는데 마이페이지가 로그인되지 않은 사용는 사용못하는 페이지이면 돌려보내고 싶은것(로그인된 사용자만 들어올 수 있게)
		// 그럼 제일 먼저 로그인이 되었는지 안되었는지를 확인해야한다
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("loginUser") != null) { // 로그인된 사용자
			return true;
		} else { // 로그인안된 사용자
			session.setAttribute("alertMsg", "권한이 없어요"); // 해더에서 알럿띄우기
			response.sendRedirect(request.getContextPath()); // 메인으로 돌려보내기
			return false;
			
		}
	}
}
