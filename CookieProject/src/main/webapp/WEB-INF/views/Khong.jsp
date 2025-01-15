<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<script>
		const arr = ['아침', '점심', '저녁'];
		
		localStorage.setItem('arr', JSON.stringify(arr)); 
		// 네임벨류로 값을 저장해줘야함 + 배열을 통쨰로 넣을 수는 없음(배열이 아니라 통으로 문자열로 들어감)-> 배열을 문자열 형태로 바꿔서 JSON형태로 넣어줘야함
		// JSON.stringify() : JSON문자열로 변환하는 메소드
		
		console.log('-----------------------------------------');
		
		const lacalArr = JSON.parse(localStorage.getItem('arr')); 
		// 키값으로 값 뽑기 -> json형태의 문자열로 형태로 바꿔서 집어넣기때문에 그냥 getItem만 하면 json형태의 문자열로 나옴 -> 그래서 다시 또 바꿔서 빼줘야함
		// JSON.parse(): JSON문자열을 다시 배열로 파싱하는 메소드
		console.log(localArr);
		
		console.log('-----------------------------------------');
		
		localArr.push('내일'); // 새롭게 요소추가
		localStorage.setItem('arr',JSON.stringify(localArr)); //새로운 요소를 localStorage에 추가
		
		//삭제 : removeItem(키값);
		localStorage.removeItem('arr');
		
	</script>





</body>
</html>