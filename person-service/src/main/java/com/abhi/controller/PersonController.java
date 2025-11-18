package com.abhi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController {

	@GetMapping("/name/{id}")
	public String getPersonName(@PathVariable("id") String id) throws InterruptedException {
		Thread.sleep(4000);
		if(id.equalsIgnoreCase("USER_1")) {
			return "Abhishek Tiwari";
		}
		throw new RuntimeException();
	}
}
