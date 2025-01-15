<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>안녕 나는 소켓 클라이언트 맛보기 페이지야</title>
</head>
<body>
	<h1>베이징덕은 왜 비쌈?</h1>
	
	<button onclick="connect();">소캣접속~</button>
	<button onclick="disconnect();">전화끊기</button>
	
	<hr>
	
	<input type="text" id="client-message">
	<button onclick="send();">전송</button>
	
	<script>
	
		var socket;	 // 반납해야하므로 밖으로 뺴끼
		
		function send(){
			const msg = document.getElementById('client-message').value;
			
			socket.send(msg);
		}
		
		
	
		function connect(){
			
			const uri = 'ws://localhost/socket/test';
			socket = new WebSocket(uri); // socket 자원반납하면 전화끊기됨
			
			socket.onopen = () => { //소캣이 연결되었을 때 호출되는 핸들러
				
				console.log('서버와 연결!');
				
			};
			
			socket.onclose = () => { // 연결이 종료되었을 때 호출되는 핸들러
				
				console.log('서버와의 연결 종료!');
				
			};
			
			socket.onerror = e => {
				console.log(e);
				console.log('서버와 연결과정에서 문제가 발생함!');
			};
			
			socket.onmessage = e => {
				console.log(e);
			};
	
		}
		
		function disconnect(){
			socket.close();
		}
	
	</script>
	

</body>
</html>