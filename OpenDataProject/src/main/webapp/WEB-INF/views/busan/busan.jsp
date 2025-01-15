<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>부싼갈매기</title>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</head>
<body>

	<h1 align="center" style="margin:50px; font-size:48px; font-weigth:bold;">나의 세금을 들여 공무원들이 인증한 부산의 맛집들</h1>

	<!-- GUGUN_NM : 구군
		 MAIN_TITLE : 가게명
		 ADDR1 : 주소
		 RPRSNTV_MENU : 대표메뉴
		 MAIN_IMG_THUMB: 음식사진
	
	 -->
	<table class="table table-hover table-info">
	
		<thead class="table-success">
		
			<tr>
				<th>가게명</th>
				<th>구군</th>
				<th>주소</th>
				<th>대표메뉴</th>
				<th>사진</th>
				<th>상세보기</th>
			</tr>
		
		</thead>
		
		<tbody class="table-info">
		
		
		</tbody>
	
	</table>

	<hr>
	<div class="d-grid gap-2">
		<button style="width:100%;" class="btn btn-outline-info" id="more">더보기</button>
	</div>
	
	<hr>
	
	
	<script>
		$(function(){
			
			let pageNo = 1;
			
			function getBusan(){
				
				$.ajax({
					url : 'busan',
					data : {
						page : pageNo
					},
					success : result =>{
						console.log(result);
						
						const items = result.getFoodKr.item;
						
						const view = items.map(e =>`
								<tr>
									<td>\${e.MAIN_TITLE}</td>
									<td>\${e.GUGUN_NM}</td>
									<td>\${e.ADDR1}</td>
									<td>\${e.RPRSNTV_MENU}</td>
									<td>
										<img src="\${e.MAIN_IMG_THUMB}" width="200" height="160"/>
									</td>
									<td>
										<button class="btn btn-outline-warning" style="width:100%; height:100%" onclick="goDetail(\${e.UC_SEQ})">상세보기</button>
									</td>
								</tr>
						
						`).join('');
						
						$('tbody').append(view);
						pageNo += 1;
					}
					
				})
				
			};
			
			
			$('#more').click(getBusan); // 더보기 버튼 누르면 한페이지 더 생성
			
			getBusan();
		});
	
			function goDetail(pk){
				location.href="detail/"+pk;
			}
	</script>
	
	
	
	
</body>
</html>