package com.kh.hyper.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.web.multipart.MultipartFile;

import com.kh.hyper.board.model.vo.Board;
import com.kh.hyper.board.model.vo.Reply;

@Mapper
public interface BoardMapper {
	
	
	// 몇개?
	int selectTotalCount();
	
	// 목록 조회
	List<Board> selectBoardList(RowBounds rowBounds);
	
	//조회수 증가
	int increaseCount(Long boardNo); 
	
	// 상세 조회
	Board selectById(Long boardNo);
	
	// 작성
	void insertBoard(Board board);
	
	// 수정
	int updateBoard(Board board);
	
	// 삭제
	int deleteBoard(Long boardNo);

	// 댓글 작성
	int insertReply(Reply reply);
	
	// 댓글 목록 조회
	List<Reply> selectReplyList(Long boardNo);
	
	
	
	

}
