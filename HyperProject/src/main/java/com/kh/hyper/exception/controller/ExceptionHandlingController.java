package com.kh.hyper.exception.controller;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.kh.hyper.exception.BoardNoValueException;
import com.kh.hyper.exception.BoardNotFoundException;
import com.kh.hyper.exception.ComparePasswordException;
import com.kh.hyper.exception.TooLargeValueException;
import com.kh.hyper.exception.UserFoundException;
import com.kh.hyper.exception.UserIdNotFoundException;

import lombok.extern.slf4j.Slf4j;

// 예외가 발생했을때 어떤 핸들러를 보낼지 작성하는 곳 이 예외가 발생하면 이거 보내줘~

@Slf4j
@ControllerAdvice
public class ExceptionHandlingController {
	
	private ModelAndView createErrorResponse(String errorMsg, Exception e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("errorMsg", errorMsg).setViewName("common/error_page");
		log.info("발생 예외 : {}", e.getMessage(), e);
		return mv;
	}
	// 중복된 코드를 메소드 하나로 분리해서 사용
	
	@ExceptionHandler(BoardNotFoundException.class)
	protected ModelAndView NoSuchBoardError(BoardNotFoundException e) {
		return createErrorResponse("게시글이 존재하지 않습니다.", e);
	}
	
	@ExceptionHandler(BoardNoValueException.class)
	protected ModelAndView noValueError(BoardNoValueException e) {
		return createErrorResponse("필수 입력사항을 모두 입력해주세요.", e);
	}
	
	//-----------------------Member---------------------------------------
	
	@ExceptionHandler(DuplicateKeyException.class)
	protected ModelAndView handelTransactionError(DuplicateKeyException e) {
		return createErrorResponse("잘못된 요청입니다", e);
	}
	
	/*
	@ExceptionHandler(DuplicateKeyException.class)
	protected ModelAndView handelTransactionError(DuplicateKeyException e) {
		ModelAndView mv = new ModelAndView(); 
		mv.addObject("errorMsg", "잘못된 요청입니다.").setViewName("common/error_page");
		return mv;
	}
	*/
	
	@ExceptionHandler(UserIdNotFoundException.class)
	protected ModelAndView NoSuchUserIdError(DuplicateKeyException e) {
		return createErrorResponse("아이디가 존재하지 않습니다.", e);
	}
	
	/*
	@ExceptionHandler(UserIdNotFoundException.class)
	protected ModelAndView NoSuchUserIdError(UserIdNotFoundException e) { //UserIdNotFoundException 이 예외가 발생했을 때 사용하는 핸들러 
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("errorMsg","아이디가 존재하지 않습니다.").setViewName("common/error_page");
		
		log.info("발생한 예외 : {}", e.getMessage(),e);
		
		return mv;
		
	}
	*/
	@ExceptionHandler(ComparePasswordException.class)
	protected ModelAndView NotMatchingPasswordError(ComparePasswordException e) { //ComparePasswordException 이 예외가 발생했을 떄 사용하는 핸들러
		return createErrorResponse("비밀번호가 일치하지 않습니다.", e);
	}
	
	
	/*
	@ExceptionHandler(ComparePasswordException.class)
	protected ModelAndView NotMatchingPasswordError(ComparePasswordException e) { //ComparePasswordException 이 예외가 발생했을 떄 사용하는 핸들러
		ModelAndView mv = new ModelAndView();
		mv.addObject("errorMsg", "비밀번호가 일치하지 않습니다.").setViewName("common/error_page");
		return mv;
	}
	*/
	
	@ExceptionHandler(UserFoundException.class)
	protected ModelAndView UserExitsError(UserFoundException e) {
		
		return createErrorResponse("이미 존재하는 아이디입니다.",e);
	}
	
	/*
	@ExceptionHandler(UserFoundException.class)
	protected ModelAndView UserExitsError(UserFoundException e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("errorMsg", "이미 존재하는 아이디입니다.").setViewName("common/error_page");
		return mv;
	}
	*/
	
	
	@ExceptionHandler(TooLargeValueException.class)
	protected ModelAndView TooLargeValueException(TooLargeValueException e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("errorMsg", "유효하지 않은 값을 입력하셨습니다.").setViewName("common/error_page");
		return mv;
	}
	

}
