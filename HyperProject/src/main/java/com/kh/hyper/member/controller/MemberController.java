package com.kh.hyper.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kh.hyper.common.ModelAndViewUtil;
import com.kh.hyper.member.model.service.MemberService;
import com.kh.hyper.member.model.vo.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor // 룸복에서 제공하는 애노테이션 -> 자동으로 기본생성자를 생성해줌 
public class MemberController {
	
	
	/*
	//@Autowired  //-> 필드주입 (필드에 자동으로 주입시켜주는것) ==> 생성자주입보다 쓸게 줄어듬 그래도 생성자 주입 사용하는것을 추천
	private MemberService memberService; //인터페이스 타입으로 따로 뺌 -> 메소드가 호출했을 때 출동
	
	private BCryptPasswordEncoder passwordEncoder; // 암호하할때 필요한것을 bean에 등록 필요할떄마다 사용하기 위해 필드로 등록해놓기 
	// 이건 null->  생성자 주입을 해줘야함=> 생성자 매개변수 대입해줘야함
	
	// Spring의 D.I(Dependency Injection)
	@Autowired // 생성자주입--> 매개변수와 일치하는 타입의 Bean객체를 검색해서 인자값으로 주입을 해줌!
	// 스프링이 이 생성자를 호출할때 이 인자값을 넣어야지됨 bean에서 이 메소드를 찾을 때 값을 넣어서 찾아준다(내가 가지고 있는 bean을 주입해주는것) / == 생성자 주입 
	public MemberController(MemberService memberService, BCryptPasswordEncoder passwordEncoder) {
		this.memberService = memberService;
		this.passwordEncoder = passwordEncoder;
	}
	
	//===> 필드가 늘어나면 생성자 추가를 계속 해줘야함 -> 귀찮 -> Lombok사용하면 편함
	*/
	
	//무조건 주입을 받고싶은 필드들이기 때문에 final을 붙이면 됨 + 선언과 동시에 초기화 (controller생성할때 받고싶은것이기때문에 위에 애노테이션을 작성
	private final MemberService memberService;
	//private final BCryptPasswordEncoder passwordEncoder; // 요청 받기 . 요청 처리 . 응답 지정 이 역할만 controller가 하게 되면서 password는 없어져도 도미 
	// private String name; final키워드가 붙지 않으면 생성자 매개변수로 포함되지 않음 
	private final ModelAndViewUtil mv;
	
	
	
	/*
	 * public void login(String userId, String userPwd){
	 * 
	 * }
	 * 
	 * public void save(String userId, ...){
	 * 	new MemberView().success
	 * } 
	 * 
	 */
	
	/*
	 * login.me 요청이 들어오면 -> login()를 찾아가얌 -> @RequestMapping(value="어떤요청")
	 * insert.me
	 * update.me
	 */
	/*
	@RequestMapping(value="login.me")//RequestMapping타입의 애노테이션을 등록함으로써 HandlerMapping을 등록 ->bean으로 등록
	public void login() {
		
		System.out.println("로그인 요청을 보냈니?");
	}
	
	@RequestMapping(value="insert")
	public void insert() {
		System.out.println("추가할래?");
	}
	
	@RequestMapping(value="delete")
	public void delete() {
		System.out.println("삭제할래");
	}
	*/
	
