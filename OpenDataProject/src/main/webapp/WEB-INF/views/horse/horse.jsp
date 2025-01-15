<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>

	<h1>도박을 끊읍시다.</h1>
	
	<button id="requestBtn" class="btn btn-lg">지금 바로 상담하러가기</button>
	
	<script>
		$(function(){
			
			let page = 1;
			
			$("#requestBtn").click(function(){
				
				$.ajax({
					url : 'horse',
					data : {
						pageNo : page
					},
					success : result =>{
						console.log(result);
						const items = result.response.body.items.item;
						console.log(items);
						const str = items.map(e=> `
												<tr>
													<td>\${e.stDate}</td>
													<td>\${e.edDate}</td>
													<td>\${e.fltTitle}</td>
													<td>\${e.fltPositionAdd}</td>
												</tr>`).join('');
						const resultBody = document.getElementById('result-body');
						resultBody.innerHTML += str;
						page += 1;
					}
				});
				
			});
			
		});
		
	
	</script>
	
	<!-- 
		stDate : 상담일정 시작일
		edDate : 상담일정 종료일
		fltTitle : 유캔 센터명
		fltPositionAdd : 유캔 센터 주소
	 -->
	
	<table class="table table-hover">
	
		<thead>
			<tr>
				<th>상담 일정 시작일</th>
				<th>상담 일정 종료일</th>
				<th>유캔 센터 명</th>
				<th>유캔 센터 주소</th>
			</tr>
		
		</thead>
		
		<tbody id="result-body">
		
		</tbody>
	
	</table>

</body>
</html>