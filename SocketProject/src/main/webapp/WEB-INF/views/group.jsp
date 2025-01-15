<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채팅방</title>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    
    <style>
    
    	body{
			background-color : black;
			color:white;
    	}
    	
    	
    	.my-msg{
    		min-width : 200px;
    		min-height:40px;
    		height:auto;
    		width:300px;
    		padding : 20px;
    		margin-left:auto;
    		background-color:lightyellow;
    		color:black;
    		border-radius : 15px;
    	}
    	
    	.msg{
    		min-width : 200px;
    		min-height:40px;
    		height:auto;
    		width:300px;
    		padding:20px;
    		margin-right:auto;
    		background-color:lightpink;
    		color:black;
    		border-radius : 15px;
    	
    	}
    </style>
	
</head>
<body>
	<h1>채팅방에 오신것을 환영합니다.</h1>
	
	<button class="btn btn-sm btn-outline-primary" onclick="connect();">접속하기</button>
	<button class="btn btn-sm btn-outline-danger" onclick="disconnect();">접속종료</button>
	
	<hr>
	
	<div class="form-group" style="width:95%; height:200px; margin:auto;">
	<input type="text" id="client-message" class="form-control">
	<button class="btn btn-sm btn-info" style="width:100%" onclick="send();">전송</button>
	</div>
	
	<div style="border:1px solid rgba(0,0,0,0.1); width:1400px; height:600px; margin:auto; 
				background:lightgray; border-radius:20px; padding:30px;" id="wrap">
	</div>
	
	<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
	
	<script>
	
		var socket;	 // 반납해야하므로 밖으로 뺴끼
		var nickName;
		
		function send(){
			const msg = document.getElementById('client-message').value;
			
			socket.send(nickName + ":"+ msg);
			document.getElementById('client-message').value='';
		}
		
		
	
		function connect(){
			
			nickName = prompt('닉네임 작성해주세요');
			
			const uri = 'ws://localhost/socket/chat';
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
				console.log(e.data);
				const $div = $('<div></div>');
				$div.text(e.data);
				const nick = e.data.split(':')[0];
				console.log(nick);
				if(nick == nickName){
					$div.addClass('my-msg')
				}else{
					$div.addClass('msg');
				}
				$('#wrap').append($div);
			};
	
		}
		
		function disconnect(){
			socket.close();
		}
	
	</script>
	

</body>
</html>