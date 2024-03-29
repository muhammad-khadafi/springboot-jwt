package com.apap.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Map;

@RestController
@CrossOrigin()
public class HelloWorldController {

	@RequestMapping({ "/hello" })
	public String hello() {
		return "Hello World";
	}

	@RequestMapping({ "/hello2" })
	public String hello(@RequestHeader("Authorization") String token) {
		Map<String, String> decodedToken = decode(token);
		String result = "";
		if (decodedToken.get("ROLE").equals("Dosen")) {
			result = "Halo dosen, selamat mengajar";
		} else if (decodedToken.get("ROLE").equals("Mahasiswa")) {
			result = "Halo mahasiswa, selamat belajar";
		}
		return result;
	}

	private Map<String, String> decode(String token) {
		String[] chunks = token.split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String payload = new String(decoder.decode(chunks[1]));
		Gson gson = new Gson();
		Map<String, String> decodedToken = gson.fromJson(payload, new TypeToken<Map<String, String>>() {}.getType());
		return decodedToken;
	}

}
