package com.kh.hyper.board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.hyper.board.model.service.BoardService;
import com.kh.hyper.board.model.vo.Reply;
import com.kh.hyper.common.model.vo.ResponseData;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("reply")
@RequiredArgsConstructor
public class BoardApiController {
	
	// 이 페이지는 ajax 전용 controller 
	// -> 이 안에 있는 핸들러는 데이터를 돌려줄 핸들러이기 때문에 모두 @ResponseBody 애노테이션이 달려있을 것  
	// ==>모두 이 애노테이션을 달아야한다면 @ResponseBody와 @Controller가 합쳐진 -> @RestController를 사용
	
	
	private final BoardService boardService;
	
	
	@PostMapping // 위에서 애노테이션으로 작성-> 매핑값이 더이상 붙어있지 않아도 됨 
	public ResponseEntity<ResponseData> ajaxInsertReply(Reply reply) {
		
		//ResponseEntity responseData;
		int result =  boardService.insertReply(reply);		
		ResponseData response = ResponseData.builder()
											.message("댓글 등록에 성공했습니다.")
											.status(HttpStatus.OK.toString())
											.data(result)
											.build();
		
		return new ResponseEntity<ResponseData>(response, HttpStatus.OK);
	}
	
	
	
	
	
	@GetMapping //(produces="application/json; charset=UTF-8") ->responseData로 돌리면 안써도 됨 자동으로 json
	public ResponseEntity<ResponseData> ajaxSelectReply(Long boardNo){
		
		List<Reply> replies = boardService.selectReplyList(boardNo);
		
		ResponseData response = ResponseData.builder()
											.message("댓글 조회에 성공했습니다.")
											.status(HttpStatus.OK.toString())
											.data(replies)
											.build();
		
		return new ResponseEntity <ResponseData> (response,HttpStatus.OK);
	}
	

}
