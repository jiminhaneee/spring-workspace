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
	
	<style>
	
		#items{
			padding : 40px;
			width : 1000px;
			display : flex;
			margin : auto;
			gap : 15px;
			flex-wrap : wrap;
		}
	
	</style>

</head>
<body>

	<h1>지식인에 질문하세요~</h1>
	
	<input type="text" id="query">
	
	<button id="alsong-dalsong">궁금해요</button>
	
	<span id="result-count"></span>
	
	
	<div id="items">
	
	
	</div>
	
	
	
	<script>
	
		$('#alsong-dalsong').click(function(){
			$.ajax({
				url : 'kin',
				data : {
					query : $('#query').val()
				},
				success : result => {
					console.log(result);
					
					$('#result-count').html(
						`총 <label style="color : crimson;">\${result.total}</label>건의 결과가 있습니다.`
					);
					
					const item = result.items;
					
					const card = item.map(e => `
							
							<div style="width : 300px; height : 450px; padding:10px; display:inline-block">
								<h5>\${e.title}</h5>
								<br/>
								<p>\${e.description}</p>
								<a href="\${e.link}" target="_blank">보러가기~</a>
							</div>
							
					`).join('');
					
					$('#items').html(card);
				}
			});
		});
	
	</script>





</body>
</html>