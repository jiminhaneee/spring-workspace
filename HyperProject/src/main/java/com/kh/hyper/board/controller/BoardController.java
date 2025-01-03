package com.kh.hyper.board.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.hyper.board.model.service.BoardService;
import com.kh.hyper.board.model.vo.Board;
import com.kh.hyper.board.model.vo.Reply;
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
	
	@PostMapping("boards")				//첨부파일이 여러개인 경우 Multipart[] 배열로 받으면 됨!
	public ModelAndView save(Board board, MultipartFile upfile, HttpSession session) {
		
		//log.info("게시글 정보 :{}, 파일정보 : {}", board, upfile);
		
		// 첨부파일의 존재 유뮤 확인 -> null이냐 아니냐로 확인 불가 -> 객체가 무조건 생성되기 때문에!
		// => 차이점 : filename필드에 원본명이 존재하는가 ""빈문자열인가 
		
		// 전달된 파일이 존재할 경우 : 파일명 수정 후 업로드 
		boardService.insertBoard(board, upfile);
		session.setAttribute("alertMsg", "게시글 등록 성공");
		return mv.setViewNameAndData("redirect:boards", null);
	}
	
	@GetMapping("boards/{id}")
	public ModelAndView selectById(@PathVariable(name="id")Long id) { //@PathVariable-> 위의 {id}값을 가지고 올 때 사용
		//log.info("{}", id);
		
		Map<String, Object> responseData = boardService.selectById(id);
		return mv.setViewNameAndData("board/detail", responseData);
	}
	
	@PostMapping("boards/delete")
	public ModelAndView deleteBoard(Long boardNo, String changeName) {
		
		boardService.deleteBoard(boardNo, changeName);
		
		return mv.setViewNameAndData("redirect:/boards", null); 
		
	}
	
	@PostMapping("boards/update-form")
	public ModelAndView updateForm(Long boardNo) {
		Map<String, Object> reponseData = boardService.selectById(boardNo);
		return mv.setViewNameAndData("board/update", reponseData);
	}
	
	@PostMapping("boards/update")
	public ModelAndView update(Board board, MultipartFile upfile) {
		log.info("게시글 정보 :{}, 파일정보 : {}", board, upfile);
		//DB가서 업데이트해야하는데
		//Board -> boardTitle, boardContent, boardWriter, boardNo
		// 이 내게는 무조건 있음
		
		/*
		 * 첨부파일은?
		 * 
		 * 1. 기존 첨부파일 X, 새 첨부파일 X -> 그렇구나
		 * 2. 기존 첨부파일 O, 새 첨부파일 O -> originName : 쌔거, changeName : 쌔거
		 * 3. 기존 첨부파일 X, 새 첨부파일 O -> originName : 쌔거, changeName: 쌔거
		 * 4. 기존 첨부파일 O, 새 첨부파일 X -> originName : 기존 첨부파일 정보, changeName: 기존 첨부파일 정보
		 */
		
		boardService.updateBoard(board, upfile);
		
		return mv.setViewNameAndData("redirect:/boards", null);
	}
	
	//------------------댓글-----------------------
	
	/*
	 * < 암묵적인 약속 - 전송방식>
	 * 
	 * SELECT -> GET
	 * 
	 * INSERT -> POST
	 * 
	 * UPDATE -> PUT
	 * 
	 * DELETE -> DELETE
	 * 
	 * 
	 */
	/*
	@ResponseBody // 데이터가 돌아와야하므로 
	@PostMapping("reply")
	public int ajaxInsertReply(Reply reply) {
		return boardService.insertReply(reply);		
	}
	
	
	@ResponseBody
	@GetMapping( value = "reply", produces="application/json; charset=UTF-8") 
	public List<Reply> ajaxSelectReply(Long boardNo){
		return boardService.selectReplyList(boardNo);
	}
	
	//----여기까지 댓글 1절 / 2절은 이 두개를 따른 controller로 빼기 -> BoardApiController
	
	 */
	
	@GetMapping("map")
	public String mapForward() {
		return "common/map";
	}
	
}
