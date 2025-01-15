package com.kh.od.api.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
//@RequestMapping(produces="application/json; UTF-8")//문자열이 아니라 json의 형태로 돌리고 싶으므로 produces를 적어주면 됨-> json으로 오면서 인코딩도 같이 해줌
@RequestMapping(produces="text/xml; charset=UTF-8") //xml형태로 보낼떄
public class AirController {
	
	@GetMapping("air")
	public void airPollution() throws IOException { //throws
		
		//System.out.println("테스트");
		
		//내가 Java언어로 만든 Client 프로그램으로 API서버로 요청 보내고 응답 받기!
		
		// String 자료형으로 URL선언하기 -> URL은 공공데이터에서 알려주는 요청 url보기(Call Back URL)
		
		String requestUrl = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty"; 
			   requestUrl += "?serviceKey=YzGqdkcA4isWdSx3k0R7ELhNp2FSOuM3jS1Bpas5CX6O8tIWS0gtoG9dNFZ4l66x8r04C%2B2ybs0riAxJT7vxxA%3D%3D";
			   requestUrl += "&sidoName=" + URLEncoder.encode("서울","UTF-8"); //<- 요청을 보낼 url만들기 / 한글이 들어가있으면 인코딩을 해줘야함
			   requestUrl += "&returnType=json"; // 문서를 보고 returnType뭐가 있는지 확인하고 작성 -> json은 일렬로 데이터를 반환함(json형태로)
			   
		// url만 쓰고 요청이 돌아오지 않음 뒤에 이어서 필수 입력값들을 적어줘야함(필수입력값들은 기술문서를 보면 됨)/위 방식은 한번에 안쓰고 잘라줌!
		
		// System.out.println(requestUrl); // url이 잘만들어졌는지 찍어보기
			   
		// 자바코드 가지고 요청을 보낼 것 
	    // Connection =>  **HttpURLConnection 객체를 활용해서 API서버로 요청 
	    // 1. java.net.URL 객체 생성=> 생성자 호출할때 requestUrl를 인자값으로 전달을 해줘야함 
		URL url = new URL(requestUrl);
		
		// 2. URL객체를 이용해서 HttpURLConection객체 생성
		HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection(); 
		//openConnection의 반환타입이 urlConnection이라 HttpURLConnection에 대입을 할 수가 없어 오류가 남 -> 강제형변환을 할 수 있는 조건 (부모자식관계이기때문에 강제형변환이 가능)
		
		// 3. 요청에 필요한 Header설정
		urlConnection.setRequestMethod("GET");
		
		// 응답받는 데이터랑 내가 만든 프로그램이랑 연결을 시켜줘야함 -> 요청에서 응답을 출력할려면 먼저 입력을 받아야하는데 입력을 받을려면 스트림이 필요 
		
		// 4. API서버와 스트림을 연결
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())); 
		// 입력을 받는것이기때문에 getInputStream()이 필요 + 받아올 데이터양이 많기때문에 한줄단위로 입력을 받아오는 보조스트림 BufferedReader 사용 
		// InputStream 1바이트 BufferReader은 2바이트 (문자열스트림과 바이트스트림을 붙일 수 없음(같이 사용불가)=> inputStream에 inputStramReader를 붙여줘야함))
		// Reader: 문자열 입력 스트림(문자단위로 데이터를 읽고 쓰는 스트림)
		// Input: 바이트 스트림 (바이트 단위로 데이터를 읽고 쓰는 스트림/ 파일/ 데이터 소캣등에서 데이터들을 전송할때 사용)
		
		/*
		System.out.println(br.readLine());//br.readLine()을 통해 응답 받아오기(응답이 돌아옴)/ 한줄씩 땡겨오는것(몇줄이 올지 모르므로 반복문 돌리기)
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		*/
		
		/*
		while(br.readLine() != null) {
			System.out.println(br.readLine());
		}
		*/
		// -> 이렇게 출력하면 퐁당퐁당 값이 나오게됨 while문에서도 readLine/ 안에서도 readLine이 있기때문에! 아래와 같이 반복문을 돌려서 responseXml에 받아와야함(요즘에는 xml보다는 json을 더 많이 사용)
		
		// xml형태로 받을때는 위에 returnType을 따로 지정할 필요가 없음 + 반복문을 통해 한줄씩 데이터를 출력해줘야함
		/*
		String responseXml = "";
		while((responseXml = br.readLine()) != null) {
			System.out.println(responseXml);
		}
		*/
		
		// json형태로 받을 때는 위에 url에 resultType을 json으로 지정해줘야한다
		// json으로 반환받을 때는 반복문을 사용할 필요가 없음 한줄로 다 나오기때문에
		
		System.out.println(br.readLine());
		
		br.close();
		urlConnection.disconnect(); // 연결 끊기
		
		
		
	}
	
	
	//json형태
	/*
	@GetMapping("search.air")
	public ResponseEntity<String> searchAir(String sidoName) throws UnsupportedEncodingException, URISyntaxException {
		
		String requestUrl = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty"; 
			   requestUrl += "?serviceKey=YzGqdkcA4isWdSx3k0R7ELhNp2FSOuM3jS1Bpas5CX6O8tIWS0gtoG9dNFZ4l66x8r04C%2B2ybs0riAxJT7vxxA%3D%3D";
		       requestUrl += "&sidoName=" + URLEncoder.encode( sidoName,"UTF-8"); // 사용자가 air.jsp에서 선택한 값이 들어갈 것 
		       requestUrl += "&returnType=json"; 
		       
		//url 객체 생성하는 방법 말고 다른 방법을 사용해보자!->RestTemplate
		RestTemplate restTemplate = new RestTemplate();
		URI uri = new URI(requestUrl);
		
		String response = restTemplate.getForObject(uri, String.class); //어떤 클래스타입으로 응답받고 싶은지 두번재에 적어줌-> 이 타입으로 반환해서 돌아옴
		
		return ResponseEntity.ok(response);
		
		
		
	}
	*/
	
	
	//xml
	@GetMapping("search.air")
	public ResponseEntity<String> searchAir(String sidoName) throws UnsupportedEncodingException, URISyntaxException {
		
		String requestUrl = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty"; 
			   requestUrl += "?serviceKey=YzGqdkcA4isWdSx3k0R7ELhNp2FSOuM3jS1Bpas5CX6O8tIWS0gtoG9dNFZ4l66x8r04C%2B2ybs0riAxJT7vxxA%3D%3D";
		       requestUrl += "&sidoName=" + URLEncoder.encode( sidoName,"UTF-8"); // 사용자가 air.jsp에서 선택한 값이 들어갈 것 
		       //requestUrl += "&returnType=json"; 
		       
		//url 객체 생성하는 방법 말고 다른 방법을 사용해보자!->RestTemplate
		RestTemplate restTemplate = new RestTemplate();
		URI uri = new URI(requestUrl);
		
		String response = restTemplate.getForObject(uri, String.class); //어떤 클래스타입으로 응답받고 싶은지 두번재에 적어줌-> 이 타입으로 반환해서 돌아옴
		
		return ResponseEntity.ok(response);
		
		
		
	}
	
	
	
	
	
	
	
	

}
