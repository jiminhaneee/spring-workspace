package com.kh.hyper.board.model.service;


import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kh.hyper.board.model.vo.Board;
import com.kh.hyper.board.model.vo.Reply;

public interface BoardService {
	
	
	Map<String, Object> selectBoardList(int currentPage);
	
	void insertBoard(Board board, MultipartFile upfile);
	
	Map<String, Object> selectById(Long boardNo);
	
	Board updateBoard(Board board,  MultipartFile upfile);
	
	void deleteBoard(Long boardNo, String changeName);
	
	// 댓글
	// 나중에 서비스로 어떻게 돌려줄것인가 부터 생각
	// insert니깐 int로 돌려주자
	int insertReply(Reply reply);
	
	// 댓글조회 -> 특정 게시물의 댓글을 조회하는 것이므로 boardNo를 넘김
	List<Reply> selectReplyList(Long  boardNo);
	
	

}
