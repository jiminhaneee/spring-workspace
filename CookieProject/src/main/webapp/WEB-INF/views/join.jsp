<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%--
		계획 : saveId라는 name속성값을 가진 쿠키가 존재한다면
			  아이디 저장 체크박스를 체크한것으로 간주
			  saveId라는 name속성값을 가진 Cookie의 value속성값을
			  아이디 입력 input요소의 value속성값으로 넣어줄 것 + 체크박스도 체크해놓을 것 
	--%>

	<c:choose>
		<c:when test="${ not empty cookie.saveId }">
			ID : <input type="text" value="${ cookie.saveId.value }"/> <br>
			<input type="checkbox" checked> 아이디저장
		</c:when>
		<c:otherwise>
			ID : <input type="text">
			<input type="checkbox"> 아이디저장
		</c:otherwise>
	
	</c:choose>




</body>
</html>