	/*
	 * Spring에서 요청 시 전달 값(Parameter)를 받아서 사용하는 방법 
	 * 
	 * 1. HttpServletRequest를 이용해서 전달받기(기존의 JSP / Servlet 방식)
	 * 
	 * 핸들러의 매개변수로 HttpServletRequest타입을 작성해두면
	 * DispatcherServlet이 해당 메서드를 호출할 때 request객체를 전달해서 매개변수로 주입해줌
	 * 
	 */
	/*
	@RequestMapping(value="login.me")
	public String login(HttpServletRequest request) {
		
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		
		System.out.println(id);
		System.out.println(pwd);
		
		return "main";
		
	}
	*/
	/*
	 * 2. @RequestParam 애노테이션을 이용하는 방법
	 * 
	 * request.getParameter("키")로 벨류를 뽑아오는 역할을 대신해주는 애노테이션 
	 * value속성의 값으로 jsp작성한 키 값을 적으면 알아서 해당 매개변수에 주입을 해줌 
	 * 
	 * 만약, 넘어온 값이 비어있는 형태라면 defaultValue속성으로 기본값을 지정할 수 있음
	 * 
	 * defaultValue="대체값" -> 아무런 값이 넘어오지 않았을 때 대체할 기본값 설정 
	 * 
	 */
	/*
	@RequestMapping("login.me")
	public String login(@RequestParam(value="id", defaultValue="user01") String id, @RequestParam(value="pwd") String pwd) {
		
		System.out.println(id);
		System.out.println(pwd);
		
		return "main";
	
	}
	*/
	/*
	 * 3. RequestParam 애노테이션을 생략하는 방법
	 * 
	 * 단, 매개변수 명의 jsp에서 잔달한 key값과 동일하게 세팅해둬야 자동으로 값이 주입
	 * 단점이라고 한다면 키값에 의미가 명확하지 않을 수 있고, defaultValue 속성을 사용할 수 없음 
	 * 
	 */
	/*
	@RequestMapping("login.me")
	public String login(String id, String pwd) {
		
		System.out.println(id);
		System.out.println(pwd);
		
		Member member = new Member();
		member.setUserId(id);
		member.setUserPwd(pwd);
		
		return "main";
	}
	*/
	
	/*
	 * 4. 커맨드 객체 방식 
	 * 
	 * 1. 앞단에서 전달되는 key값과 객체의 필드명이 동일해야함 
	 * 2. 매개변수로 받아오는 객체한테 기본생성자가 반드시 존재해야한다
	 * 3. 값을 넣어주고 싶은 필드에 setter메소드가 반드시 존재해야한다 
	 * 
	 * 
	 * 스프링에서 해당 객체를 기본생성자를 통해서 생성한 후 내부적으로 setter메소드를 찾아서 요청 시 전달값을 해당 필드에 담아줌 == setter injection
	 * 
	 */
	/*
	 * 
	@RequestMapping("login.me")
	public String login(Member member) {
		
		//System.out.println(member);
		
		// new MemberServiceImpl().login(member); -> 문제점 : 의존성이 너무 높아짐 
		// Service클래스의 수정이 일어날 경우 Service클래스를 의존하고 있던 Controller가 영향을 받게됨 
		
		Member loginMember = memberService.login(member);
		if(loginMember != null) {
			System.out.println("로그인 성공");
		}else {
			System.out.println("로그인 실패...");
		}
		
		return "main";
	}
		 */
	

	/*
	 * 	Client의 요청 처리 후 응답데이터를 담아서 응답페이지로 포워딩 또는 URL 재요청 하는 방법
	 * 
	 *  1. 스프링에서 제공하는 Model객체를 사용하는 방법
	 *  포워딩할 응답 뷰로 전달하고자 하는 데이터를 맵형식(key-value)으로 담을 수 있는 영역
	 *  Model객체는 requestScope 대용
	 *  
	 *  단, setAtrribute()가 아닌 addAttribute()를 호출해야함
	 */
	
	/*
	@PostMapping("login.me")
	public String login(Member member, Model model, HttpSession session) {
		
		Member loginMember = memberService.login(member);
		
		if(loginMember != null) { // 정보가 있다 => loginMember를 sessionScope에 담기 
			session.setAttribute("loginUser", loginMember);
			
			// return "main"; -> forwarding -> Redirect
			// sendRedirect
			// localhost/spring
			
			// redirect : 요청할 URL
			return "redirect:/";
			
		}else { //로그인 실패 => 에러문구를 requestScope에 담아서 에러페이지로 포워딩해줄것
			model.addAttribute("errorMsg","로그인 실패!");
			/*
			 *\ /WEB-INF/views/common/error_page.jsp
			 *
			 * String Type return -> viewName에 대입
			 * -> DispatcherServlet -> ViewResolver
			 * 
			 * - prefix : /WEB-INF/views 앞에다가 붙여주고
			 * - 중간 : return viewName;
			 * -suffix : .jsp 뒤에다가
			 *
			
			return "common/error_page";
		}
		
	}
	
	 */
	
