package com.kh.hyper.member.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.hyper.member.model.vo.Member;

//DAO대신 Mapper사용

@Mapper
public interface MemberMapper {
	
	Member login(Member member);
	
	int join(Member member);
	
	// 반환받아야하는 타입을 적고 sql문의 id값과 동일하게 작성해주면 sql 알아서 처리해줌(반납, 찾아가기 다 )
	
	int updateMember(Member member);
	
	int deleteMember(Member member);
	
	int test();

	int checkId(String userId);

}
