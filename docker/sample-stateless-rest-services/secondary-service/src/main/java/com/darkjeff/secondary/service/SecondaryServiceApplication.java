package com.darkjeff.secondary.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@RestController
@RequestMapping("/api/secondary")
public class SecondaryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecondaryServiceApplication.class, args);
	}


	@GetMapping
	public String getSecondaryInfo() {
		return "SECONDARY SERVICE [ " + getHostInfo() + " ]";
	}

	private String getHostInfo() {
		try {
			return "IP : " + InetAddress.getLocalHost().getHostAddress() + " - Hostname : " + InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			return "unknown host info";
		}
	}

}