	/*
	 * 2. ModelAndView타입을 사용하는 방법
	 * 
	 * Model은 데이터를 key-value세트로 담을 수 있는 객체
	 * View는 응답 뷰에 대한 정보를 담을 수 있음
	 * 
	 * Model객체와 View가 결합된 형태의 객체
	 * 
	 * 
	 */
	@PostMapping("login.me")
	public ModelAndView login(Member member, HttpSession session) {
		
		// 사용자가 입력한 비밀번호 : member => memberPwd필드(평문 == plaintext)
		
		// Member Table의 USER_PWD컬럼에는 암호문이 들어있기 때문에
		// WHERE 조건절의 결과가 제대로 true가 될 순 없음
		
		Member loginMember = memberService.login(member);
		
		// Member타입의 loginMember의 userPwd 필드 : DB에 기록된 암호화된 비밀번호
		// Member타입의 member의 userPwd 필드 : 사용자가 입력한 평문 비밀번호
		
		// BCryptPasswordEncoder객체 : matches()
		// matches(평문, 암호문)
		// 암호문에 포함되어있는 버전과 반복횟수와 소금값을 가지고 인자로 전달된 평문을 다시 암호화를 거쳐서
		// 두 번째 인자로 전달된 암호문과 같은지 다른지를 비교한 결과값을 반환
	
		
		
		//log.info("{}" , member);
		//log.info("{}" , loginMember);
		
		
		
		
		
		//if( loginMember != null && passwordEncoder.matches(member.getUserPwd(), loginMember.getUserPwd())) {
			
			// 조회결과에 있는 경우 session에 담아야하는데 session이 없어서 위에서 
			//session.setAttribute("loginUser", loginMember);
			//session.setAttribute("alertMsg", "로그인에 성공했습니다.");
			//mv.setViewName("redirect:/");
			
		//}else {
			
		//	mv.addObject("errorMsg","로그인 실패..");
		//	mv.setViewName("common/error_page");
			
			//mv.addObject("errorMsg","로그인 실패..").setViewName("common/errorPage"); -> 이렇게도 할 수 있음
			
		//}
		
		//---------------------------------------------------------------------
		
		//로그인 예외처리를 하고나면 아래의 코드만 하면 됨 -> 이렇게하면 controller에서는 요청받기 & 응답받기만 진행!!!
		session.setAttribute("loginUser", loginMember);
		session.setAttribute("alertMsg", "로그인에 성공했습니다.");
		return mv.setViewNameAndData("redirect:/", null);
		
	}
	
	
	@GetMapping("logout.me")
	public String logout(HttpSession session) {
		session.removeAttribute("loginUser");
		return "redirect:/";
	}
	
	
	@GetMapping("enrollform.me") //a태그로 요청을 보냈으므로 get방식
	public String enrollForm() {
		// enrollform.jsp로 보내야함
		
		// /WEB-INF/views/member/enroll_form.jsp
		// /WEB-INF/views/                  .jsp
		return "member/enroll_form"; 
	}
	
