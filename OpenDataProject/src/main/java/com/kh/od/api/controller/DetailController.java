package com.kh.od.api.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
public class DetailController {
	
	@GetMapping("detail/{pk}")
	public String goDetail(@PathVariable(name="pk")int pk, Model model) throws URISyntaxException {
		
		String requestUrl = "http://apis.data.go.kr/6260000/FoodService/getFoodKr";
		   requestUrl += "?serviceKey=YzGqdkcA4isWdSx3k0R7ELhNp2FSOuM3jS1Bpas5CX6O8tIWS0gtoG9dNFZ4l66x8r04C%2B2ybs0riAxJT7vxxA%3D%3D";
		   requestUrl += "&numOfRows=10";
		   requestUrl += "&pageNo"+ 1;
		   requestUrl += "&resultType=json";
		   requestUrl += "&UC_SEQ="+pk;
		   
		URI uri = new URI(requestUrl);
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(uri, String.class);
		
		// GSON 라이브러리
		// JsonObject, JsonArray
		
		JsonObject jsonObj = JsonParser.parseString(response).getAsJsonObject();
		//System.out.println(jsonObj); // 문자열같아보이지만 문자열이 아니라 타입이 jsonObject이다 =>{"getFoodKr":{"header":{"code":"00","message":"NORMAL_CODE"},"item":[{"UC_SEQ":70,"MAIN_TITLE":"만드리곤드레밥","GUGUN_NM":"강서구","LAT":35.177387,"LNG":128.95245,"PLACE":"만드리곤드레밥","TITLE":"만드리곤드레밥","SUBTITLE":"","ADDR1":"강서구 공항앞길 85번길 13","ADDR2":"","CNTCT_TEL":"051-941-3669","HOMEPAGE_URL":"","USAGE_DAY_WEEK_AND_TIME":"10:00-20:00\n(19:50 라스트오더)","RPRSNTV_MENU":"돌솥곤드레정식, 단호박오리훈제","MAIN_IMG_NORMAL":"https://www.visitbusan.net/uploadImgs/files/cntnts/20191209162810545_ttiel","MAIN_IMG_THUMB":"https://www.visitbusan.net/uploadImgs/files/cntnts/20191209162810545_thumbL","ITEMCNTNTS":"곤드레밥에는 일반적으로 건조 곤드레나물이 사용되는데,\n이곳은 생 곤드레나물을 사용하여 돌솥밥을 만든다.\n된장찌개와 함께 열 가지가 넘는 반찬이 제공되는 돌솥곤드레정식이 인기있다\n"}],"numOfRows":10,"pageNo":1,"totalCount":1}}

		
		JsonObject getFood = jsonObj.getAsJsonObject("getFoodKr"); // jsonObj의 getFoodKr에 접근하여 JsonObject형태의 getFood에 담는다 => 안의 객체만 쏙 뽑아내는 과정
		//System.out.println(getFood);
		
		JsonArray itemArr = getFood.getAsJsonArray("item");//getFood안에 있는 item만 뽑아내기 + 배열의 형태로 뽑아냄 -> 우리는 이 배열의 요소들이 필요한것
		//System.out.println(itemArr);
		
		//객체타입의 오브젝트로 뽑아내기
		JsonObject item = itemArr.get(0).getAsJsonObject(); // 배열안의 객체들만 뽑아내기
		//System.out.println(item);
		
		//객체들을 변수로 뽑아내기
		String lat = item.get("LAT").getAsString();//뽑고싶은 속성명을("")에 적어주기 + 뽑을때 스트링형태로 뽑고싶으므로.getAsString() //위도
		//System.out.println(lat);
		String lng = item.get("LNG").getAsString();//경도
		String day = item.get("USAGE_DAY_WEEK_AND_TIME").getAsString();
		String img = item.get("MAIN_IMG_NORMAL").getAsString();
		String desc = item.get("ITEMCNTNTS").getAsString();
		String title = item.get("TITLE").getAsString();
		String address = item.get("ADDR1").getAsString();
		
		model.addAttribute("lat",lat);
		model.addAttribute("lng",lng);
		model.addAttribute("day",day);
		model.addAttribute("img",img);
		model.addAttribute("desc",desc);
		model.addAttribute("title",title);
		model.addAttribute("address",address);
		
		return "busan/detail";
	}

}
