package com.kh.hyper.common.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

// 응답용 객체
@AllArgsConstructor
@Data
@Builder
public class ResponseData {

	private String status;
	private String message;
	private Object data; //모두 다 담고싶은경우 타입 Object
	
	
}
