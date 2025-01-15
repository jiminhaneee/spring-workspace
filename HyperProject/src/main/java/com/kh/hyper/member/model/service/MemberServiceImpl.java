package com.kh.hyper.member.model.service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.kh.hyper.exception.ComparePasswordException;
import com.kh.hyper.member.model.dao.MemberMapper;
import com.kh.hyper.member.model.vo.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@Component == Bean으로 등록하겠다
@EnableTransactionManagement // 등록한 트랜잭션 매니지먼트를 사용할 수 있게됨
@Service // Component보다 더 구체적으로 ServiceBean으로 등록하겠다
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

	//private final MemberDao memberDao;	
	//private final SqlSessionTemplate sqlSession; // 기존의 myBatis의 sqlSession 대체
	// 필드에 final을 붙이고 위에 애노테이션을 달면 알아서 생성자를 추가해주므로
	// 필드가 추가된다고 해서 생성자를 추가할 필요가 없어짐 
	
	private final MemberMapper mapper;
	//private final BCryptPasswordEncoder passwordEncoder;
	private final PasswordEncryptor passwordEncoder;
	private final MemberValidator validator;
	
	/*
	@Autowired
	public MemberServiceImpl(MemberDao memberDao, SqlSessionTemplate sqlSession) {
		this.memberDao = memberDao;
		this.sqlSession = sqlSession;
	}
	*/
	//스프링이 관리하는 객체 
	@Override
	public Member login(Member member) {
		
		/*
		 * SqlSession sqlSession = getSqlSession(); -> 템블릿으로 대체가 되고 spring에 bean으로 등록이 됨 
		 * 
		 * Member member = new MemberDao().login(sqlSession, member);
		 * 
		 * sqlSession.close();
		 * 
		 * return member;
		 */
		
		//Member loginUser = memberDao.login(sqlSession, member);
		// 스프링이 사용이 끝나면 자동으로 객체를 반납해주기 때문에 close()를 호출하지 않음
		
		//return memberDao.login(sqlSession, member); -> dao사용했을 때 
		//return mapper.login(member); //dao사라지고 mapper로 간단하게 사용했을 때
		
		//--------------------------------------------------------------------
		/*
		//코드 간단하게 바꾸기 - 1번
		
		// 1. 아이디가 존재하지 않는 경우
		// 2. 비밀번호가 잘못된 경우
		// 3. 둘 다 통과해서 정상적으로 조회가 된 경우 
		
		//컨트롤러에게 사용자의 정보가 돌아가는 경우 -> 조회가 정상적으로 된경우(아이디, 비밀번호 둘다)
		// 1. 그럼 조회부터 해보자
		Member loginMember = mapper.login(member);
		
		// 2. 아이디가 존재하지 않는 경우
		// -> 우리에겐 예외상황이라고 볼 수 있음 = 로그인을 실패하는 상황-> 사용자에게 없는 아이디 입력했다고 알려줘야함 
		// 예외상황이 발생한것이므로 예외상황을 확인해야함 -> runtimeException (실행중에 발생하는 예외 - 어떤 아이디를 사용자가 입력할지 모르므로)
		if(loginMember == null) { // 없는 아이디를 조회한 경우 null이 반환될 것이다 그럼 runtime예외가 발생할것
			throw new UserIdNotFoundException("존재하지 않는 아이디로 접속 요청");  //->throw사용해서 예외를 발생시킴
		}// -> exceptionhandlercontroller로
		
		
		
		// 3. 비밀번호가 일치하지 않을 경우
		if(!!!passwordEncoder.matches(member.getUserPwd(), loginMember.getUserPwd())) {
			throw new ComparePasswordException("비밀번호가 일치하지 않습니다.");
		}else {
			return loginMember; //여기까지 왔음 성공했으므로 사용자의 정보 controller에게 돌려주기 
		}
		*/
		
		//-------------------------------------------------------------------------
		
		/*
		Member loginMember = validator.validateMemberExists(member);
		log.info(member.getUserPwd());
		log.info(loginMember.getUserPwd());
		boolean flag = passwordEncoder.matches(member.getUserPwd(), loginMember.getUserPwd());
		log.info("{}", flag);
		if(!flag) {
			throw new ComparePasswordException("비밀번호가 일치하지 않습니다.");
		}else {
			return loginMember;
		}
		*/
		
		Member loginMember = validator.validateMemberExists(member);
		
		if(!!!passwordEncoder.matches(member.getUserPwd(), loginMember.getUserPwd())) {
			throw new ComparePasswordException("비밀번호가 일치하지 않습니다.");
		} else {
			return loginMember;
		}
	}

	@Override
	@Transactional // 두 트랜잭션을 묶어서 사용하겠다!
	public void join(Member member) {
		
		// 커넥션 만들기
		// DAO호출
		// 트랜잭션 처리
		// 자원반납
		// 결과반환
		
		//int result = memberDao.join(sqlSession, member);
		
		//트랜잭션처리 -> sqlSessionTemplate가 트랜잭션 자동으로 commit해줌 -> 그래서 return에 한번에 작성가능
		
		//return memberDao.join(sqlSession, member);
		
		//mapper.join(member);//사용자가 입력한 값으로 회원가입이 되는 메소드 //이 부분은 정상적으로 테이블에 들어감-오토커밋이 일어나기때문
		//return mapper.test(); // unique조건 위배로 오류발생 테이블에 들어가지 못함 
		// 두 구문을 하나의 트랜잭션으로 묶어줘야함(두개 중 하나만 commit되면 안됨) -> 트랜잭션을 관리할 수 있는 클래스를 bean으로 등록해야함 root-context에서 진행
		// 트랜잭션을 하나로 묶고나면 윗 코드에 해당하는 데이터도 commit되지 않음 -> 묶었기때문에 하나라도 틀리면 rollback되고 모두 성공해야 commit이 된다 
		
		
		// 현 문제점: 코드에 예외처리를 안함 -> 오류가 생겼을때 회원가입 실패했습니다 창을 사용자엑 띄워줘야하는데 이렇게만 하면 500.404에러 화면이 나옴
		// 아래와 같이 예외처리를 해줘야 해결 그래야 예외가 발생을 해도 사용자는 모름 
		/*
		try {
			mapper.test();
			mapper.join(member);
			return 1;
		}catch(DuplicateKeyException e) {
			return 0;
		}
		*/
		//------------------------------------------------------------------
		/*
		// 코드 간단하게 바꾸기 2절
		
		// -> 암호화 작업을 여기서 한 후 join을 해줘야함 
		
		// 유호한 값인지 검증이 먼저 필요
		// case1. 사용자가 입력한 값이 userID컬럼에 존재하면 안됨
		// case2. 사용자가 입력한 아이디값이 30글자가 넘어가선 안됨
		// case3. 위 두 조건을 만족했다면 사용자가 입력한 비밀번호 값을 암호화해서 DB에 INSERT
		
		
		// 사용자가 입력한 id값이 table의 userid에 있으면 안됨 예외가 발생할것이기 때문 -> 그럼 db에가서 똑같은 값이 있는지 조회를 해와양한다
		Member userInfo = mapper.login(member);
		
		
		if(userInfo != null && member.getUserId().equals(userInfo.getUserId())) {
			throw new UserFoundException("이미존재하는 아이디입니다.");
		}
		
		if(member.getUserId().length() > 30 ) {
			throw new TooLargeValueException("값의 길이가 너무 깁니다.");
		}
		
		// case1.2를 만족했으면 값을 암호화해서 다시 db에 insert해줘야함
		member.setUserPwd(passwordEncoder.encode(member.getUserPwd()));
		mapper.join(member); // 이게 수행이 되고 controller로 갔다는 것은 성공을 했다는 의미! -> 그래서 return이 필요 없어 void로 선언해도 됨
		
		//return 0;
		
		
		*/
		
		//-----------------------------------------------------------
		// 코드 간단하게 바꾸기 3절
		//-> 회원 검증에 대한 클래스를 따로 빼서 관리 (MemberValidator 생성)
		
		Member userInfo = mapper.login(member);
		
		validator.validateJoinMember(member);
		
		member.setUserPwd(passwordEncoder.encode(member.getUserPwd()));
		
		mapper.join(member); // 이게 수행이 되고 controller로 갔다는 것은 성공을 했다는 의미! -> 그래서 return이 필요 없어 void로 선언해도 됨

	}

	
	@Override
	public void updateMember(Member member, HttpSession session) {
		/*
		// 새션도 같이 받아서 앞단에서 넘어온 userId와 session의 loginUSer키값의 userId필드값이 동일한지 확인
		//-> 사용자가 입력한 userId값이 DB컬럼에 존재하는지 
		// 사용자가 입력한 업데이트할 값들이 칼럼의 크기가 넘치지 않는지 확인 || 제약조건에 부합하는지 
		
		// 사용자가 입력한 userId값이 DB칼럼에 존재하는지 확인
		Member userInfo = mapper.login(member);
		
		if(userInfo == null) {
			throw new UserIdNotFoundException("존재하지 않는 사용자입니다.");
		}
		// 위 코드에서 update 유효성 검사하고 가능하면 아래 코드에서 실행!!
		mapper.updateMember(member);
		//return 0; // 잘 수행이 되면 controller로 돌아가고 잘 안되면 위에서 다 예외처리가 실행되고 controller로는 돌아가지 못한다 그래서 반환값이 무조건 필요하지 않음
		*/
		
		//--------------------------------------------------------------
		
		validator.validateMemberExists(member); //member만 넘기면 DB에 없으면 예외 발생하고 있으면 업데이트되고 실행됨
		
		mapper.updateMember(member);
		
		session.setAttribute("loginUser", mapper.login(member));
		
	}
	
	
	
	

	@Override
	public void deleteMember(String userPwd, HttpSession session) {
		
		/*
		Member loginUser = (Member)session.getAttribute("loginUser");
		loginUser.setUserPwd(userPwd);
		
		Member userInfo = mapper.login(loginUser);
		
		if(userInfo == null) {
			throw new UserIdNotFoundException("존재하지 않는 사용자입니다.");
		}
		
		if(!!! passwordEncoder.matches(loginUser.getUserPwd(), userInfo.getUserPwd())) {
			throw new ComparePasswordException("비밀번호가 일치하지 않습니다.");
		}
		 
		mapper.deleteMember(userInfo);
		//return 0;
		*/
		//-------------------------------------------------------------
		
		
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		loginUser.setUserPwd(userPwd);
		
		Member userInfo = validator.validateMemberExists(loginUser);
		
		
		if(passwordEncoder.matches(loginUser.getUserPwd(), userInfo.getUserPwd())) {
			throw new ComparePasswordException("비밀번호가 일치하지 않습니다.");
		}
		
		mapper.deleteMember(userInfo);
		
		
		
	}
	
	
	
	@Override
	public String checkId(String userId) {
		
		
		return mapper.checkId(userId)>0 ? "NNNNN" : "NNNNY";
	}
	
	
	
	
	

}
