<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style> 
        .content {
            background-color:rgb(247, 245, 245);
            width:80%;
            margin:auto;
        }
        .innerOuter {
            border:1px solid lightgray;
            width:80%;
            margin:auto;
            padding:5% 10%;
            background-color:white;
        }

        table * {margin:5px;}
        table {width:100%;}
    </style>
</head>
<body>
        
    <jsp:include page="../common/menubar.jsp" />

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 상세보기</h2>
            <br>
			
			<!--<button onclick="history.back();">안녕 나는 버튼이야</button> <!-- 아까 전에 보던걸 다시 보는거라(되돌아가는거라)조회가 증가하지 않음  -->
            <a class="btn btn-secondary" style="float:right;" href="/hyper/boards">목록으로</a>
            <br><br>

            <table id="contentArea" algin="center" class="table">
                <tr>
                    <th width="100">제목</th>
                    <td colspan="3">${ board.boardTitle }</td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>${ board.boardWriter }</td>
                    <th>작성일</th>
                    <td>${ board.createDate }</td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <!-- 첨부파일이 있는경우 없는경우 나눠서 -->
                    <c:choose>
                    <c:when test="${ empty board.originName} ">
                    <td colspan="3">
                        첨부파일이 존재하지 않습니다.
                    </td>
                    </c:when>
                 
  					<c:otherwise>
                    <td colspan="3">
                        <a href="${ board.changeName }" download="${ board.originName }">${ board.originName }</a>
                    </td>
                    </c:otherwise>
                    </c:choose>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3"></td>
                </tr>
                <tr>
                    <td colspan="4"><p style="height:150px;">${ board.boardContent }</p></td>
                </tr>
            </table>
            <br>

            <div align="center">
            
            
                <!-- 수정하기, 삭제하기 버튼은 이 글이 본인이 작성한 글일 경우에만 보여져야 함 -->
                <c:if test="${ sessionScope.loginUser.userId eq requestScope.board.boardWriter }">
                <a class="btn btn-primary" onclick = "postSubmit(1);" >수정하기</a>
                <a class="btn btn-danger" onclick = "postSubmit(2);">삭제하기</a>
                </c:if>
            </div>
            
            <script>
            	function postSubmit(num){
            		
            		if(num == 1){
            			$('#postForm').attr('action', '/hyper/boards/update-form').submit();
            		}else{
            			$('#postForm').attr('action', '/hyper/boards/delete').submit();
            		}
            		
            		
            	}
            </script>
            
            <form action ="" method="post" id="postForm">
            	<input type="hidden" name="boardNo" value="${ board.boardNo }" />
            	<input type="hidden" name="changeName" value="${ board.changeName }"/>
            	<input type="hidden" name="boardWriter" value="${ board.boardWriter }" />
            	<!-- <input type="hidden" name="userId" value="${ loginUser.userId }" />  -->
            </form>
   
            
            <!-- 
            	case 1 : 수정하기 누르면
            			 수정할 수 있는 입력 양식이 있어야함
            			 입력 양식에는 원본 게시글 정보들이 들어있어야함
            			 
            	case 2 : 삭제하기 누르면 
            			 Board테이블 가서 STATUS 컬럼 'N'으로 바꾸고
            			 혹시 첨부파일도 있었다면 같이 지워줌 
            			 
            	-> 1,2 모두 pk가 있어야한다 
             -->
            
            
            
            <br><br>
            <!-- 댓글 기능은 나중에 ajax 배우고 나서 구현할 예정! 우선은 화면구현만 해놓음 -->
            <table id="replyArea" class="table" align="center">
                <thead>
                    <tr>
                        <th colspan="2">
                            <textarea class="form-control" name="" id="content" cols="55" rows="2" style="resize:none; width:100%;"></textarea>
                        </th>
                        <th style="vertical-align:middle"><button class="btn btn-secondary">등록하기</button></th> 
                    </tr>
                    <tr>
                        <td colspan="3">댓글(<span id="rcount">3</span>)</td>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th>user02</th>
                        <td>ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ꿀잼</td>
                        <td>2023-03-12</td>
                    </tr>
                    <tr>
                        <th>user01</th>
                        <td>재밌어요</td>
                        <td>2023-03-11</td>
                    </tr>
                    <tr>
                        <th>admin</th>
                        <td>댓글입니다!!</td>
                        <td>2023-03-10</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <br><br>

    </div>
    
    <jsp:include page="../common/footer.jsp" />
    
</body>
</html>