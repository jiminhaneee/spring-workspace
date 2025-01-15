package com.kh.od.api.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value="horse", produces="application/json; charset=UTF-8")
public class HorseController {
	
	@GetMapping
	public ResponseEntity<String> youCanCenter(@RequestParam(name="pageNo")int pageNo) throws URISyntaxException {
		
		String url = "https://apis.data.go.kr/B551015/API304/ucan_info";
			   url += "?serviceKey=YzGqdkcA4isWdSx3k0R7ELhNp2FSOuM3jS1Bpas5CX6O8tIWS0gtoG9dNFZ4l66x8r04C%2B2ybs0riAxJT7vxxA%3D%3D";
			   url += "&pageNo=" + pageNo;
			   url += "&numOfRows=3";
			   
		RestTemplate restTemplate = new RestTemplate();
		URI uri = new URI(url);
		String response = restTemplate.getForObject(uri, String.class);
		return ResponseEntity.ok(response);
		
		
		
	}

}
