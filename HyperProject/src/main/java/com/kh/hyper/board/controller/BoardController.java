package com.kh.hyper.board.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.hyper.board.model.service.BoardService;
import com.kh.hyper.board.model.vo.Board;
import com.kh.hyper.common.ModelAndViewUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor // 생성자 주입 
@Slf4j
public class BoardController {
	
	private final BoardService boardService;
	private final ModelAndViewUtil mv;
	
	// menubar.jsp에서 게시판 클릭 시 => boards ==>page == 1
	// 페이징바에서 눌렀다 => board?page = 요청 페이지 ==>page == 요청 페이지 
	
	@GetMapping("boards")
	public ModelAndView selectBoardList(@RequestParam(value="page", defaultValue="1")int page) {
		
		Map<String, Object> map = boardService.selectBoardList(page);
		
		return mv.setViewNameAndData("board/list", map);
	}
	
	@GetMapping("insertForm")
	public String insertForm() {
		
		return "board/insert_form";
	}
	
	@PostMapping("boards")
	public ModelAndView save(Board board, MultipartFile upfile, HttpSession session) {
		
		//log.info("게시글 정보 :{}, 파일정보 : {}", board, upfile);
		
		// 첨부파일의 존재 유뮤 확인 -> null이냐 아니냐로 확인 불가 -> 객체가 무조건 생성되기 때문에!
		// => 차이점 : filename필드에 원본명이 존재하는가 ""빈문자열인가 
		
		// 전달된 파일이 존재할 경우 : 파일명 수정 후 업로드 
		boardService.insertBoard(board, upfile);
		session.setAttribute("alertMsg", "게시글 등록 성공");
		return mv.setViewNameAndData("redirect:boards", null);
	}
	
	@GetMapping("boards/${id}")
	public ModelAndView selectById(@PathVariable(name="id")Long id) {
		log.info("{}", id);
		
		return mv.setViewNameAndData(null, null);
	}
	

}
