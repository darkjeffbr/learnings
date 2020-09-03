package com.darkjeff.secondary.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@RestController
@RequestMapping("/")
public class SecondaryServiceApplication {
	
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss.SSSSSS");

	public static void main(String[] args) {
		SpringApplication.run(SecondaryServiceApplication.class, args);
	}

	@GetMapping("/health")
	public ResponseEntity<Void> health(){
		return ResponseEntity.ok().build();
	}

	@GetMapping("/api/secondary")
	public String getSecondaryInfo() {
		return getTimeInfo() + "   SECONDARY SERVICE [ " + getHostInfo() + " ]";
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
