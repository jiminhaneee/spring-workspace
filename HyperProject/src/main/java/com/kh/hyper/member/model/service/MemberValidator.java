package com.kh.hyper.member.model.service;

import org.springframework.stereotype.Component;

import com.kh.hyper.exception.TooLargeValueException;
import com.kh.hyper.exception.UserFoundException;
import com.kh.hyper.exception.UserIdNotFoundException;
import com.kh.hyper.member.model.dao.MemberMapper;
import com.kh.hyper.member.model.vo.Member;

import lombok.RequiredArgsConstructor;

//회원 검증에 대한 데이터를 관리하는 파일

@Component
@RequiredArgsConstructor
public class MemberValidator {
	
	private final MemberMapper mapper; // 아이디를 검증할려면 mapper에 다녀와야하므로 mapper를 필드로 생성해줌
	
	// 회원에 대해 검증을 할 때 이미 존재하는 회원인지/ 존재하지 않는 회원인지 / 아이디가 너무 긴지 등등을 확인할 수 있다 
	// 그런것들을 하나씩 메소드로 생성을 한다
	
	// 이미 존재하는 회원인지를 확인하는 메소드
	public void validateDuplicateMember(Member member) {
		Member existingMember = mapper.login(member);
		if(existingMember != null && !member.getUserId().equals(existingMember.getUserId())) {
			throw new UserFoundException("이미 존재하는 회원입니다.");
		}
	}
	
	// 아이디의 길이를 확인하는 메소드
	public void validateIdLength(Member member) {
		if(member.getUserId().length() > 30) {
			throw new TooLargeValueException("아이디가 너무 깁니다");
		}
	}
	
	public void validateJoinMember(Member member) {
		validateDuplicateMember(member);
		validateIdLength(member);
	}
	
	public Member validateMemberExists(Member member) {
		Member existingMember = mapper.login(member);
		if(existingMember != null) {
			return existingMember;
		}
		throw new UserIdNotFoundException("존재하지 않는 사용자입니다.");
	}

}