	// 회원가입 실패했을 경우도 생각해서 string이 아닌 modelandview로 해야함(오류메세지 보내야하므로)
	@PostMapping("sign-up.me") 
	public ModelAndView singUp(Member member, 
			//ModelAndView mv
			HttpSession session
			, HttpServletRequest request) {
		// 매개변수에는 내가 가공할 타입을 적어준다 
		// + modelandview를 스프링에게 받아야함
		/*
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		*/
		//System.out.println(member);
		//log.info("{}",member);
		
		// 문제점1. 한글 문자가 깨짐 -> post방식으로 왔으니깐 인코딩을 해야함 
		//-> request로 해야하니깐 달라고 해야함(매개변수 httpServlet~) -> 받음 다음에 request.setCharacter~ 
		//-> UnsupportedEncodingException 발생 예외처리를 해줘야함(throws가 없어서 직접해줘야함)
		// 이렇게 해도 한글이 깨짐 왜? 값을 뽑기전!!에 미리 UTF-8방식으로 변경해주어야하기때문에! -> 우린 request에서 값을 다 뽑고 다 담고나서 인코딩함 이건 의미가 없어
		// 그럼 어떻게해야함? -> 한글부분만 다시 값을 뽑아서 담는 방법도 생각해볼 수 있음 member.setUserName(request.getParameter("userName");
		// => 우린 filter라는걸 사용해볼것!! 제공이 되어서 만들어진것만 사용하면 됨 -> 스프링에서 제공하는 "인코딩filter"를 사용하자!!
		
		//인코딩filter --> 어디서 등록하냐-> web.xml
		
		// 문제점2. 나이를 입력하지 않았을 경우 int자료형 age필드에 빈문자열이 Bind되기 때문에 
		// Parsing과정에서 String -> int NumberFormatException이 발생함 (400에러발생)
		// 클라이언트에게 400 Bad Request가 뜸 -> Loombook libaray설치 -> 이걸 사용하면 자료형을 바꿔도 다 자동으로 바꿔줌
		
		
		// 문제점 3. 비밀번호가 사용자가 입력한 그대로의 평문(plain text) -> 암호화를 해줘야함 
		// -> spring security가 필요 -> bean으로 등록해서 사용
		// 필요할 떄마다 부를려고 필드로 등록
		// Bcrypt 이용해서 암호화 -> Spring Security Modules에서 제공(pom.xml)
		// passwordEncoder를 .xml파일을 이용해서 configurationBean으로 Bean등록
		//=> web.xml에서 sprint-security.xml파일을 로딩할 수 있도록 추가 
		
		// 평문 출력(사용자가 입력한 문자열 그대로)
		//System.out.println("평문 : " + member.getUserPwd());
		//log.info("평문 : {}", member.getUserPwd());
		
		// 암호화 작업(암호문을 만드는 방법)
		//String encPwd = passwordEncoder.encode(member.getUserPwd()); //인자값으로 사용자가 입력한 평문을 넣는다 -> 암호문을 반환해줌
		//-> 이 작업을 서비스에서 할 것 
		
		
		// 암호문 출력
		//System.out.println("암호문 : " + encPwd);
		//log.info("암호문 : {}", encPwd);
		
		
		//평문을 암호문으로 바꿔서 대입
		//member.setUserPwd(encPwd); -> 이 작업을 서비스에서 할 것 
		// Member객체 userPwd필드에 평문이 아닌 암호문을 담아서 insert를 수행 
		
		/*
		if(memberService.join(member) > 0) { //->0아니면1이기떄문에 대소비교를 해야함
			session.setAttribute("alertMsg", "회원가입에 성공했습니다.");
			mv.setViewName("redirect:/");
			
		} else {
			mv.addObject("errorMsg","회원가입실패").setViewName("common/error_page");
			//						/WEB-INF/views/		common/error_page	 . jsp
		}
		*/
		//------------------------------------------------------------------------
		// 요청 받기 . 요청 처리 . 응답 지정  -> controller역할 / 회원가입에 대한 로직은 모두 service에서
		memberService.join(member);
		session.setAttribute("alertMsg", "가입 성공 성공");
		//mv.setViewName("redirect:/");
		return mv.setViewNameAndData("redirect:/", null);
	}
	
	
	
	
	@GetMapping("mypage.me")
	public String mypage() {
		
		return "member/my_page";
	}
	
	
	
	// 정보를 가지고 db에가서 update문 실행해서 성공.실패를 돌리는것을 = update
	
