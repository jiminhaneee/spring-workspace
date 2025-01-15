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
		광고 하루 안보기 계획
		
		if(쿠키가 없으면){
			광고 띄우기 코드
		}
	 --%>
	 
	 <c:if test="${ empty cookie.cookie }">
		 <script>
		 	open('http://www.naver.com', '나 광고', 'width=400, height=600');
		 </script>
	</c:if>
	
</body>
</html>