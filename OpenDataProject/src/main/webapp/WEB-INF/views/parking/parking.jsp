<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>해운대구 주차장 정보</title>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</head>
<body>

	<h1 align="center" style="margin:50px; font-size:40px; font-weight:bold;">해운대구 주차장 정보</h1>

	<!-- 
		parkingLotName : 주차장명
		address : 주소
		clsName : 주차장유형
		pNum : 주차면수
		clsName : 분류
		
		--상세--
		주차장 위치
		lat : 위도
		lng : 경도
		
		
		
	 -->
	 
	 <table class = "table table-hover table-secondary">
	 
	 
	 	<thead class="table-success">
	 	
	 		<tr>
	 			<th>주차장명</th>
	 			<th>주소</th>
	 			<th>주자창유형</th>
	 			<th>주차면수</th>
	 			<th>분류</th>
	 			<th>위치</th>
	 		
	 		</tr>
	 	
	 	</thead>
	 	
	 	<tbody class="table-light">
	 	
	 	</tbody>

	 </table>
	 
	 <hr>
	 
	 <div class="d-grid gap-2">
 		 <button style="width:100%;"class="btn btn-outline-success" type="button" id="more">더보기</button>
	 
	 </div>
	 
	 <hr>
	 
	   <div class="modal fade" id="mapModal">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">주차장 위치</h5>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    <div id="map" style="width:100%;height:400px;"></div>
                </div>
            </div>
        </div>
    </div>
	 
	 
	 <script>
	 
	 	$(function(){
	 		
	 		let pageNo = 1;
	 		
	 		function getParking(){
	 			
	 			$.ajax({
	 				
	 				url : 'parking',
	 				data : {
	 					page : pageNo
	 				},
	 				success : result => {
	 					
	 					console.log(result);
	 					
	 					const items = result.getParkingLotList.item;
	 					
	 					const view = items.map(e =>`
	 						<tr>
	 							<td>\${e.parkingLotName}</td>
	 							<td>\${e.address}</td>
	 							<td>\${e.clsName}</td>
	 							<td>\${e.pNum}</td>
	 							<td>\${e.clsName}</td>
	 							<td>
	 								<button type="button" class="btn btn-outline-primary view-location-btn" 
	 								data-toggle="modal" data-target="#mapModal" data-lat="${e.lat}" data-lng="${e.lng}" id="mapBtn">위치보기</button>
	 								
	 							</td>
	 						</tr>
	 						
	 					
	 					`).join('');
	 					
	 					$('tbody').append(view);
	 					pageNo += 1;
	 					
	 				}
	 				
	 			});	
	 			
	 		};
	 		
	 		$('#more').click(getParking);
	 		getParking();
	 		
	 		$('#mapModal').on('shown.bs.modal', function(e){
	 			
	 			var button = $(e.relatedTarget);
	 			var lat = button.data('lat');
	 			var lng = button.data('lng');
	 			
	 			var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
			    mapOption = { 
			        center: new kakao.maps.LatLng(lat,lng), // 지도의 중심좌표
			        level: 3 // 지도의 확대 레벨
			    };
			
				var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
				
				// 마커가 표시될 위치입니다 
				var markerPosition  = new kakao.maps.LatLng(lat,lng); 
				
				// 마커를 생성합니다
				var marker = new kakao.maps.Marker({
				    position: markerPosition
				});
				
				// 마커가 지도 위에 표시되도록 설정합니다
				marker.setMap(map);
				
				// 아래 코드는 지도 위의 마커를 제거하는 코드입니다
				// marker.setMap(null);   
	 			
	 		});
	 		
	 		$('#mapModal').on('hidden.bs.modal', function () {
	 	        $('#map').html(''); // 지도 영역 초기화
	 	    });
	 	});

	 
	 </script>
	 





</body>
</html>