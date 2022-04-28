package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class SpringWithSpotifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWithSpotifyApplication.class, args);
		PageController.authorizationCodeUri_Sync();
	}
}