	@PostMapping("update.me")
	public ModelAndView update(Member member, 
								//ModelAndView mv, 
								HttpSession session) { // 요청 받기
		
		
		/*
		if(memberService.updateMember(member) > 0) {
		
			// DB로부터 수정된 회원정보를 다시 조회해서
			// sessionScope의 loginUser라는 키값으로 덮어씌울것
			session.setAttribute("loginUser", memberService.updateMember(member));
			session.setAttribute("alertMsg", "정보수정에 성공했습니다.");
			
			mv.setViewName("redirect:mypage.me");
			
		}else { // 수정 실패 => 에러문구를 담아서 에러페이지로 포워딩
			
			mv.addObject("erroMsg", "정보수정에 실패했습니다.").setViewName("common/error_page");
		
		}
		*/
		
		
		//-------------------------------------------------
		
		log.info("{}", member);
		
		//session.setAttribute("loginUser", memberService.login(member)); //요청 처리 -ji
		memberService.updateMember(member, session);
		session.setAttribute("alertMsg", "정보수정에 성공했습니다.");
		//mv.setViewName("redirect:mypage.me"); // 응답지정

		
		return mv.setViewNameAndData("redirect:mypage.me",null);
	}
	
	
	
	
	
	
	
	@PostMapping("delete.me")
	public ModelAndView delete(String userPwd, HttpSession session
								//ModelAndView mv
								) {
		
		//userPwd : 회원 탈퇴 시 요청 시 사용자가 입력한 비밀번호 평문
		// session의 loginUser객체의 userPwd필드 : DB에 기록된 암호화된 비밀번호 -> session에서 뽑아야하므로 HttpSession이 필요
		
		
		
		/*
		Member loginUser = ((Member)session.getAttribute("loginUser"));
		
		String encPwd = loginUser.getUserPwd(); //-> 암호화된 비밀번호를 받기
		
		if(passwordEncoder.matches(userPwd, encPwd)) {//-> 두개를 비교해서 true가 나오면 사용자가 올바르게 입력한 것 
			//비밀번호가 사용자가 입력한 평문을 이용해서 만든 비밀번호일 경우
			 
			 // 일치했으면 탈퇴할 수 있게 해야하므로 -> service로 보냄 -> 다녀오면 update되어서 오는 것이니간 제대로 되었다면 0보다 클 것 
			if(memberService.deleteMember(loginUser, session) > 0) {
				
				session.removeAttribute("loginUser");
				session.setAttribute("alertMsg", "잘가");
				return "redirect:/";
				
				
			}else { //탈퇴가 제대로 완료되지 않은 경우 
				session.setAttribute("alertMsg", "관리자에게 문의하세요");
				return "common/errorPage";
			}
		}else {//true가 아니면 비밀번호를 틀리게 작성한 것 
			session.setAttribute("alertMsg", "비밀번호가 일치하지 않습니다.");
			return "redirect:mypage.me";
		}
	
	
	*/
	//--------------------------------------------------------------------
	
	memberService.deleteMember(userPwd, session);
	session.removeAttribute("loginUser");
	session.setAttribute("alertMsg", "잘가시오");
	//mv.setViewName("redirect:/");
	
	return mv.setViewNameAndData("redirect:/", null);// 메소드를 호출하면서 -> 매개변수에 선언하지 않더라도 반한해줄 수 있게 되었다 ----강의 다시 듣기 1218 점심시간 이후 2번째 동영상
	
	}
	
	
	//------------------------------------------------------------
	
	// 아이디 중복 체크 AJAX
	@ResponseBody
	@GetMapping("idcheck")
	public String checkId(String userId) {
		
		//String result = memberService.checkId(userId);
		//log.info("아이디 중복이 발생했는가 :{}", result);
		
		return memberService.checkId(userId); // 한번에 돌려주기
	}
	
	
	
	
	
	//---------------------------------------------------------------------
	
	private ModelAndView setViewNameAndData(String viewName, String key, Object data) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(viewName);
		if(key != null && data != null) {
			mv.addObject(key, data);
		}
		return mv;
	}
	
	
	
	
}
