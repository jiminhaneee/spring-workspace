package com.kh.od.api.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value="parking", produces="application/json; charset=UTF-8")
public class ParkingController {
	
	@GetMapping
	public ResponseEntity<String> getParking(int page) throws URISyntaxException{

		String requestUrl = "http://apis.data.go.kr/3330000/HeaundaeParkingInfoService/getParkingLotList";
			   requestUrl += "?serviceKey=YzGqdkcA4isWdSx3k0R7ELhNp2FSOuM3jS1Bpas5CX6O8tIWS0gtoG9dNFZ4l66x8r04C%2B2ybs0riAxJT7vxxA%3D%3D";
			   requestUrl += "&numOfRows=10";
			   requestUrl += "&pageNo" + page;
			   requestUrl += "&resultType=json";
			   
			   System.out.println(requestUrl);
			   
			   URI uri = new URI(requestUrl);
			   RestTemplate restTemplate = new RestTemplate();
			   String response = restTemplate.getForObject(uri, String.class);
			   
			   System.out.println(response);
				
			   return ResponseEntity.ok(response);
	}
	

}
