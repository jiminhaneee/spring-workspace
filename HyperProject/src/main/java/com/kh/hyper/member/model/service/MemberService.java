package com.kh.hyper.member.model.service;

import javax.servlet.http.HttpSession;

import com.kh.hyper.member.model.vo.Member;

public interface MemberService {
	
	
	// 로그인
	Member login(Member member);
	
	/*
	// 회원가입
	int join(Member member);
	
	// 회원 정보 수정
	int updateMember(Member member);
	
	// 회원 탈퇴
	//int deleteMember(Member member); - 버전1
	int deleteMember(String userPwd, HttpSession session);
	*/
	// ------------- 1절
	
	void join(Member member);
	
	void updateMember(Member member);
	
	void deleteMember(String userPwd, HttpSession session);
	
	// 아이디 중복 체크 
	String checkId(String userId);
	
	// ------------- 2절
	
	// 이메일 인증 
	
	// ------------- 3절
	

}
