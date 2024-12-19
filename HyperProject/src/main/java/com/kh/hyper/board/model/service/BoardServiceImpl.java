package com.kh.hyper.board.model.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.hyper.board.model.dao.BoardMapper;
import com.kh.hyper.board.model.vo.Board;
import com.kh.hyper.common.model.vo.PageInfo;
import com.kh.hyper.common.template.Pagination;
import com.kh.hyper.exception.BoardNoValueException;
import com.kh.hyper.exception.BoardNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service // 클래스 에노테이션 달아줘야함
@RequiredArgsConstructor
@Slf4j // log
public class BoardServiceImpl implements BoardService {
	
	private final BoardMapper mapper;
	private final ServletContext context; 
	
	// 얻어내고 싶은 것이 totalcount -> 밖으로 빼서 하나의 메소드로 생성 
	private int getTotalCount() {
		
		int totalCount = mapper.selectTotalCount();
		
		if(totalCount == 0) {
			throw new BoardNotFoundException("게시글 없어~");
		}
		
		return totalCount;
	}
	
	
	private PageInfo getPageInfo(int totalCount, int page) {
		return Pagination.getPageInfo(totalCount, page,5, 5);
	}
	
	private List<Board> getBoardList(PageInfo pi){
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		
		return mapper.selectBoardList(rowBounds);
	}
	
	private void validateBoard(Board board) { // 값이 비어있는지 아닌지 검사
		
		if( board == null ||
		   board.getBoardTitle() == null  ||  board.getBoardTitle().trim().isEmpty() || // -> 빈문자열이 있나 확인하는것 (있으면 안되니까)
		   board.getBoardContent() == null  || board.getBoardContent().trim().isEmpty() ||
		   board.getBoardWriter() == null || board.getBoardWriter().trim().isEmpty()) {
			
			throw new BoardNoValueException("부적절한 입력값");
		}
		
		// <> 스크립트 태그 제거해야함 + /n을 br로 바꿔야함
		// XSS공격 방지를 위한 처리 
		/*
		 *  < == &lt;
		 *  > == &gt;
		 *  \ == &quot;
		 *  & == &amp
		 *  
		 */
		
		String boardTitle = escapeHtml(board.getBoardTitle());
		String boardContent = escapeHtml(board.getBoardContent());
		
		board.setBoardTitle(convertNewlineToBr(boardTitle));
		board.setBoardContent(convertNewlineToBr(boardContent));
		
	}
	
	private String escapeHtml(String value) {
		return value.replaceAll("<", "&lt;")
					.replaceAll(">","&gt;");
	}
	
	private String convertNewlineToBr(String value) {
		return value.replaceAll("\n", "<br>");
	}
	
	
	private void handleFileUpload(Board board, MultipartFile upfile) {
		
			
			String fileName = upfile.getOriginalFilename(); // 원본파일명 
			
			String ext = fileName.substring(fileName.lastIndexOf(".")); // 이름만 잘라내기 
		
			String currentTime = new SimpleDateFormat("yyyMMddHHmmss").format(new Date());
			int randomNum = (int)Math.random()*90000 + 10000;
			String changeName = currentTime + randomNum + ext; // 새로운 파일명
			
			String savePath = context.getRealPath("/resources/upload_files/"); // 저장소
			
			
			// 새로운 파일명을 가지고 저장소에 저장하면 됨
			try {
				upfile.transferTo(new File(savePath + changeName));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			//-----------------------파일 업로드 완료--------------------------------
			
			
			//첨부파일이 존재했다 -> 업로드 + Board객체에 originName + changeName
			board.setOriginName(fileName);
			board.setChangeName("resources/upload_files" + changeName);
		
	}

	@Override
	public Map<String, Object> selectBoardList(int currentPage) {
		
		// 총 갯수 -> DB가서 조회
		// 요청 페이지 -> currentPage확인
		// 한페이지에 게시글 몇개 -> 5 (원하는 만큼 설정)
		// 페이징바 몇개 -> 5
		
		/*
		//DB다녀와야함
		int totalCount = mapper.selectTotalCount();
		
		if(totalCount == 0) {
			throw new BoardNotFoundException("게시글 없어~");
		}
		--> 이부부 밖으로 빼서 사용하자 -> 다른곳에서도 사용할 수 있게 
		*/
		
		int totalCount = getTotalCount(); // 밖으로 뺐으니깐 불러서 사용하기
		
		
		//log.info("게시글 개수 : {}" , totalCount);
		//log.info("요청페이지 : {}", currentPage);
		
		// PageInfo pi = Pagination.getPageInfo(totalCount, currentPage, 5, 5); 
		// -> 값들이 바뀔 수도 있기때문에 밖으로 빼서 사용하자
		PageInfo pi = getPageInfo(totalCount, currentPage);
		
		
		/*
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		
		List<Board> boards = mapper.selectBoardList(rowBounds);
		
		이것도 따로 빼서 사용
		*/
		List<Board> boards = getBoardList(pi);
		
		//log.info("게시글 목록 : {}", boards);
		
		
		
		// 모두 map에 담아서 돌려주기
		Map<String, Object> map = new HashMap();
		map.put("boards", boards);
		map.put("pageInfo", pi);
		
		return map;
	}

	@Override
	public void insertBoard(Board board, MultipartFile upfile) {
		
		validateBoard(board); //Board 유효성 검증
		
		//파일 유무 체크 / 업로드 
		// 코드 작성은 방어적으로 -> 빈문자열을 비교해주는 것이 좋음
		if(!!!("".equals(upfile.getOriginalFilename()))){
			/*
			String fileName = upfile.getOriginalFilename(); // 원본파일명 
			
			String ext = fileName.substring(fileName.lastIndexOf(".")); // 이름만 잘라내기 
		
			String currentTime = new SimpleDateFormat("yyyMMddHHmmss").format(new Date());
			int randomNum = (int)Math.random()*90000 + 10000;
			String changeName = currentTime + randomNum + ext; // 새로운 파일명
			
			String savePath = context.getRealPath("/resources/upload_files/"); // 저장소
			
			
			// 새로운 파일명을 가지고 저장소에 저장하면 됨
			try {
				upfile.transferTo(new File(savePath + changeName));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			//-----------------------파일 업로드 완료--------------------------------
			
			
			//첨부파일이 존재했다 -> 업로드 + Board객체에 originName + changeName
			board.setOriginName(fileName);
			board.setChangeName("resources/upload_files" + changeName);
			
			-> 따로 뺴기
			*/
			handleFileUpload(board, upfile);
		}
		
		// 첨부파일이 존재하지 않을 경우 :board == 제목, 내용, 작성자
		// 첨부파일이 존재할 경우 : board == 제목, 내용, 작성자, 원본명, 변경명
		
		
		/*
		if("".equals(board.getBoardTitle())|| "".equals(board.getBoardContent())  || "".equals( board.getBoardWriter())) {
			throw new BoardNoValueException("부적절한 입력값");
		} 
		-> 밖으로 빼고 맨 앞에서 유효성 검사를 진행
		*/
		
		mapper.insertBoard(board);
		
		
	}

	@Override
	public Board selectById(Long boardNo) {
		
		
		return null;
	}

	@Override
	public void updateBoard(Board board) {

	}

	@Override
	public void deleteBoard(Long boardNo) {

	}

}