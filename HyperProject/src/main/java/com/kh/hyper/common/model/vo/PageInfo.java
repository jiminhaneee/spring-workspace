package com.kh.hyper.common.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PageInfo {
	
	
	private int listCount;
	private int currentPage;
	private int boardLimit;
	private int pageLimit;
	
	private int maxPage;
	private int startPage;
	private int endPage;
	// 위 세개는 계산을 해야하므로 따로 Pagination클래스 생성

}
