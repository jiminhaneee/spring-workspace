package com.kh.hyper.member.model.vo;

import java.sql.Date;

import lombok.Data;


/*
 * Lombok(롬복)
 * 
 *  자동 코드 생성 라이브러리 
 *  
 *  Lombok 설치 방법
 *  1. 라이브러리 다운로드
 *  2. 다운로드 된 .jar 파일을 찾아서 설치(작업할 IDE체크)
 *  3. IDE 재실행 
 *  
 *  @Getter
 *  @Setter
 *  @ToString
 *  @NoArgsConstructor
 *  --------------------
 *  @Builder
 *  @AllArgsContructor
 *  --------------------
 *  @Data 다 만들어주지만 빼고싶은 것을 못빼는 단점이 있음 
 *  --------------------
 *  
 *  - Lombok 사용 시 주의사항
 *  
 *  - pName이라는 필드를 선언했을 경우 
 *  Lombok의 명명규칙 == setPName() / getPName(String pName)
 *  
 *  => getter메소드를 내부적으로 호출할 때 
 *  ${ pName } / #{ pName } == getpName() -> 외자식으로 쓰면 안됨 personName이렇게 풀로 써야함 
 */
/*
@NoArgsConstructor // -> 기본생성자 생성
@AllArgsConstructor 
@Getter // 모든 필드에 대한 getter 생성
@Setter 
@ToString
*/
@Data // 다 만들어주는 애노테이션
public class Member {
	
	private String userId;
	private String userPwd;
	private String userName;
	private String email;
	private String gender;
	private String age;
	private String phone;
	private String address;
	private Date enrollDate;
	private Date modifyDate;
	
}
