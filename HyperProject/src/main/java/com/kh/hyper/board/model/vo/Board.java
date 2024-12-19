package com.kh.hyper.board.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Builder
@ToString
public class Board {
	
	
	private Long boardNo; 
	// 래퍼클래스 Long
	//기본자료형 int는 null값을 가질 수 없음-> 오라클 db에는 빈칸이 들어갈 수 없어 컬럼의 값이 없는 경우 null값으로 들어가고 조회가 됨 -> 기본자료형은 이를 표현할 수 없기때문에 Long을 사용 
	//primarykey는 기본자료형보다 래퍼클래스 (integer냐 Long이냐 long이 담을 수 있는 수가 더 커서 Long을 대부분 사용)
	private String boardTitle;
	private String boardWriter;
	private String boardContent;
	private String originName;
	private String changeName;
	private int count;
	private String createDate;
	private String status;

}

	/* @Builder
	 * 
	 * 모든 필드에 값을 집어넣지 않고 몇개의 필드에만 값을 넣고 싶은경우 builder를 사용해서 값을 각 필드에 집어 넣어주면 어떤 필드에 어떤 값이 들어갔는지
	 * 한눈에 확인이 가능 / builder를 사용하지 않고 한번에 Board board = new build(1,'제목','글쓴이',null,null)이렇게되면
	 * 나중에 null의 컬럼이 어떤 건지 모르므로 builder를 사용해서 필요한 곳에만 값을 넣어주는 것이 좋다!
	 * 
	 * > vo에서 애노테이션 작성하고 controller부분에서 아래 코드 작성하면서 값 집어넣음
	 * 
	 * 	{ //vo클래스의 @builder -> 각 필드에 set을 할 수 있는 메소드들이 들어있음
		
		Board board = Board.builder()
							.boardTitle("안뇽").boardContent("반가워").build(); }
	 * 
	 * .builder()안에 setter메소드들이 들어있고 .build()로 닫아주면 해당 값들만 필드에 들어가게 된다. 
	 */
	 