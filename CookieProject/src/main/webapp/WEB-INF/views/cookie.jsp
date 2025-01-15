<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>쿠키다~~</h1>
	
	<%--
		EL구문을 이용해서 쿠키접근
		
		${ cookie.쿠키명 }
	
	 --%>
	
	<div>
		쿠키 : ${ cookie.cookie }
	</div>
	
	<div>
		쿠키있나요?<br>
		쿠키쿠키있나요? : ${ not empty cookie.cookie } <br>
	</div>
	
	<div>
		쿠키 값 : ${ cookie.cookie.value }
	</div>
	
	
	<br><br>
</body>
</html>