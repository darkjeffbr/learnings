package com.darkjeff.main.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestOperationsExtensionsKt;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@RestController
@RequestMapping("/api/main")
public class MainServiceApplication {
	
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss.SSSSSS");

	private RestTemplate restTemplate;

	@Value("${secondary.service.call:false}")
	private boolean secondaryServiceCall;

	@Value("${secondary.service.url:}")
	private String secondaryServiceUrl;

	MainServiceApplication(){
		this.restTemplate = new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(MainServiceApplication.class, args);

	}

	@GetMapping
	public String getMainInfo() {
		return getTimeInfo() + "   MAIN SERVICE [ " + getHostInfo() + " ]" + getSecondaryServiceInfo();
	}

	private String getSecondaryServiceInfo() {
		if(secondaryServiceCall){
			try {
				String url = secondaryServiceUrl + "/api/secondary";
				return " - " + restTemplate.getForObject(url, String.class);
			} catch (RestClientResponseException e){
				return " - ERROR CALLING SECONDARY SERVICE [" + e.getRawStatusCode() +"]";
			}
		}
		return "";
	}

	private String getHostInfo() {
		try {
			return "IP : " + InetAddress.getLocalHost().getHostAddress() + " - Hostname : " + InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			return "unknown host info";
		}
	}
	
	private String getTimeInfo(){
		return simpleDateFormat.format(new Date());
	}

}
